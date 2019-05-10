package NeuronalesNetz;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

/**
 * Diese Klasse wird zum Trainieren von Neuronalen Netzwerken verwendet.
 * Es wird mittels Stochastic Gradient Decent versucht den Fehler eines NNs zu minimieren, sodass bei allen Trainingsdaten richtige Ergenbisse ausgegeben werden.
 */
public class NeuralTraining {
	
	private Network network;
	private TrainData train;
	private TrainData test;
	
	private double learningRate;
	private double momentum;

	/**
	 * Erzuegt ein neues NeuralTrain Objekt mit den spezifizierten Einstellungen.
	 * @param net Das Neuronale Netz, dass trainiert werden soll
	 * @param learningrate die einzelnen Gewichts änderungen werden mit diesem Wert multipliziert. Umso kleiner diese Zahl ist umso genauer wird trainiert, jedoch umso länger wird es dauern
	 * @param momentum
	 * @param train die Trainingsdaten an die das Netz antrainiert wird
     * @param test die Testdaten anhand denen das Netz validiert werden soll
     */

	public NeuralTraining(Network net, double learningrate, double momentum, TrainData train, TrainData test){
		setNetwork(net);
		setMomentum(momentum);
		setLearningRate(learningrate);
		this.train = train;

        this.test = test;
	}
	
	private void backpropagate(double[] error){
		for(int i = 0; i < network.getLayers()[network.getLayers().length-1].getSize(); i++){
			network.getLayer(network.getSize()-1).getNode(i).setSignalError(error[i]);
		}
		for(int layer = network.getSize()-2; layer > 0; layer--){
			for(int node = 0; node < network.getLayer(layer).getSize(); node++){
				double signalError = 0;
				for(int nodeInPrevLayer = 0; nodeInPrevLayer < network.getLayer(layer+1).getSize(); nodeInPrevLayer++){
					signalError = signalError + (network.getLayer(layer+1).getNode(nodeInPrevLayer).getSignalError() * network.getLayer(layer+1).getNode(nodeInPrevLayer).getWeight(node));
				}
				double output = network.getLayer(layer).getNode(node).getOutput();
				network.getLayer(layer).getNode(node).setSignalError(signalError * output * (1-output));
			}
		}
		
	}
	
	private void updateWeigths(double leariningRate, double momentum){
		for(int layer = 1; layer < network.getSize(); layer++){
			for(int node = 0; node < network.getLayer(layer).getSize(); node++){
				network.getLayer(layer).getNode(node).setBiasDiff(leariningRate * network.getLayer(layer).getNode(node).getSignalError()+network.getLayer(layer).getNode(node).getBiasDiff()*momentum);
				network.getLayer(layer).getNode(node).setBias(network.getLayer(layer).getNode(node).getBias() - network.getLayer(layer).getNode(node).getBiasDiff());
				for(int weight = 0; weight < network.getLayer(layer).getNode(node).getSize(); weight++){
					network.getLayer(layer).getNode(node).setWeightDiff(network.getLayer(layer).getNode(node).getSignalError() * network.getLayer(layer-1).getNode(weight).getOutput()*leariningRate + network.getLayer(layer).getNode(node).getWeightDiff(weight)*momentum, weight);
					network.getLayer(layer).getNode(node).setWeight(network.getLayer(layer).getNode(node).getWeight(weight) - network.getLayer(layer).getNode(node).getWeightDiff(weight), weight);
				}
			}
		}
		
	}

	/**
	 * Trainiert das Neuronale Netz für maxCycle Iterationen
	 * Eine Iteration berechent für alle Trainingsdaten die Ausgabe und passt nach Minibatch-vielen die Gewichte neu an
	 * @param maxCycle
	 * @param minibatch
	 * @param leariningRate
	 * @param momentum
     * @return
     */

	public Network train(long maxCycle,int minibatch, double leariningRate, double momentum){
        TrainData testdata = new TrainData("test");
		for(int cycle = 0; cycle < maxCycle; cycle ++){
            System.out.println("Training cycle: "+cycle+" starting...");
            train.shuffle();
			double[] error = new double[train.getLabel()[0].length];
			for(int sample = 0; sample < train.getLength(); sample++){

				double[] out = network.feedForward(train.getData(sample));
                add(error, crossEntropyErrorSignalDiff(out, train.getLabel(sample)));

                if(sample%minibatch ==minibatch-1){
					error = divide(error, minibatch);
                    if(sample%1000 == 999){
                      //System.out.println("1000. MiniBatch Error: " + Arrays.toString(error));
                    }
                    backpropagate(error);
					updateWeigths(leariningRate, momentum);
					error = new double[error.length];
				}
			}


            System.out.println("Error on test set:\t"+ Arrays.toString(Arrays.stream(IntStream.range(0, testdata.getLength())
                    .mapToObj(i -> minus(network.feedForward(testdata.getData(i)), testdata.getLabel(i)))
                    .reduce(new double[testdata.getLabel(0).length], (a, b) -> {
                        double[] acc = new double[testdata.getLabel(0).length];
                        for (int i = 0; i < acc.length; i++) {
                            acc[i] = a[i] + b[i];
                        }
                        return acc;
                    })).mapToObj(d -> d / testdata.getLength()).toArray(Double[]::new)));
		}
		
		return null;
	}

	double[] minus(double[] a, double[] b){
	    double[]c = new double[a.length];
	    for(int i = 0; i< a.length; i++){
	        c[i] = a[i] - b[i];
	        c[i] = c[i] * c[i];
        }
	    return c;
    }

	/**
	 * Brechnet den Squared Error des Neuronalen Netzes für einen Input
	 * @param output
	 * @param desiredOutput
     * @return
     */
	public double squaredError(double[] output, double[] desiredOutput){
		double nodeError = 0;
		for(int j = 0; j < output.length; j ++){
			nodeError += Math.pow((Math.abs(output[j] - desiredOutput[j])), 2);
		}
		return nodeError;
	}

	/**
	 * Berechent den MSE für alle Inputs im TrainingsSet
	 * @return
     */
	public double meanSquaredErrorOverAll(){
		double diffSum = 0;
		for(int i = 0; i < train.getLength(); i++){
			double[] output = network.feedForward(train.getData(i));
			double nodeError = 0;
			for(int j = 0; j < output.length; j ++){
				nodeError += Math.pow((Math.abs(output[j] - train.getLabel(i)[j])), 2);
			}
			diffSum += nodeError;
		}
		return diffSum / train.getLength();
	}

	/**
	 * Brechent den Cross-Entropy-Error für einen Input
	 * @param output
	 * @param desiredOutput
     * @return
     */
	public double[] crossEntropyError(double[] output, double[] desiredOutput){
		double [] error = new double[output.length];
		for(int i = 0; i < output.length; i++){
			error[i] = -1*(desiredOutput[i]*Math.log(output[i])+(1-desiredOutput[i])*Math.log(output[i]));
		}
		return error;
	}


	private double[] crossEntropyErrorSignalDiff(double[] output, double[] desiredOutput){
		return sub(output, desiredOutput);
	}
	
	private double[] sub(double a[], double b[]){
		double[] sub = a.clone();
		for(int i = 0; i < a.length; i++){
			sub[i] -= b[i];
		}
		return sub;
	}
		
	private double[] add(double a[], double b[]){
		for(int i = 0; i < a.length; i++){
			a[i] += b[i];
		}
		return a;
	}
	
	private double[] divide(double a[], int d){
		for(int i = 0; i < a.length; i++){
			a[i] /= d;
		}
		return a;
	}


	/**
	 * Exprortiert das Neuronale Netz in eine Datei, die von dier Klasse NeuralNet eingelesen werden kann
	 * @return
     */
	public double[][][] exportWeights(){
        double[][][] network;
        network = new double[this.network.getSize()][][];
        for(int i= 0; i < network.length; i++){
            network[i] = new double[this.network.getLayer(i).getSize()][];
            for(int j = 0; j < network[i].length; j++){
                network[i][j] = new double[this.network.getLayer(i).getNode(j).getWeights().length + 1];
                System.arraycopy( this.network.getLayer(i).getNode(j).getWeights(), 0, network[i][j],1, this.network.getLayer(i).getNode(j).getWeights().length);
                network[i][j][0] = this.network.getLayer(i).getNode(j).getBias();
            }
        }
        try {
            FileOutputStream fileOut = new FileOutputStream("test.txt");
            ObjectOutputStream outNetwork = new ObjectOutputStream(fileOut);
            outNetwork.writeObject(network);

        } catch ( IOException k) {
            System.out.println("error writing file");
        }
        return network;
    }
	

	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	public Network getNetwork() {
		return network;
	}
	public void setNetwork(Network network) {
		this.network = network;
	}
	public double getLearningRate() {
		return learningRate;
	}
	public void setLearningRate(double learningRate) {
		this.learningRate = learningRate;
	}
	public double getMomentum() {
		return momentum;
	}
	public void setMomentum(double momentum) {
		this.momentum = momentum;
	}
	public TrainData getTrain() {
		return train;
	}
	public void setTrain(TrainData train) {
		this.train = train;
	}
}
