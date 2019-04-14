package model;

public class GradeableComponent extends Component{
	private double weight;

	public GradeableComponent(String n, boolean isEditable, double weight) {
		super(n, isEditable);
		setWeight(weight);
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
}
