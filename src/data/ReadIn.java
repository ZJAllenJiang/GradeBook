package data;

public interface ReadIn {
	
	// given a line in csv
	// get all data needed to compose an object
	public void readFromRowData(String line);
}
