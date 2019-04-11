package model;

import com.sun.org.apache.xerces.internal.xs.StringList;

import java.util.ArrayList;

public class Course {
	
	//------------ Course charateristics ------------
	private String name;
	private String code;
	private int year;
	private boolean status;
	
	//------------ Course contents ------------
	private ArrayList<Category> categories;
	private ArrayList<Student> students;
	private Summary summary;
	
	
	//------------ Constructors ------------
	public Course(String name, String code, int year) {
		this.name = name;
		this.code = code;
		this.year = year;
		categories = new ArrayList<>();
		students = new ArrayList<>();
		summary = new Summary();
		status = true;
	}
	
	public Course() {
		this("dummy", "dummy", 0);
		
		categories.add(new GradeableCategory());
		Student student = new Student();
		students.add(student);
		Component component = new GradableComponent("dummy", true);
		categories.get(0).addComponent(students, component);
		categories.get(0).addStudentEntry(student);
	}
	
	//------------ Characteristics Getters & Setters ------------
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public String getCode() {
		return code;
	}
	
	public void setCode(String code) {
		this.code = code;
	}
	
	public int getYear() {
		return year;
	}
	
	public void setYear(int year) {
		this.year = year;
	}
	
	public boolean status() {
		return status;
	}
	
	public void setStatus(boolean status) {
		this.status = status;
	}
	
	public ArrayList<Category> getAllCategories() {
		return this.categories;
	}
	
	public Category getCategory(int index) {
		return categories.get(index);
	}
	
	public Summary getSummary() {
		return this.summary;
	}
	
	
	//------------ Methods for adding a category ------------
	public void addCategory(double weight, String name, boolean isGradeable) {
		if (isGradeable)
			addGradeable(weight, name);
		else
			addNonGradeable(name);
	}
	
	private void addGradeable(double weight, String name) {
		categories.add(new GradeableCategory(weight, name));
	}
	
	private void addNonGradeable(String name) {
		categories.add(new TextCategory(name));
	}
	
	public void deleteCategory(String name) {
		for (Category categ : categories) {
			if (categ.getName().equals(name)) {
				categories.remove(categ);
				break;
			}
		}
	}
	
	//------------- Methods for adding students ------------
	public void addStudent(String sId, String fName, String mName, String lName) {
		Student student = new Student(sId, fName, mName, lName);
		students.add(student);
		for (Category category : categories) {
			category.addStudentEntry(student);
		}
	}
	
	public void bulkLoadStudents(String csvName) {
	
	}
	
	public void deleteStudent(String sId) {
		for (Student stud : students) {
			if (stud.getSid().equals(sId)) {
//				categories.deleteStudent(students.indexOf(stud));
				students.remove(stud);
			}
		}
	}
}
