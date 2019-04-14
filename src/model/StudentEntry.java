package model;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;

import data.Writable;


public class StudentEntry implements Writable{
	
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
		dataEntries.add(new GradeEntry(component));
	}
	
	public void deleteDataEntry(Component component) {
//		dataEntries.remove(component);
	}
	
	public DataEntry<?> getDataEnty(String componentName) {
		for (DataEntry entry : dataEntries) {
			if (entry.getComponent().getName().equals(componentName)) {
				return dataEntries.get(dataEntries.indexOf(entry));
			}
		}
		return null;
	}
	
	public Student getStudent() {
		return student;
	}
	
	@Override
	public String getKey() {
		// TODO Auto-generated method stub
		return student.toString();
	}

	@Override
	public ArrayList<String> writeAsRecord() {
		// TODO Auto-generated method stub
		ArrayList<String> res = new ArrayList<String>();
		
		for (DataEntry<?> entry : dataEntries) {
			// add values together with comments in records
			res.add(entry.getData().toString());
			if (entry.hasComment())
				res.add(entry.getComment());
			else
				res.add("");
		}
		return res;
	}

	@Override
	public ArrayList<String> getColumnName() {
		// TODO Auto-generated method stub
		ArrayList<String> res = new ArrayList<String>();
				
		res.add("ID/Name");
		for (DataEntry<?> key : dataEntries) {
			// ensure that each col has a corresponding comment col
			res.add(key.getComponent().getName());
			res.add("Comment");
		}

		return res;
	}
	
	
	// test only
	public ArrayList<DataEntry<?>> getAllData() {
		return dataEntries;
	}
}
