import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Iterator;
import java.util.List;

import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;

public class TaskTable extends JPanel{

	private Calendar calendar;
	private DefaultTableModel tableModel;
	private JTable table;
	private JScrollPane scrollPane;
	private TaskDataBase taskDB;
	private ColorCellRenderer cellRenderer;
	private int year;
	private int dayOfYear;
	
	private List<Task> taskList = new ArrayList<Task>();
	
	public TaskTable(MainAppFrame mainAppFrame) {
		
		taskDB = new TaskDataBase(mainAppFrame);
		tableModel = new DefaultTableModel();
		table  = new JTable(tableModel);
		scrollPane = new JScrollPane(table);
		calendar = Calendar.getInstance();
		year = calendar.get(Calendar.YEAR);
		dayOfYear = calendar.get(Calendar.DAY_OF_YEAR);
		cellRenderer = new ColorCellRenderer();
		
		taskDB.update(ScriptSQL.createTable(year, dayOfYear));
		taskList = taskDB.getTaskListFromDataBase(ScriptSQL.selectTable(year, dayOfYear));

		table.setPreferredScrollableViewportSize(new Dimension(500,310));
		table.setFillsViewportHeight(true);
		table.setRowHeight(25);
		table.setDefaultRenderer(Object.class, cellRenderer);
		
		tableModel.addColumn("Task Name");
		tableModel.addColumn("Status");
		tableModel.addColumn("Due Date");
		
		add(scrollPane);

		updateTable(year, dayOfYear);
		
	}
	
	public void addNewTask(String taskName, String status, String dueDate) {
		tableModel.addRow(new String[] {taskName, status, dueDate});
	}
	
	public void addNewTask(Task task) {
		tableModel.addRow(new String[] {task.getName(), task.getStatus(), task.getDueDate()});
	}
	
	public void updateTable(int year, int dayOfYear) {
		tableModel = (DefaultTableModel) table.getModel();
		tableModel.setRowCount(0);
		taskDB.update(ScriptSQL.createTable(year, dayOfYear));
		taskList.clear();
		taskList = taskDB.getTaskListFromDataBase(ScriptSQL.selectTable(year, dayOfYear));
		Iterator iterator = taskList.iterator();
		while(iterator.hasNext()) 
			addNewTask((Task) iterator.next());
	}
	
	
	public int getSelectedRow() {
		return table.getSelectedRow();
	}
	
	public class ColorCellRenderer extends DefaultTableCellRenderer{
		
		public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
			
			String status = (String)table.getModel().getValueAt(row, 1);
			table.setFont(new Font("Calibri", Font.PLAIN, 14));
			if(status.equals("Not Completed")) {
				setForeground(Color.BLACK);
				setBackground(Color.RED);
			}else {
				setForeground(Color.BLACK);
				setBackground(Color.GREEN);
			}
			return super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
		}
		
	}
	
	public TaskDataBase getDataBaseConnection() {
		return this.taskDB;
	}
	
	public JTable getTable() {
		return table;
	}
	 	
}

