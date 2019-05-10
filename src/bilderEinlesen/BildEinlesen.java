package bilderEinlesen;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;


/**
 * Dient zum einlesen von Bildern und konvertierung zu java.awt.image.BufferedImage welche im weiteren Programm verwendet werden.
 */
public class BildEinlesen  {
	
	private BufferedImage bild;


    /***
     * @param filename Pfad zum Bild
     *
     */
    public BildEinlesen (String filename)
	{
		try {
			setBild  (ImageIO.read(new File(filename)));
			
		} catch (IOException e) {
			e.printStackTrace();
		}

		int abstandX = bild.getWidth()/100*3;
		int abstandY = bild.getHeight()/100*3;

		setBild(bild.getSubimage(abstandX, abstandY, bild.getWidth()-2*abstandX, bild.getHeight()-2*abstandY));
	}

	public BufferedImage getBild () {
		return bild;
	}

	public void setBild(BufferedImage bild) {
		this.bild = bild;
	}
	
}