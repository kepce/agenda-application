package com.ed.utility;

import java.time.LocalDate;
import java.time.Period;
import java.time.format.DateTimeFormatter;

public class GlobalDate {
	
	public static final String SEPERATOR = "/";
	public static final DateTimeFormatter APP_DATE_FORMAT = DateTimeFormatter.ofPattern("dd / MM / uuuu");
	
	private static LocalDate date = LocalDate.now();
	
	private GlobalDate() {}
	
	public static LocalDate getDate() {
		return date;
	}
	
	public static String getFormattedDate() {
		return date.format(APP_DATE_FORMAT);
	}
	
	public static void updateDateWith(Period period) {
		date = date.plus(period);
	}
	
	
	
}
