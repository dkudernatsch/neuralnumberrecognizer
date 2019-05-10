package GUI;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

import javax.swing.JPanel;

import ObjekErkennung.Result;

public class ErgebnisPanel extends JPanel {
	private ArrayList<Result> results;
	private BufferedImage image;
	public ErgebnisPanel(ArrayList<Result> results){
		this.results = results;
		image = results.get(1).getParent();
		
	}
	@Override
	public void paintComponent(Graphics g) 
	  { 
		for(Result result: results){
			paintBorder(result);
		}
		
	    g.drawImage(image, 0, 0, null);
	    schreiben(g);
	    
	    repaint(); 
	  }
	public void paintBorder(Result result){
		
		int minX = result.getBound().getMinX()-3;
		int maxX = result.getBound().getMaxX()+3;
		int minY = result.getBound().getMinY()-3;
		int maxY = result.getBound().getMaxY()+3;
		Color c;
		if(result.getClassificationArray()[result.getCalsssification()]>=.5){
			c = new Color((int)(255*(1-result.getClassificationArray()[result.getCalsssification()])), 255, 30);
		}
		else
		{
			c = new Color(255, (int)(255*(result.getClassificationArray()[result.getCalsssification()])), 30);
		}
		for(int i = 0; i<=4; i++){
			
		
			for(int y = minY; y<=maxY; y++){
				image.setRGB(maxX, y, c.getRGB());
				image.setRGB(minX, y, c.getRGB());
			}
			for(int x = minX; x<=maxX; x++){
				image.setRGB(x, maxY, c.getRGB());
				image.setRGB(x, minY, c.getRGB());
			}
		minX--;
		maxX++;
		minY--;
		maxY++;
		}
	}
	public void schreiben(Graphics g){
		g.setFont(new Font("Arial Black", Font.PLAIN, 24));
		g.setColor(Color.BLACK);
		for(Result result: results){
			g.drawString(""+result.getCalsssification(), result.getBound().getMaxX(), result.getBound().getMaxY()-15);
		}
		
		
	}
	

}
