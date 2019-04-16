package gui;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.SwingConstants;
import java.awt.Font;
import javax.swing.JTextField;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class LoginPage {

	private JFrame frame;
	private JTextField userNametextField;
	private JTextField passwordtextField;

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
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		
		JLabel welcomeLabel = new JLabel("Welcome");
		welcomeLabel.setFont(new Font("Lucida Grande", Font.PLAIN, 25));
		welcomeLabel.setHorizontalAlignment(SwingConstants.CENTER);
		welcomeLabel.setBounds(240, 17, 120, 60);
		frame.getContentPane().add(welcomeLabel);
		
		JLabel userNameLabel = new JLabel("Username");
		userNameLabel.setHorizontalAlignment(SwingConstants.CENTER);
		userNameLabel.setFont(new Font("Lucida Grande", Font.PLAIN, 20));
		userNameLabel.setBounds(150, 108, 100, 40);
		frame.getContentPane().add(userNameLabel);
		
		JLabel passwordLabel = new JLabel("Password");
		passwordLabel.setHorizontalAlignment(SwingConstants.CENTER);
		passwordLabel.setFont(new Font("Lucida Grande", Font.PLAIN, 20));
		passwordLabel.setBounds(150, 180, 100, 40);
		frame.getContentPane().add(passwordLabel);
		
		userNametextField = new JTextField();
		userNametextField.setBounds(330, 108, 180, 40);
		frame.getContentPane().add(userNametextField);
		userNametextField.setColumns(10);
		
		passwordtextField = new JTextField();
		passwordtextField.setBounds(330, 180, 180, 40);
		frame.getContentPane().add(passwordtextField);
		passwordtextField.setColumns(10);
		
		JButton loginButton = new JButton("Login");
		loginButton.setFont(new Font("Lucida Grande", Font.PLAIN, 16));
		loginButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(userNametextField.getText().equals("cpk") && passwordtextField.getText().equals("cpk")) {
					frame.dispose();
					new CourseCollectionPage();
				}else {
					frame.dispose();
					new loginErrorPage();
				}
				
			}
		});
		loginButton.setBounds(240, 300, 120, 40);
		frame.getContentPane().add(loginButton);
		
		frame.setResizable(false);
	}
}
