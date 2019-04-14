package model;


import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

public class TextCategory extends Category {
	private String content;
	
	public TextCategory() {
		this("Dummy", new ArrayList<>(Collections.singletonList(new Student())));
	}
	
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
