package model;

import java.util.ArrayList;

public class UndergraduateStudent extends Student{

	public UndergraduateStudent() {
		super(StudentType.UNDERGRADUATE);
	}

	public UndergraduateStudent(String sid, String fName, String mName, String lName, boolean status) {
		super(sid, fName, mName, lName, status, StudentType.UNDERGRADUATE);
	}
	
	@Override
	public String getKey() {
		// TODO Auto-generated method stub
		return sid;
	}

	@Override
	public ArrayList<String> writeAsRecord() {
		// TODO Auto-generated method stub
		ArrayList<String> res = new ArrayList<String>();
		res.add(this.getFirstName());
		res.add(this.getMiddleName());
		res.add(this.getLastName());
		if (status)
			res.add("Active");
		else
			res.add("Inactive");
		res.add(StudentType.UNDERGRADUATE.toString());
		
		return res;
	}

	@Override
	public ArrayList<String> getColumnName() {
		// TODO Auto-generated method stub
		ArrayList<String> res = new ArrayList<String>();
		res.add("Student ID");
		res.add("First name");
		res.add("Middle initial");
		res.add("Last name");
		res.add("Status");
		res.add("Student Type");
		
		return res;
	}

	@Override
	public void readFromRowData(String line) {
		// TODO Auto-generated method stub
		
		// the separator of csv is ','
		String[] terms = line.split(",");
		
		// should be one to one mapping 
		if (terms.length != 6) {
			System.out.println("[Student readFromRowData] can't compose object, "
					+ "the rowdata is: " + line);
			return;
		}
		this.setSid(terms[0]);
		this.setName(terms[1], terms[2], terms[3]);
		if (terms[4].equals("Active"))
			this.setStatus(true);
		else if (terms[4].equals("Inactive"))
			this.setStatus(false);
		else {
			this.setStatus(false);
			System.out.println("[Student readFromRowData] miss matched type."
					+ " status = " + terms[4]);
		}
	}
}
