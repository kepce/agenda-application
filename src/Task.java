
public class Task {

	private String name;
	private String status = "Not Completed";
	private boolean isCompleted;
	private String dueDate;
	
	public Task() {}

	public Task(String name, String status, String dueDate) {
		super();
		this.name = name;
		this.status = status;
		this.dueDate = dueDate;
		this.isCompleted = status.equals("Completed") ? true : false;
	}

	public Task(String name, boolean isCompleted, String dueDate) {
		super();
		this.name = name;
		this.isCompleted = isCompleted;
		this.dueDate = dueDate;
		this.status = this.isCompleted ? "Completed" : "Not Completed";
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getStatus() {
		return status;
	}

	public boolean isCompleted() {
		return isCompleted;
	}

	public void setCompleted(boolean isCompleted) {
		this.isCompleted = isCompleted;
	}

	public String getDueDate() {
		return dueDate;
	}

	public void setDueDate(String dueDate) {
		this.dueDate = dueDate;
	}

	
	
	
	
}
