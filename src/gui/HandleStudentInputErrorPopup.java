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

public class HandleStudentInputErrorPopup {

	private JFrame frame;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					HandleStudentInputErrorPopup window = new HandleStudentInputErrorPopup();
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
	public HandleStudentInputErrorPopup() {
		initialize();
		
	}
	
	private void initialize() {
		// TODO Auto-generated method stub
		
	}

	/**
	 * @wbp.parser.entryPoint
	 */
	public HandleStudentInputErrorPopup(String str) {
		initialize(str);
	}

	

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize(String str) {
		frame = new JFrame();
		frame.setVisible(true);
		frame.setBounds(100, 100, 450, 200);
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
		frame.setTitle("Input Error");
		
		JLabel tipLabel = new JLabel(str);
		tipLabel.setHorizontalAlignment(SwingConstants.CENTER);
		tipLabel.setFont(new Font("Lucida Grande", Font.PLAIN, 15));
		tipLabel.setBounds(25, 30, 400, 60);
		frame.getContentPane().add(tipLabel);
		
		JButton backButton = new JButton("Back");
		backButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				frame.dispose();
				new CreateStudentPage();
			}
		});
		backButton.setBounds(165, 120, 120, 30);
		frame.getContentPane().add(backButton);
		frame.setResizable(false);
	}

}
