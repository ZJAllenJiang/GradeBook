package model;

public abstract class DataEntry<T> {
	private Component component;
	private T data;
	private String comment;
	
	public DataEntry(Component component) {
		this.component = component;
	}

	public T getData() {
		return data;
	}

	public void setData(T data) {
		this.data = data;
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

	public Component getComponent() {
		return component;
	}
}
