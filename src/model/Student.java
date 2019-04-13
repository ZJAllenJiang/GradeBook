package model;

public class Student {
	private String sid;
	private Name name;
	private boolean status = true;
	
	public Student(){
		this("0", "me", "me", "me");
	}
	
	public Student(String sid, String fName, String mName, String lName) {
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
	
	@Override
	public String toString() {
		return sid + " " + name.toString();
	}
}
