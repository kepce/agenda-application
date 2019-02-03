import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.sql.ResultSet;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
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

	private DefaultTableModel tableModel;
	private JTable table;
	private JScrollPane scrollPane;
	private TaskDataBase taskDB;
	private ColorCellRenderer cellRenderer;
	private List<Task> taskList = new ArrayList<Task>();
	private List<RepeatedTask> repeatedTaskList = new ArrayList<RepeatedTask>();
	private LocalDate today;
	
	public TaskTable(MainAppFrame mainAppFrame) {
		
		today = LocalDate.now();
		taskDB = new TaskDataBase(mainAppFrame);
		taskDB.update(ScriptSQL.createRepeatedTaskTable());
		taskDB.update(ScriptSQL.createTable(mainAppFrame.getTableName()));
		taskList = taskDB.getTaskListOf(today.format(DateTimeFormatter.ofPattern("uuuu_DDD")));
		repeatedTaskList = taskDB.getRepeatedTaskList();
		
		tableModel = new DefaultTableModel();
		table  = new JTable(tableModel);
		scrollPane = new JScrollPane(table);
		
		cellRenderer = new ColorCellRenderer();
	
		table.setPreferredScrollableViewportSize(new Dimension(500,310));
		table.setFillsViewportHeight(true);
		table.setRowHeight(25);
		table.setDefaultRenderer(Object.class, cellRenderer);
		
		tableModel.addColumn("Task Name");
		tableModel.addColumn("Status");
		tableModel.addColumn("Due Date");
		
		add(scrollPane);

		updateTable(today);
	}
	
	public void addNewTask(String taskName, String status, String dueDate) {
		tableModel.addRow(new String[] {taskName, status, dueDate});
	}
	
	public void addNewTask(Task task) {
		tableModel.addRow(new String[] {task.getName(), task.getStatus(), task.getDueDate()});
	}
	
	public void addNewTask(RepeatedTask repeatedTask) {
		tableModel.addRow(new String[] {repeatedTask.getName(), "Not Completed", "Daily Task"});
	}
	
	public void updateTable(LocalDate date) {
		String tableName = date.format(DateTimeFormatter.ofPattern("uuuu_DDD"));
		tableModel = (DefaultTableModel) table.getModel();
		tableModel.setRowCount(0);
		taskDB.update(ScriptSQL.createTable(tableName));
		taskList.clear();
		taskList = taskDB.getTaskListOf(tableName);
		Iterator iterator = taskList.iterator();
		while(iterator.hasNext()) 
			addNewTask((Task) iterator.next());
		repeatedTaskList.clear();
		repeatedTaskList = taskDB.getRepeatedTaskList();
		Iterator repIterator = repeatedTaskList.iterator();
		RepeatedTask repTask;
		while(repIterator.hasNext()) {
			repTask = (RepeatedTask) repIterator.next();
			for(int i=0; i < 7; i++) {
				if(repTask.getRepeatedDays()[i] && ((date.getDayOfWeek().getValue()-1) == i)) {
					addNewTask(repTask);
				}
			}	
		}
	}
	
	public int getSelectedRow() {
		return table.getSelectedRow();
	}
	
	public class ColorCellRenderer extends DefaultTableCellRenderer{
		
		public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
			
			String status = (String)table.getModel().getValueAt(row, 1);
			String dueDate = (String) table.getModel().getValueAt(row, 2);
			table.setFont(new Font("Calibri", Font.PLAIN, 14));
			if(status.equals("Not Completed")) {
				setForeground(Color.BLACK);
				setBackground(Color.RED);
			}else {
				setForeground(Color.BLACK);
				setBackground(Color.GREEN);
			}
			if(dueDate.equals("Daily Task")) {
				setForeground(Color.BLACK);
				setBackground(Color.MAGENTA);
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

