package data;

import java.util.ArrayList;

/**
 *  when an object would be written into csv file
 *  it should implement this interface first
 */
public interface Writable {
	
	/*
	 *  indicate the row index for that object
	 *  e.g: if a student will be written into a csv file
	 *  		his/her name, or id, should be returned
	 */
	public String getKey();
	
	/*
	 *  indicate the rest of that row for that object
	 *  e.g: if a student will be written into a csv file
	 * 			all values beside the key should be returned
	 */
	public ArrayList<String> writeAsRecord();
	
	/*
	 *  indicate the name of each column
	 * 	e.g: if a student will be written into a csv file
	 * 			all attributes should be returned
	 */
	public ArrayList<String> getColumnName();
}
