import javax.swing.SwingUtilities;

import GUI.GUI;

public class Main {

	public static void main(String[] args) {

		SwingUtilities.invokeLater(GUI::new);

	}

}
