package com.ed.utility;

import java.time.LocalDate;

import com.ed.main.Task;

public class SQLScripts {
	
	public static String createTable() {
		return "CREATE TABLE IF NOT EXISTS tasks.task_table"
				+ " (task_id INT NOT NULL AUTO_INCREMENT,"
				+ " task_creation_date VARCHAR(15) NOT NULL,"
				+ " task_name VARCHAR(35) NOT NULL,"
				+ " task_description VARCHAR(100) NOT NULL,"
				+ " task_status VARCHAR(5) NOT NULL,"
				+ " task_due_date VARCHAR(100) NOT NULL,"
				+ " task_repetition INT NOT NULL,"
				+ " PRIMARY KEY(task_id));";
	}
	
	public static String selectAll() {
		return "SELECT * FROM tasks.task_table;";
	}
	
	public static String selectById(int taskId) {
		return "SELECT * FROM tasks.task_table WHERE task_id = '" + taskId + "'";
	}
	public static String selectByDate(LocalDate date) {
		return "SELECT * FROM tasks.task_table"
				+ " WHERE task_creation_date = '" + date.format(GlobalDate.APP_DATE_FORMAT) + "' AND task_repeatition = '" + Task.NOT_REPEATED + "';";
	}
	
	public static String selectByName(String name) {
		return "SELECT * FROM tasks.task_table WHERE task_name = '" + name + "'";
	}
	
	public static String selectDailyTasks() {
		return "SELECT * FROM tasks.task_table WHERE task_repeatition = '" + Task.DAILY + "'";
	}
	
	public static String selectWeeklyTasks() {
		return "SELECT * FROM tasks.task_table WHERE task_repeatition = '" + Task.WEEKLY + "'"; 
	}
	public static String insertRow(Task task) {
		return "INSERT INTO tasks.task_table"
				+ " (task_name, task_description, task_status, task_due_date, task_creation_date, task_repeatition)"
				+ " VALUES ('" + task.getName() + "', '" + task.getDescription() + "', '" + task.isCompleted() + "', '" + task.getFormattedDueDate() + "', '" + task.getFormattedCreationDate() + "', '" + task.getRepetitionConstant() + "');" ;
	}
	
	public static String updateStatus(int taskId, boolean isCompleted) {
			return "UPDATE tasks.task_table" 
					+ " SET task_status = '" + isCompleted + "'"
					+ " WHERE task_id = '" + taskId + "';";	
	}
	
	public static String updateRow(int taskId, Task task) {
			return "UPDATE tasks.task_table"
				+ " SET task_name = '" + task.getName() + "',"
				+ " task_status = '" + task.getStatus() + "',"
				+ " task_due_date = '" + task.getDueDate() + "',"
				+ "task_creation_date = '" + task.getCreationDate() + "',"
				+ "task_description = '" + task.getDescription() + "',"
				+ "task_repetition = '" + task.getRepetitionConstant() + "',"
				+ "' WHERE task_id = '" + taskId + "';";		
	}
	
	public static String deleteRow(int selectedTaskId) {
		return "DELETE FROM tasks.task_table WHERE task_id = '" + selectedTaskId + "';";
	}
}
