package model;

import java.util.Map;
import java.util.TreeMap;

public class GradeableComponent extends CategoryComponent {
	private double weight;
	private double maxScore;	//Must be > 0
	private DataEntryMode dateEntryMode;
	
	public enum DataEntryMode{
		POINTS_EARNED,
		POINTS_LOST,
		PERCENTAGE;
	}
	
	// self compose through reading data from database
	public GradeableComponent(Map<String, String> line) {
		super();
		setAttributes(line);
	}

	public GradeableComponent(String n, boolean isEditable, 
			double weight, double maxScore, DataEntryMode dateEntryMode) {
		super(n, isEditable);
		setWeight(weight);
		setMaxScore(maxScore);
		setDateEntryMode(dateEntryMode);
	}

	@Override
	public DataEntry<Double> createEntry() {
		return new GradeEntry(this);
	}

	public double getWeight() {
		return weight;
	}

	public void setWeight(double weight) {
		this.weight = weight;
	}

	public double getMaxScore() {
		return maxScore;
	}

	public void setMaxScore(double maxScore) {
		this.maxScore = maxScore;
	}

	public DataEntryMode getDateEntryMode() {
		return dateEntryMode;
	}

	public void setDateEntryMode(DataEntryMode dateEntryMode) {
		this.dateEntryMode = dateEntryMode;
	}

	@Override
	public Map<String, String> getAllAttributes() {
		// TODO Auto-generated method stub
		Map<String, String> attributes = new TreeMap<String, String>();
		
		attributes.put("name", getName());
		attributes.put("weight", Double.toString(this.weight));
		attributes.put("maxScore", Double.toString(this.maxScore));
		attributes.put("mode", dateEntryMode.toString());
		attributes.put("type", "gradeable");
		
		if (this.isEditable())
			attributes.put("isEditable", "true");
		else
			attributes.put("isEditable", "false");
		
		return attributes;
	}

	@Override
	public void setAttributes(Map<String, String> line) {
		// TODO Auto-generated method stub
		if (line.containsKey("weight") && line.containsKey("maxScore") 
				&& line.containsKey("mode") && line.containsKey("name") 
				&& line.containsKey("isEditable")) {
			setName(line.get("name"));
			this.weight = Double.parseDouble(line.get("weight"));
			this.maxScore = Double.parseDouble(line.get("maxScore"));
			this.dateEntryMode = DataEntryMode.valueOf(line.get("mode"));
			boolean editable = (line.get("isEditable").equals("true")) ? true : false;
			setEditable(editable);
		} else 
			System.out.println("[GradeableComponent setAttributes] not enough attributes to read in");
		
		return;
	}
}
