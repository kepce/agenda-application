package com.ed.utility;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Component;

import com.ed.task.Task;

@Component
public class TaskManager {
	
	private DatabaseConnection databaseConnection;
	
	public TaskManager(DatabaseConnection databaseConnection) {
		this.databaseConnection = databaseConnection;
	}
	
	public Task getTaskWithId(int taskId) {
		try(ResultSet resultSet = databaseConnection.query(SQLScripts.selectById(taskId));){
			if(resultSet.next()) {
				return new Task(resultSet);
			}
		}catch(SQLException ex) {
			ex.printStackTrace();
		}
		return null;
	}
	
	public List<Task> getTaskListOf(LocalDate date) {
		List<Task> taskList = new ArrayList<>();
		try(ResultSet resultSet = databaseConnection.query(SQLScripts.selectByDate(date));){
			while(resultSet.next()) {
				taskList.add(new Task(resultSet));
			}
		}catch(SQLException ex) {
			ex.printStackTrace();
		}
		return taskList;
	}
	
	public List<Task> getDailyTasks(){
		List<Task> dailyTaskList = new ArrayList<>();
		try(ResultSet resultSet = databaseConnection.query(SQLScripts.selectDailyTasks())){
			while(resultSet.next()) {
				dailyTaskList.add(new Task(resultSet));
			}
		}catch(SQLException ex) {
			ex.printStackTrace();
		}
		return dailyTaskList;
	}
	
	public List<Task> getWeeklyTasks(){ 
		List<Task> weeklyTaskList = new ArrayList<>();
		try(ResultSet resultSet = databaseConnection.query(SQLScripts.selectWeeklyTasks())){
			while(resultSet.next()) {
				weeklyTaskList.add(new Task(resultSet));
			}
		}catch(SQLException ex) {
			ex.printStackTrace();
		}
		return weeklyTaskList;
	}
	
	public List<Task> getAllTasks(){
		List<Task> taskList = new ArrayList<>();
		try(ResultSet resultSet = databaseConnection.query(SQLScripts.selectAll())){
			while(resultSet.next()) {
				taskList.add(new Task(resultSet));
			}
		}catch(SQLException ex) {
			ex.printStackTrace();
		}
		return taskList;
	}
	
	public int getRepetitionConstantIfExists(Task task) {
		List<Task> taskList = new ArrayList<>();
		try(ResultSet resultSet = databaseConnection.query(SQLScripts.selectByName(task.getName()));){
			while(resultSet.next()) {
				taskList.add(new Task(resultSet));
			}
		}catch(SQLException ex) {
			ex.printStackTrace();
		}
		Optional<Task> optionalTask = taskList.stream()
											  .filter(t->t.getRepetitionConstant() != 0)
											  .findFirst();
		if(optionalTask.isPresent())
			return optionalTask.get().getRepetitionConstant();
		else
			return Task.NOT_REPEATED;
	}
}
