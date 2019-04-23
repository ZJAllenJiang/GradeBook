package gui;

import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JRadioButton;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableModel;

import model.Course;

public class CreateStudentPage {

	private JFrame frame;
	private JTextField idTextField;
	private JTextField firstNameField;
	private JTextField middleNameField;
	private JTextField lastNameField;
	


	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					CreateStudentPage window = new CreateStudentPage();
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
	public CreateStudentPage(Course course) {
		initialize(course);
	}
	

	public CreateStudentPage() {
		// TODO Auto-generated constructor stub
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize(Course course) {
		
		frame = new JFrame();
		frame.setVisible(true);
		frame.setBounds(100, 100, 300, 350);
		//frame.setLocationRelativeTo(null);
		// set the window in the middle of screen
		int windowWidth = frame.getWidth(); 
		int windowHeight = frame.getHeight(); 
		Toolkit kit = Toolkit.getDefaultToolkit(); 
		Dimension screenSize = kit.getScreenSize(); 
		int screenWidth = screenSize.width; 
		int screenHeight = screenSize.height; 
		frame.setLocation(screenWidth / 2 - windowWidth / 2, screenHeight / 2 - windowHeight / 2);
				
		frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		frame.setTitle("Create New Course");
		
		JLabel addANewStudentLabel = new JLabel("Add a new student");
		addANewStudentLabel.setHorizontalAlignment(SwingConstants.CENTER);
		addANewStudentLabel.setFont(new Font("Lucida Grande", Font.PLAIN, 16));
		addANewStudentLabel.setBounds(50, 10, 200, 60);
		frame.getContentPane().add(addANewStudentLabel);
		
		JLabel idLabel = new JLabel("Student ID");
		idLabel.setBounds(50, 60, 190, 20);
		frame.getContentPane().add(idLabel);
		
		idTextField = new JTextField();
		idTextField.setToolTipText("");
		idTextField.setBounds(50, 80, 175, 30);
		frame.getContentPane().add(idTextField);
		idTextField.setColumns(10);
		
		JLabel firstNameLabel = new JLabel("First Name");
		firstNameLabel.setBounds(50, 115, 190, 20);
		frame.getContentPane().add(firstNameLabel);
		
		firstNameField = new JTextField();
		firstNameField.setBounds(50, 135, 175, 30);
		frame.getContentPane().add(firstNameField);
		firstNameField.setColumns(10);
		
		JLabel middleNameLabel = new JLabel("Middle Name");
		middleNameLabel.setBounds(50, 170, 180, 20);
		frame.getContentPane().add(middleNameLabel);
		
		middleNameField = new JTextField();
		middleNameField.setBounds(50, 190, 175, 30);
		frame.getContentPane().add(middleNameField);
		middleNameField.setColumns(10);
		
		JLabel lastNameLabel = new JLabel("Last Name");
		lastNameLabel.setBounds(50, 225, 80, 20);
		frame.getContentPane().add(lastNameLabel);
		
		
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
				String sId = idTextField.getText();
				String fName = firstNameField.getText();
				String mName = middleNameField.getText();
				String lName = lastNameField.getText();
				if(!sId.equals("") && !fName.equals("") && !mName.equals("") && !lName.equals("") ) {
					course.addStudent(sId, fName, mName, lName);
					new CourseGradebookButtonPage(course);
					frame.dispose();	
				}
				
				
				
			}
		});
		saveButton.setBounds(150, 290, 120, 30);
		frame.getContentPane().add(saveButton);
		
		lastNameField = new JTextField();
		lastNameField.setBounds(50, 245, 175, 30);
		frame.getContentPane().add(lastNameField);
		lastNameField.setColumns(10);
		frame.setResizable(false);
		
		
		//return newStudentList;
	}
	
}
