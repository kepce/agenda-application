package com.ed.utility;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import com.ed.main.Task;

public class DataBaseConnection {

	private static volatile DataBaseConnection dbc = null;
	
	private static final String TAG = "jdbc:mysql://";
	private static final String HOST_NAME = "localhost";
	private static final String PORT_NUMBER = "3306";
	
	private static final String URL = TAG + HOST_NAME + ":" + PORT_NUMBER + "/";
	
	private static final String DATA_BASE_NAME = "tasks";
	private static final String USERNAME = "root";
	private static final String PASSWORD = "12345qwer";
	
	private Connection connection;

	private DataBaseConnection() {
		try {
			connection = DriverManager.getConnection(URL, USERNAME, PASSWORD);
		} catch (SQLException ex) {
			ex.printStackTrace();
		}
	}
	
	public static DataBaseConnection getInstance() {
		if(dbc == null) {
			synchronized(DataBaseConnection.class) {
				if(dbc == null)
					return new DataBaseConnection();
			}
		}
		return dbc;
	}
	
	public ResultSet query(String script) {
		ResultSet resultSet = null;
		try {
			Statement statement = connection.createStatement();
			resultSet = statement.executeQuery(script);
		} catch (SQLException ex) {
			ex.printStackTrace();
		}
		return resultSet;
	}
	
	public void update(String script) {
		try(Statement statement = connection.createStatement()){
			statement.executeUpdate(script);
		}catch(SQLException ex) {
			ex.printStackTrace();
		}
	}
	
	public Task getTaskWithId(int taskId) {
		try(ResultSet resultSet = this.query(SQLScripts.selectById(taskId));){
			if(resultSet.next()) {
				return new Task(resultSet.getInt("task_id"),
						  resultSet.getString("task_name"),
						  resultSet.getString("task_description"),
						  resultSet.getString("task_due_date"),
						  resultSet.getString("task_creation_date"),
						  resultSet.getInt("task_repetition"),
						  resultSet.getBoolean("task_Status"));
			}
		}catch(SQLException ex) {
			ex.printStackTrace();
		}
		return null;
	}
	
	public List<Task> getTaskListOf(LocalDate date) {
		List<Task> taskList = new ArrayList<>();
		try(ResultSet resultSet = this.query(SQLScripts.selectByDate(date));){
			while(resultSet.next()) {
				taskList.add(new Task(resultSet.getInt("task_id"),
									  resultSet.getString("task_name"),
									  resultSet.getString("task_description"),
									  resultSet.getString("task_due_date"),
									  resultSet.getString("task_creation_date"),
									  resultSet.getInt("task_repetition"),
									  resultSet.getBoolean("task_Status")));
			}
		}catch(SQLException ex) {
			ex.printStackTrace();
		}
		return taskList;
	}
	
	public List<Task> getDailyTasks(){
		List<Task> dailyTaskList = new ArrayList<>();
		try(ResultSet resultSet = this.query(SQLScripts.selectDailyTasks())){
			while(resultSet.next()) {
				dailyTaskList.add(new Task(resultSet.getInt("task_id"),
									  resultSet.getString("task_name"),
									  resultSet.getString("task_description"),
									  resultSet.getString("task_due_date"),
									  resultSet.getString("task_creation_date"),
									  resultSet.getInt("task_repetition"),
									  resultSet.getBoolean("task_Status")));
			}
		}catch(SQLException ex) {
			ex.printStackTrace();
		}
		return dailyTaskList;
	}
	
	public List<Task> getWeeklyTasks(){ 
		List<Task> weeklyTaskList = new ArrayList<>();
		try(ResultSet resultSet = this.query(SQLScripts.selectWeeklyTasks())){
			while(resultSet.next()) {
				weeklyTaskList.add(new Task(resultSet.getInt("task_id"),
									  resultSet.getString("task_name"),
									  resultSet.getString("task_description"),
									  resultSet.getString("task_due_date"),
									  resultSet.getString("task_creation_date"),
									  resultSet.getInt("task_repetition"),
									  resultSet.getBoolean("task_Status")));
			}
		}catch(SQLException ex) {
			ex.printStackTrace();
		}
		return weeklyTaskList;
	}
	
	public List<Task> getAllTasks(){
		List<Task> taskList = new ArrayList<>();
		try(ResultSet resultSet = this.query(SQLScripts.selectAll())){
			while(resultSet.next()) {
				taskList.add(new Task(resultSet.getInt("task_id"),
									  resultSet.getString("task_name"),
									  resultSet.getString("task_description"),
									  resultSet.getString("task_due_date"),
									  resultSet.getString("task_creation_date"),
									  resultSet.getInt("task_repetition"),
									  resultSet.getBoolean("task_Status")));
			}
		}catch(SQLException ex) {
			ex.printStackTrace();
		}
		return taskList;
	}
	
	public int getRepetitionConstantIfExists(Task task) {
		List<Task> taskList = new ArrayList<>();
		try(ResultSet resultSet = this.query(SQLScripts.selectByName(task.getName()));){
			while(resultSet.next()) {
				taskList.add(new Task(resultSet.getInt("task_id"),
									  resultSet.getString("task_name"),
									  resultSet.getString("task_description"),
									  resultSet.getString("task_due_date"),
									  resultSet.getString("task_creation_date"),
									  resultSet.getInt("task_repetition"),
									  resultSet.getBoolean("task_Status")));
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

