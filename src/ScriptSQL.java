
public class ScriptSQL {
	
	public static String createTable(String tableName) {
		return "CREATE TABLE IF NOT EXISTS tasks.table" + tableName
				+ " (task_name VARCHAR(45) NOT NULL,"
				+ " status VARCHAR(45) NOT NULL,"
				+ " due_date VARCHAR(45) NOT NULL,"
				+ " PRIMARY KEY(task_name));";
	}
	
	public static String selectTable(String tableName) {
		return "SELECT * FROM tasks.table" + tableName + ";";
	}
	
	public static String insertRow(String tableName, String taskName, String status, String dueDate) {
		return "INSERT INTO tasks.table"+ tableName
				+ " (task_name, status, due_date)"
				+ " VALUES ('" + taskName + "', '" + status + "', '" + dueDate + "');" ;
	}
	
	public static String updateStatus(String tableName, String selectedTaskName, boolean isCompleted) {
		if(isCompleted) 
			return "UPDATE tasks.table" + tableName 
					+ " SET status = 'Completed'"
					+ " WHERE task_name = '" + selectedTaskName + "';";
		else 
			return "UPDATE tasks.table" + tableName 
					+ " SET status = 'Not Completed' "
					+ "WHERE task_name = '" + selectedTaskName + "';";
			
	}
	
	public static String updateAll(String tableName, String selectedTaskName, String newTaskName, boolean isCompleted, String newDueDate) {
		if(isCompleted) 
			return "UPDATE tasks.table" + tableName + 
					" SET task_name = '" + newTaskName + "', status = 'Completed', dueDate = '" + newDueDate + 
					"' WHERE task_name = '" + selectedTaskName + "';";
		else 
			return "UPDATE tasks.table" + tableName + 
					" SET task_name = '" + newTaskName + "', status = 'Not Completed', dueDate = '" + newDueDate + 
					"' WHERE task_name = '" + selectedTaskName + "';";
		
	}
	
	public static String deleteRow(String tableName, String selectedTaskName) {
		return "DELETE FROM tasks.table" + tableName + " WHERE task_name = '" + selectedTaskName + "';";
	}
	
	public static String deleteRepeatedRow(String selectedTaskName) {
		return "DELETE FROM tasks.repeated_task_table WHERE task_name = '" + selectedTaskName + "';";
	}
	
	public static String createRepeatedTaskTable() {
		return "CREATE TABLE IF NOT EXISTS tasks.repeated_task_table ("
				+ "task_id INT NOT NULL AUTO_INCREMENT,"
				+ " task_name VARCHAR(45) NOT NULL,"
				+ " repeated_on VARCHAR(45) NOT NULL,"
				+ " PRIMARY KEY(task_id));";
	}
	
	public static String selectRepeatedTaskTable() {
		return "SELECT * FROM tasks.repeated_task_table;";
	}
	
	/// <<<<<<<<< not done
	public static String insertRepeatedTask(String taskName, String repeatedDays) {
		return "INSERT INTO tasks.repeated_task_table"+" (task_name, repeated_on) VALUES ('" +
				taskName + "', '" + repeatedDays + "');" ;
	} //////////////////

}
