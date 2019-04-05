package model;

import java.util.ArrayList;

public class StudentEntry {
	ArrayList<DataEntry> dataEntries;
	Student student;
	
	public StudentEntry(Student student) {
		this.student = student;
		dataEntries = new ArrayList<>();
	}
}
