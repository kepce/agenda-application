package com.ed.utility;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.omg.CORBA.DATA_CONVERSION;
import org.springframework.stereotype.Component;

import com.ed.task.Task;

@Component
public class DatabaseConnection {

	private static final String TAG = "jdbc:mysql://";
	private static final String HOST_NAME = "localhost";
	private static final String PORT_NUMBER = "3306";
	
	private static final String DATA_BASE_NAME = "tasks";
	private static final String USERNAME = "root";
	private static final String PASSWORD = "12345qwer";
	
	private static final String URL = TAG + HOST_NAME + ":" + PORT_NUMBER + "/" + DATA_BASE_NAME;
	
	private Connection connection;

	public DatabaseConnection() {
		try {
			connection = DriverManager.getConnection(URL, USERNAME, PASSWORD);
		} catch (SQLException ex) {
			ex.printStackTrace();
		}
	}
	
	public ResultSet query(String script) {
		ResultSet resultSet = null;
		try {
			Statement statement = connection.createStatement();
			resultSet = statement.executeQuery(script);
		} catch (SQLException ex) {
			ex.printStackTrace();
		}
		return resultSet;
	}
	
	public void update(String script) {
		try(Statement statement = connection.createStatement()){
			statement.executeUpdate(script);
		}catch(SQLException ex) {
			ex.printStackTrace();
		}
	}

	
}

