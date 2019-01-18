import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.sql.ResultSet;
import java.util.Calendar;

import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;

public class TaskTable extends JPanel{

	private Calendar calendar = Calendar.getInstance();
	private DefaultTableModel tableModel = new DefaultTableModel();
	public JTable table;
	private JScrollPane scrollBar;
	public TaskDataBase taskDB = new TaskDataBase();
	private ResultSet tasks;
	private ColorCellRenderer cellRenderer = new ColorCellRenderer(); 

	
	public TaskTable() {		
		taskDB.update(ScriptSQL.createTable(calendar.get(Calendar.YEAR), calendar.get(Calendar.DAY_OF_YEAR)));
		
		tasks = taskDB.query(ScriptSQL.selectTable(calendar.get(Calendar.YEAR), calendar.get(Calendar.DAY_OF_YEAR)));

		table  = new JTable(tableModel);
		table.setPreferredScrollableViewportSize(new Dimension(500,310));
		table.setFillsViewportHeight(true);
		table.setRowHeight(25);
		scrollBar = new JScrollPane(table);
		add(scrollBar);
		
		
		tableModel.addColumn("Task Name");
		tableModel.addColumn("Status");
		tableModel.addColumn("Due Date");
		
		try {
			while(tasks.next()) {
				addNewTask(tasks.getString("task_name"), tasks.getString("status"), tasks.getString("due_date"));
			}
		}catch(Exception e) {
			e.printStackTrace();
		}
		table.setDefaultRenderer(Object.class, cellRenderer);
	
	}
	
	public void addNewTask(String taskName, String status, String dueDate) {
		tableModel.addRow(new String[] {taskName, status, dueDate});
	}
	
	public void updateTable(int year, int dayOfYear) {
		tableModel = (DefaultTableModel) table.getModel();
		tableModel.setRowCount(0);
		taskDB.update(ScriptSQL.createTable(year, dayOfYear));
		tasks = taskDB.query(ScriptSQL.selectTable(year, dayOfYear));
		try {
			while(tasks.next()) {
				addNewTask(tasks.getString("task_name"), tasks.getString("status"), tasks.getString("due_date"));
			}
		}catch(Exception e) {
			e.printStackTrace();
		}
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
	 	
}

