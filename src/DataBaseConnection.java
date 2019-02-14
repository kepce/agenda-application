import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

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
	private Statement statement;
	private ResultSet resultSet;

	private DataBaseConnection() {
		try {
			connection = DriverManager.getConnection(URL, USERNAME, PASSWORD);
			statement = connection.createStatement();
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
		try {
			resultSet = statement.executeQuery(script);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return resultSet;
	}
	
	public synchronized void update(String script) {
		try {
			statement.executeUpdate(script);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public List<Task> getTaskListOf(String date) {
		List<Task> taskList = new ArrayList<>();
		try {
			resultSet = this.query(ScriptSQL.select(date));
			while(resultSet.next()) {
				taskList.add(new Task(resultSet.getString("task_name"),
									  resultSet.getString("task_status"),
									  resultSet.getString("task_due_date"),
									  resultSet.getString("task_creation_date")));
			}
		}catch(SQLException ex) {
			ex.printStackTrace();
		}
		return taskList;
	}
	
	public List<RepeatedTask> getRepeatedTaskList(){
		List<RepeatedTask> repeatedTaskList = new ArrayList<>();
		try {
			resultSet = query(ScriptSQL.selectRepeatedTaskTable());
			while(resultSet.next()) {
				repeatedTaskList.add(new RepeatedTask(resultSet.getString("task_name"), RepeatedTask.toBoolean(resultSet.getString("repeated_on"))));
			}
		}catch(SQLException ex) {
			ex.printStackTrace();
		}
		return repeatedTaskList;
	}
	

}

