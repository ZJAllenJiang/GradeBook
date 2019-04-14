package gui;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JTabbedPane;
import javax.swing.SwingUtilities;
import javax.swing.plaf.TabbedPaneUI;

import model.Category;
import model.Course;
import model.Summary;

public class GradeBookPanel extends JPanel {
	private Course course;
	private JTabbedPane gradeBookTabs;
	
	public GradeBookPanel(Course course) {
		super(new GridLayout(1, 1));
		this.course = course;
		
		setUpGUI();
	}

	private void setUpGUI() {
		gradeBookTabs = new JTabbedPane();
		
		//Add the Summary
		gradeBookTabs.addTab(Summary.SUMMARY, new GradeBookTablePanel(course.getSummary()));
		
		//Add the other Categories
		for(Category category : course.getAllCategories()) {
			gradeBookTabs.addTab(category.getName(), new GradeBookTablePanel(category));
		}
		
		gradeBookTabs.addMouseListener(new MouseAdapter() {
		    @Override
		    public void mouseClicked(MouseEvent e) 
		    {
		    	if(SwingUtilities.isRightMouseButton(e)){
		    		TabbedPaneUI tabUI = gradeBookTabs.getUI();
		    		int tabIndex = tabUI.tabForCoordinate(gradeBookTabs, e.getX(), e.getY());
		    		if(tabIndex >= 0) {
						String tabName = gradeBookTabs.getTitleAt(tabIndex);
						//Can't modify Summary tab
						if(!tabName.equals(Summary.SUMMARY)) {
			    			JPopupMenu popupMenu = new JPopupMenu();
			    			
			    			//Add menu items
			    			JMenuItem statisticItem = new JMenuItem("Compute Statistics");
			    			statisticItem.addActionListener(new ActionListener() {
			    	            @Override
			    	            public void actionPerformed(ActionEvent e) {
			    	            	System.out.println("Compute statistics on column: " + tabName);
			    	            }
			    	        });
			    	        popupMenu.add(statisticItem);
			    			
			    			JMenuItem editItem = new JMenuItem("Edit");
			    			editItem.addActionListener(new ActionListener() {
			    	            @Override
			    	            public void actionPerformed(ActionEvent e) {
			    	            	System.out.println("Do edit on column: " + tabName);
			    	            }
			    	        });
			    	        popupMenu.add(editItem);
			    	        
			    	        JMenuItem deleteItem = new JMenuItem("Delete");
			    	        deleteItem.addActionListener(new ActionListener() {
			    	            @Override
			    	            public void actionPerformed(ActionEvent e) {
			    	            	System.out.println("Do delete on column: " + tabName);
			    	            }
			    	        });
			    	        popupMenu.add(deleteItem);
			    			
			    	        popupMenu.show(gradeBookTabs, e.getX(), e.getY());
						}
		    		}
		        }
		    }
		});
		
		add(gradeBookTabs);
	}
}
