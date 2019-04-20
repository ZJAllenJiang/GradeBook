package model;

import java.util.ArrayList;

public abstract class Category {
	protected String name;
	protected ArrayList<CategoryComponent> components;
	protected ArrayList<StudentEntry> studentEntries;
	
	
	public Category(String categName, ArrayList<Student> students) {
		this.name = categName;
		
		components = new ArrayList<>();
		studentEntries = new ArrayList<>();
		for (Student student : students) {
			studentEntries.add(new StudentEntry(student, components));
		}
	}
	
	public String getName() {
		return name;
	}
	
	public void setName(String newName) {
		name = newName;
	}
	
	public boolean isGradeable() {
		return this instanceof GradeableCategory;
	}
	
	public ArrayList<CategoryComponent> getComponents() {
		return components;
	}
	
	public boolean hasComponent(String componentName) {
		for (CategoryComponent c : components) {
			if (c.getName().equals(componentName)) {
				return true;
			}
		}
		return false;
	}
	
	public ArrayList<StudentEntry> getStudentEntries() {
		return studentEntries;
	}
	
	public void addStudentEntry(Student student) {
		studentEntries.add(new StudentEntry(student, components));
	}
	
	public void addComponent(CategoryComponent component) {
		components.add(component);
		for (StudentEntry studentEntry : studentEntries) {
			studentEntry.addToStudentEntry(component);
		}
	}
	
	public CategoryComponent getComponent(String componentName) {
		for (CategoryComponent c : components) {
			if (c.getName().equals(componentName)) {
				return c;
			}
		}
		return null;
	}
	
	private DataEntry<?> getEntry(int studentEntryIndex, String componentName) {
		DataEntry<?> dataEntry = studentEntries.get(studentEntryIndex).getDataEnty(componentName);
		return dataEntry;
	}
	
	public boolean isComponentGradeable(String componentName) {
		for (CategoryComponent c : components) {
			if (c.getName().equals(componentName)) {
				return isComponentGradeable(c);
			}
		}
		return false;
	}
	
	private boolean isComponentGradeable(CategoryComponent c) {
		return c.isGradeable();
	}
	
	public String getComment(int studentEntryIndex, String componentName) {
		DataEntry<?> dataEntry = getEntry(studentEntryIndex, componentName);
		if (dataEntry == null) {
			//Shouldn't happen...
			return null;
		}
		
		return dataEntry.getComment();
	}
	
	public boolean entryHasComment(int studentEntryIndex, String componentName) {
		DataEntry<?> dataEntry = getEntry(studentEntryIndex, componentName);
		if (dataEntry == null) {
			//Shouldn't happen...
			return false;
		}
		return dataEntry.hasComment();
	}
	
	public boolean validUserEntry(int studentEntryIndex, String componentName, String guiData) {
		DataEntry<?> dataEntry = getEntry(studentEntryIndex, componentName);
		if (dataEntry == null) {
			//Shouldn't happen...
			return false;
		}
		return dataEntry.isValidData(guiData);
	}
	
	public boolean isComponentEditable(String componentName) {
		CategoryComponent c = getComponent(componentName);
		if(c == null) {
			return false;
		}
		return c.isEditable();
	}

	public void deleteComponent(String componentName) {
		// TODO Auto-generated method stub
		System.err.println("TODO: Implement Delete Category.deleteComponent()");
	}
}


