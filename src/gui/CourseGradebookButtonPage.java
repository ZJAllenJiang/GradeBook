package gui;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.Toolkit;

import javax.swing.JFrame;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.awt.event.ActionEvent;
import javax.swing.JLabel;
import javax.swing.SwingConstants;

import model.Course;

import java.awt.Font;

public class CourseGradebookButtonPage {

	private JFrame frame;
	private GradeBookPanel gBookPanel;
	
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					//CourseGradebookButtonPage window = new CourseGradebookButtonPage();
					//window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public CourseGradebookButtonPage() {
	}
	
	
	/**
	 * @wbp.parser.entryPoint
	 */
	public CourseGradebookButtonPage(Course course) {
		initialize(course);
	}
	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize(Course course) {
		//initialize GradebookPanel 
		//new GradeBookPanel(currentCourse);
		//JFrame alexFrame = new JFrame("Gradebook");
		gBookPanel = new GradeBookPanel(course);
		
		
		//frame.setSize(1000, 800);
		//frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		//frame.setVisible(true); 
		
		
		frame = new JFrame();
		frame.setVisible(true);
		Toolkit kit = Toolkit.getDefaultToolkit(); 
		Dimension screenSize = kit.getScreenSize(); 
		int screenWidth = screenSize.width; 
		//System.out.println(screenWidth);
		int screenHeight = screenSize.height;
		//System.out.println(screenHeight);
		frame.setBounds(100, 100, 1000, 800);
		//frame.setLocationRelativeTo(null);
		// set the window in the middle of screen
		int windowWidth = frame.getWidth(); 
		int windowHeight = frame.getHeight();  
		frame.setLocation(screenWidth / 2 - windowWidth / 2, screenHeight / 2 - windowHeight / 2);
				
		frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		
		JButton backButton = new JButton("Back");
		backButton.setFont(new Font("Lucida Grande", Font.PLAIN, 18));
		backButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				frame.dispose();
			}
		});
		backButton.setBounds(30, 20, 120, 40);
		frame.getContentPane().add(backButton);
		
		JLabel courseTitleLabel = new JLabel("");
		courseTitleLabel.setFont(new Font("Lucida Grande", Font.PLAIN, 25));
		courseTitleLabel.setHorizontalAlignment(SwingConstants.CENTER);
		courseTitleLabel.setBounds(300, 60, 400, 60);
		courseTitleLabel.setText("CS " + course.getCode() + " Gradebook");
		frame.getContentPane().add(courseTitleLabel);
		
		JButton addStudentButton = new JButton("Add Student");
		addStudentButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				gBookPanel.setAllData(true);
				new CreateStudentPage();
				String sId = CreateStudentPage.newStudentList.get(0);
				String fName = CreateStudentPage.newStudentList.get(1);
				String mName = CreateStudentPage.newStudentList.get(2);
				String lName = CreateStudentPage.newStudentList.get(3);
				course.addStudent(sId, fName, mName, lName);
				gBookPanel.setAllData(false);
			}
		});
		addStudentButton.setFont(new Font("Lucida Grande", Font.PLAIN, 18));
		addStudentButton.setBounds(40, 140, 200, 40);
		frame.getContentPane().add(addStudentButton);
		
		JButton importStudentsButton = new JButton("Import Students");
		importStudentsButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				//wait for method in Course -> bulkLoadStudents
				// after that could use addStudent() method repeatedly
			}
		});
		importStudentsButton.setFont(new Font("Lucida Grande", Font.PLAIN, 18));
		importStudentsButton.setBounds(280, 140, 200, 40);
		frame.getContentPane().add(importStudentsButton);
		
		JButton gradingSchemeButton = new JButton("Grading Scheme");
		gradingSchemeButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				// wait for the import existing grading scheme data
			}
		});
		gradingSchemeButton.setFont(new Font("Lucida Grande", Font.PLAIN, 18));
		gradingSchemeButton.setBounds(520, 140, 200, 40);
		frame.getContentPane().add(gradingSchemeButton);
		
		JButton addCategoryButton = new JButton("Add Category");
		addCategoryButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
			}
		});
		addCategoryButton.setFont(new Font("Lucida Grande", Font.PLAIN, 18));
		addCategoryButton.setBounds(760, 140, 200, 40);
		frame.getContentPane().add(addCategoryButton);
		
		gBookPanel.setBounds(100, 100, 600, 400);
		frame.getContentPane().add(gBookPanel);
		
		JButton resetButton = new JButton("Reset");
		resetButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});
		resetButton.setFont(new Font("Lucida Grande", Font.PLAIN, 18));
		resetButton.setBounds(40, 720, 170, 40);
		frame.getContentPane().add(resetButton);
		
		JButton recalculateButton = new JButton("Recalculate");
		recalculateButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});
		recalculateButton.setFont(new Font("Lucida Grande", Font.PLAIN, 18));
		recalculateButton.setBounds(600, 720, 170, 40);
		frame.getContentPane().add(recalculateButton);
		
		JButton saveChangesButton = new JButton("Save changes");
		saveChangesButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});
		saveChangesButton.setFont(new Font("Lucida Grande", Font.PLAIN, 18));
		saveChangesButton.setBounds(790, 720, 170, 40);
		frame.getContentPane().add(saveChangesButton);
		frame.setTitle("Course Gradebook");
	}
}
