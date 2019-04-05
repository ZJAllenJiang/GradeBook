package model;

import java.util.ArrayList;

abstract class Caterogy {
	String name;
	ArrayList<Component> components;
	ArrayList<StudentEntry> studentEntries;
	
	public Caterogy(String categName) {
		this.name = categName;
		components = new ArrayList<>();
		studentEntries = new ArrayList<>();
	}
}

class TextCategory extends Caterogy {
	public TextCategory(String name) {
		super(categName);
	}
}

class Summary extends TextCategory {
	public Summary(String categName) {
		super(categName);
	}
}

class GradeableCategory extends Caterogy {
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