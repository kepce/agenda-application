package com.ed.table;

import java.awt.Color;
import java.awt.Component;
import java.awt.Font;

import javax.swing.JLabel;
import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;

import com.ed.task.Task;
import com.ed.utility.TaskManager;

public class CellRenderer  extends DefaultTableCellRenderer{
	
	private TaskManager taskManager;

	public CellRenderer(TaskManager taskManager) {
		this.taskManager = taskManager;
	}
	
	public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
		
		String taskId = (String)table.getModel().getValueAt(row, 0).toString();
		int id = Integer.valueOf(taskId);
		Task task = taskManager.getTaskWithId(id);
		int repConst = taskManager.getRepetitionConstantIfExists(task);
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
