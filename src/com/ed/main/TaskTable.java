package com.ed.main;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;

import com.ed.frames.MainAppFrame;
import com.ed.utility.*;

public class TaskTable extends JPanel{

	private DefaultTableModel tableModel;
	private JTable table;
	private JScrollPane scrollPane;
	private DataBaseConnection dbc;
	private ColorCellRenderer cellRenderer;


	
	public TaskTable() {
		dbc = DataBaseConnection.getInstance();
		dbc.update(SQLScripts.createTable());

		tableModel = new DefaultTableModel();
		table = new JTable(tableModel);
		scrollPane = new JScrollPane(table);
		
		cellRenderer = new ColorCellRenderer();
	
		table.setPreferredScrollableViewportSize(new Dimension(500,310));
		table.setFillsViewportHeight(true);
		table.setRowHeight(25);
		table.setDefaultRenderer(Object.class, cellRenderer);
		
		tableModel.addColumn("Task ID");
		tableModel.addColumn("Task Name");
		tableModel.addColumn("Status");
		tableModel.addColumn("Due Date");
		
		add(scrollPane);

		updateTable();
	}

	public void addNewTask(Task task) {
		tableModel.addRow(new String[] {String.valueOf(task.getId()), task.getName(), task.getStatus(), task.getFormattedDueDate()});
	}
	
	public void updateTable() {
		tableModel = (DefaultTableModel) table.getModel();
		tableModel.setRowCount(0);
		List<Task> taskList = dbc.getTaskListOf(GlobalDate.getDate());
		List<Task> dailyTaskList = dbc.getDailyTasks();
		for(Task task : dailyTaskList) {
			if(!task.getCreationDate().isAfter(GlobalDate.getDate()) && !task.getDueDate().isBefore(GlobalDate.getDate()) && !taskList.contains(task)) {
				task.setRepetitionConstant(Task.NOT_REPEATED);
				task.setCompleted(false);
				task.setCreationDate(GlobalDate.getDate());
				dbc.update(SQLScripts.insertRow(task));
			}
		}
		List<Task> weeklyTaskList = dbc.getWeeklyTasks();
		for(Task task : weeklyTaskList) {
			if(!task.getCreationDate().isAfter(GlobalDate.getDate()) && !task.getDueDate().isBefore(GlobalDate.getDate()) && !taskList.contains(task)) {
				if(task.getCreationDate().getDayOfWeek() == GlobalDate.getDate().getDayOfWeek()){
					task.setRepetitionConstant(Task.NOT_REPEATED);
					task.setCompleted(false);
					task.setCreationDate(GlobalDate.getDate());
					dbc.update(SQLScripts.insertRow(task));
				}
			}
		}
		taskList = dbc.getTaskListOf(GlobalDate.getDate());
		for(Task task : taskList) {
			addNewTask(task);
		}
	}
	
	public int getSelectedRow() {
		System.out.println(table.getSelectedRow());
		return table.getSelectedRow();
	}
	
	public JTable getTable() {
		return this.table;
	}
	
	public Object getValueAt(int rowIndex, int columnIndex) {
		return this.getTable().getModel().getValueAt(rowIndex, columnIndex);
	}
	
	public class ColorCellRenderer extends DefaultTableCellRenderer{
		
		public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
			
			String status = (String)table.getModel().getValueAt(row, 2);
			String dueDate = (String) table.getModel().getValueAt(row, 2);
			table.setFont(new Font("Calibri", Font.PLAIN, 14));

			if(dueDate.equals("Daily Task") && status.equals("Not Completed")) {
				setForeground(Color.BLACK);
				setBackground(Color.MAGENTA);
			}else if(dueDate.equals("Daily Task") && status.equals("Completed")) {
				setForeground(Color.BLACK);
				setBackground(Color.GREEN);
			}else if(status.equals("Not Completed")) {
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

