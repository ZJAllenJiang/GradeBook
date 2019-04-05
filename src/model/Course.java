package model;

import java.util.ArrayList;

public class Course {
	String name;
	String code;
	ArrayList<Caterogy> categories;
	ArrayList <Student> students;
	Summary s;
	
	public Course(String name, String code) {
		this.name = name;
		this.code = code;
	}
}
