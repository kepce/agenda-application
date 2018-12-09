import java.awt.Dimension;

import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

public class TaskTable extends JPanel{

	private DefaultTableModel tableModel = new DefaultTableModel();
	private JTable taskTable;
	private JScrollPane scrollBar;
	
	
	
	public TaskTable() {
		
		taskTable  = new JTable(tableModel);
		taskTable.setPreferredScrollableViewportSize(new Dimension(500,200));
		taskTable.setFillsViewportHeight(true);
		
		scrollBar = new JScrollPane(taskTable);
		add(scrollBar);
		
		tableModel.addColumn("Task Name");
		tableModel.addColumn("Task Description");
		tableModel.addColumn("Value");
		 
	}
	
	public void addNewTask(String taskName, String taskDescription, String value) {
		tableModel.addRow(new String[] {taskName, taskDescription, value});
	}
	 
	
}
