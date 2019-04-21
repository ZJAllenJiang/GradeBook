package model;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

public class GradeableCategory extends Category implements OverallGradeable {
	double weight;

	public GradeableCategory(double weight, String categName, ArrayList<Student> students) {
		super(categName, students);
		this.weight = weight;
	}
	
	public double getWeight() {
		return weight;
	}
	
	public void setWeight(double weight) {
		this.weight = weight;
	}
	
	@Override
	public String toString() {
		return this.name + " " + (this.weight * 100) + "%";
	}

	@Override
	public boolean hasValidGradeableData(StudentEntry studentEntry) {
		double weightSum = 0;
		for(DataEntry<?> dataEntry : studentEntry.getAllData()) {
			if(!dataEntry.getComponent().isGradeable()) {
				continue;
			}
			
			//Grades should only be set if they are valid
			//If there is no grade, then we will assume 100%
			
			GradeableComponent gComponent = (GradeableComponent) dataEntry.getComponent();
			weightSum = weightSum + gComponent.getWeight();
		}
		//Check if weights add to 100% (within an error)
		if(Math.abs(1 - weightSum) > 0.0001) {
			return false;
		}

		return true;
	}
	
	@Override
	public Double computeOverallGrade(StudentEntry studentEntry) {
		if(!hasValidGradeableData(studentEntry)) {
			return null;
		}
		
		double weightedGrade = 0;
		for(DataEntry<?> dataEntry : studentEntry.getAllData()) {
			if(!dataEntry.getComponent().isGradeable()) {
				continue;
			}
			
			if(!studentEntry.getStudent().isStatus()) {
				//Student is not active
				return 0.0;
			}
			
			GradeableComponent gComponent = (GradeableComponent) dataEntry.getComponent();

			double grade;
			try {
				//Do we have a grade set?
				//Grades should only be set if they are valid
				GradeEntry gEntry = (GradeEntry) dataEntry;
				grade = gEntry.getData() / gComponent.getMaxScore() * 100;
			}catch(Exception e) {
				grade = 100;
			}
			
			weightedGrade = weightedGrade + grade * gComponent.getWeight();
		}
		
		return weightedGrade;
	}
}