package com.ed.utility;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import com.ed.main.Task;
import java.time.*;

public class LocalTaskManager {
	
	private static Map<LocalDate, List<Task>> taskMap;
	private static DataBaseConnection dbc;
	
	static {
		taskMap = new TreeMap<>();
		dbc = DataBaseConnection.getInstance();
		
		LocalDate date = LocalDate.now().plusDays(-10);
		LocalDate endDate = LocalDate.now().plusDays(10);
		while(!date.equals(endDate)) {
			taskMap.put(date, dbc.getTaskListOf(date));
			date = date.plusDays(1);
		}
	}
	
	public static void updateDataBase() {
		for(LocalDate date : taskMap.keySet()) {
			List<Task> localList = new ArrayList<>(taskMap.get(date));
			Collections.sort(localList);
			List<Task> DBList = dbc.getTaskListOf(date);
			Collections.sort(DBList);
			if(!localList.equals(DBList)) {
				// add different elements to database
				// delete redundant elements
			}
		}
	}
	
	public static void appendTaskMap(LocalDate date) {
		taskMap.putIfAbsent(date, dbc.getTaskListOf(date));
	}

	public static List<Task> getTaskListByDate(LocalDate date) {
		return taskMap.get(date);
	}
	
	public static Map<LocalDate, List<Task>> getTaskMap(){
		return taskMap;
	}
	
	
	public static void printMap(int from, int to) {
		LocalDate date = LocalDate.now().plusDays(-from);
		while(!date.equals(GlobalDate.getDate().plusDays(to))) {
			System.out.println(taskMap.get(date));
			date = date.plusDays(1);
		}
	}

}
