package model;



import model.GradeableComponent.DataEntryMode;

import java.util.ArrayList;

public class Course {
	
	//------------ Course charateristics ------------
	private String name;
	private String code;
	private int year;
	private Semester semester;
	private boolean status;
	
	//------------ Course contents ------------
	//DO NOT ADD TO THIS DIRECTLY - USE THE METHOD Course.addCategory
	private ArrayList<Category> categories;
	
	private ArrayList<Student> students;
	private Summary summary;
	
	
	//------------ Constructors ------------
	public Course(String name, String code, int year, Semester semester) {
		this.name = name;
		this.code = code;
		this.year = year;
		this.semester = semester;
		
		categories = new ArrayList<>();
		students = new ArrayList<>();
		summary = new Summary(this);
		status = true;
	}
	
	
	//  test for writing database
	public Course() {
		this("Java", "591", 2019, Semester.Fall);

		this.addStudent("U123", "Peter", "J", "Patrick");
		this.addStudent("U124", "John", "H", "Will");
		this.addStudent("U125", "Kate", "", "Rose");
		
		addCategory(new GradeableCategory(1.0, "Homework", students));
		CategoryComponent hw1 = new GradeableComponent("HW1", true, .1, 50, DataEntryMode.POINTS_EARNED);
		CategoryComponent hw2 = new GradeableComponent("HW2", true, .5, 60, DataEntryMode.POINTS_LOST);
		CategoryComponent hw3 = new GradeableComponent("HW3", true, .4, 50, DataEntryMode.PERCENTAGE);
		
		categories.get(0).addComponent(hw1);
		categories.get(0).addComponent(hw2);
		categories.get(0).addComponent(hw3);
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
	
	public ArrayList<Student> getAllStudents() {
		return this.students;
	}
	
	public Category getCategory(int index) {
		return categories.get(index);
	}
	
	public Category getCategory(String categoryName) {
		for(Category c : categories) {
			if(c.getName().equals(categoryName)) {
				return c;
			}
		}
		return null;
	}
	
	public Summary getSummary() {
		return this.summary;
	}
	
	public Semester getSemester() {
		return this.semester;
	}
	
	public void addSummary(Summary other) {
		this.summary = other;
	}
	
	
	//------------ Methods for managing a category ------------
	public void deleteCategory(String name) {
		Category oldCategory = null;
		for (Category categ : categories) {
			if (categ.getName().equals(name)) {
				categories.remove(categ);
				oldCategory = categ;
				break;
			}
		}
		
		if(oldCategory != null) {
			summary.removeComponentIfApplicable(oldCategory);
		}
	}
	
	// add category by passing an existing category
	public void addCategory(Category category) {
		categories.add(category);
		
		summary.addComponentIfApplicable(category);
	}
	
	//------------- Methods for adding students ------------
	public void addStudent(String sId, String fName, String mName, String lName) {
		Student student = new Student(sId, fName, mName, lName);
		students.add(student);
		
		summary.addStudentEntry(student);
		for (Category category : categories) {
			category.addStudentEntry(student);
		}
	}
	
	public void bulkLoadStudents(String csvName) {
	
	}
	
	public void deleteStudent(Student student) {
		students.remove(student);
		summary.deleteStudentEntry(student);
		for (Category category : categories) {
			category.deleteStudentEntry(student);
		}
	}
}
