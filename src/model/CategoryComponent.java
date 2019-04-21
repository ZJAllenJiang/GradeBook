package model;

import java.util.Map;

public abstract class CategoryComponent {
	private String name;
	private boolean isEditable;
	
	protected CategoryComponent() {}
	
	public CategoryComponent(String n, boolean isEditable) {
		name = n;
		this.isEditable = isEditable;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	public void setEditable(boolean editable) {
		this.isEditable = editable;
	}

	public boolean isEditable() {
		return isEditable;
	}
	
	public abstract DataEntry<?> createEntry();
	
	public boolean isGradeable() {
		return this instanceof GradeableComponent;
	}
	
	public abstract void setAttributes(Map<String, String> line);
	public abstract Map<String, String> getAllAttributes();
}
