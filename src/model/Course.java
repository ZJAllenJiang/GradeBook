package model;


import model.GradeableComponent.DataEntryMode;
import model.Student.StudentType;

import java.util.ArrayList;
import java.util.Objects;

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
		
		this.addStudent("U123", "Peter", "J", "Patrick", true, StudentType.UNDERGRADUATE);
		this.addStudent("U124", "John", "H", "Will", true, StudentType.UNDERGRADUATE);
		this.addStudent("U125", "Kate", "", "Rose", true, StudentType.UNDERGRADUATE);
		
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
	
	public Category getCategoryByName(String categName) {
		for (Category category : categories) {
			if (category.getName().equals(categName)) {
				return category;
			}
		}
		return null;
	}
	
	public ArrayList<Student> getAllStudents() {
		return this.students;
	}
	
	public Student getStudentById(String sId) {
		for (Student student : students) {
			if (student.getSid().equals(sId))
				return student;
		}
		return null;
	}
	
	public Category getCategory(int index) {
		return categories.get(index);
	}
	
	public Category getCategory(String categoryName) {
		for (Category c : categories) {
			if (c.getName().equals(categoryName)) {
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
	
	public void setSemester(Semester semester) {
		this.semester = semester;
	}
	
	public void addSummary(Summary other) {
		this.summary = other;
	}
	
	
	//------------ Methods for managing a category ------------
	public void addGradeable(double weight, String name) {
		categories.add(new GradeableCategory(weight, name, students));
	}
	
	public void addNonGradeable(String name) {
		categories.add(new TextCategory(name, students));
	}
	
	// add category by passing an existing category
//	public void addCategory(Category category) {
//		categories.add(category);
//	}
	
	public void deleteCategory(String name) {
		Category oldCategory = null;
		for (Category categ : categories) {
			if (categ.getName().equals(name)) {
				categories.remove(categ);
				oldCategory = categ;
				break;
			}
		}
		
		if (oldCategory != null) {
			summary.removeComponentIfApplicable(oldCategory);
		}
	}
	
	//	 add category by passing an existing category
	public boolean addCategory(Category category) {
		
		if (findCategory((category.getName())))
			return false;
		
		categories.add(category);
		summary.addComponentIfApplicable(category);
		return true;
	}
	
	private boolean findCategory(String categoryName) {
		for (Category categ : categories) {
			if (categ.getName().equals(categoryName))
				return true;
		}
		return false;
	}
	
	//------------- Methods for adding students ------------
	public boolean addStudent(String sId, String fName, String mName, String lName, boolean status, StudentType type) {
		Student student = null;
		
		for (Student stud : students) {
			if (stud.getSid().equals(sId))
				return false;
		}
		
		switch (type) {
			case UNDERGRADUATE:
				student = new UndergraduateStudent(sId, fName, mName, lName, status);
				break;
			case GRADUATE:
				student = new GraduateStudent(sId, fName, mName, lName, status);
				break;
		}
		
		if (student != null) {
			students.add(student);
			summary.addStudentEntry(student);
			for (Category category : categories) {
				category.addStudentEntry(student);
			}
		}
		return true;
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
	
	public Statistics categoryStatistics(String categoryName) {
		for (Category category : categories) {
			if (category.getName().equals(categoryName))
				return categoryStatistics(category);
		}
		return new Statistics(null);
	}
	
	public Statistics categoryStatistics(Category category) {
		ArrayList<Double> grades = new ArrayList<>();
		Double grade = null;
		for (StudentEntry studentEntry : category.getStudentEntries()) {
			if (category instanceof GradeableCategory) {
				if (studentEntry.getStudent().isStatus()) {
					grade = ((GradeableCategory) category).computeOverallGrade(studentEntry);
					if (grade == null)
						return null;
					grades.add(grade);
				}
			}
		}
		return new Statistics(grades);
	}
	
	public Statistics courseStatistics() {
		ArrayList<Double> grades = new ArrayList<>();
		Double grade = null;
		
		for (StudentEntry studentEntry : summary.getStudentEntries()) {
			grade = summary.computeOverallGrade(studentEntry);
			if (studentEntry.getStudent().isStatus()) {
				if (grade == null)
					return null;
				grades.add(grade);
			}
		}
		return new Statistics(grades);
	}
	
	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		Course course = (Course) o;

		return year == course.year &&
				Objects.equals(code, course.code) &&
				semester == course.semester;
	}
	
	public String getCourseSummaryInfo() {
		String info = "Categories: ";
		
		for(Category c : this.getAllCategories()) {
			info = info + c.getName() + " ";
		}
		
		return info;
	}
}
