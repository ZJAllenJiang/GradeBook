package model;


import java.util.ArrayList;

public class TextCategory extends Category {
	private String content;

	public TextCategory(String categName, ArrayList<Student> students) {
		super(categName, students);
		content = "";
	}
	
	
	public String getContent() {
		return content;
	}
	
	public void setContent(String content) {
		this.content = content;
	}
	
	@Override
	public String toString() {
		return "TextCategory{" +
				"name='" + name + '\'' +
				", content='" + content + '\'' +
				'}';
	}
}
