package gui;

import java.awt.Component;
import java.util.Arrays;
import java.util.Comparator;
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
		JLabel courseListLabel = new JLabel("Grading Scheme to Copy: ");
		courseListPanel.add(courseListLabel);
		
		Course[] array = new Course[courses.size()];
		array = courses.toArray(array);
		Arrays.sort(array, new Comparator<Course>() {
			@Override
			public int compare(Course o1, Course o2) {
				String s1 = o1.toString();
				String s2 = o2.toString();
				return s1.compareTo(s2);
			}
		});
		
		courseComboBox = new JComboBox<Course>(array);
		ListCellRenderer comboRenderer = new DefaultListCellRenderer() {
		    @Override
		    public Component getListCellRendererComponent(JList<?> list,
		            Object value, int index, boolean isSelected,
		            boolean cellHasFocus) {
		        if (value instanceof Course) {
		        	Course course = (Course) value;
		            value = course.toString();
		            setToolTipText(course.getCourseSummaryInfo());
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
