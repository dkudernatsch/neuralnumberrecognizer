package NeuronalesNetz.Convolutional_Net;

/**
 * Created by dkude on 27.03.2016.
 */
public class ConvolutionalLayer {
    private double[][] input;
    private FeatureMap[] maps;
    private int size;
    private double[][] output;

    public ConvolutionalLayer(int size, int width, int height, int inputSize[]){
        this.size = size;
        for(int i = 0; i < size; i++){
            maps[i] = new FeatureMap(height, width, inputSize);
        }
    }
    /*
    public double[][] feedForward(double[][] input){

    }
    */
}
