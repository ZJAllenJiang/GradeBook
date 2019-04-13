package model;

import java.util.ArrayList;

abstract class Category {
	protected String name;
	protected ArrayList<Component> components;
	protected ArrayList<StudentEntry> studentEntries;
	
	public Category(String categName) {
		this.name = categName;
		components = new ArrayList<>();
		studentEntries = new ArrayList<>();
	}
}

class TextCategory extends Category {
	public TextCategory(String categName) {
		super(categName);
	}
}

class Summary extends TextCategory {
	public Summary(String categName) {
		super(categName);
	}
}

class GradeableCategory extends Category {
	double weight;
	
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
		return weight + "%";
	}
}