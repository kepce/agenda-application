package com.ed.frames;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.time.LocalDate;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.border.Border;
import javax.swing.border.EmptyBorder;

import com.ed.main.Task;
import com.ed.utility.*;


public class AddNewTaskFrame extends JFrame {
	
	private DataBaseConnection dbc;
	
	private JPanel mainPanel, propertiesPanel, datePanel, buttonPanel;
	private JLabel taskNameLabel, taskDescLabel, dateLabel;
	private JLabel[] seperatorLabels;
	private JTextField taskNameText, dayText, monthText, yearText;
	private JTextArea taskDescArea;
	private JCheckBox repeatCheckBox;
	private JRadioButton dailyRadBut, weeklyRadBut;
	private JButton button;
	private JScrollPane scrollPane;
	
	private ButtonGroup buttonGroup;

	public AddNewTaskFrame(String title, MainAppFrame mainAppFrame) {
		super(title);
		
		dbc = DataBaseConnection.getInstance();
		
		mainPanel = new JPanel();
		mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
		mainPanel.setBorder(new EmptyBorder(0, 15, 0, 15));
		
		propertiesPanel = new JPanel();
		propertiesPanel.setLayout(new BoxLayout(propertiesPanel, BoxLayout.Y_AXIS));
		propertiesPanel.setAlignmentX(CENTER_ALIGNMENT);
		
		datePanel = new JPanel();
		datePanel.setLayout(new FlowLayout(FlowLayout.LEFT));

		buttonPanel = new JPanel();
		buttonPanel.setLayout(new FlowLayout(FlowLayout.CENTER));
		
		taskNameLabel = new JLabel("Task Name: ");
		taskDescLabel = new JLabel("Task Description: ");
		dateLabel = new JLabel("Due Date: ");
		seperatorLabels = new JLabel[] {new JLabel(GlobalDate.SEPERATOR), new JLabel(GlobalDate.SEPERATOR)};
		
		taskNameText = new JTextField();
		taskNameText.setMaximumSize( new Dimension(Integer.MAX_VALUE, taskNameText.getPreferredSize().height));
		taskNameText.setAlignmentX(LEFT_ALIGNMENT);
		dayText = new JTextField(2);
		monthText = new JTextField(2);
		yearText = new JTextField(4);
		
		taskDescArea = new JTextArea(taskNameText.getPreferredSize().width, 15);
		taskDescArea.setLineWrap(true);
		scrollPane = new JScrollPane(taskDescArea);
		scrollPane.setAlignmentX(LEFT_ALIGNMENT);
		
		dailyRadBut = new JRadioButton("Every Day");
		dailyRadBut.setEnabled(false);
		weeklyRadBut = new JRadioButton("Every Week");
		weeklyRadBut.setEnabled(false);


		repeatCheckBox = new JCheckBox("Repeat");
		repeatCheckBox.addActionListener(e->{
			if(repeatCheckBox.isSelected()) {
				dailyRadBut.setEnabled(true);
				weeklyRadBut.setEnabled(true);
			}else {
				dailyRadBut.setEnabled(false);
				weeklyRadBut.setEnabled(false);
			}
		});
		
		buttonGroup = new ButtonGroup();
		buttonGroup.add(dailyRadBut);
		buttonGroup.add(weeklyRadBut);
		
		button = new JButton("Add");
		button.addActionListener(e->{
			LocalDate dueDate = LocalDate.of(Integer.valueOf(yearText.getText()), Integer.valueOf(monthText.getText()), Integer.valueOf(dayText.getText()));
			Task task = new Task(Task.NEW_TASK_ID, taskNameText.getText(), taskDescArea.getText(), dueDate, GlobalDate.getDate(), getRepeatedProperty());
			dbc.update(SQLScripts.insertRow(task));
			mainAppFrame.getTaskTable().updateTable();
			mainAppFrame.setEnabled(true);
			AddNewTaskFrame.this.dispose();
		});
		
		propertiesPanel.add(Box.createRigidArea(new Dimension(0, 10)));
		propertiesPanel.add(taskNameLabel);
		propertiesPanel.add(taskNameText);
		propertiesPanel.add(Box.createRigidArea(new Dimension(0, 10)));
		propertiesPanel.add(taskDescLabel);
		propertiesPanel.add(scrollPane);
		propertiesPanel.add(Box.createRigidArea(new Dimension(0, 10)));
		propertiesPanel.add(repeatCheckBox);
		propertiesPanel.add(dailyRadBut);
		propertiesPanel.add(weeklyRadBut);
		propertiesPanel.add(Box.createRigidArea(new Dimension(0, 10)));
		
		datePanel.add(dateLabel);
		datePanel.add(dayText);
		datePanel.add(seperatorLabels[0]);
		datePanel.add(monthText);
		datePanel.add(seperatorLabels[1]);
		datePanel.add(yearText);
		buttonPanel.add(button);
		
		mainPanel.add(propertiesPanel);
		mainPanel.add(datePanel);
		mainPanel.add(Box.createRigidArea(new Dimension(0, 10)));
		mainPanel.add(buttonPanel);
		
		this.add(mainPanel);
		
		this.setVisible(true);
		this.setSize(400, 375);
		this.setLocationRelativeTo(null);
		this.addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent windowEvent) {
				mainAppFrame.setEnabled(true);
				AddNewTaskFrame.this.dispose();
			}
		});
		
	}
	
	private int getRepeatedProperty() {
		if(dailyRadBut.isSelected())
			return Task.DAILY;
		else if(weeklyRadBut.isSelected())
			return Task.WEEKLY;
		else
			return Task.NOT_REPEATED;
	}
	
	
	
}
