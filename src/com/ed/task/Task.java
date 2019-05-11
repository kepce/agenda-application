package com.ed.task;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

import com.ed.utility.GlobalDate;

public class Task implements Comparable<Task>{

	private String name;
	private String description;
	private boolean isCompleted;
	private LocalDate dueDate;
	private LocalDate creationDate;
	private int repetitionConstant;
	private String status;
	private int id;

	public static final int NEW_TASK_ID = -1;
	
	public static final int NOT_REPEATED = 0;
	public static final int DAILY = 1;
	public static final int WEEKLY = 2;
	
	
	/*
	 * Constructors
	 */
	
	public Task(int id, String name, String description, LocalDate dueDate, LocalDate creationDate, final int repetitionConst) {
		super();
		this.id = id;
		this.name = name;
		this.description = description;
		this.dueDate = dueDate;
		this.creationDate = creationDate;
		this.repetitionConstant = repetitionConst;
	}
	
	public Task(ResultSet resultSet) {
		super();
		try {
		this.id = resultSet.getInt("task_id");
		this.name = resultSet.getString("task_name");
		this.description = resultSet.getString("task_description");
		this.creationDate = LocalDate.parse(resultSet.getString("task_due_date"), GlobalDate.APP_DATE_FORMAT);
		this.repetitionConstant = resultSet.getInt("task_repetition");
		this.isCompleted =  resultSet.getBoolean("task_Status");
		
			try {
				this.dueDate = LocalDate.parse(resultSet.getString("task_creation_date")); 
			}catch(DateTimeParseException ex) {
				this.dueDate = LocalDate.parse(resultSet.getString("task_creation_date"), GlobalDate.APP_DATE_FORMAT);
			}catch(Exception ex){
				ex.printStackTrace();
			}
		}catch(SQLException ex) {
			ex.printStackTrace();
		}

	}
		
	/*
	 * Overriding Methods
	 */
	
	@Override
	public boolean equals(Object object) {
		if(object != null && object instanceof Task) {
			Task task = (Task) object;
			if(this.name.equals((task.getName())) && this.description.equals(task.description)){
				return true;
			}
		}
		return false;
	}
	
	@Override 
	public String toString() {
		return this.getCreationDate() + " - " + this.getName();
	}
	
	@Override
	public int compareTo(Task task) {
		return this.getCreationDate().compareTo(task.getCreationDate());
	}
	

	/*
	 * Getters
	 */
	public int getId() {
		return this.id;
	}
	public String getName() {
		return this.name;
	}

	public LocalDate getDueDate() {
		return this.dueDate;
	}
	
	public String getFormattedDueDate() {
		return this.dueDate.format(GlobalDate.APP_DATE_FORMAT);
	}
	
	public String getStatus() {
		this.status = isCompleted ? "Completed" : "Not Completed";
		return this.status;
	}
	
	public boolean isCompleted() {
		return this.isCompleted;
	}
	
	public LocalDate getCreationDate() {
		return this.creationDate;
	}
	
	public String getFormattedCreationDate() {
		return this.creationDate.format(GlobalDate.APP_DATE_FORMAT);
	}
	
	public int getRepetitionConstant() {
		return this.repetitionConstant;
	}
	
	public String getDescription() {
		return this.description;
	}
	
	/*
	 * Setters
	 */
	
	public void setRepetitionConstant(final int repetitionConstant) {
		switch(repetitionConstant) {
			case Task.DAILY:
				this.repetitionConstant = Task.DAILY;
				break;
			case Task.WEEKLY:
				this.repetitionConstant = Task.WEEKLY;
				break;
			default:
				this.repetitionConstant = Task.NOT_REPEATED;
				break;
		}
	}
	public void setName(String name) {
		this.name = name;
	}

	public void setCompleted(boolean isCompleted) {
		this.isCompleted = isCompleted;
	}

	public void setDueDate(LocalDate date) {
		this.dueDate = date;
	}
	
	public void setCreationDate(LocalDate date) {
		this.creationDate = date;
	}
	
	public void setDescription(String desc) {
		this.description = desc;
	}


}
