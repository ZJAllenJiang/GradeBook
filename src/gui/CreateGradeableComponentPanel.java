package gui;

import javax.swing.BoxLayout;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import model.GradeableComponent.DataEntryMode;

public class CreateGradeableComponentPanel extends JPanel{
	private JTextField nameField;
	private JTextField maxScoreField;
	private JTextField percentWeightField;
	private JComboBox<DataEntryMode> entryModeField;
	
	public CreateGradeableComponentPanel() {
		super();
		setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		
		setUpGUI();
	}

	private void setUpGUI() {
		addNamePanel();
		this.add(GuiUtil.createVerticalGap(10));

		addMaxScorePanel();
		this.add(GuiUtil.createVerticalGap(10));
		
		addPercentWeightPanel();
		this.add(GuiUtil.createVerticalGap(10));

		addDataEntryPanel();
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

	private void addMaxScorePanel() {
		JPanel maxScorePanel = new JPanel();
		maxScorePanel.setLayout(new BoxLayout(maxScorePanel, BoxLayout.X_AXIS));
		JLabel maxScoreLabel = new JLabel("Max Score: ");
		maxScorePanel.add(maxScoreLabel);
		maxScoreField = createTextField(5);
		maxScorePanel.add(maxScoreField);
		this.add(maxScorePanel);
	}
	
	private void addPercentWeightPanel() {
		JPanel percentWeightPanel = new JPanel();
		percentWeightPanel.setLayout(new BoxLayout(percentWeightPanel, BoxLayout.X_AXIS));
		JLabel percentWeightLabel = new JLabel("Percent Weight: ");
		percentWeightPanel.add(percentWeightLabel);
		percentWeightField = createTextField(5);
		percentWeightPanel.add(percentWeightField);
		this.add(percentWeightPanel);
	}
	
	private void addDataEntryPanel() {
		JPanel dataEntryPanel = new JPanel();
		dataEntryPanel.setLayout(new BoxLayout(dataEntryPanel, BoxLayout.X_AXIS));
		JLabel dataEntryLabel = new JLabel("Grade Entry Mode: ");
		dataEntryPanel.add(dataEntryLabel);
		entryModeField = new JComboBox<DataEntryMode>(DataEntryMode.values());
		dataEntryPanel.add(entryModeField);
		this.add(dataEntryPanel);
	}
	
	private JTextField createTextField(int numColumns) {
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
		
		double maxScore;
		try {
			maxScore = getMaxScore();
		}catch(Exception e) {
			return false;
		}
		if(maxScore <= 0) {
			return false;
		}
		
		double percentWeight;
		try {
			percentWeight = getPercentWeight();
		}catch(Exception e) {
			return false;
		}
		if(percentWeight <= 0 || 100 <= percentWeight) {
			return false;
		}
		
		return true;
	}
	
	public String getName() {
		return nameField.getText();
	}
	
	public double getMaxScore() {
		double maxScore = Double.parseDouble(maxScoreField.getText());
		return maxScore;
	}
	
	public double getPercentWeight() {
		double maxScore = Double.parseDouble(percentWeightField.getText());
		return maxScore;
	}
	
	public DataEntryMode getDataEntryMode() {
		return (DataEntryMode) entryModeField.getSelectedItem();
	}
}
