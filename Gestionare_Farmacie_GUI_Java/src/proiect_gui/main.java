package proiect_gui;

import java.awt.EventQueue;
import java.io.File;

public class main {

	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			@Override
			public void run() {
				try {
					Application window = new Application(new File("src/proiect_gui/test"));
					window.setVisible(true);
					window.setLocationRelativeTo(null);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

}
