package gui;

import java.awt.Color;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.Vector;

import javax.swing.BorderFactory;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPopupMenu;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.SwingUtilities;
import javax.swing.border.Border;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import javax.swing.table.TableCellRenderer;

import model.Category;
import model.CategoryComponent;
import model.DataEntry;
import model.Student;
import model.StudentEntry;
import model.Summary;
import model.GradeableComponent.DataEntryMode;
import model.GradeableComponent;

public class GradeBookJTable extends JTable {
	private ArrayList<String> studentHeaders;
	private Category category;
		
	public GradeBookJTable(Category category) {
		super();
		
		this.category = category;
				
		refreshTable();
		
		//Setup right clicks just once
		addRightClickHeaderMenu();
		addRightClickContentMenu();
	}
	
	public boolean isSummaryTable() {
		return category.getName().equals(Summary.SUMMARY);
	}

	public void refreshTable() {
		//Clear current data in GUI
		DefaultTableModel tableModel = (DefaultTableModel) this.getModel();
		tableModel.setRowCount(0);
		
		//Redraw the GUI
		updateHeader();
		refreshTableData();
		tableModel.fireTableDataChanged();
	}
	
	private void updateHeader() {
		Vector<String> columnNameList = new Vector<String>();
		
		//Add the student information headers
		if(isSummaryTable()) {
			studentHeaders = Summary.getAllHeaders();
		}else {
			studentHeaders = Summary.getCommonHeaders();
		}
		columnNameList.addAll(studentHeaders);
		
		//Add the category specific headers
		for(CategoryComponent component : category.getComponents()) {
			columnNameList.add(component.getName());
		}
		
		DefaultTableModel tableModel = (DefaultTableModel) this.getModel();
		tableModel.setColumnCount(0);
		this.getTableHeader().setDraggedColumn(null);
	    tableModel.setColumnIdentifiers(columnNameList);
	}

	private void refreshTableData() {
		DefaultTableModel tableModel = (DefaultTableModel) this.getModel();

		for(StudentEntry studentEntry : category.getStudentEntries()) {
			Student student = studentEntry.getStudent();
			
			Object[] row = new Object[tableModel.getColumnCount()];
			int index = 0;
			
			//Get the student data
			for(String studentHeader : studentHeaders) {
				String value = Summary.getData(student, studentHeader);
				row[index] = value;
				index++;
			}
			
			//Get the category specific data
			for(CategoryComponent component : category.getComponents()) {
				DataEntry<?> entry = studentEntry.getDataEnty(component.getName());
				String value = "";
				if(entry != null) {
					value = entry.getDisplayData();
				}
				
				row[index] = value;
				index++;
			}
			
			tableModel.addRow(row);
		}
	}

	private boolean isComponentSpecificToCategory(String columnName) {
		return category.hasComponent(columnName);
	}
	
	public boolean isCellEditable(String columnName) { 
		if(!isSummaryTable()) {
			if(!isComponentSpecificToCategory(columnName)) {
				//Can only edit student data in Summary table
				return false;
			}
		}
		else {
			if(!isComponentSpecificToCategory(columnName)) {
				//Can edit student data
				return true;
			}
		}

		return category.isComponentEditable(columnName);
	}
	
	@Override
	public boolean isCellEditable(int row, int column) { 
		String columnName = this.getColumnName(column);
		boolean isEditable = isCellEditable(columnName);
		return isEditable;
	};
	
	@Override
	public TableCellRenderer getCellRenderer(int row, int column) {
		return new CustomCellRenderer();
	}
	
	private class CustomCellRenderer extends DefaultTableCellRenderer {
		public java.awt.Component getTableCellRendererComponent(JTable table, Object value,
				boolean isSelected, boolean hasFocus, int row, int column) {
			java.awt.Component rendererComp = super.getTableCellRendererComponent(table, value, 
					isSelected, hasFocus,
					row, column);
			//Gradeable column coloring
			String columnName = table.getColumnName(column);
			boolean isGradeable = category.isComponentGradeable(columnName);
			if(!isSelected && isGradeable) {
				GradeableComponent gComponent = (GradeableComponent)(category.getComponent(columnName));
				switch(gComponent.getDateEntryMode()) {
				case POINTS_EARNED:
					//rendererComp.setBackground(Color.CYAN);
					break;
				case POINTS_LOST:
					//rendererComp.setBackground(Color.PINK);
					break;
				case PERCENTAGE:
					//rendererComp.setBackground(Color.GREEN);
					break;
				}
			}
			
			//Invalid input coloring
			if(isComponentSpecificToCategory(columnName) && !category.validUserEntry(row, columnName, (String)value)) {
				rendererComp.setBackground(Color.RED);
			}

			//Comment coloring
			if(category.entryHasComment(row, columnName)) {
				//Add tooltip
				String comment = category.getComment(row, columnName);
				if(comment != null) {
					((JLabel)rendererComp).setToolTipText(comment);
				}

				//Border coloring
				Border border = BorderFactory.createMatteBorder(2, 2, 2, 2, Color.YELLOW);
				((JComponent) rendererComp).setBorder(border);
			}
			
			return rendererComp;
		}
	}
	
	@Override
	protected JTableHeader createDefaultTableHeader() {
        return new JTableHeader(columnModel) {
        	@Override
            public String getToolTipText(MouseEvent e) {
                String toolTipText = null;
                java.awt.Point p = e.getPoint();
                int guiColumn = columnModel.getColumnIndexAtX(p.x);
                String columnName = GradeBookJTable.this.getColumnName(guiColumn);
                if(category.isComponentGradeable(columnName)) {
                	GradeableComponent gComponent = ((GradeableComponent) category.getComponent(columnName));
                	toolTipText = "<html>";
                	double weight = gComponent.getWeight();
                	toolTipText = toolTipText + "Weight: " + weight + "<br>";
                	double maxScore = gComponent.getMaxScore();
                	toolTipText = toolTipText + "Max Score: " + maxScore + "<br>";
                	String entryMode = gComponent.getDateEntryMode().toString();
                	toolTipText = toolTipText + "Grade Entry Mode: " + entryMode;
                	toolTipText = toolTipText + "</html>";
                }
                return toolTipText;
            }
        };
    }
	
	private String selectedModelHeader = null;
	private void addRightClickHeaderMenu() {
		JPopupMenu popupMenu = new JPopupMenu() {
			@Override
			public void show(java.awt.Component invoker, int x, int y) {
				int guiColumn = GradeBookJTable.this.columnAtPoint(new Point(x, y));
				String columnName = GradeBookJTable.this.getColumnName(guiColumn);
				if(GradeBookJTable.this.isComponentSpecificToCategory(columnName)) {
					super.show(invoker, x, y);
				}
			}
		};
		
		//Add menu items
		JMenuItem statisticItem = new JMenuItem("Compute Statistics");
		statisticItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
            	//Set the valid data in the model in the format it is in now
    			GradeBookJTable.this.setDataFromGUI();
            	
            	System.out.println("Compute statistics on column: " + selectedModelHeader);
            }
        });
        popupMenu.add(statisticItem);
		
		JMenuItem editItem = new JMenuItem("Edit");
		editItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
            	JFrame topFrame = (JFrame) SwingUtilities
            			.getWindowAncestor(GradeBookJTable.this);

            	CategoryComponent c = category.getComponent(selectedModelHeader);
            	if(c.isGradeable()) {
            		GradeableComponent gradeableComp = (GradeableComponent) c;
            		CreateGradeableComponentPanel createGradeablePanel =
            				new CreateGradeableComponentPanel(gradeableComp.getName(), gradeableComp.getMaxScore(),
            						gradeableComp.getWeight() * 100, gradeableComp.getDateEntryMode());
            		int result = JOptionPane.showConfirmDialog(topFrame, 
            				createGradeablePanel, 
            				"Edit " + selectedModelHeader,
            				JOptionPane.OK_CANCEL_OPTION);

            		if(result == JOptionPane.OK_OPTION) {
            			if(!createGradeablePanel.hasProperData()) {
            				JOptionPane.showMessageDialog(null, 
            						"Invalid data entered.");
            				return;
            			}

            			String name = createGradeablePanel.getName();
            			if(!c.getName().equals(name) && category.getComponent(name) != null) {
            				String categoryName = category.getName();
            				JOptionPane.showMessageDialog(null, 
            						"Already have a " + categoryName + " with the name " 
            								+ name + ".");
            				return;
            			}
            			
            			//Set the valid data in the model in the format it is in now
            			GradeBookJTable.this.setDataFromGUI();

            			double maxScore = createGradeablePanel.getMaxScore();
            			double percentWeight = createGradeablePanel.getPercentWeight();
            			DataEntryMode entryMode = createGradeablePanel.getDataEntryMode();
            			
            			gradeableComp.setName(name);
            			gradeableComp.setMaxScore(maxScore);
            			gradeableComp.setWeight(percentWeight / 100);
            			gradeableComp.setDateEntryMode(entryMode);
            			
                    	GradeBookJTable.this.refreshTable();
            		}
            	}
            	else {
            		//Set the valid data in the model in the format it is in now
        			GradeBookJTable.this.setDataFromGUI();
        			
        			
                	GradeBookJTable.this.refreshTable();
            	}
            }
		});
        popupMenu.add(editItem);
        
        JMenuItem deleteItem = new JMenuItem("Delete");
        deleteItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
            	//Set the valid data in the model in the format it is in now
    			GradeBookJTable.this.setDataFromGUI();
    			
            	System.out.println("Do delete on column: " + selectedModelHeader);
            	
            	
            	GradeBookJTable.this.refreshTable();
            }
        });
        popupMenu.add(deleteItem);
       
        //Set the context in the header to the right click header cell
        this.getTableHeader().addMouseListener( new MouseAdapter() {
            public void mousePressed(MouseEvent e) {
            	JTableHeader header = (JTableHeader) e.getSource();
                int guiColumn = header.columnAtPoint(e.getPoint());
                selectedModelHeader = GradeBookJTable.this.getColumnName(guiColumn);
                
                //Can compute statistics?
                if(category.isComponentGradeable(selectedModelHeader)) {
                	statisticItem.setVisible(true);
                }
                else {
                	statisticItem.setVisible(false);
                }
            }
        });    
    
        this.getTableHeader().setComponentPopupMenu(popupMenu);
	}
	
	private void addRightClickContentMenu() {
		JPopupMenu popupMenu = new JPopupMenu() {
			@Override
			public void show(java.awt.Component invoker, int x, int y) {
				int guiColumn = GradeBookJTable.this.columnAtPoint(new Point(x, y));
				String columnName = GradeBookJTable.this.getColumnName(guiColumn);
				if(GradeBookJTable.this.isComponentSpecificToCategory(columnName)) {
					super.show(invoker, x, y);
				}
			}
		};
		
		//Add menu items
        JMenuItem commentItem = new JMenuItem("Edit Comment");
        commentItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
            	java.awt.Component cell = (java.awt.Component)e.getSource();
                JPopupMenu popup = (JPopupMenu)cell.getParent();
                JTable table = (JTable)popup.getInvoker();
                int row = table.getSelectedRow();
                StudentEntry studentEntry = category.getStudentEntries().get(row);
                int column = table.getSelectedColumn();
                String columnName = table.getColumnName(column);
                
                DataEntry<?> entry = studentEntry.getDataEnty(columnName);
                String comment = entry.getComment();
                
                JFrame topFrame = (JFrame) SwingUtilities.getWindowAncestor(table);
                JTextArea ta = new JTextArea(10, 30);
                if(comment != null) {
                	ta.setText(comment);
                }
                JScrollPane sp = new JScrollPane(ta);
                int result = JOptionPane.showConfirmDialog(topFrame, sp, 
						"Press OK to keep the comment", JOptionPane.OK_CANCEL_OPTION);
                if(result == JOptionPane.OK_OPTION) {
                	entry.setComment(ta.getText());
                }
                else {
                	entry.setComment(null);
                }
            }
        });
        popupMenu.add(commentItem);
        
        //Set the context in the table to the right clicked cell
        this.addMouseListener(new MouseAdapter() {
            public void mousePressed(MouseEvent e) {
                JTable source = (JTable)e.getSource();
                int row = source.rowAtPoint( e.getPoint() );
                int column = source.columnAtPoint( e.getPoint() );

                //if (! source.isRowSelected(row)){
                source.changeSelection(row, column, false, false);
                //}
            }
        });
        
        this.setComponentPopupMenu(popupMenu);
	}
	
	public void setDataFromGUI() {
		for (int row = 0; row < this.getModel().getRowCount(); row++){
			StudentEntry studentEntry = this.category.getStudentEntries().get(row);
			Student student = studentEntry.getStudent();
			
			for (int modelColumnIndex = 0; modelColumnIndex < this.getModel().getColumnCount(); modelColumnIndex++){
				int viewColumnIndex = this.convertColumnIndexToView(modelColumnIndex);
				String columnName = this.getColumnName(viewColumnIndex);
				String guiValue = this.getModel().getValueAt(row, modelColumnIndex).toString();

				if(this.isSummaryTable() && studentHeaders.contains(columnName)) {
					Summary.setData(student, columnName, guiValue);
				}
				else if(isComponentSpecificToCategory(columnName)){
					DataEntry<?> dataEntry = studentEntry.getDataEnty(columnName);
					if(dataEntry != null) {
						boolean success = dataEntry.setDataFromGUI(guiValue);
						if(!success) {
							//Invalid data!
							//return false;
						}
					}
				}
			}	
		}
	}
}
