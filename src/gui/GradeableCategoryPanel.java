package gui;

import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class GradeableCategoryPanel extends NamePanel{
	private JTextField percentWeightField;
	
	public GradeableCategoryPanel() {
		super();
	}
	
	public GradeableCategoryPanel(String name, double percentWeight) {
		super(name);
		
		percentWeightField.setText(Double.toString(percentWeight));
	}

	@Override
	protected void setUpGUI() {
		super.setUpGUI();
		this.add(GuiUtil.createVerticalGap(10));

		addPercentWeightPanel();
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

	@Override
	public boolean hasProperData() {
		boolean isGood = super.hasProperData();
		if(!isGood) {
			return false;
		}

		double percentWeight;
		try {
			percentWeight = getPercentWeight();
		}catch(Exception e) {
			return false;
		}
		if(percentWeight <= 0 || 100 < percentWeight) {
			return false;
		}
		
		return true;
	}

	public double getPercentWeight() {
		double maxScore = Double.parseDouble(percentWeightField.getText());
		return maxScore;
	}
}
