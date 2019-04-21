package model;

public interface OverallGradeable {
	public boolean hasValidGradeableData(StudentEntry studentEntry);
	
	public Double computeOverallGrade(StudentEntry studentEntry);
}
