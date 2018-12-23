import java.util.Calendar;

public class ScriptSQL {
	
	public static String createTable(int year, int dayOfYear) {
		return "CREATE TABLE IF NOT EXISTS tasks.table" + year + "_" + dayOfYear 
				 +  " (task_name VARCHAR(45) NOT NULL, task_desc VARCHAR(100) NOT NULL," 
				+ " due_date VARCHAR(20) NOT NULL, done VARCHAR(2) NOT NULL, PRIMARY KEY(task_name));";
	}
	
	public static String selectTable(int year,int dayOfYear) {
		return "SELECT * FROM tasks.table" + year + "_" + dayOfYear;
	}
}
