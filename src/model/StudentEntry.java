package model;

import java.util.ArrayList;
import java.util.HashMap;

public class StudentEntry {
	private ArrayList<DataEntry<?>> dataEntries;
	private Student student;
	
	
	public StudentEntry(Student student, ArrayList<Component> components) {
		this.student = student;
		dataEntries = new ArrayList<>();
		for (Component component : components) {
			if (component instanceof GradeableComponent)
				dataEntries.add(new GradeEntry(component));
			else
				dataEntries.add(new TextEntry(component));
		}
		
	}
	
	public void updateStudentGradeEntry(Component component) {
//		dataEntries.put(component.getName(), new GradeEntry(component));
	}
	
	public void deleteDataEntry(Component component) {
//		dataEntries.remove(component);
	}
	
	public DataEntry<?> getDataEnty(Component component) {
		for (DataEntry entry : dataEntries) {
			if (entry.getComponent().getName().equals(component.getName())) {
				return dataEntries.get(dataEntries.indexOf(entry));
			}
		}
		return null;
	}
	
	public Student getStudent() {
		return student;
	}
	
}
