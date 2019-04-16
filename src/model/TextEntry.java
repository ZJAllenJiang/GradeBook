package model;

public class TextEntry extends DataEntry<String>{

	public TextEntry(CategoryComponent component) {
		super(component);
	}
	
	public boolean isValidData(String guiData) {
		return true;
	}

	@Override
	protected boolean doSetDataFromGUI(String guiData) {
		this.setData(guiData);
		return true;
	}
}
