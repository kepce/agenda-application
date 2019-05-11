package com.ed.table;
import java.awt.Color;
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

import com.ed.task.Task;
import com.ed.utility.*;
import com.ed.views.MainAppFrame;
import com.ed.views.TaskDescriptionFrame;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;;

@Component
public class TaskTable extends JPanel implements AppendableTable<Task>, UpdatableTable{

	private JTable table;
	private JScrollPane scrollPane;
	private DatabaseConnection databaseConnection;
	private DefaultTableCellRenderer cellRenderer;
	private DefaultTableModel tableModel;
	private TaskManager taskManager;

	
	public TaskTable(DatabaseConnection databaseConnection,
					 DefaultTableModel tableModel,
					 DefaultTableCellRenderer cellRenderer,
					 TaskManager taskManager) {
		
		this.databaseConnection = databaseConnection;
		this.cellRenderer = cellRenderer;
		this.tableModel = tableModel;
		this.taskManager = taskManager;

		databaseConnection.update(SQLScripts.createTable());
	
		table = new JTable(tableModel);
		scrollPane = new JScrollPane(table);
	
		table.setPreferredScrollableViewportSize(new Dimension(500,310));
		table.setFillsViewportHeight(true);
		table.setRowHeight(25);
		table.setDefaultRenderer(Object.class, cellRenderer);

		
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
		
		this.add(scrollPane);

		this.updateTable();
	}

	@Override
	public void addNew(Task task) {
		tableModel.addRow(new String[] {String.valueOf(task.getId()), task.getName(), task.getStatus(), task.getFormattedDueDate()});
	}
	
	@Override
	public void updateTable() {
		tableModel.setRowCount(0);
		List<Task> taskList = taskManager.getTaskListOf(GlobalDate.getDate());
		List<Task> dailyTaskList = taskManager.getDailyTasks();
		for(Task task : dailyTaskList) {
			if(!task.getCreationDate().isAfter(GlobalDate.getDate()) && !task.getDueDate().isBefore(GlobalDate.getDate()) && !taskList.contains(task)) {
				task.setRepetitionConstant(Task.NOT_REPEATED);
				task.setCompleted(false);
				task.setCreationDate(GlobalDate.getDate());
				databaseConnection.update(SQLScripts.insertRow(task));
			}
		}
		List<Task> weeklyTaskList = taskManager.getWeeklyTasks();
		for(Task task : weeklyTaskList) {
			if(!task.getCreationDate().isAfter(GlobalDate.getDate()) && !task.getDueDate().isBefore(GlobalDate.getDate()) && !taskList.contains(task)) {
				if(task.getCreationDate().getDayOfWeek() == GlobalDate.getDate().getDayOfWeek()){
					task.setRepetitionConstant(Task.NOT_REPEATED);
					task.setCompleted(false);
					task.setCreationDate(GlobalDate.getDate());
					databaseConnection.update(SQLScripts.insertRow(task));
				}
			}
		}
		taskList = taskManager.getTaskListOf(GlobalDate.getDate());
		for(Task task : taskList) {
			addNew(task);
		}
	}
	
	@Autowired
	@Qualifier("cellMouseListener")
	public void setMouseListener(MouseListener mouseListener) {
		table.addMouseListener(mouseListener);
	}
	public int getSelectedRow() {
		return table.getSelectedRow();
	}
	
	public JTable getTable() {
		return this.table;
	}

}

