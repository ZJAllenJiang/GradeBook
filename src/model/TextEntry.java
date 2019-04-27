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
	
	@Override
	public void setDataWithString(String xdata) {
		try {
			
			// read String from database
			// the real number of value so don't need to check entry mode and convert
			this.setData(xdata);
			
		}catch(Exception e) {
			System.out.println("[GradeEntry setDataWithString] " + e.getMessage());
		}
	}
}
