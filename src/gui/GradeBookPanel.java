package gui;

import java.awt.GridLayout;

import javax.swing.JPanel;
import javax.swing.JTabbedPane;

import model.Category;
import model.Course;

public class GradeBookPanel extends JPanel {
	private Course course;
	private JTabbedPane gradeBookTabs;
	
	public GradeBookPanel(Course course) {
		super(new GridLayout(1, 1));
		this.course = course;
		
		setUpGUI();
	}

	private void setUpGUI() {
		gradeBookTabs = new JTabbedPane();
		
		//Add the Summary
		gradeBookTabs.addTab("Summary", new GradeBookTablePanel(course.getSummary()));
		
		//Add the other Categories
		for(Category category : course.getAllCategories()) {
			gradeBookTabs.addTab(category.getName(), new GradeBookTablePanel(category));
		}
		
		add(gradeBookTabs);
	}
}
