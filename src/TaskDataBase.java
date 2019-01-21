import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JOptionPane;

public class TaskDataBase {

	private static final String DATABASE_NAME = "tasks";
	private static final String URL = "jdbc:mysql://localhost:3306/" + DATABASE_NAME;
	private static final String USERNAME = 	"root";
	private static final String PASSWORD = "12345qwer";
	
	private Connection connection;
	private Statement statement;
	private ResultSet resultSet;
	
	private List <Task> taskList = new ArrayList<Task>();
	
	public TaskDataBase(MainAppFrame mainAppFrame){
		try { 
			connection = DriverManager.getConnection(URL, USERNAME, PASSWORD);
			statement = connection.createStatement();
		}catch(Exception e) {
			JOptionPane.showMessageDialog(mainAppFrame, "Error in database connection!", "Error!", JOptionPane.WARNING_MESSAGE);
		}
	}
	
	public List<Task> getTaskListFromDataBase(String script) {
		try {
			resultSet = statement.executeQuery(script);
			while(resultSet.next()) {
				taskList.add(new Task(resultSet.getString("task_name"), resultSet.getString("status"), resultSet.getString("due_date")));
			}
		}catch(SQLException e) {
			e.printStackTrace();
		}
		return taskList;
	}
	
	public ResultSet query(String script) {
		try {
			resultSet = statement.executeQuery(script);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return resultSet;
	}
	
	public void update(String script) {
		try {
			statement.executeUpdate(script);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	
}
