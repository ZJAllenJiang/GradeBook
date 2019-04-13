package model;

import java.util.ArrayList;
import java.util.HashMap;

public class StudentEntry {
	private HashMap<String, DataEntry<?>> dataEntries;
	private Student student;
	
	public StudentEntry(Student student) {
		this.student = student;
		dataEntries = new HashMap<>();
		
	}
	
	public void updateStudentGradeEntry(Component component) {
		dataEntries.put(component.getName(), new GradeEntry(component));
	}
	
	public void deleteDataEntry(String componentName) {
		dataEntries.remove(componentName);
	}
	
	public DataEntry<?> getDataEnty(Component component) {
		return dataEntries.get(component.getName());
	}

	public Student getStudent() {
		return student;
	}
	
}
