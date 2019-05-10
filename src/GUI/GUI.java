package GUI;

import javax.swing.ImageIcon;
import javax.swing.JButton;

import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.filechooser.FileNameExtensionFilter;

import bilderEinlesen.BildEinlesen;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FileDialog;

import java.awt.Font;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;


public class GUI extends JFrame {
	JFrame fenster = new JFrame();
	JLabel bild = new JLabel();
	JMenuBar menu = new JMenuBar();
	JMenu exit = new JMenu("Exit");
	JMenu neu = new JMenu("Bild");
	JMenuItem schliessen = new JMenuItem("Programm schlie\u00DFen");
	JMenuItem loeschen = new JMenuItem("Bild verwerfen");
	JMenuItem neuAuswaehlen = new JMenuItem("Neues Bild ausw\u00e4hlen");
	
	JButton ausfuehren = new JButton();
	JButton auswaehlen = new JButton();
	BufferedImage image;
	ImageIcon icon1;

	FileDialog fd;
	BildEinlesen b;
	private File file;
	
	public GUI(){
		this.setTitle("Neural Numbers");
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		//auswaehlen.setBackground(new Color();
	
		
		auswaehlen.setText("Bild ausw\u00e4hlen");
	
		ausfuehren.setText("Programm ausf\u00fchren");
		this.add(auswaehlen, BorderLayout.CENTER);
		
		this.add(ausfuehren,  BorderLayout.WEST);
		this.setSize(1280, 720);
		add(menu, BorderLayout.NORTH);
		menu.add(exit);
		menu.add(neu);
		neu.add(loeschen);
		neu.add(neuAuswaehlen);
		exit.add(schliessen);
		neuAuswaehlen.addActionListener(this::auswahl);
		loeschen.addActionListener(c->{
			
		add(auswaehlen, BorderLayout.CENTER);
		pack();
		this.setSize(1280, 720);
		});
		
		schliessen.addActionListener(c->System.exit(0));
	
		
		ausfuehren.setFont(new Font("sans serif", Font.BOLD, 35));
		exit.setFont(new Font("sans serif", Font.BOLD, 25));
		schliessen.setFont(new Font("sans serif", Font.BOLD, 25));
		neuAuswaehlen.setFont(new Font("sans serif", Font.BOLD, 20));
		loeschen.setFont(new Font("sans serif", Font.BOLD, 20));
		neu.setFont(new Font("sans serif", Font.BOLD, 25));
		auswaehlen.setFont(new Font("sans serif", Font.BOLD, 35));
		this.setLocationRelativeTo(null);
		this.setVisible(true);
		auswaehlen.addActionListener(this::auswahl);
		
		ausfuehren.addActionListener(d->{
		
			fenster = new Ergebnisse(file);
			fenster.setTitle("Ausgabe");
			fenster.setSize(1070, 720);
			fenster.setVisible(true);
			
			
		});
		
	}
	public static void main(String[] args){
		new GUI();
	}
	private ImageIcon fitToSize(ImageIcon icon){
		double width = icon.getIconWidth();
		double height = icon.getIconHeight();
		int newWidth = 0;
		int newHeight = 0;
		double ratio = 0;
		if(icon.getIconHeight()>icon.getIconWidth()){
			 ratio = 1200/height;
			
		}
		else
		{
			ratio = 600/width;
		}
		newWidth = (int) (icon.getIconHeight()*ratio);
		newHeight = (int)(icon.getIconWidth()*ratio);
		icon = new ImageIcon(icon.getImage().getScaledInstance(newWidth, newHeight, Image.SCALE_SMOOTH));
		return icon;
	
	}
	public void auswahl(ActionEvent e){
		 JFileChooser fileDialog = new JFileChooser();
		    fileDialog.setFileFilter( new FileNameExtensionFilter("*.jpg;*.png",
		      "jpg", "png") );
		    
		    if(fileDialog.showOpenDialog(this)== JFileChooser.APPROVE_OPTION){
		    	
		    	file = fileDialog.getSelectedFile();
			    bild.setIcon(fitToSize(new ImageIcon(file.getAbsolutePath())));
			    remove(auswaehlen);
			    
			    add(bild, BorderLayout.CENTER);
			 	pack();
			 	icon1 = new ImageIcon(file.getAbsolutePath());
			 	icon1.getIconWidth();
			    } 
	}
}
	
	