package com.ed.views;

import java.awt.BorderLayout;
import java.time.Period;

import javax.annotation.PostConstruct;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.stereotype.Component;

import com.ed.utility.*;
import com.ed.views.AddNewTaskFrame;
import com.ed.views.DeleteTaskFrame;
import com.ed.table.CellMouseListener;
import com.ed.table.CellRenderer;
import com.ed.table.TableModel;
import com.ed.table.TaskTable;
import com.ed.config.GeneralConfig;


public class MainAppFrame extends JFrame{
	
	private JButton leftButton;
	private JButton rightButton;
	private JComboBox comboBox;
	private JButton okButton;
	private JLabel dateLabel;
	
	private DatabaseConnection databaseConnection;
	private TaskTable taskTable;
	private AnnotationConfigApplicationContext context;
	
	public MainAppFrame(String title) {
		super(title);
		
		context = new AnnotationConfigApplicationContext(GeneralConfig.class);
		
		databaseConnection = context.getBean("databaseConnection", DatabaseConnection.class);
		taskTable = context.getBean("taskTable", TaskTable.class);
		
		setLayout(new BorderLayout());
		JPanel topPanel = new JPanel();
		JPanel bottomPanel = new JPanel();
		
		dateLabel = new JLabel(GlobalDate.getFormattedDate());
		
		rightButton = new JButton(" > ");
		rightButton.addActionListener(e->{
			GlobalDate.updateDateWith(Period.ofDays(1));
			dateLabel.setText(GlobalDate.getFormattedDate());
			taskTable.updateTable();
		});
		
		leftButton = new JButton(" < ");
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

		okButton = new JButton(" OK ");
		okButton.addActionListener(e->{
			String selectedItem = comboBox.getItemAt(comboBox.getSelectedIndex()).toString();
			if(selectedItem.equals("Add New Task")) {
				this.setEnabled(false);
				AddNewTaskFrame addNewTaskFrame = new AddNewTaskFrame("Add a New Task", this);
				addNewTaskFrame.setVisible(true);
			}else if(selectedItem.equals("Task Completed")) {
				try {
					String selectedTaskId= taskTable.getTable().getModel().getValueAt(taskTable.getSelectedRow(), 0).toString();

					databaseConnection.update(SQLScripts.updateStatus(Integer.valueOf(selectedTaskId), true));
					taskTable.updateTable();
				}catch(Exception ex) {
					showNotSelectedWarning(ex);
				}
			}else if(selectedItem.equals("Delete Task")) {
				try {
					this.setEnabled(false);
					String selectedTaskId = taskTable.getTable().getModel().getValueAt(taskTable.getSelectedRow(), 0).toString();
					DeleteTaskFrame deleteTaskFrame = new DeleteTaskFrame("Warning", this);
					deleteTaskFrame.setSelectedTaskId(Integer.valueOf(selectedTaskId));
					deleteTaskFrame.setVisible(true);
				}catch(Exception ex) {
					this.setEnabled(true);
					showNotSelectedWarning(ex);
				}
			}
		});
		
		bottomPanel.add(comboBox);
		bottomPanel.add(okButton);
		
		this.add(topPanel,BorderLayout.PAGE_START);
		this.add(bottomPanel, BorderLayout.PAGE_END);
		this.add(taskTable, BorderLayout.CENTER);
		
		this.setSize(550, 440);
		this.setResizable(false);
		this.setLocationRelativeTo(null);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setVisible(true);
	}
	
	private void showNotSelectedWarning(Exception ex) {
		JOptionPane.showMessageDialog(this, "You have not selected any task.", "Warning!", JOptionPane.WARNING_MESSAGE);
		ex.printStackTrace();
	}

	public AnnotationConfigApplicationContext getContext() {
		return this.context;
	}
	
}








