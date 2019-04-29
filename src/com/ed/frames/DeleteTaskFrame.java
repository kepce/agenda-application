package com.ed.frames;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import com.ed.utility.*;
import com.ed.main.*;

public class DeleteTaskFrame extends JFrame{


	private JLabel warningLabel;
	private JButton deleteButton;
	private JButton cancelButton;
	private JPanel bottomPanel;
	private JPanel centerPanel;
	private EmptyBorder emptyBorder;
	private Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();

	private DataBaseConnection dbc;
	
	public DeleteTaskFrame(String title, MainAppFrame mainAppFrame) {
		super(title);
		
		dbc = DataBaseConnection.getInstance();

		this.setLayout(new BorderLayout());
		bottomPanel = new JPanel();
		centerPanel = new JPanel();

		int selectedTaskId = Integer.valueOf(
				mainAppFrame.getTaskTable().getTable().getModel().getValueAt(mainAppFrame.getTaskTable().getSelectedRow(), 0).toString()
			);

		String warningMessage = "<html> Are you sure you want to delete the task with the ID:  \"" + selectedTaskId + "\" ?"
								+ "<br> If this is a repeated task then all related tasks will be deleted! </html>";
		
		warningLabel = new JLabel(warningMessage);
		emptyBorder = new EmptyBorder(10,15,10,10);
		warningLabel.setBorder(emptyBorder);
		centerPanel.add(warningLabel);


		this.add(centerPanel, BorderLayout.CENTER);
		
		deleteButton = new JButton("Delete");
		deleteButton.addActionListener(e->{
			List<Task> dailyTaskList = dbc.getDailyTasks();
			List<Task> weeklyTaskList = dbc.getWeeklyTasks();
			Task selectedTask = dbc.getTaskWithId(selectedTaskId);
			if(selectedTask != null && (dailyTaskList.contains(selectedTask) || weeklyTaskList.contains(selectedTask))) {
				Task repeatedTask = null;
				if(dailyTaskList.contains(selectedTask))
					repeatedTask = dailyTaskList.get(dailyTaskList.indexOf(selectedTask));
				else
					repeatedTask = weeklyTaskList.get(weeklyTaskList.indexOf(selectedTask));
				dbc.update(SQLScripts.deleteRow(repeatedTask.getId()));
				List<Task> taskList = dbc.getAllTasks();
				for(Task task : taskList) {
					if(task.equals(selectedTask)) {
						dbc.update(SQLScripts.deleteRow(task.getId()));
					}
				}
			}else {
				dbc.update(SQLScripts.deleteRow(selectedTaskId));
			}
			mainAppFrame.getTaskTable().updateTable();
			this.dispose();
		});
		
		cancelButton = new JButton("Cancel");
		cancelButton.addActionListener(e->{
			this.dispose();
		});
		
		bottomPanel.add(deleteButton);
		bottomPanel.add(cancelButton);
		add(bottomPanel, BorderLayout.PAGE_END);
		
		this.setSize(400, 120);
		this.setVisible(true);
		this.setResizable(false);
		this.setLocation((dim.width - this.getHeight())/2, (dim.height - this.getHeight())/2);
		this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
	}
}

