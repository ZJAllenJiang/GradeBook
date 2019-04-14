package model;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

public class GradeableCategory extends Category {
	double weight;
	
	public GradeableCategory() {
		this(0.5, "dummy", new ArrayList<>(Collections.singletonList(new Student())));
		
	}
	
	public GradeableCategory(double weight, String categName, ArrayList<Student> students) {
		super(categName, students);
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