package gui;

import java.util.ArrayList;
import java.util.Vector;

import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
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
				DataEntry<?> entry = studentEntry.getDataEnty(component);
				String value = "";
				if(entry != null) {
					value = String.valueOf(entry.getData());
				}
				
				row[index] = value;
				index++;
			}
			
			tableModel.addRow(row);
		}
	}
	
	@Override
	public boolean isCellEditable(int row, int column) {                
		if(!isSummaryTable()) {
			int modelColumn = convertColumnIndexToModel(column);
			if(modelColumn < studentHeaders.size()) {
				//Can only edit student data in Summary table
				return false;
			}
		}
		
		return super.isCellEditable(row, column);
	};
	
	@Override
	public TableCellRenderer getCellRenderer(int row, int column) {
		return super.getCellRenderer(row, column);
	}
}
