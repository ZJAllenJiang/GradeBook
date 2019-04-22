package gui;

import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class StatisticsPanel extends JPanel{

	public StatisticsPanel(double median, double mean, double stdDev) {
		super();
		setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		
		setUpGUI(median, mean, stdDev);
	}

	private void setUpGUI(double median, double mean, double stdDev) {
		addDataPanel("Median", median);
		addDataPanel("Average", mean);
		addDataPanel("Standard Deviation", stdDev);
	}

	private void addDataPanel(String label, double value) {
		JPanel dataPanel = new JPanel();
		dataPanel.setLayout(new BoxLayout(dataPanel, BoxLayout.X_AXIS));
		JLabel dataLabel = new JLabel(label + ": ");
		dataPanel.add(dataLabel);
		JLabel data = new JLabel(Double.toString(value));
		dataPanel.add(data);
		this.add(dataPanel);
	}
}
