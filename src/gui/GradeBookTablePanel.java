package gui;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.Box;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import model.Category;
import model.CategoryComponent;
import model.GradeableComponent;
import model.GradeableComponent.DataEntryMode;
import model.TextComponent;


public class GradeBookTablePanel extends JPanel {
	private Category category;
	
	private GradeBookJTable gradeBookTable;
	
	public GradeBookTablePanel(Category category) {
		super(new BorderLayout());
		this.category = category;

		setUpGUI();
	}

	private void setUpGUI() {
		addButtonsForTable();
		addTable();
	}

	private void addButtonsForTable() {
		JPanel addPanel = new JPanel();
		if(category.isGradeable()) {
			JButton addGradeBtn = new JButton("Add New " + category.getName());
			addGradeBtn.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					System.out.println("Added new column to " + category.getName());
					CategoryComponent c = new GradeableComponent("Grade", true,
							.1, 50, DataEntryMode.POINTS_EARNED);
					category.addComponent(c);
					gradeBookTable.refreshTable();
				}
			});
			addPanel.add(addGradeBtn);
			addPanel.add(Box.createRigidArea(new Dimension(50, 0)));	
		}
		JButton addTextBtn = new JButton("Add New Text Column");
		addTextBtn.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				System.out.println("Added new column to " + category.getName());
				CategoryComponent c = new TextComponent("Text", true);
				category.addComponent(c);
				gradeBookTable.refreshTable();
			}
		});
		addPanel.add(addTextBtn);
		this.add(addPanel, BorderLayout.PAGE_START);
	}
	
	private void addTable() {
		gradeBookTable = new GradeBookJTable(category);
		
		JScrollPane scrollPane = new JScrollPane(gradeBookTable);
		this.add(scrollPane, BorderLayout.CENTER);
	}
}
