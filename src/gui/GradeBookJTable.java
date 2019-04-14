package gui;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.Vector;

import javax.swing.BorderFactory;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JMenuItem;
import javax.swing.JPopupMenu;
import javax.swing.JTable;
import javax.swing.border.Border;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import javax.swing.table.TableCellRenderer;

import model.Category;
import model.Component;
import model.DataEntry;
import model.Student;
import model.StudentEntry;
import model.Summary;

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
		//Clear current data
		DefaultTableModel tableModel = (DefaultTableModel) this.getModel();
		tableModel.setRowCount(0);
		
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
		for(Component component : category.getComponents()) {
			columnNameList.add(component.getName());
		}
		
		DefaultTableModel tableModel = (DefaultTableModel) this.getModel();
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
			for(Component component : category.getComponents()) {
				DataEntry<?> entry = studentEntry.getDataEnty(component.getName());
				String value = "";
				if(entry != null && entry.hasData()) {
					value = String.valueOf(entry.getData());
				}
				
				row[index] = value;
				index++;
			}
			
			tableModel.addRow(row);
		}
	}
	
	private int getModelColumn(int guiColumn) {
		return convertColumnIndexToModel(guiColumn);
	}
	
	@Override
	public boolean isCellEditable(int row, int column) {                
		if(!isSummaryTable()) {
			int modelColumn = getModelColumn(column);
			if(modelColumn < studentHeaders.size()) {
				//Can only edit student data in Summary table
				return false;
			}
		}

		return super.isCellEditable(row, column);
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
			//Add a tooltip to the headers
			table.getTableHeader().setToolTipText("Right click to edit");

			//Gradeable column coloring
			String columnName = table.getColumnName(column);
			boolean isGradeable = category.isComponentGradeable(columnName);
			if(!isSelected && isGradeable) {
				rendererComp.setBackground(Color.CYAN);
			}

			//Comment coloring
			if(category.componentHasComment(row, columnName)) {
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
	
	private int selectedModelHeader = -1;
	private void addRightClickHeaderMenu() {
		JPopupMenu popupMenu = new JPopupMenu();
		
		//Add menu items
        JMenuItem deleteItem = new JMenuItem("Delete");
        deleteItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
            	System.out.println("Do delete on column: " + selectedModelHeader);
            }
        });
        popupMenu.add(deleteItem);
        
       
        //Set the context in the header to the right click header cell
        this.getTableHeader().addMouseListener( new MouseAdapter() {
            public void mousePressed(MouseEvent e) {
            	JTableHeader header = (JTableHeader) e.getSource();
                int guiColumn = header.columnAtPoint(e.getPoint());
                selectedModelHeader = GradeBookJTable.this.getModelColumn(guiColumn);
            }
        });    
    
        this.getTableHeader().setComponentPopupMenu(popupMenu);
	}
	
	private void addRightClickContentMenu() {
		JPopupMenu popupMenu = new JPopupMenu();
		
		//Add menu items
        JMenuItem deleteItem = new JMenuItem("View Comment");
        deleteItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
            	java.awt.Component cell = (java.awt.Component)e.getSource();
                JPopupMenu popup = (JPopupMenu)cell.getParent();
                JTable table = (JTable)popup.getInvoker();
                System.out.println("View comment for cell: " + table.getSelectedRow() + ", " + table.getSelectedColumn());
            	//JFrame topFrame = (JFrame) SwingUtilities.getWindowAncestor(this);
                //JOptionPane.showMessageDialog(topFrame, "Right-click performed on table and choose DELETE");
            }
        });
        popupMenu.add(deleteItem);
        
        //Set the context in the table to the right clicked cell
        this.addMouseListener( new MouseAdapter() {
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
}
