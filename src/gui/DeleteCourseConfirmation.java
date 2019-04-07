package gui;

import java.awt.EventQueue;
import java.awt.Font;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableModel;
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

	public DeleteCourseConfirmation(int currentRow) {
		initialize(currentRow);
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize(int currentRow) {
		frame = new JFrame();
		frame.setVisible(true);
		frame.setBounds(100, 100, 450, 250);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		
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
				frame.dispose();
			}
		});
		yesButton.setBounds(240, 150, 120, 30);
		frame.getContentPane().add(yesButton);
	}

}
