package com.ed.table;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import org.springframework.stereotype.Component;

import com.ed.task.Task;
import com.ed.utility.DatabaseConnection;
import com.ed.utility.TaskManager;
import com.ed.views.TaskDescriptionFrame;

@Component
public class CellMouseListener implements MouseListener {

	private TaskManager taskManager;
	private TaskTable taskTable;
	
	public CellMouseListener(TaskTable taskTable, TaskManager taskManager) {
		this.taskTable = taskTable;
		this.taskManager = taskManager;
	}
	
	@Override
	public void mouseClicked(MouseEvent e) {
		if(e.getClickCount() == 2) {
			String selectedTaskId = taskTable.getTable().getModel().getValueAt(taskTable.getSelectedRow(), 0).toString();
			int taskId = Integer.valueOf(selectedTaskId);
			Task task = taskManager.getTaskWithId(taskId);
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
