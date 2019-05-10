package GUI;

import java.io.File;
import java.util.ArrayList;

import javax.swing.JFrame;

import NeuronalesNetz.NeuralNet;
import ObjekErkennung.ImageProcessor;
import ObjekErkennung.Result;
import bilderEinlesen.BildEinlesen;
import bilderEinlesen.BilderAusgeben;

public class Ergebnisse extends JFrame {
		
		public Ergebnisse(File file){
			NeuralNet n = new NeuralNet("Net98TrainingData96TestData.txt");
	        BildEinlesen b1 = new BildEinlesen(file.getPath());


	        ImageProcessor imgP = new ImageProcessor(b1.getBild());
	        imgP.maskImages(200);
	        imgP.darkenImages();
	        imgP.scaleImages();
	        imgP.addBoders();
	        ArrayList<Result> resultImages = imgP.getResultImages();

	       //System.out.println(resultImages.get(0).getImage());

	        for(int i = 0; i < resultImages.size(); i++) {

	            double[] erg = n.feedForward(
	                resultImages.get(i).getImage().toDoubleArray()
	            );
	            resultImages.get(i).setClassificationArray(erg);
	            resultImages.get(i).setCalsssification(n.getMaxindex(erg));

	            double classification = erg[resultImages.get(i).getCalsssification()];

	            /*if(classification > .01) {
	                System.out.println("Bild " + i + " : " + n.getMaxindex(erg) + " -- " + classification);
	            }
	            
	            BilerAusgeben.bildAusgeben(resultImages.get(i),i);*/
	           

	        }
	        add(new ErgebnisPanel(resultImages));
		}
}
