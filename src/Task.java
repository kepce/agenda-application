import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class Task {

	private String name;
	private String status = "Not Completed";
	private boolean isCompleted;
	private String dueDate;
	private String creationDate;
	
	public Task() {}

	public Task(String name, String status, String dueDate, String date) {
		super();
		this.name = name;
		this.status = status;
		this.dueDate = dueDate;
		this.isCompleted = status.equals("Completed") ? true : false;
		this.creationDate = date;
	}
	
	@Override
	public boolean equals(Object object) {
		if(object instanceof Task) {
			Task task = (Task) object;
			if(this.name.equals((task.getName()))){
				return true;
			}
		}
		return false;
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
	

	public String getDueDate() {
		return dueDate;
	}

	public boolean isCompleted() {
		return isCompleted;
	}

	public void setCompleted(boolean isCompleted) {
		this.isCompleted = isCompleted;
		if(isCompleted)
			status = "Completed";
		else
			status = "Not Completed";
	}


	public void setDueDate(String dueDate) {
		this.dueDate = dueDate;
	}
	
	public String getCreationDate() {
		return creationDate;
	}
	
	public void setCreationDate(LocalDate date) {
		creationDate = date.format(DateTimeFormatter.ofPattern("dd / MM / uuuu"));
	}
	

	
	
	
	
}
