package model;

public class GradableComponent extends Component{

	public GradableComponent(String n, boolean isEditable) {
		super(n, isEditable);
	}

	@Override
	public DataEntry<Double> createEntry() {
		return new GradeEntry(this);
	}
}
