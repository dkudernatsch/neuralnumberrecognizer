package NeuronalesNetz.Convolutional_Net;
import NeuronalesNetz.Sigmoid;
/**
 * Created by dkude on 27.03.2016.
 */
public class FeatureMap {

    private double[][] input;
    private Sigmoid node;
    int height;
    int width;
    double[][] output;

    public FeatureMap(int heigth, int width, int[] inputSize){
        this.height = heigth;
        this.width = width;

        node = new Sigmoid(heigth*width);
    }

    public double[][] feedForward(double[][] input){
        int anzHeight = input.length - height;
        int anzWidth  = input[0].length - width;

        double[][] tempOutput = new double[anzHeight][anzWidth];
        for(int i = 0; i < anzHeight; i++){
            for(int j = 0; j < anzWidth; j++ ){
                tempOutput[i][j] = node.feedForward(trimmInput(input,i,j));
            }

        }
        this.output = tempOutput;
        return output;
    }

    private double[] trimmInput(double[][] input, int heightIndex, int widthIndex){
        double[] newInput = new double[height*width];
        for(int i = 0; i < height; i++){
            System.arraycopy(input[heightIndex+i], widthIndex, newInput, i*width, width);
        }
        return newInput;
    }

}
