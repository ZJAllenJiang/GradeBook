package gui;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.Toolkit;

import javax.swing.JFrame;
import javax.swing.JButton;
import javax.swing.JFileChooser;

import java.awt.event.ActionListener;
import java.io.File;
import java.util.ArrayList;
import java.awt.event.ActionEvent;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;

import data.DatabaseAPI;
import model.Course;
import model.Student;

import java.awt.Font;

public class CourseGradebookButtonPage {

	private JFrame frame;
	private GradeBookPanel gBookPanel;
	private Course course;
	
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					CourseGradebookButtonPage window = new CourseGradebookButtonPage();
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
		this.course = course;
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
				new CourseCollectionPage();
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
				new CreateStudentPage(course);
				frame.dispose();
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
				JFileChooser jfc = new JFileChooser();
				jfc.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES );
				//jfc.showDialog(new JLabel(), "Select");
				int result = jfc.showOpenDialog(null);
				if(result != JFileChooser.CANCEL_OPTION) {
				File file = jfc.getSelectedFile();
				if(file.isDirectory()){
					System.out.println("Selected file folder is: " + file.getAbsolutePath());
				}else if(file.isFile()){
					System.out.println("Selected file is: " + file.getAbsolutePath());
				}
				System.out.println(jfc.getSelectedFile().getName());
				
			
				gBookPanel.setAllData(true);
				ArrayList<Student> studentsArray = DatabaseAPI.importStudents(file.getAbsolutePath());
				for (int i = 0; i < studentsArray.size(); i++) {
					String sId = studentsArray.get(i).getSid();
					String fName = studentsArray.get(i).getFirstName();
					String mName = studentsArray.get(i).getMiddleName();
					String lName = studentsArray.get(i).getLastName();
					course.addStudent(sId, fName, mName, lName);
				}
				gBookPanel.setAllData(false);
			}
				
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
		
		gBookPanel.setBounds(50, 200, 900, 500);
		frame.getContentPane().add(gBookPanel);
		
		JButton resetButton = new JButton("Reset");
		resetButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Course resetCourse = DatabaseAPI.loadCourse(course.getName(), course.getCode(), course.getYear());
				frame.dispose();
				new CourseGradebookButtonPage(resetCourse);
				//new CourseCollectionPage();
			}
		});
		resetButton.setFont(new Font("Lucida Grande", Font.PLAIN, 18));
		resetButton.setBounds(40, 720, 170, 40);
		frame.getContentPane().add(resetButton);
		
		JButton recalculateButton = new JButton("Recalculate");
		recalculateButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				boolean status = gBookPanel.updateOverallGrades();
    			
				if(status) {
					System.err.println("TODO: Compute the statistics!");
					
					StatisticsPanel statisticsPanel = new StatisticsPanel(0, 0, 0);

					JOptionPane.showMessageDialog(frame, statisticsPanel, 
							"Statistics for Course: " + course.getName(), JOptionPane.PLAIN_MESSAGE);
				}
				else {
					JOptionPane.showMessageDialog(frame, "Error when computing the statistics for course: " + course.getName(), 
							"Error", JOptionPane.ERROR_MESSAGE);
				}
			}
		});
		recalculateButton.setFont(new Font("Lucida Grande", Font.PLAIN, 18));
		recalculateButton.setBounds(600, 720, 170, 40);
		frame.getContentPane().add(recalculateButton);
		
		JButton saveChangesButton = new JButton("Save changes");
		saveChangesButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				DatabaseAPI.saveCourse(course);
			}
		});
		saveChangesButton.setFont(new Font("Lucida Grande", Font.PLAIN, 18));
		saveChangesButton.setBounds(790, 720, 170, 40);
		frame.getContentPane().add(saveChangesButton);
		frame.setTitle("Course Gradebook");
		frame.setResizable(false);
	}
	
	
}
