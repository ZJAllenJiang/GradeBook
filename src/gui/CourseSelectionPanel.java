package gui;

import java.util.List;

import javax.swing.BoxLayout;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class CourseSelectionPanel extends JPanel {
	private JComboBox<String> courseComboBox;
	
	public CourseSelectionPanel(List<String> courses) {
		super();
		setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		
		setUpGUI(courses);
	}

	protected void setUpGUI(List<String> courses) {
		addCourseListPanel(courses);
	}

	private void addCourseListPanel(List<String> courses) {
		JPanel courseListPanel = new JPanel();
		courseListPanel.setLayout(new BoxLayout(courseListPanel, BoxLayout.X_AXIS));
		JLabel courseListLabel = new JLabel("Course Template to Copy: ");
		courseListPanel.add(courseListLabel);
		String[] array = new String[courses.size()];
		courseComboBox = new JComboBox<String>(courses.toArray(array));
		courseListPanel.add(courseComboBox);
		this.add(courseListPanel);
	}

	public String getSelectedCourse() {
		return (String) courseComboBox.getSelectedItem();
	}
}
