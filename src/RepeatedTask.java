
public class RepeatedTask extends Task{


	private boolean[] repeatOn = new boolean[7];
	private boolean isEverydayTask = false;
	
	public static final int MONDAY 		= 0;
	public static final int TUESDAY 	= 1;
	public static final int WEDNESDAY 	= 2;
	public static final int THURSDAY 	= 3;
	public static final int FRIDAY 		= 4;
	public static final int SATURDAY 	= 5;
	public static final int SUNDAY 		= 6;
	
	RepeatedTask(){}
	
	RepeatedTask(String name, String status, String dueDate, boolean[] repeatOn){
		super(name, status, dueDate);
		this.repeatOn = repeatOn;
		isEverydayTask = true;
		for(boolean b : repeatOn)
			isEverydayTask = isEverydayTask && b;
	}
	
	RepeatedTask(String name, boolean isCompleted, String dueDate, boolean[] repeatOn){
		super(name, isCompleted, dueDate);
		this.repeatOn = repeatOn;
		isEverydayTask = true;
		for(boolean b : repeatOn)
			isEverydayTask = isEverydayTask && b;
	}
	
	RepeatedTask(String name, boolean isCompleted, String dueDate, boolean isEverydayTask){
		super(name, isCompleted, dueDate);
		this.isEverydayTask = isEverydayTask;
	}

	public boolean isRepeatedOn(int dayOfWeek) {
		if(isEverydayTask)
			return true;
		else
			 return repeatOn[dayOfWeek];
	}

	public void repeatOn(int dayOfWeek,boolean isRepeated) {
		this.repeatOn[dayOfWeek] = isRepeated;
		isEverydayTask = isEverydayTask && this.repeatOn[dayOfWeek];
	}
	
	public boolean isRepeatedEveryday() {
		return isEverydayTask;
	}
	
	
	
}
