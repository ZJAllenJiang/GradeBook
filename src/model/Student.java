package model;

import java.util.ArrayList;

import data.ReadIn;
import data.Writeout;

public abstract class Student implements Writeout, ReadIn{
	public enum StudentType{
		UNDERGRADUATE,
		GRADUATE;
	}
	
	protected String sid;
	protected Name name;
	protected boolean status = true;
	protected StudentType type;
	
	public Student(StudentType type){
		this.sid = "";
		this.name = new Name("", "", "");
		this.status = true;
		this.setType(type);
	}
	
	public Student(String sid, String fName, String mName, String lName, boolean status, StudentType type) {
		this(type);
		this.sid = sid;
		name = new Name(fName, mName, lName);
		this.status = status;
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
	abstract public String getKey();

	@Override
	abstract public ArrayList<String> writeAsRecord();

	@Override
	abstract public ArrayList<String> getColumnName();

	@Override
	abstract public void readFromRowData(String line);
	
	
	// factory mode, compose an object by specifying type
	public static Student factory(StudentType type) {
		Student x = null;
		switch(type) {
		case GRADUATE:
			x = new GraduateStudent();
			break;
		case UNDERGRADUATE:
			x = new UndergraduateStudent();
			break;
		}
		
		return x;
	}
}
