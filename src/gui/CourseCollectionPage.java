package gui;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JLabel;
import java.awt.Font;
import javax.swing.JButton;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;

import data.DatabaseAPI;
import model.Course;

import java.awt.ScrollPane;
import java.awt.Toolkit;

import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JPanel;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import java.awt.event.ActionEvent;
import javax.swing.ListSelectionModel;
import javax.swing.ScrollPaneConstants;

public class CourseCollectionPage {
	public static JTable courseTable;
	private JFrame frame;
	

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					CourseCollectionPage window = new CourseCollectionPage();
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
	public CourseCollectionPage() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		Course javaCourse = new Course();
//		DatabaseAPI.saveCourse(javaCourse);
		
		
		frame = new JFrame();
		frame.setVisible(true);
		frame.setBounds(100, 100, 800, 500);
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
		frame.setTitle("Course Collection");
		
		JLabel courseLabel = new JLabel("Courses");
		courseLabel.setHorizontalAlignment(SwingConstants.CENTER);
		courseLabel.setFont(new Font("Lucida Grande", Font.PLAIN, 25));
		courseLabel.setBounds(340, 20, 120, 60);
		frame.getContentPane().add(courseLabel);
		
		JButton addCourseButton = new JButton("Add Course +");
		addCourseButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				frame.dispose();
				new CreateCoursePage();
			}
		});
		addCourseButton.setFont(new Font("Lucida Grande", Font.PLAIN, 18));
		addCourseButton.setBounds(325, 90, 150, 35);
		frame.getContentPane().add(addCourseButton);
		
		JButton logoutButton = new JButton("Logout");
		logoutButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				frame.setVisible(false);
				new LoginPage();
			}
		});
		logoutButton.setBounds(10, 10, 120, 40);
		frame.getContentPane().add(logoutButton);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(50, 150, 700, 300);
		frame.getContentPane().add(scrollPane);
		
		String[] columnName = {"Name", "Code", "Year", "Semester", "Status", "Action1", "Action2", "Action3"};
//		Object[][] courseInfo= {
//				{"Java Development", "CS591", "2019", "Spring", "Active", "View", "Edit", "Delete"}
//		};
		
		ArrayList<Course> courseList = DatabaseAPI.getCourseList();
		Object[][] courseInfo = new Object[courseList.size()][8];
		int index = 0;
		for (int i = 0; i < courseList.size(); i++) {
			courseInfo[index][0] = courseList.get(i).getName();
			courseInfo[index][1] = courseList.get(i).getCode();
			courseInfo[index][2] = String.valueOf(courseList.get(i).getYear());
			courseInfo[index][3] = "Spring";
			courseInfo[index][4] = "Active";
			courseInfo[index][5] = "View";
			courseInfo[index][6] = "Edit";
			courseInfo[index][7] = "Delete";
			index++;
		}
		DefaultTableModel model = new DefaultTableModel(courseInfo, columnName);
		courseTable = new JTable(courseInfo, columnName) {
			public boolean isCellEditable(int row, int column) {
			    return false;
			   }
		};
		courseTable.setSurrendersFocusOnKeystroke(true);
		courseTable.setSelectionMode(ListSelectionModel.SINGLE_INTERVAL_SELECTION);
		courseTable.setRowSelectionAllowed(false);
		courseTable.setModel(model);
		courseTable.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
		TableColumn firstColumn = courseTable.getColumnModel().getColumn(0);
		firstColumn.setPreferredWidth(150);
		firstColumn.setMaxWidth(150);
		firstColumn.setMinWidth(150);
		scrollPane.setViewportView(courseTable);
		
		
		courseTable.addMouseListener(new MouseListener() {
			
			
			@Override
			public void mouseReleased(java.awt.event.MouseEvent e) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void mousePressed(java.awt.event.MouseEvent e) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void mouseExited(java.awt.event.MouseEvent e) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void mouseEntered(java.awt.event.MouseEvent e) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void mouseClicked(java.awt.event.MouseEvent e) {
				int currentColumn = courseTable.getSelectedColumn();
				int currentRow = courseTable.getSelectedRow();
				frame.validate();
				if(currentColumn == 5) {
					Course currentCourse = courseList.get(currentRow);
					//Course javaCourse = new Course();
					new CourseGradebookButtonPage(currentCourse);
					frame.dispose();
				}
				if(currentColumn == 6) {
					Course currentCourse = courseList.get(currentRow);
					new EditCoursePage(currentRow, currentCourse);
					
				}
				if(currentColumn == 7) {
					Course currentCourse = courseList.get(currentRow);
					new DeleteCourseConfirmation(currentRow, currentCourse);
					frame.dispose();
					
				}
					
			}
			
		});
		
		frame.setResizable(false);
		
		
	}
}
