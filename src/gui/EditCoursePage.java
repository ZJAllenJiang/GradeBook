package gui;

import java.awt.EventQueue;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JRadioButton;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableModel;
import javax.swing.JComboBox;

public class EditCoursePage {

	private JFrame frame;
	private JTextField nameTextField;
	private JTextField codeTextField;
	private JTextField yearTextField;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					EditCoursePage window = new EditCoursePage();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public EditCoursePage() {
		initialize();
	}
	private void initialize() {
		// TODO Auto-generated method stub
		
	}

	public EditCoursePage(int currentRow) {
		initialize(currentRow);
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize(int currentRow) {
		frame = new JFrame();
		frame.setVisible(true);
		frame.setBounds(100, 100, 300, 350);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		
		JLabel createNewCourseLabel = new JLabel("Edit course information");
		createNewCourseLabel.setHorizontalAlignment(SwingConstants.CENTER);
		createNewCourseLabel.setFont(new Font("Lucida Grande", Font.PLAIN, 16));
		createNewCourseLabel.setBounds(50, 10, 200, 40);
		frame.getContentPane().add(createNewCourseLabel);
		
		JLabel nameLabel = new JLabel("Name ");
		nameLabel.setBounds(50, 45, 190, 20);
		frame.getContentPane().add(nameLabel);
		
		DefaultTableModel tableModel = (DefaultTableModel) CourseCollectionPage.courseTable.getModel();
		nameTextField = new JTextField();
		nameTextField.setToolTipText("");
		nameTextField.setText((String) tableModel.getValueAt(currentRow, 0));
		nameTextField.setBounds(50, 65, 175, 30);
		frame.getContentPane().add(nameTextField);
		nameTextField.setColumns(10);
		
		JLabel codeLabel = new JLabel("Code ");
		codeLabel.setBounds(50, 100, 190, 20);
		frame.getContentPane().add(codeLabel);
		
		codeTextField = new JTextField();
		codeTextField.setText((String) tableModel.getValueAt(currentRow, 1));
		codeTextField.setBounds(50, 120, 175, 30);
		frame.getContentPane().add(codeTextField);
		codeTextField.setColumns(10);
		
		JLabel yearLabel = new JLabel("Year ");
		yearLabel.setBounds(50, 155, 180, 20);
		frame.getContentPane().add(yearLabel);
		
		yearTextField = new JTextField();
		yearTextField.setText((String) tableModel.getValueAt(currentRow, 2));
		yearTextField.setBounds(50, 175, 175, 30);
		frame.getContentPane().add(yearTextField);
		yearTextField.setColumns(10);
		
		JLabel semesterLabel = new JLabel("Semester");
		semesterLabel.setBounds(50, 210, 80, 20);
		frame.getContentPane().add(semesterLabel);
		
		JRadioButton fallRadioButton = new JRadioButton("Fall");
		fallRadioButton.setBounds(40, 230, 70, 23);
		frame.getContentPane().add(fallRadioButton);
		
		JRadioButton springRadioButton = new JRadioButton("Spring");
		springRadioButton.setBounds(105, 230, 80, 23);
		frame.getContentPane().add(springRadioButton);
		
		JRadioButton summerRadioButton = new JRadioButton("Summer");
		summerRadioButton.setBounds(180, 230, 90, 23);
		frame.getContentPane().add(summerRadioButton);
		
		ButtonGroup bg = new ButtonGroup();
		bg.add(fallRadioButton);
		bg.add(springRadioButton);
		bg.add(summerRadioButton);
		
		String semester = ((String) tableModel.getValueAt(currentRow, 3));
		if(semester.equals("Fall")) {
			fallRadioButton.setSelected(true);
		}
		if(semester.equals("Spring")) {
			springRadioButton.setSelected(true);
		}
		if(semester.equals("Summer")) {
			summerRadioButton.setSelected(true);
		}
		
		
		JLabel statusLabel = new JLabel("Status");
		statusLabel.setBounds(50, 260, 60, 20);
		frame.getContentPane().add(statusLabel);
		
		JComboBox statusComboBox = new JComboBox();
		statusComboBox.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
			}
		});
		statusComboBox.addItem("Active");
		statusComboBox.addItem("Inactive");
		statusComboBox.setBounds(115, 260, 120, 30);
		frame.getContentPane().add(statusComboBox);
		
		String status = ((String) tableModel.getValueAt(currentRow, 4));
		if(status.equals("Active")) {
			statusComboBox.setSelectedItem("Active");
		}
		if(status.equals("Inactive")) {
			statusComboBox.setSelectedItem("Inactive");
		}
		
		
		JButton cancelButton = new JButton("Cancel");
		cancelButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				frame.dispose();
				
			}
		});
		cancelButton.setBounds(30, 290, 120, 30);
		frame.getContentPane().add(cancelButton);
		
		JButton saveButton = new JButton("Save");
		saveButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String name = nameTextField.getText();
				String code = codeTextField.getText();
				String year = yearTextField.getText();
				String semester = "";
				String status = (String) statusComboBox.getSelectedItem();
				if(fallRadioButton.isSelected()) {
					semester = fallRadioButton.getText();
				}
				if(springRadioButton.isSelected()) {
					semester = springRadioButton.getText();
				}
				if(summerRadioButton.isSelected()) {
					semester = summerRadioButton.getText();
				}
				Object[] courseRow = {name, code, year, semester, status, "View", "Edit", "Delete"};
				DefaultTableModel tableModel = (DefaultTableModel) CourseCollectionPage.courseTable.getModel();
				if(name.equals("") || code.equals("") || year.equals("") || semester.equals("")) {
					// could add some more features
				}else {
					tableModel.setValueAt(name, currentRow, 0);
					tableModel.setValueAt(code, currentRow, 1);
					tableModel.setValueAt(year, currentRow, 2);
					tableModel.setValueAt(semester, currentRow, 3);
					tableModel.setValueAt(status, currentRow, 4);
					
				}
				frame.dispose();
				
			}
		});
		saveButton.setBounds(150, 290, 120, 30);
		frame.getContentPane().add(saveButton);
		
		
	}
}
