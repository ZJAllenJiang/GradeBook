package model;

import java.util.ArrayList;

public class Summary extends TextCategory {
	public static final String SUMMARY = "Summary";
	
	public static final String STUDENT_ID = "ID";
	public static final String FIRST_NAME = "First Name";
	public static final String MIDDLE_NAME = "Middle Name";
	public static final String LAST_NAME = "Last Name";
	public static final String STATUS = "Status";
	
	public Summary() {
		this(SUMMARY);
	}
	
	public Summary(String categName) {
		super(categName);
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
		case FIRST_NAME:
			s.setName(value, s.getMiddleName(), s.getLastName());
		case MIDDLE_NAME:
			s.setName(s.getFirstName(), value, s.getLastName());
		case LAST_NAME:
			s.setName(s.getFirstName(), s.getMiddleName(), value);
		case STATUS:
			s.setStatus(Boolean.getBoolean(value));
		}
	}
}

