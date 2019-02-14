
public class ScriptSQL {
	
	public static String createTable() {
		return "CREATE TABLE IF NOT EXISTS tasks.task_table"
				+ " (task_id INT NOT NULL AUTO_INCREMENT,"
				+ " task_creation_date VARCHAR(15) NOT NULL,"
				+ " task_name VARCHAR(35) NOT NULL,"
				+ " task_status VARCHAR(15) NOT NULL,"
				+ " task_due_date VARCHAR(15) NOT NULL,"
				+ " PRIMARY KEY(task_id));";
	}
	
	public static String select() {
		return "SELECT * FROM tasks.task_table;";
	}
	
	public static String select(String date) {
		return "SELECT * FROM tasks.task_table"
				+ " WHERE task_creation_date = '" + date + "';";
	}
	
	public static String insertRow(Task task) {
		return "INSERT INTO tasks.task_table"
				+ " (task_name, task_status, task_due_date, task_creation_date)"
				+ " VALUES ('" + task.getName() + "', '" + task.getStatus() + "', '" + task.getDueDate() + "', '" + task.getCreationDate() + "');" ;
	}
	
	public static String updateStatus(String selectedTaskName, String creationDate, boolean isCompleted) {
		if(isCompleted) 
			return "UPDATE tasks.task_table" 
					+ " SET task_status = 'Completed'"
					+ " WHERE task_name = '" + selectedTaskName + "'"
					+ " AND task_creation_date = '" + creationDate + "';";
		else 
			return "UPDATE tasks.task_table"
					+ " SET task_status = 'Not Completed'"
					+ " WHERE task_name = '" + selectedTaskName + "'"
					+ " AND task_creation_date = '" + creationDate + "';";
			
	}
	
	public static String updateRow(String selectedTaskName, Task task) {
			return "UPDATE tasks.task_table"
				+ " SET task_name = '" + task.getName() + "', status = '" + task.getStatus() + "', dueDate = '" + task.getDueDate() + 
					"' WHERE task_name = '" + selectedTaskName + "';";		
	}
	
	public static String deleteRow(String selectedTaskName) {
		return "DELETE FROM tasks.task_table WHERE task_name = '" + selectedTaskName + "';";
	}
	
	public static String deleteRowFromRepTable(String selectedTaskName) {
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
	
	public static String insertRepeatedTask(String taskName, String repeatedDays) {
		return "INSERT INTO tasks.repeated_task_table"+" (task_name, repeated_on) VALUES ('" +
				taskName + "', '" + repeatedDays + "');" ;
	}

}
