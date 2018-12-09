import java.util.Calendar;

public class SimpleCalendar{

	private  Calendar calendar = Calendar.getInstance();
	
	private int year = calendar.get(Calendar.YEAR);
	private int month = calendar.get(Calendar.MONTH);
	private int dayOfWeek = calendar.get(Calendar.DAY_OF_WEEK);
	private int date = calendar.get(Calendar.DAY_OF_MONTH);
	private int hour = calendar.get(Calendar.HOUR_OF_DAY);
	private int minute = calendar.get(Calendar.MINUTE);
	private int second = calendar.get(Calendar.SECOND);
	
	public int getYear() {
		return year;
	}
	public int getMonth() {
		return month;
	}
	public int getDayOfWeek() {
		return dayOfWeek;
	}
	public int getDate() {
		return date;
	}
	public int getHour() {
		return hour;
	}
	public int getMinute() {
		return minute;
	}
	public int getSecond() {
		return second;
	}
}
