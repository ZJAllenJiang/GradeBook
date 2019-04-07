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


import java.awt.ScrollPane;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JPanel;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.ActionEvent;
import javax.swing.ListSelectionModel;

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
		frame = new JFrame();
		frame.setVisible(true);
		frame.setBounds(100, 100, 600, 400);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		
		JLabel courseLabel = new JLabel("Courses");
		courseLabel.setHorizontalAlignment(SwingConstants.CENTER);
		courseLabel.setFont(new Font("Lucida Grande", Font.PLAIN, 25));
		courseLabel.setBounds(240, 17, 120, 60);
		frame.getContentPane().add(courseLabel);
		
		JButton addCourseButton = new JButton("Add Course +");
		addCourseButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				//frame.setVisible(false);
				new CreateCoursePage();
			}
		});
		addCourseButton.setFont(new Font("Lucida Grande", Font.PLAIN, 18));
		addCourseButton.setBounds(225, 70, 150, 35);
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
		scrollPane.setBounds(25, 110, 550, 250);
		frame.getContentPane().add(scrollPane);
		
		String[] columnName = {"Name", "Code", "Year", "Semester", "Status", "Action1", "Action2", "Action3"};
		Object[][] courseInfo= {
				{"Java Development", "CS591", "2019", "Spring", "Active", "View", "Edit", "Delete"}
		};
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
					// new Gradebook page
				}
				if(currentColumn == 6) {
					new EditCoursePage(currentRow);
					
				}
				if(currentColumn == 7) {
					new DeleteCourseConfirmation(currentRow);
									
				}
					
			}
			
		});
		

		
		
	}
}
