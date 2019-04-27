package model;

public abstract class DataEntry<T> {
	private CategoryComponent component;
	private T data;
	private String comment;
	
	public DataEntry(CategoryComponent component) {
		this.component = component;
	}

	public T getData() {
		return data;
	}

	public void setData(T data) {
		this.data = data;
	}
	
	public boolean hasData() {
		return getData() != null;
	}

	public String getDisplayData() {
		if(!hasData()) {
			return "";
		}
		return String.valueOf(getData());
	}
	
	public String getComment() {
		return comment;
	}
	
	public boolean hasComment() {
		return comment != null && !comment.equals("");
	}

	public void setComment(String comment) {
		this.comment = comment;
	}

	public CategoryComponent getComponent() {
		return component;
	}
	
	public abstract boolean isValidData(String guiData);
	
	public boolean setDataFromGUI(String guiData) {
		if(!isValidData(guiData)) {
			//Could not set the data because it is invalid
			return false;
		}
		
		return doSetDataFromGUI(guiData);
	}
	
	protected abstract boolean doSetDataFromGUI(String guiData);
	
	// need to implement because every time string type is returned 
	// when read from database
	abstract public void setDataWithString(String xdata);
}
