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
import model.Course;
import model.GradeableComponent;
import model.GradeableComponent.DataEntryMode;
import model.TextComponent;


public class GradeBookTablePanel extends JPanel {
	private GradeBookPanel gradeBookPanel;
	private Category category;
	
	private GradeBookJTable gradeBookTable;
	
	public GradeBookTablePanel(GradeBookPanel gradeBookPanel, Category category) {
		super(new BorderLayout());
		this.gradeBookPanel = gradeBookPanel;
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
					handleAddNewComponent(true);
				}
			});
			addPanel.add(addGradeBtn);
			addPanel.add(GuiUtil.createHorizontalGap(50));	
		}
		
		JButton addTextBtn = new JButton("Add New Text Column");
		addTextBtn.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				handleAddNewComponent(false);
			}
		});
		addPanel.add(addTextBtn);
		this.add(addPanel, BorderLayout.PAGE_START);
	}
	
	public void handleAddNewComponent(boolean isGradeable) {
		JFrame topFrame = (JFrame) SwingUtilities
				.getWindowAncestor(GradeBookTablePanel.this);
		
		NamePanel componentPanel;
		String type;
		if(isGradeable) {
			componentPanel = new GradeableComponentPanel();
			type = category.getName();
		}
		else {
			componentPanel = new NamePanel();
			type = "Text Column";
		}
		
        int result = JOptionPane.showConfirmDialog(topFrame, 
        		componentPanel, 
				"Add New " + type,
				JOptionPane.OK_CANCEL_OPTION);
        
        if(result == JOptionPane.OK_OPTION) {
        	if(!componentPanel.hasProperData()) {
		    	JOptionPane.showMessageDialog(null, 
		    			"Invalid data entered.");
		    	return;
        	}
        	
        	String name = componentPanel.getName();
        	if(category.getComponent(name) != null) {
        		JOptionPane.showMessageDialog(null, 
		    			"Already have a " + type + " with the name " 
        				+ name + ".");
        		return;
        	}
        	
        	//Set the valid data in the model so that we can recreate the table
        	gradeBookTable.setDataFromGUI();
        	
        	CategoryComponent component;
        	if(isGradeable) {
        		GradeableComponentPanel gradeablePanel = (GradeableComponentPanel) componentPanel;
        		double maxScore = gradeablePanel.getMaxScore();
        		double percentWeight = gradeablePanel.getPercentWeight();
        		DataEntryMode entryMode = gradeablePanel.getDataEntryMode();
        		component = new GradeableComponent(name, true,
        				percentWeight/100, maxScore, entryMode);
        	}
    		else {
    			component = new TextComponent(name, true);
    		}
        	
			category.addComponent(component);
			
			gradeBookTable.refreshTable();
        }
	
	}

	private void addTable() {
		gradeBookTable = new GradeBookJTable(gradeBookPanel, category);
		
		JScrollPane scrollPane = new JScrollPane(gradeBookTable);
		this.add(scrollPane, BorderLayout.CENTER);
	}
	
	public void syncModelAndGUI(boolean doSetData) {
		if(doSetData) {
			gradeBookTable.setDataFromGUI();
		}
		gradeBookTable.refreshTable();
	}

	public boolean updateOverallGrades(Course course) {
		return gradeBookTable.updateOverallGradesIfApplicable(course);
	}
}
