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


