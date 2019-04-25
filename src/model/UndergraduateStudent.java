package model;

public class UndergraduateStudent extends Student{

	public UndergraduateStudent() {
		super(StudentType.UNDERGRADUATE);
	}

	public UndergraduateStudent(String sid, String fName, String mName, String lName) {
		super(sid, fName, mName, lName, StudentType.UNDERGRADUATE);
	}
}
