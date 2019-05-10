import NeuronalesNetz.NeuralNet;
import ObjekErkennung.ImageProcessor;
import ObjekErkennung.Result;
import bilderEinlesen.BildEinlesen;
import bilderEinlesen.BilderAusgeben;

import java.util.ArrayList;



/**
 * Created by dkude on 27.04.2016.
 */

public class Test {
    public static void main(String[] args) {

        NeuralNet n = new NeuralNet("Net98TrainingData96TestData.txt");
        BildEinlesen b1 = new BildEinlesen("testBilder/testImage3.png");


        ImageProcessor imgP = new ImageProcessor(b1.getBild());
        imgP.maskImages(200);
        imgP.darkenImages();
        imgP.scaleImages();
        imgP.addBoders();
        ArrayList<Result> resultImages = imgP.getResultImages();

        System.out.println(resultImages.get(0).getImage());

        for(int i = 0; i < resultImages.size(); i++) {

            double[] erg = n.feedForward(
                resultImages.get(i).getImage().toDoubleArray()
            );
            resultImages.get(i).setClassificationArray(erg);
            resultImages.get(i).setCalsssification(n.getMaxindex(erg));

            double classification = erg[resultImages.get(i).getCalsssification()];

            if(classification > .01) {
                System.out.println("Bild " + i + " : " + n.getMaxindex(erg) + " -- " + classification);
            }

            BilderAusgeben.bildAusgeben(resultImages.get(i),i);

        }


    }

}
