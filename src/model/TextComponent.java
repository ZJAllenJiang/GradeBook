package model;

public class TextComponent extends Component {

	public TextComponent(String n, boolean isEditable) {
		super(n, isEditable);
	}

	@Override
	public DataEntry<?> createEntry() {
		return new TextEntry(this);
	}
}
