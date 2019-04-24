package gui;

import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableModel;

import data.DatabaseAPI;
import model.Course;

public class SaveCourseConfirmationPage {

	private JFrame frmSaveCourseConfirmation;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					SaveCourseConfirmationPage window = new SaveCourseConfirmationPage();
					window.frmSaveCourseConfirmation.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public SaveCourseConfirmationPage() {
		initialize();
	}
	
	private void initialize() {
		// TODO Auto-generated method stub
		
	}

	public SaveCourseConfirmationPage(Course course) {
		initialize(course);
	}

	

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize(Course course) {
		frmSaveCourseConfirmation = new JFrame();
		frmSaveCourseConfirmation.setVisible(true);
		frmSaveCourseConfirmation.setBounds(100, 100, 450, 250);
		//frame.setLocationRelativeTo(null);
		// set the window in the middle of screen
		int windowWidth = frmSaveCourseConfirmation.getWidth(); 
		int windowHeight = frmSaveCourseConfirmation.getHeight(); 
		Toolkit kit = Toolkit.getDefaultToolkit(); 
		Dimension screenSize = kit.getScreenSize(); 
		int screenWidth = screenSize.width; 
		int screenHeight = screenSize.height; 
		frmSaveCourseConfirmation.setLocation(screenWidth / 2 - windowWidth / 2, screenHeight / 2 - windowHeight / 2);
				
		frmSaveCourseConfirmation.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		frmSaveCourseConfirmation.getContentPane().setLayout(null);
		frmSaveCourseConfirmation.setTitle("Save Course Confirmation");
		
		JLabel warningLabel = new JLabel("Are you sure you want to save course's information?");
		warningLabel.setHorizontalAlignment(SwingConstants.CENTER);
		warningLabel.setFont(new Font("Lucida Grande", Font.PLAIN, 15));
		warningLabel.setBounds(25, 20, 400, 60);
		frmSaveCourseConfirmation.getContentPane().add(warningLabel);
		
		JButton cancelButton = new JButton("Cancel");
		cancelButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				frmSaveCourseConfirmation.dispose();
				new CourseGradebookButtonPage(course);
			}
		});
		cancelButton.setBounds(90, 150, 120, 30);
		frmSaveCourseConfirmation.getContentPane().add(cancelButton);
		
		JButton yesButton = new JButton("Yes");
		yesButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				DatabaseAPI.saveCourse(course);
				new CourseGradebookButtonPage(course);
				frmSaveCourseConfirmation.dispose();
			}
		});
		yesButton.setBounds(240, 150, 120, 30);
		frmSaveCourseConfirmation.getContentPane().add(yesButton);
		frmSaveCourseConfirmation.setResizable(false);
	}

}
