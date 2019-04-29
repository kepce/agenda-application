package com.ed.main;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumnModel;

import com.ed.frames.MainAppFrame;
import com.ed.frames.TaskDescriptionFrame;
import com.ed.utility.*;

public class TaskTable extends JPanel{

	private DefaultTableModel tableModel;
	private JTable table;
	private JScrollPane scrollPane;
	private DataBaseConnection dbc;
	private ColorCellRenderer cellRenderer;
	private MouseListener mouseListener;

	
	public TaskTable() {
		dbc = DataBaseConnection.getInstance();
		dbc.update(SQLScripts.createTable());

		tableModel = new DefaultTableModel() {
			@Override
			public boolean isCellEditable(int row, int col) {
				return false;
			}
		};
		
		table = new JTable(tableModel);
		scrollPane = new JScrollPane(table);
		
		cellRenderer = new ColorCellRenderer();
	
		table.setPreferredScrollableViewportSize(new Dimension(500,310));
		table.setFillsViewportHeight(true);
		table.setRowHeight(25);
		table.setDefaultRenderer(Object.class, cellRenderer);
		table.addMouseListener(new MouseListenerForCells());
		
		tableModel.addColumn("Task ID");
		tableModel.addColumn("Task Name");
		tableModel.addColumn("Status");
		tableModel.addColumn("Until");
		
		TableColumnModel columnModel = table.getColumnModel();
		columnModel.getColumn(0).setMinWidth(70);
		columnModel.getColumn(0).setMaxWidth(70);
		columnModel.getColumn(2).setMinWidth(110);
		columnModel.getColumn(2).setMaxWidth(110);
		columnModel.getColumn(3).setMinWidth(130);
		columnModel.getColumn(3).setMaxWidth(130);
		
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
	
	public class MouseListenerForCells implements MouseListener{

		@Override
		public void mouseClicked(MouseEvent e) {
			if(e.getClickCount() == 2) {
				String selectedTaskId = table.getModel().getValueAt(table.getSelectedRow(), 0).toString();
				int taskId = Integer.valueOf(selectedTaskId);
				Task task = dbc.getTaskWithId(taskId);
				TaskDescriptionFrame taskDescriptionFrame = new TaskDescriptionFrame("Task Info", task);
				
			}
		}

		@Override
		public void mouseEntered(MouseEvent e) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void mouseExited(MouseEvent e) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void mousePressed(MouseEvent e) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void mouseReleased(MouseEvent e) {
			// TODO Auto-generated method stub
			
		}
		
	}
	
	public class ColorCellRenderer extends DefaultTableCellRenderer{
		
		public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
			
			String taskId = (String)table.getModel().getValueAt(row, 0).toString();
			int id = Integer.valueOf(taskId);
			Task task = dbc.getTaskWithId(id);
			int repConst = dbc.getRepetitionConstantIfExists(task);
			table.setFont(new Font("Calibri", Font.PLAIN, 14));

			if(repConst == Task.NOT_REPEATED) {
				if(task.isCompleted()) {
					setForeground(Color.BLACK);
					setBackground(Color.GREEN);
				}else {
					setForeground(Color.BLACK);
					setBackground(Color.RED);
				}
			}else if(repConst == Task.DAILY) {
				if(task.isCompleted()) {
					setForeground(Color.BLACK);
					setBackground(Color.GREEN);
				}else {
					setForeground(Color.BLACK);
					setBackground(Color.MAGENTA);	
				}
			}else if(repConst == Task.WEEKLY) {
				if(task.isCompleted()) {
					setForeground(Color.BLACK);
					setBackground(Color.GREEN);
				}else {
					setForeground(Color.BLACK);
					setBackground(Color.YELLOW);
				}
			}else {
				setForeground(Color.BLACK);
				setBackground(Color.GREEN);
			}
			this.setHorizontalAlignment(JLabel.CENTER);
			this.setToolTipText(task.getDescription());

			return super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
		}
	}	
}

