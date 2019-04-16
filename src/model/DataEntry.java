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
	
	// need to implement because every time string type is returned 
	// when read from database
	public String setDataWithString(String data) {
		
		return null;
	}
}
