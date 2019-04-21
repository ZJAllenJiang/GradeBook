package model;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

public class Summary extends TextCategory implements OverallGradeable {
	public static final String SUMMARY = "Summary";
	
	public static final String STUDENT_ID = "ID";
	public static final String FIRST_NAME = "First Name";
	public static final String MIDDLE_NAME = "Middle Name";
	public static final String LAST_NAME = "Last Name";
	public static final String STATUS = "Enrolled?";
	
	private Course course;
	
	public Summary(Course course) {
		super(SUMMARY, new ArrayList<Student>());
		
		this.course = course;
	}
	
	public void addComponentIfApplicable(Category newCategory) {
		if(!newCategory.isGradeable()) {
			return;
		}
		
		String name = newCategory.getName();
		TextComponent newComponent = new TextComponent(name, false);
		this.addComponent(newComponent);
	}
	
	public void removeComponentIfApplicable(Category oldCategory) {
		if(!oldCategory.isGradeable()) {
			return;
		}
		
		String name = oldCategory.getName();
		this.deleteComponent(name);
	}

	public static ArrayList<String> getAllHeaders(){
		ArrayList<String> commonHeaders = getCommonHeaders();
		ArrayList<String> allHeaders = new ArrayList<String>();
		allHeaders.addAll(commonHeaders);
		allHeaders.add(STATUS);
		return allHeaders;
	}
	
	public static ArrayList<String> getCommonHeaders(){
		ArrayList<String> commonHeaders = new ArrayList<String>();
		commonHeaders.add(STUDENT_ID);
		commonHeaders.add(LAST_NAME);
		commonHeaders.add(FIRST_NAME);
		commonHeaders.add(MIDDLE_NAME);	
		return commonHeaders;
	}
	
	public static String getData(Student s, String categoryName) {
		String retData;
		switch (categoryName) {
		case STUDENT_ID:
			retData = s.getSid();
			break;
		case FIRST_NAME:
			retData = s.getFirstName();
			break;
		case MIDDLE_NAME:
			retData = s.getMiddleName();
			break;
		case LAST_NAME:
			retData = s.getLastName();
			break;
		case STATUS:
			retData = Boolean.toString(s.isStatus());
			break;
		default:
			retData = "";
		}

		return retData;
	}
	
	public static void setData(Student s, String categoryName, String value) {
		switch (categoryName) {
		case STUDENT_ID:
			s.setSid(value);
			break;
		case FIRST_NAME:
			s.setName(value, s.getMiddleName(), s.getLastName());
			break;
		case MIDDLE_NAME:
			s.setName(s.getFirstName(), value, s.getLastName());
			break;
		case LAST_NAME:
			s.setName(s.getFirstName(), s.getMiddleName(), value);
			break;
		case STATUS:
			s.setStatus(Boolean.parseBoolean(value));
			break;
		}
	}

	@Override
	/**
	 * Check if all the weights of categories sum to 100%
	 * Check if all the categories are valid
	 */
	public boolean hasValidGradeableData(StudentEntry studentSummaryEntry) {
		Student student = studentSummaryEntry.getStudent();
		double weightSum = 0;
		for(Category category : course.getAllCategories()) {
			if(category instanceof OverallGradeable) {
				StudentEntry studentEntry = category.getStudentEntry(student);
				if(!((OverallGradeable) category).hasValidGradeableData(studentEntry)) {
					return false;
				}
				if(category instanceof GradeableCategory) {
					weightSum = weightSum + ((GradeableCategory) category).getWeight();
				}
			}
		}
		//Check if weights add to 100% (within an error)
		if(Math.abs(1 - weightSum) > 0.0001) {
			return false;
		}
		
		return true;
	}

	@Override
	public Double computeOverallGrade(StudentEntry studentSummaryEntry) {
		if(!hasValidGradeableData(studentSummaryEntry)) {
			return null;
		}
		
		Student student = studentSummaryEntry.getStudent();
		double finalGrade = 0;
		for(Category category : course.getAllCategories()) {
			if(category instanceof OverallGradeable) {
				if(category instanceof GradeableCategory) {
					StudentEntry studentEntry = category.getStudentEntry(student);
					double categoryGrade = ((OverallGradeable) category).computeOverallGrade(studentEntry)
							* ((GradeableCategory) category).getWeight();
					finalGrade = finalGrade + categoryGrade;
				}
			}
		}
		
		return finalGrade;
	}
}

