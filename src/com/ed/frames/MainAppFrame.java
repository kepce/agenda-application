package com.ed.frames;

import java.awt.BorderLayout;
import java.time.Period;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import com.ed.main.TaskTable;
import com.ed.utility.*;
import com.ed.frames.EditTaskFrame;
import com.ed.frames.DeleteTaskFrame;
import com.ed.frames.AddNewTaskFrame;

public class MainAppFrame extends JFrame{
	
	private JButton leftButton = new JButton(" < ");
	private JButton rightButton = new JButton(" > ");
	private JComboBox comboBox;
	private JButton okButton = new JButton(" OK ");
	private JLabel dateLabel = new JLabel(GlobalDate.getFormattedDate());
	private TaskTable taskTable;
	private DataBaseConnection dbc;
	
	
	public MainAppFrame(String title) {
		super(title);
		dbc = DataBaseConnection.getInstance();
		taskTable = new TaskTable();

		setLayout(new BorderLayout());
		JPanel topPanel = new JPanel();
		JPanel bottomPanel = new JPanel();
		
		rightButton.addActionListener(e->{
			GlobalDate.updateDateWith(Period.ofDays(1));
			dateLabel.setText(GlobalDate.getFormattedDate());
			taskTable.updateTable();
		});
		
		leftButton.addActionListener(e->{
			GlobalDate.updateDateWith(Period.ofDays(-1));
			dateLabel.setText(GlobalDate.getFormattedDate());
			taskTable.updateTable();
		});
		
		topPanel.add(leftButton);
		topPanel.add(dateLabel);
		topPanel.add(rightButton);
		
		String tableActionOptions[] = {"Add New Task",
									   "Task Completed",
									   "Delete Task"};
		
		comboBox = new JComboBox(tableActionOptions);

		okButton.addActionListener(e->{
			String selectedItem = comboBox.getItemAt(comboBox.getSelectedIndex()).toString();
			if(selectedItem.equals("Add New Task")) {
				AddNewTaskFrame newTaskFrame = new AddNewTaskFrame("Add a New Task", this);
				this.setEnabled(false);
			}else if(selectedItem.equals("Task Completed")) {
				try {
					String selectedTaskId= taskTable.getTable().getModel().getValueAt(taskTable.getSelectedRow(), 0).toString();
					dbc.update(SQLScripts.updateStatus(Integer.valueOf(selectedTaskId), true));
					taskTable.updateTable();
				}catch(Exception ex) {
					showNotSelectedWarning(ex);
				}
			}else if(selectedItem.equals("Delete Task")) {
				try {
					DeleteTaskFrame deleteTaskFrame = new DeleteTaskFrame("Warning!", this);
				}catch(Exception ex) {
					showNotSelectedWarning(ex);
				}
			}
		});
		
		bottomPanel.add(comboBox);
		bottomPanel.add(okButton);
		
		this.add(topPanel,BorderLayout.PAGE_START);
		this.add(bottomPanel, BorderLayout.PAGE_END);
		this.add(taskTable, BorderLayout.CENTER);
		
		this.setVisible(true);
		this.setSize(600, 450);
		this.setResizable(false);
		this.setLocationRelativeTo(null);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		
	}
	
	private void showNotSelectedWarning(Exception ex) {
		JOptionPane.showMessageDialog(this, "You have not selected any task.", "Warning!", JOptionPane.WARNING_MESSAGE);
		ex.printStackTrace();
	}
	
	public TaskTable getTaskTable() {
		return this.taskTable;
	}

	

}








