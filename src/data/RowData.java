package data;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;

import model.Component;
import model.DataEntry;
import model.GradeEntry;
import model.Student;

public class RowData implements Writable {
	private HashMap<String, DataEntry<?>> dataEntries;
	private Student student;
	
	public RowData(Student student) {
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
	
	@Override
	public String getKey() {
		// TODO Auto-generated method stub
		return student.toString();
	}

	@Override
	public ArrayList<String> writeAsRecord() {
		// TODO Auto-generated method stub
		ArrayList<String> res = new ArrayList<String>();
		
		ArrayList<String> keys = new ArrayList<String>();
		keys.addAll(dataEntries.keySet());
		keys.sort(new Comparator<String>() {
			@Override
			public int compare(String o1, String o2) {
				// TODO Auto-generated method stub
				return o1.compareTo(o2);
			}
		});
		
		for (String key : keys) {
			DataEntry<?> entry = dataEntries.get(key);
			res.add(entry.getData().toString());
			if (entry.getComment() == null)
				res.add("");
			else
				res.add(entry.getComment());
		}
		return res;
	}

	@Override
	public ArrayList<String> getColumnName() {
		// TODO Auto-generated method stub
		ArrayList<String> res = new ArrayList<String>();
		
		// sort by key, making the same order for each dataentry
		ArrayList<String> keys = new ArrayList<String>();
		keys.addAll(dataEntries.keySet());
		keys.sort(new Comparator<String>() {
			@Override
			public int compare(String o1, String o2) {
				// TODO Auto-generated method stub
				return o1.compareTo(o2);
			}
		});
		
		res.add("ID/Name");
		for (String key : keys) 
			res.add(key);

		return res;
	}

}
