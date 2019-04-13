package model;

public class GradeableCategory extends Category {
	double weight;
	
	public GradeableCategory() {
		this(0.5, "dummy");
	}
	
	public GradeableCategory(double weight, String categName) {
		super(categName);
		this.weight = weight;
	}
	
	public double getWeight() {
		return weight;
	}
	
	public void setWeight(double weight) {
		this.weight = weight;
	}
	
	@Override
	public String toString() {
		return this.name + " " + this.weight + "%";
	}
}