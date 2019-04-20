package gui;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.Box;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.SwingUtilities;

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
					String categoryName = category.getName();
					
					JFrame topFrame = (JFrame) SwingUtilities
							.getWindowAncestor(GradeBookTablePanel.this);
					CreateGradeableComponentPanel createGradeablePanel =
							new CreateGradeableComponentPanel();
			        int result = JOptionPane.showConfirmDialog(topFrame, 
			        		createGradeablePanel, 
							"Add New " + categoryName,
							JOptionPane.OK_CANCEL_OPTION);
			        
			        if(result == JOptionPane.OK_OPTION) {
			        	if(!createGradeablePanel.hasProperData()) {
					    	JOptionPane.showMessageDialog(null, 
					    			"Invalid data entered.");
					    	return;
			        	}
			        	
			        	String name = createGradeablePanel.getName();
			        	if(category.getComponent(name) != null) {
			        		JOptionPane.showMessageDialog(null, 
					    			"Already have a " + categoryName + " with the name " 
			        				+ name + ".");
			        		return;
			        	}
			        	
			        	//Set the valid data in the model so that we can recreate the table
			        	gradeBookTable.setDataFromGUI();
			        	
			        	double maxScore = createGradeablePanel.getMaxScore();
			        	double percentWeight = createGradeablePanel.getPercentWeight();
			        	DataEntryMode entryMode = createGradeablePanel.getDataEntryMode();
			        	CategoryComponent c = new GradeableComponent(name, true,
			        			percentWeight/100, maxScore, entryMode);
						category.addComponent(c);
						gradeBookTable.refreshTable();
			        }
				}
			});
			addPanel.add(addGradeBtn);
			addPanel.add(GuiUtil.createHorizontalGap(50));	
		}
		
		JButton addTextBtn = new JButton("Add New Text Column");
		addTextBtn.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				//Set the valid data in the model so that we can recreate the table
	        	gradeBookTable.setDataFromGUI();
	        	
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
