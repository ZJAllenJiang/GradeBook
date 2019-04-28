package data;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

import model.Category;
import model.Course;
import model.Semester;
import model.Student;

public class DatabaseAPI extends DataUtil{
	// save a course into database
	public static void saveCourse(Course course) {
		save(course);
	}
	
	// check up a course by specifying its name/code/year
	// @return null if no such Course exist
	public static Course loadCourse(String name, String code, int year, Semester semester) {
		return load(name, code, year, semester);
	}
	
	// delete files for a particular course
	// won't throw exception but print out reminder msg if no such course
	public static void dropCourse(String name, String code, int year, Semester semester) {
		drop(name, code, year, semester);
	}
	
	// get all existing Course in database
	// @return an empty ArrayList if nothing in database
	public static ArrayList<Course> getCourseList() {
		ArrayList<Course> courses = readCourseList();
		Collections.sort(courses, new Comparator<Course>() {
			@Override
			public int compare(Course o1, Course o2) {
				int result = Integer.compare(o1.getYear(), o2.getYear());
				if(result != 0) {
					return result;
				}
				
				result = o1.getSemester().compareTo(o2.getSemester());
				if(result != 0) {
					return result;
				}
				
				result = o1.getCode().compareTo(o2.getCode());
				if(result != 0) {
					return result;
				}
				
				return o1.getName().compareTo(o2.getName());
			}
		});
		
		return courses;
	}
	
	// get scheme from a existing course
	// @return ArrayList with lenth == 0 if such a course doesn't exist
	private static ArrayList<Category> getScheme(String name, String code, int year, Semester semester) {
		return getCategories(name, code, year, semester);
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
