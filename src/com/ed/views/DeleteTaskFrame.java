package com.ed.views;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

import com.ed.utility.*;
import com.ed.config.GeneralConfig;
import com.ed.table.TaskTable;
import com.ed.task.*;


public class DeleteTaskFrame extends JFrame{

	private JLabel warningLabel;
	private JButton deleteButton;
	private JButton cancelButton;
	private JPanel bottomPanel;
	private JPanel centerPanel;
	private EmptyBorder emptyBorder;
	
	private int selectedTaskId;
	private String warningMessage;
	
	private DatabaseConnection databaseConnection;
	private TaskTable taskTable;
	private TaskManager taskManager;
	
	public DeleteTaskFrame(String title, MainAppFrame mainAppFrame) {
		super(title);
		
		AnnotationConfigApplicationContext context = 
//				new AnnotationConfigApplicationContext(GeneralConfig.class);
		mainAppFrame.getContext();
		
		databaseConnection = context.getBean("databaseConnection", DatabaseConnection.class);
		taskTable = context.getBean("taskTable", TaskTable.class);
		taskManager = context.getBean("taskManager", TaskManager.class);
		
		this.setLayout(new BorderLayout());
		bottomPanel = new JPanel();
		centerPanel = new JPanel();
		
		warningLabel = new JLabel("");
		emptyBorder = new EmptyBorder(10,15,10,10);
		warningLabel.setBorder(emptyBorder);
		centerPanel.add(warningLabel);

		this.add(centerPanel, BorderLayout.CENTER);
		
		deleteButton = new JButton("Delete");
		deleteButton.addActionListener(e->{
			List<Task> dailyTaskList = taskManager.getDailyTasks();
			List<Task> weeklyTaskList = taskManager.getWeeklyTasks();
			Task selectedTask = taskManager.getTaskWithId(selectedTaskId);
			if(selectedTask != null && (dailyTaskList.contains(selectedTask) || weeklyTaskList.contains(selectedTask))) {
				Task repeatedTask = null;
				if(dailyTaskList.contains(selectedTask))
					repeatedTask = dailyTaskList.get(dailyTaskList.indexOf(selectedTask));
				else
					repeatedTask = weeklyTaskList.get(weeklyTaskList.indexOf(selectedTask));
				databaseConnection.update(SQLScripts.deleteRow(repeatedTask.getId()));
				List<Task> taskList = taskManager.getAllTasks();
				for(Task task : taskList) {
					if(task.equals(selectedTask)) {
						databaseConnection.update(SQLScripts.deleteRow(task.getId()));
					}
				}
			}else {
				databaseConnection.update(SQLScripts.deleteRow(selectedTaskId));
			}
			taskTable.updateTable();
			mainAppFrame.setEnabled(true);
			this.dispose();
		});
		
		cancelButton = new JButton("Cancel");
		cancelButton.addActionListener(e->{
			mainAppFrame.setEnabled(true);
			this.dispose();
		});
		
		bottomPanel.add(deleteButton);
		bottomPanel.add(cancelButton);
		add(bottomPanel, BorderLayout.PAGE_END);
		
		this.setSize(400, 120);
		//this.setVisible(true);
		this.setResizable(false);
		this.setLocationRelativeTo(null);
		this.addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent windowEvent) {
				mainAppFrame.setEnabled(true);
				DeleteTaskFrame.this.dispose();
			}
		});
		this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
	}
	
	public void setSelectedTaskId(int Id) {
		this.selectedTaskId = Id;
		this.warningMessage = "<html> Are you sure you want to delete the task with the ID:  \"" + selectedTaskId + "\" ?"
				+ "<br> If this is a repeated task then all related tasks will be deleted! </html>";
		warningLabel.setText(warningMessage);
	}
}

