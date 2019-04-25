package model;

import javafx.util.Pair;

import java.awt.*;
import java.lang.reflect.Array;
import java.util.ArrayList;

public abstract class Category {
	protected String name;
	protected ArrayList<CategoryComponent> components;
	protected ArrayList<StudentEntry> studentEntries;
	protected TextComponent averages;
	
	
	public Category(String categName, ArrayList<Student> students) {
		this.name = categName;
		
		components = new ArrayList<>();
		studentEntries = new ArrayList<>();
		averages = new TextComponent("Total", false);
		averages.createEntry();
		
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
	
	public void deleteStudentEntry(Student student) {
		StudentEntry entryToDelete = getStudentEntry(student);
		if (entryToDelete != null) {
			studentEntries.remove(entryToDelete);
		}
	}
	
	protected StudentEntry getStudentEntry(Student student) {
		for (StudentEntry entry : studentEntries) {
			if (entry.getStudent().equals(student)) {
				return entry;
			}
		}
		return null;
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
		if (c == null) {
			return false;
		}
		return c.isEditable();
	}
	
	public void deleteComponent(String componentName) {
		// TODO Auto-generated method stub
		System.err.println("TODO: Implement Delete Category.deleteComponent()");
		for (CategoryComponent c : components) {
			
			if (c.getName().equals(componentName)) {
				for (StudentEntry entry : studentEntries)
					entry.removeComponent(componentName);
				components.remove(c);
				return;
			}
		}
		
	}
	
	
	Statistics componentStatistics(String componentName) {
		
		
		CategoryComponent component = null;
		
		for (CategoryComponent c : components) {
			if (c.getName().equals(componentName)) {
				component = c;
				break;
			}
		}
		
		if (component == null)
			return new Statistics(null);
		
		if (!(component instanceof GradeableComponent))
			return new Statistics(null);
		
		ArrayList<Double> componentGrades = new ArrayList<>();
		
		for (StudentEntry studentEntry : studentEntries) {
			if (studentEntry.getStudent().isStatus())
				componentGrades.add((Double) studentEntry.getDataEnty(componentName).getData());
		}
		
		return new Statistics(componentGrades);
	}
}


