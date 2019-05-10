package bilderEinlesen;

import ObjekErkennung.IntImage;
import ObjekErkennung.Result;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.PipedReader;
import java.io.Serializable;


/**
 * Dient der Ausgabe von Bildern in speziellen vom Programm verwendeten Formaten.
 * zB die Umwandlung von einem Result in ein Bild mit beschreibung im Dateinamen
 */
public class BilderAusgeben {
    public static void bildAusgeben(IntImage data, int num){
        BufferedImage bImage = convertToBufferedImage(data);
        String fileName = "output/image"+num+".jpeg";
        saveImage(fileName, bImage);
    }

    public static void bildAusgeben(Result result, int num) {

        BufferedImage bImage = convertToBufferedImage(result.getImage());
        int classificationAccurcy = (int) (result.getClassificationArray()[result.getCalsssification()]*100);
        String fileName = "output/image"+num+"_Classification"+result.getCalsssification()+"_Accuracy"+classificationAccurcy+".jpeg";

        saveImage(fileName, bImage);
    }

    private static void saveImage(String fileName, BufferedImage bImage){
        File outputfile = new File(fileName);
        try {
            ImageIO.write(bImage, "jpg", outputfile);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static BufferedImage convertToBufferedImage(IntImage data){
        int height = data.getHeight();
        int width = data.getWidth();

        BufferedImage bImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);

        for(int y = 0; y < height; y++){
            for(int x = 0; x < width; x++){
                Color c = new Color(data.get(x,y),data.get(x,y),data.get(x,y));
                bImage.setRGB(x, y, c.getRGB());
            }
        }

        return bImage;
    }

}
