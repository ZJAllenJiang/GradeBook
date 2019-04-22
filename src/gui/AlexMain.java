package gui;

import java.awt.BorderLayout;

import javax.swing.JFrame;

import data.DataUtil;
import data.DatabaseAPI;
import model.Course;

public class AlexMain {

	public static void main(String[] args) {
		JFrame alexFrame = new JFrame("Gradebook");
//		DatabaseAPI.saveCourse(new Course());
//		Course javaCourse = DatabaseAPI.loadCourse("Java", "591", 2019);
		Course javaCourse = new Course();
		
		
		alexFrame.add(new GradeBookPanel(javaCourse), BorderLayout.CENTER);
		
		alexFrame.setSize(1000, 800);
		alexFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		alexFrame.setVisible(true); 
		
		
		/*
		new CourseGradebookButtonPage(javaCourse);
		*/
	}

}
