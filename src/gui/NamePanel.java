package gui;

import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;


public class NamePanel extends JPanel {
	private JTextField nameField;
	
	public NamePanel() {
		super();
		setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		
		setUpGUI();
	}
	
	public NamePanel(String name) {
		this();
		
		nameField.setText(name);
	}

	protected void setUpGUI() {
		addNamePanel();
	}

	private void addNamePanel() {
		JPanel namePanel = new JPanel();
		namePanel.setLayout(new BoxLayout(namePanel, BoxLayout.X_AXIS));
		JLabel nameLabel = new JLabel("Name: ");
		namePanel.add(nameLabel);
		nameField = createTextField(12);
		namePanel.add(nameField);
		this.add(namePanel);
	}

	protected JTextField createTextField(int numColumns) {
		JTextField textField = new JTextField();  
		textField.setColumns(numColumns);
		textField.setMaximumSize(textField.getPreferredSize());
		return textField;
	}
	
	public boolean hasProperData() {
		String name = getName();
		if(name == null || name.equals("")) {
			return false;
		}
		
		return true;
	}
	
	public String getName() {
		return nameField.getText();
	}
}
