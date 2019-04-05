package model;

import java.util.ArrayList;

public class Course {
	private String name;
	private String code;
	private ArrayList<Caterogy> categories;
	private ArrayList <Student> students;
	private Summary s;
	
	public Course(String name, String code) {
		this.name = name;
		this.code = code;
	}
}
