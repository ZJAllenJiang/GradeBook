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
	
	public String getName() {
		return name;
	}
	
	public void addStudentEntry(Student student){
		studentEntries.add(new StudentEntry(student));
	}
	
	public void addComponent(ArrayList<Student> students, Component component){
		components.add(component);
		for (Student student : students){
			addStudentEntry(student);
		}
	}
}

class TextCategory extends Category {
	private String content;
	
	public TextCategory() {
		this("Dummy");
	}
	
	public TextCategory(String categName) {
		super(categName);
		content = "";
	}
	
	
	public String getContent() {
		return content;
	}
	
	public void setContent(String content) {
		this.content = content;
	}
	
	@Override
	public String toString() {
		return "TextCategory{" +
				"name='" + name + '\'' +
				", content='" + content + '\'' +
				'}';
	}
}

class Summary extends TextCategory {
	public Summary() {
		this("Summary");
	}
	
	public Summary(String categName) {
		super(categName);
	}
}

class GradeableCategory extends Category {
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