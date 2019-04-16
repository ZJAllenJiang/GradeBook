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
				formattedScore = score / gComponent.getMaxScore();
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
}
