package NeuronalesNetz;


import java.util.Arrays;

public class Test {
	public static void main(String[]args){


        int[] size ={784, 100, 10};
		Network n = new Network(size);
		NeuralTraining neur = new NeuralTraining(n, .5, 0, new TrainData(""), new TrainData("test"));
        System.out.println(Arrays.toString(neur.getNetwork().feedForward(neur.getTrain().getData(6))));
        //System.out.println( neur.meanSquaredErrorOverAll());
		long time1 = System.nanoTime();
        neur.train(10, 10, .2, -.5);
		//System.out.println(neur.meanSquaredErrorOverAll());
        long time2 = System.nanoTime();
        System.out.println((time2-time1)/1000000000);
        System.out.println(Arrays.toString(neur.getNetwork().feedForward(neur.getTrain().getData(6))));
        System.out.println(neur.meanSquaredErrorOverAll());
        MnistLoader.printDigit(neur.getTrain().getData(6));
        neur.exportWeights();

		/*
        int index = 374;
        NeuralNet net = new NeuralNet("test.txt");
        TrainData t = new TrainData("test");
        System.out.println(Arrays.toString(net.feedForward(t.getData(index))));
        System.out.println(Arrays.toString(t.getLabel(index)));
        MnistLoader.printDigit(t.getData(index));
*/



    }
}
