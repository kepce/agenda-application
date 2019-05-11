package com.ed.config;

import java.awt.event.MouseListener;

import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;

import com.ed.table.CellMouseListener;
import com.ed.table.CellRenderer;
import com.ed.table.TableModel;
import com.ed.table.TaskTable;
import com.ed.utility.DatabaseConnection;
import com.ed.utility.TaskManager;
import com.ed.views.AddNewTaskFrame;
import com.ed.views.DeleteTaskFrame;
import com.ed.views.MainAppFrame;

@Configuration
@ComponentScan("com.ed")
public class GeneralConfig {

	@Bean
	@Scope("singleton")
	public DatabaseConnection databaseConnection() {
		return new DatabaseConnection();
	}
	
	@Bean
	@Scope("prototype")
	public DefaultTableModel tableModel() {
		return new TableModel();
	}
	
	@Bean
	@Scope("singleton")
	public TaskManager taskManager() {
		return new TaskManager(databaseConnection());
	}
	@Bean
	@Scope("prototype")
	public DefaultTableCellRenderer cellRenderer() {
		return new CellRenderer(taskManager());
	}
	
	@Bean
	@Scope("singleton")
	public TaskTable taskTable() {
		return new TaskTable(databaseConnection(),
							 tableModel(),
							 cellRenderer(),
							 taskManager());
	}
	
	@Bean
	@Scope("prototype")
	public MouseListener cellMouseListener() {
		return new CellMouseListener(taskTable(), taskManager());
	}
	

}
