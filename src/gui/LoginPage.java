package gui;

import java.awt.Dimension;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.SwingConstants;
import java.awt.Font;
import java.awt.Toolkit;

import javax.swing.JTextField;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JPasswordField;

public class LoginPage {

	private JFrame frame;
	private JTextField userNametextField;
	private JPasswordField passwordField;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					LoginPage window = new LoginPage();
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
	public LoginPage() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setVisible(true);
		frame.setBounds(100, 100, 600, 400);
		//frame.setLocationRelativeTo(null);
		// set the window in the middle of screen
		int windowWidth = frame.getWidth(); 
		int windowHeight = frame.getHeight(); 
		Toolkit kit = Toolkit.getDefaultToolkit(); 
		Dimension screenSize = kit.getScreenSize(); 
		int screenWidth = screenSize.width; 
		int screenHeight = screenSize.height; 
		frame.setLocation(screenWidth / 2 - windowWidth / 2, screenHeight / 2 - windowHeight / 2);
		
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		frame.setTitle("GrA+deBook Login");
		
		JLabel welcomeLabel = new JLabel("Welcome");
		welcomeLabel.setFont(new Font("Lucida Grande", Font.PLAIN, 25));
		welcomeLabel.setHorizontalAlignment(SwingConstants.CENTER);
		welcomeLabel.setBounds(240, 17, 120, 60);
		frame.getContentPane().add(welcomeLabel);
		
		JLabel userNameLabel = new JLabel("Username");
		userNameLabel.setHorizontalAlignment(SwingConstants.CENTER);
		userNameLabel.setFont(new Font("Lucida Grande", Font.PLAIN, 20));
		userNameLabel.setBounds(140, 108, 100, 40);
		frame.getContentPane().add(userNameLabel);
		
		JLabel passwordLabel = new JLabel("Password");
		passwordLabel.setHorizontalAlignment(SwingConstants.CENTER);
		passwordLabel.setFont(new Font("Lucida Grande", Font.PLAIN, 20));
		passwordLabel.setBounds(140, 180, 100, 40);
		frame.getContentPane().add(passwordLabel);
		
		userNametextField = new JTextField();
		userNametextField.setBounds(330, 108, 180, 40);
		frame.getContentPane().add(userNametextField);
		userNametextField.setColumns(10);
		
		JButton loginButton = new JButton("Login");
		loginButton.setFont(new Font("Lucida Grande", Font.PLAIN, 16));
		loginButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(userNametextField.getText().equals("cpk") && passwordField.getText().equals("cpk")) {
					frame.dispose();
					new CourseCollectionPage();
				}else {
					frame.dispose();
					new loginErrorPage();
				}
				
			}
		});
		
		passwordField = new JPasswordField();
		passwordField.setBounds(330, 180, 180, 40);
		frame.getContentPane().add(passwordField);
		loginButton.setBounds(240, 280, 120, 40);
		frame.getContentPane().add(loginButton);
		
		frame.setResizable(false);
	}
}
