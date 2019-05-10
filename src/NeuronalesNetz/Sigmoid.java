package NeuronalesNetz;
import java.util.Random;

/**
 * Represetiert ein Sigmoid Neuron. Ist im Grundsatz eine Abstraktion der Funktion:
 *
 *  f(x1, ... ,xn) = sum(x1*w1, ... , xn*wn)
 *  f2 = 1/(1-(e^(-f1)))
 *
 *  Jedes Sigmoid Neuron hat eine Anzahl von Eingängen n und eine glechen Anzahl an sogenannten Gewichte.
 *  Gewichte werden zuerst zufällig generiert und dann mittels Stochastic Gradient Decent an die Trainingsdaten angepasst.
 */

public class Sigmoid {
	private double[] weights;
	private double[] weightDiff;
	private double bias;
	private double biasDiff;
	private int size;
	private double output;
	private double signalError;
	private boolean isInputNode;

    /**
     * Ezeugt ein Sigmoid Neuron mit <code>size</code> Eingängen und Gewichte, die zufällig gewählt sind.
     * @param size
     */

	public Sigmoid(int size){
		weights = new double[size];
		weightDiff = new double[size];
		this.size = size;
		isInputNode = size == 0;
		initialize();
	}

    /**
     * Im fall das ein Neuron in einem Input Layer steht sollen die Daten nicht verändert werden.
     * Diese Methode schreibt nur den Eingang in den Ausgang und berechnet sonst nichts.
     * @param input
     * @return
     */

	public double feedForward(double input){
		output = input;
		return output;
	}


    /**
     * Berechet die obenn erwähnte Funktion.
     * @param input Eingangsvektor der Funktion
     * @return Ergebnis der Funktion
     */
	public double feedForward (double[] input){
		double sum = 0;
		for(int i = 0 ; i < weights.length; i++){
			sum += weights[i]*input[i];
		}
		sum+=bias;
		output = 1/(1+Math.exp(-sum));
		return output;
	}
	
	private void initialize(){
		if(size != 0){
			Random r = new Random();
			for(int i = 0; i < weights.length; i++){
				weights[i] = r.nextGaussian()*1/Math.sqrt(size);
			}
			bias = r.nextGaussian();
		}
	}
			
	
	public String toString(){
		String s= "weights: ";
		for(double weight: weights){
			s += weight +", ";
		}
		s+= "\nbias: "+bias;
		s+= "\noutput: "+output;
		return s;
	}
	
	public double getWeight(int index) {
		return weights[index];
	}

	public void setWeight(double weight, int index) {
		this.weights[index] = weight;
	}

	
	public double[] getWeights() {
		return weights;
	}

	public void setWeights(double[] weights) {
		this.weights = weights;
	}

	public double getBias() {
		return bias;
	}

	public void setBias(double bias) {
		this.bias = bias;
	}

	public double getOutput() {
		return output;
	}

	public void setOutput(double output) {
		this.output = output;
	}

	public double getSignalError() {
		return signalError;
	}

	public void setSignalError(double signalError) {
		this.signalError = signalError;
	}

	public double getWeightDiff(int index) {
		return weightDiff[index];
	}

	public void setWeightDiff(double weightDiff, int index) {
		this.weightDiff[index] = weightDiff;
	}

	public double getBiasDiff() {
		return biasDiff;
	}

	public void setBiasDiff(double biasDiff) {
		this.biasDiff = biasDiff;
	}
	
	public int getSize(){
		return size;
	}
	
}
