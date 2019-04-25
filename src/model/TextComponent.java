package model;

import java.util.Map;
import java.util.TreeMap;

import model.GradeableComponent.DataEntryMode;

public class TextComponent extends CategoryComponent {
	
	// self compose through reading data from database
	public TextComponent(Map<String, String> line) {
		setAttributes(line);
	}

	public TextComponent(String n, boolean isEditable) {
		super(n, isEditable);
	}

	@Override
	public DataEntry<?> createEntry() {
		return new TextEntry(this);
	}
	
	@Override
	public Map<String, String> getAllAttributes() {
		// TODO Auto-generated method stub
		Map<String, String> attributes = new TreeMap<String, String>();
		
		attributes.put("name", getName());
		attributes.put("type", "text");
		if (this.isEditable())
			attributes.put("isEditable", "true");
		else
			attributes.put("isEditable", "false");
		
		return attributes;
	}

	@Override
	public void setAttributes(Map<String, String> line) {
		// TODO Auto-generated method stub
		if (line.containsKey("isEditable") && line.containsKey("name")) {
			boolean editable = (line.get("isEditable").equals("true")) ? true : false;
			setEditable(editable);
			setName(line.get("name"));
		} else
			System.out.println("[TextComponent setAttributes] not enough attributes to read in");
		
		return;
	}

}
