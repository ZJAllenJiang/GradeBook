package model;

import java.util.ArrayList;

import data.ReadIn;
import data.Writeout;

public abstract class Student implements Writeout, ReadIn{
	public enum StudentType{
		UNDERGRADUATE,
		GRADUATE;
	}
	
	private String sid;
	private Name name;
	private boolean status = true;
	private StudentType type;
	
	public Student(StudentType type){
		this.setType(type);
	}
	
	public Student(String sid, String fName, String mName, String lName, StudentType type) {
		this(type);
		this.sid = sid;
		name = new Name(fName, mName, lName);
	}
	
	public String getSid() {
		return sid;
	}
	
	public void setSid(String sid) {
		this.sid = sid;
	}
	
	public String getFirstName() {
		return name.getFirstName();
	}
	
	public String getMiddleName() {
		return name.getMiddleName();
	}
	
	public String getLastName() {
		return name.getLastName();
	}
	
	public void setName(String fname, String mName, String lName) {
		this.name.setName(fname, mName, lName);
	}
	
	public boolean isStatus() {
		return status;
	}
	
	public void setStatus(boolean status) {
		this.status = status;
	}
	
	public StudentType getType() {
		return type;
	}

	public void setType(StudentType type) {
		this.type = type;
	}

	@Override
	public String toString() {
		return sid + " " + name.toString();
	}

	@Override
	public String getKey() {
		// TODO Auto-generated method stub
		return sid;
	}

	@Override
	public ArrayList<String> writeAsRecord() {
		// TODO Auto-generated method stub
		ArrayList<String> res = new ArrayList<String>();
		res.add(this.getFirstName());
		res.add(this.getMiddleName());
		res.add(this.getLastName());
		if (status)
			res.add("Active");
		else
			res.add("Inactive");
		
		return res;
	}

	@Override
	public ArrayList<String> getColumnName() {
		// TODO Auto-generated method stub
		ArrayList<String> res = new ArrayList<String>();
		res.add("Student ID");
		res.add("First name");
		res.add("Middle initial");
		res.add("Last name");
		res.add("Status");
		
		return res;
	}

	@Override
	public void readFromRowData(String line) {
		// TODO Auto-generated method stub
		
		// the separator of csv is ','
		String[] terms = line.split(",");
		
		// should be one to one mapping 
		if (terms.length != 5) {
			System.out.println("[Student readFromRowData] can't compose object, "
					+ "the rowdata is: " + line);
			return;
		}
		
		this.setSid(terms[0]);
		this.setName(terms[1], terms[2], terms[3]);
		if (terms[4].equals("Active"))
			this.setStatus(true);
		else if (terms[4].equals("Inactive"))
			this.setStatus(false);
		else {
			this.setStatus(false);
			System.out.println("[Student readFromRowData] miss matched type."
					+ " status = " + terms[4]);
		}
	}
}
