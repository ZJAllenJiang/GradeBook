package model;

public class TextEntry extends DataEntry<String>{

	public TextEntry(Component component) {
		super(component);
	}
	
	public boolean isValidData(String guiData) {
		return true;
	}
}
