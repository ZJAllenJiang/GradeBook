package data;

import java.util.ArrayList;

import model.Course;

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
	
	// get all existing Course in database
	// @return an empty ArrayList if nothing in database
	public static ArrayList<Course> getCourseList() {
		return readCourseList();
	}
}
