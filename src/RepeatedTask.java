
public class RepeatedTask extends Task{


	private boolean[] repeatOn = new boolean[7];
	private boolean isRepeatedEveryDay = false;
	
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
		isRepeatedEveryDay = true;
		for(boolean b : repeatOn)
			isRepeatedEveryDay = isRepeatedEveryDay && b;
	}
	
	RepeatedTask(String name, boolean isCompleted, String dueDate, boolean[] repeatOn){
		super(name, isCompleted, dueDate);
		this.repeatOn = repeatOn;
		isRepeatedEveryDay = true;
		for(boolean b : repeatOn)
			isRepeatedEveryDay = isRepeatedEveryDay && b;
	}

	public boolean isRepeatedOn(int dayOfWeek) {
		return repeatOn[dayOfWeek];
	}

	public void repeatOn(int dayOfWeek,boolean isRepeated) {
		this.repeatOn[dayOfWeek] = isRepeated;
		isRepeatedEveryDay = isRepeatedEveryDay && this.repeatOn[dayOfWeek];
	}
	
	
	
}
