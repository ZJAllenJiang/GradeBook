package gui;

import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.Toolkit;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableModel;

import data.DatabaseAPI;
import model.Course;

import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class DeleteCourseConfirmation {
	
	private JFrame frame;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					DeleteCourseConfirmation window = new DeleteCourseConfirmation();
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
	public DeleteCourseConfirmation() {
		initialize();
	}
	private void initialize() {
		// TODO Auto-generated method stub
		
	}

	public DeleteCourseConfirmation(int currentRow, Course currentCourse) {
		initialize(currentRow, currentCourse);
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize(int currentRow, Course currentCourse) {
		frame = new JFrame();
		frame.setVisible(true);
		frame.setBounds(100, 100, 450, 250);
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
		frame.setTitle("Delete Course Confirmation");
		
		JLabel warningLabel = new JLabel("Are you sure you want to delete this course?");
		warningLabel.setHorizontalAlignment(SwingConstants.CENTER);
		warningLabel.setFont(new Font("Lucida Grande", Font.PLAIN, 15));
		warningLabel.setBounds(25, 20, 400, 60);
		frame.getContentPane().add(warningLabel);
		
		JButton cancelButton = new JButton("Cancel");
		cancelButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				frame.dispose();
			}
		});
		cancelButton.setBounds(90, 150, 120, 30);
		frame.getContentPane().add(cancelButton);
		
		JButton yesButton = new JButton("Yes");
		yesButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				DefaultTableModel tableModel = (DefaultTableModel) CourseCollectionPage.courseTable.getModel();
				tableModel.removeRow(currentRow);
				DatabaseAPI.dropCourse(currentCourse.getName(), currentCourse.getCode(), currentCourse.getYear(),
						currentCourse.getSemester());
				frame.dispose();
				new CourseCollectionPage();
			}
		});
		yesButton.setBounds(240, 150, 120, 30);
		frame.getContentPane().add(yesButton);
		frame.setResizable(false);
	}

}
