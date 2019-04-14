package model;

import java.util.ArrayList;

public abstract class Category {
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
	
	public ArrayList<Component> getComponents() {
		return components;
	}

	public ArrayList<StudentEntry> getStudentEntries() {
		return studentEntries;
	}

	public void addStudentEntry(Student student){
		studentEntries.add(new StudentEntry(student, components));
	}
	
	public void addComponent(ArrayList<Student> students, Component component){
		components.add(component);
		for (Student student : students){
			addStudentEntry(student);
		}
	}
	
	private DataEntry<?> getEntry(int studentEntryIndex, String componentName){
		DataEntry<?> dataEntry = studentEntries.get(studentEntryIndex).getDataEnty(componentName);
		return dataEntry;
	}

	public boolean isComponentGradeable(String columnName) {
		for(Component c : components) {
			if(c.getName().equals(columnName)) {
				return isComponentGradeable(c);
			}
		}
		return false;
	}

	private boolean isComponentGradeable(Component c) {
		return c instanceof GradeableComponent;
	}

	public String getComment(int studentEntryIndex, String componentName) {
		DataEntry<?> dataEntry = getEntry(studentEntryIndex, componentName);
		if(dataEntry == null) {
			//Shouldn't happen...
			return null;
		}
		
		return dataEntry.getComment();
	}
	
	public boolean componentHasComment(int studentEntryIndex, String componentName) {
		DataEntry<?> dataEntry = getEntry(studentEntryIndex, componentName);
		if(dataEntry == null) {
			//Shouldn't happen...
			return false;
		}
		return dataEntry.hasComment();
	}
}


