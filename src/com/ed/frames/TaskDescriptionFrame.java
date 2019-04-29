package com.ed.frames;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Container;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.AbstractButton;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JRootPane;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

import com.ed.main.Task;

public class TaskDescriptionFrame extends JFrame{

	private JTextArea textArea;
	private JButton okButton;
	private JScrollPane scrollPane;
	
	public TaskDescriptionFrame(String title, Task task) {
		super(title);
		
		String formattedTaskInfo = "\nTask Name:\n"
								   + " - " + task.getName() + "\n\n"
								   + "Creation Date:\n"
								   + " - " + task.getFormattedCreationDate() + "\n\n"
								   + "Task Description:\n"
								   + " - " + task.getDescription() + "\n\n"
								   + "\n";
								   
		textArea = new JTextArea(100, 15);
		textArea.setText(formattedTaskInfo);
		textArea.setLineWrap(true);
		textArea.setEditable(false);
		
		scrollPane = new JScrollPane(textArea);		
		
		this.add(scrollPane);
		this.setSize(260, 250);
		this.setResizable(false);
		this.setLocationRelativeTo(null);
		this.setVisible(true);
	}
	
}
