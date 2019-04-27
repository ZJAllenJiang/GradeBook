package model;

import model.GradeableComponent.DataEntryMode;

public class GradeEntry extends DataEntry<Double>{

	public GradeEntry(CategoryComponent component) {
		super(component);
	}
	
	@Override
	public String getDisplayData() {
		if(!hasData()) {
			return "";
		}
		
		double score = getData();
		double formattedScore = score;
		GradeableComponent gComponent = (GradeableComponent) getComponent();
		DataEntryMode entryMode = gComponent.getDateEntryMode();
		switch(entryMode){
			case POINTS_EARNED:
				formattedScore = score;
				break;
			case POINTS_LOST:
				formattedScore = gComponent.getMaxScore() - score;
				break;
			case PERCENTAGE:
				formattedScore = score / gComponent.getMaxScore() * 100;
				break;
		}
		
		return String.valueOf(formattedScore);
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
		
		GradeableComponent gComponent = (GradeableComponent) getComponent();
		DataEntryMode entryMode = gComponent.getDateEntryMode();
		switch(entryMode){
			case POINTS_EARNED:
			case POINTS_LOST:
				//Input value larger than max score?
				if(value > gComponent.getMaxScore()) {
					return false;
				}
				break;
			case PERCENTAGE:
				//Larger than 100%?
				if(value > 100) {
					return false;
				}
				break;
		}
		
		return true;
	}
	
	@Override
	protected boolean doSetDataFromGUI(String guiData) {
		if(guiData == null || guiData.equals("")) {
			//User is clearing data
			setData(null);
			return true;
		}
		
		double guiValue = Double.parseDouble(guiData);
		GradeableComponent gComponent = (GradeableComponent) getComponent();
		DataEntryMode entryMode = gComponent.getDateEntryMode();
		double validValue = -1;
		switch(entryMode){
			case POINTS_EARNED:
				validValue = guiValue;
				break;
			case POINTS_LOST:
				validValue = gComponent.getMaxScore() - guiValue;
				break;
			case PERCENTAGE:
				validValue = guiValue / 100 * gComponent.getMaxScore();				
				break;
		}

		this.setData(validValue);
		return true;
	}
	
	@Override
	public void setDataWithString(String xdata) {
		// don't need to handle
		if (xdata == null || xdata.equals("") || xdata.equals(" "))
			return;
		
		try {
			
			// read String from database
			// the real number of value so don't need to check entry mode and convert
			double douVal = Double.parseDouble(xdata);
			this.setData(douVal);
			
		}catch(Exception e) {
			System.out.println("[GradeEntry setDataWithString] " + e.getMessage());
		}
	}
}
