package data;

import java.util.ArrayList;

import model.Course;
import model.Student;

public class DatabaseAPI extends DataUtil{
	// save a course into database
	public static void saveCourse(Course course) {
		save(course);
	}
	
	// check up a course by specifying its name/code/year
	// @return null if no such Course exist
	public static Course loadCourse(String name, String code, int year) {
		return load(name, code, year);
	}
	
	// delete files for a particular course
	// won't throw exception but print out reminder msg if no such course
	public static void dropCourse(String name, String code, int year) {
		drop(name, code, year);
	}
	
	// get all existing Course in database
	// @return an empty ArrayList if nothing in database
	public static ArrayList<Course> getCourseList() {
		return readCourseList();
	}
	
	// read a student list from a csv file
	// The file should have five cols that are :
	//	Student ID: String
	//	First name: String
	//	Middle initial: String
	//	Last name: String
	//	Status: only two values are acceptable for this field, 
	//          (Active) vs (Inactive)
	public static ArrayList<Student> importStudents(String filename) {
		return readStudent(filename); 
	}
}
