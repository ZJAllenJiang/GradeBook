package model;

import java.util.ArrayList;
import java.util.Collections;

public class Statistics {
	double mean;
	double median;
	double standardDev;
	
	public Statistics(ArrayList<Double> grades) {
		if(grades != null) {
			Collections.sort(grades);
		}
		mean = computeMean(grades);
		median = computeMedian(grades);
		standardDev = computeDeviation(grades);
	}
	
	
	private double computeMean(ArrayList<Double> grades) {
		if (grades == null || grades.size() == 0)
			return 0;
		
		double sum = 0.0;
		for (Double grade : grades)
			sum += grade;
		return sum / grades.size();
	}
	
	
	private double computeMedian(ArrayList<Double> grades) {
		if (grades == null || grades.size() == 0)
			return 0;
		
		if (grades.size() % 2 == 0)
			return ((grades.get(grades.size() / 2 - 1) + grades.get(grades.size() / 2)) / 2);
		return grades.get(grades.size() / 2);
	}
	
	private double computeDeviation(ArrayList<Double> grades) {
		if (grades == null || grades.size() == 0)
			return 0;
		
		double sum = 0;
		for (Double grade : grades)
			sum += (grade - mean) * (grade - mean);
		return Math.sqrt(sum / (grades.size()));
	}
	
	
	public double getMean() {
		return mean;
	}
	
	public double getMedian() {
		return median;
	}
	
	public double getStandardDev() {
		return standardDev;
	}
}
