
public class ScriptSQL {
	
	public static String createTable(int year, int dayOfYear) {
		return "CREATE TABLE IF NOT EXISTS tasks.table" + year + "_" + dayOfYear 
				 +  " (task_name VARCHAR(45) NOT NULL, status VARCHAR(45) NOT NULL," 
				+ " due_date VARCHAR(45) NOT NULL, PRIMARY KEY(task_name));";
	}
	
	public static String selectTable(int year,int dayOfYear) {
		return "SELECT * FROM tasks.table" + year + "_" + dayOfYear + ";";
	}
	
	public static String insertRow(int year, int dayOfYear, String taskName, String status, String dueDate) {
		
		return "INSERT INTO tasks.table"+ year + "_" + dayOfYear + 
				" (task_name, status, due_date) VALUES ('" +
				taskName + "', '" + status + "', '" + dueDate + "');" ;
	}
	
	public static String updateStatus(int year, int dayOfYear, String selectedTaskName, boolean isCompleted) {
		if(isCompleted) 
			return "UPDATE tasks.table" + year + "_" + dayOfYear + " SET status = 'Completed' WHERE task_name = '" + selectedTaskName + "';";
		else {
			return "UPDATE tasks.table" + year + "_" + dayOfYear + " SET status = 'Not Completed' WHERE task_name = '" + selectedTaskName + "';";
		}	
	}
	public static String deleteRow(int year, int dayOfYear, String selectedTaskName) {
		return "DELETE FROM tasks.table" + year + "_" + dayOfYear + " WHERE task_name = '" + selectedTaskName + "';";
	}
	

}
