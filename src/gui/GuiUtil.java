package gui;

import java.awt.Component;
import java.awt.Dimension;

import javax.swing.Box;

public class GuiUtil {
	private GuiUtil() {}
	
	public static Component createVerticalGap(int height) {
		return Box.createRigidArea(new Dimension(0, height));
	}
	
	public static Component createHorizontalGap(int width) {
		return Box.createRigidArea(new Dimension(width, 0));
	}
}
