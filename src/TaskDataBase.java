import java.sql.*;

public class TaskDataBase {

	private static Connection connection;
	private static Statement statement;
	private static ResultSet resultSet;
	
	private static final String URL = "jdbc:mysql://localhost:3306/";
	private static final String USERNAME = 	"root";
	private static final String PASSWORD = "12345qwer";
	
	public TaskDataBase() {
		try {
			connection = DriverManager.getConnection(URL + "tasks", USERNAME, PASSWORD);	
		}catch(Exception e) {
			e.printStackTrace();
		}	
	}
	
	public static ResultSet query(String script) {
		try {
			statement = connection.createStatement();
			resultSet = statement.executeQuery(script);
		}catch(Exception e) {
			e.printStackTrace();
		}
		return resultSet;
	}
	
	public static void update(String script) {
		try {
			statement.executeUpdate(script);
		}catch(Exception e) {
			e.printStackTrace();
		}
	}
}