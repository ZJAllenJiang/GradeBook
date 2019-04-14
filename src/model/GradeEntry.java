package model;

public class GradeEntry extends DataEntry<Double>{

	public GradeEntry(Component component) {
		super(component);
	}
	
	public boolean isValidData(String guiData) {
		if(guiData == null || guiData.equals("")) {
			return true;
		}
		
		//Is this a valid number?
		double value;
		try {  
			value = Double.parseDouble(guiData);  
		} catch(NumberFormatException e){  
			return false;  
		} 
		
		//Is this a valid number for the component?
		//Negative?
		if(value < 0) {
			return false;
		}
		
		//Larger than input value?
		//TODO
		
		return true;
	}
}
