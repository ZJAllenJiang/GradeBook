package model;


class TextCategory extends Category {
	private String content;
	
	public TextCategory() {
		this("Dummy");
	}
	
	public TextCategory(String categName) {
		super(categName);
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
