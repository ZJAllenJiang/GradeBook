package model;

import java.util.ArrayList;
import data.ReadIn;
import data.Writeout;


public class StudentEntry implements Writeout, ReadIn {
	
	private ArrayList<DataEntry<?>> dataEntries;
	private Student student;
	
	
	public StudentEntry(Student student, ArrayList<CategoryComponent> components) {
		this.student = student;
		dataEntries = new ArrayList<>();
		for (CategoryComponent component : components) {
			if (component instanceof GradeableComponent)
				dataEntries.add(new GradeEntry(component));
			else
				dataEntries.add(new TextEntry(component));
		}
		
	}
	
	public void addToStudentEntry(CategoryComponent component) {
		dataEntries.add(component.createEntry());
	}
	
	public void deleteDataEntry(CategoryComponent component) {
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
			if (entry.getData() != null)
				res.add(entry.getData().toString());
			else
				res.add(" "); // add space to that field indicating empty
			
			
			if (entry.hasComment())
				res.add(entry.getComment());
			else
				res.add(" "); // add space to that field indicating empty
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
	
	@Override
	public void readFromRowData(String line) {
		// TODO Auto-generated method stub
		
		// the separator of csv is ','
		String[] terms = line.split(",");
		
		// ensure that two times of existing components equals to terms.length - 1 
		// one component has a value col together with a comment col
		if (terms.length - 1 != 2 * dataEntries.size()) {
			System.out.println("[StudentEntry readFromRowData] mis matched size. "
					+ "terms.length = " + terms.length + " , size of components = "
					+ dataEntries.size());
			return;
		}
		
		for (int i = 0; i < dataEntries.size(); i++) {
			String data = terms[i * 2 + 1];
			String comment = terms[i * 2 + 2];
			dataEntries.get(i).setDataWithString(data);
			
			// only set comment when there is content
			if (!comment.equals(" "))
				dataEntries.get(i).setComment(comment);
		}
	}
	
	double studentGrade() {
		return 0;
	}
	
	void removeComponent(String componentName) {
		dataEntries.remove(getDataEnty(componentName));
	}
	
}
