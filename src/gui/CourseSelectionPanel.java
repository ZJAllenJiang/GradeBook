package gui;

import java.awt.Component;
import java.util.List;

import javax.swing.BoxLayout;
import javax.swing.DefaultListCellRenderer;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.ListCellRenderer;

import model.Course;

public class CourseSelectionPanel extends JPanel {
	private JComboBox<Course> courseComboBox;
	
	public CourseSelectionPanel(List<Course> courses) {
		super();
		setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		
		setUpGUI(courses);
	}

	protected void setUpGUI(List<Course> courses) {
		addCourseListPanel(courses);
	}

	private void addCourseListPanel(List<Course> courses) {
		JPanel courseListPanel = new JPanel();
		courseListPanel.setLayout(new BoxLayout(courseListPanel, BoxLayout.X_AXIS));
		JLabel courseListLabel = new JLabel("Course Template to Copy: ");
		courseListPanel.add(courseListLabel);
		
		Course[] array = new Course[courses.size()];
		courseComboBox = new JComboBox<Course>(courses.toArray(array));
		ListCellRenderer comboRenderer = new DefaultListCellRenderer() {
		    @Override
		    public Component getListCellRendererComponent(JList<?> list,
		            Object value, int index, boolean isSelected,
		            boolean cellHasFocus) {
		        if (value instanceof Course) {
		            setToolTipText("TEST");
		            value = ((Course) value).getName();
		        } else {
		            setToolTipText(null);
		        }
		        return super.getListCellRendererComponent(list, value, index, isSelected,
		                cellHasFocus);
		    }
		};
		courseComboBox.setRenderer(comboRenderer);
		
		courseListPanel.add(courseComboBox);
		this.add(courseListPanel);
	}

	public Course getSelectedCourse() {
		return (Course) courseComboBox.getSelectedItem();
	}
}
