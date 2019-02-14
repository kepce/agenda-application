import java.time.LocalDate;

public class RepeatedTask extends Task{


	private boolean[] repeatOn = new boolean[7];
	private boolean isEverydayTask = false;
	
	private String[] repeatDays; //  <<<
	private LocalDate date; //    <<<
	
	public RepeatedTask(){}
	
	public RepeatedTask(String name, boolean[] repeatOn){
		super(name, "Not Completed", "Daily Task", null);
		this.repeatOn = repeatOn;
		isEverydayTask = true;
		for(boolean b : repeatOn)
			isEverydayTask = isEverydayTask && b;
	}

	public RepeatedTask(String name, String[] repeatedDays) { // <<<
		super(name, "Not Completed", "Daily Task", null);
		this.repeatDays = repeatedDays;
	}
	
	public boolean[] getRepeatedDays() {
		return repeatOn;
	}

	public void repeatOn(int dayOfWeek, boolean isRepeated) {
		this.repeatOn[dayOfWeek] = isRepeated;
		isEverydayTask = isEverydayTask && this.repeatOn[dayOfWeek];
	}
	
	public boolean isRepeatedEveryday() {
		return isEverydayTask;
	}
	
	public static boolean[] toBoolean(String string) {
		boolean[] repeatedDays = new boolean[7];
		for(int i=0; i<7; i++) {
			if(string.toCharArray()[i]== '1')
				repeatedDays[i] = true;
			else
				repeatedDays[i] = false;
		}
		return repeatedDays;
	}
	
	public static String booleanToString(boolean[] boolArray) {
		String string = "";
		for(int i=0; i<7; i++) {
			if(boolArray[i])
				string += "1";
			else
				string += "0";
		}
		return string;
	}
	
	
	
}
