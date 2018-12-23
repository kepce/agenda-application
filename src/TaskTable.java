import java.awt.Dimension;
import java.sql.ResultSet;
import java.util.Calendar;

import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

public class TaskTable extends JPanel{

	private Calendar calendar = Calendar.getInstance();
	private DefaultTableModel tableModel = new DefaultTableModel();
	private JTable taskTable;
	private JScrollPane scrollBar;
	private TaskDataBase taskDB = new TaskDataBase();
	private ResultSet tasks;

	
	public TaskTable() {
		
		taskDB.update(ScriptSQL.createTable(calendar.get(Calendar.YEAR), calendar.get(Calendar.DAY_OF_YEAR)));
		
		tasks = taskDB.query(ScriptSQL.selectTable(calendar.get(Calendar.YEAR), calendar.get(Calendar.DAY_OF_YEAR)));

		taskTable  = new JTable(tableModel);
		taskTable.setPreferredScrollableViewportSize(new Dimension(500,310));
		taskTable.setFillsViewportHeight(true);
		taskTable.setRowHeight(25);
		scrollBar = new JScrollPane(taskTable);
		add(scrollBar);
		
		tableModel.addColumn("Task Name");
		tableModel.addColumn("Task Description");
		tableModel.addColumn("Due Date");
		
		try {
			while(tasks.next()) {
				addNewTask(tasks.getString("task_name"), tasks.getString("task_desc"), tasks.getString("due_date"));
			}
		}catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public void addNewTask(String taskName, String taskDescription, String dueDate) {
		tableModel.addRow(new String[] {taskName, taskDescription, dueDate});
	}
	
	public void updateTable(int year, int dayOfYear) {
		tableModel = (DefaultTableModel) taskTable.getModel();
		tableModel.setRowCount(0);
		taskDB.update(ScriptSQL.createTable(year, dayOfYear));
		tasks = taskDB.query(ScriptSQL.selectTable(year, dayOfYear));
		try {
			while(tasks.next()) {
				addNewTask(tasks.getString("task_name"), tasks.getString("task_desc"), tasks.getString("due_date"));
			}
		}catch(Exception e) {
			e.printStackTrace();
		}
	}
	 
	
}
