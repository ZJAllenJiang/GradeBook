package gui;

import java.awt.BorderLayout;

import javax.swing.JFrame;

public class AlexMain {

	public static void main(String[] args) {
		JFrame alexFrame = new JFrame("Gradebook");
		
		alexFrame.add(new GradeBookPanel(), BorderLayout.CENTER);
		
		alexFrame.setSize(1000, 800);
		alexFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		alexFrame.setVisible(true); 
	}

}
