package model;


public class GraduateStudent extends Student {
	
	public GraduateStudent() {
		super(StudentType.GRADUATE);
	}

	public GraduateStudent(String sid, String fName, String mName, String lName) {
		super(sid, fName, mName, lName, StudentType.GRADUATE);
	}

}
