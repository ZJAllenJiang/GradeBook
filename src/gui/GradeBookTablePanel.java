package gui;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import model.Category;
import model.Summary;


public class GradeBookTablePanel extends JPanel {
	private Category category;
	
	private JButton addComponentBtn;
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
		addComponentBtn = new JButton("Add Component");
		addComponentBtn.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub

			}

		});
		addPanel.add(addComponentBtn);
		this.add(addPanel, BorderLayout.PAGE_START);
	}
	
	private void addTable() {
		gradeBookTable = new GradeBookJTable(category);
		
		JScrollPane scrollPane = new JScrollPane(gradeBookTable);
		this.add(scrollPane, BorderLayout.CENTER);
	}
}