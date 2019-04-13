package model;

public class GradeableComponent extends Component{

	public GradeableComponent(String n, boolean isEditable) {
		super(n, isEditable);
	}

	@Override
	public DataEntry<Double> createEntry() {
		return new GradeEntry(this);
	}
}
