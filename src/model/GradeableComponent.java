package model;

public class GradeableComponent extends CategoryComponent{
	private double weight;
	private double maxScore;
	private DataEntryMode dateEntryMode;
	
	public enum DataEntryMode{
		POINTS_EARNED,
		POINTS_LOST,
		PERCENTAGE;
	}

	public GradeableComponent(String n, boolean isEditable, 
			double weight, double maxScore, DataEntryMode dateEntryMode) {
		super(n, isEditable);
		setWeight(weight);
		setMaxScore(maxScore);
		setDateEntryMode(dateEntryMode);
	}

	@Override
	public DataEntry<Double> createEntry() {
		return new GradeEntry(this);
	}

	public double getWeight() {
		return weight;
	}

	public void setWeight(double weight) {
		this.weight = weight;
	}

	public double getMaxScore() {
		return maxScore;
	}

	public void setMaxScore(double maxScore) {
		this.maxScore = maxScore;
	}

	public DataEntryMode getDateEntryMode() {
		return dateEntryMode;
	}

	public void setDateEntryMode(DataEntryMode dateEntryMode) {
		this.dateEntryMode = dateEntryMode;
	}
}
