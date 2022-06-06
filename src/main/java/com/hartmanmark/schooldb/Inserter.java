package com.hartmanmark.schooldb;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

import org.apache.ibatis.jdbc.ScriptRunner;

public class Inserter {

	private String url = "jdbc:postgresql://localhost/postgres";
	private String user = "postgres";
	private String password = "123";
	private String pathToDataSQLFile = "/home/user/java/GitLab/Task 7/Data.sql";

	public void insertStudents(String firstName, String lastName, int i) throws SQLException {
		String str = "INSERT INTO school.students(STUDENT_ID, GROUP_ID, FIRST_NAME, LAST_NAME) VALUES (" + i
				+ " , NULL  , '" + firstName + "', '" + lastName + "');";
		Statement statement = connect().createStatement();
		statement.execute(str);
	}

	public void insertGroups(String groupName, int i) throws SQLException {
		String str = "INSERT INTO school.groups(GROUP_ID, GROUP_NAME) VALUES (" + i + ", '" + groupName + "');";
		Statement statement = connect().createStatement();
		statement.execute(str);
	}

	public void insertCourses() throws SQLException, FileNotFoundException {
		ScriptRunner scriptRunner = new ScriptRunner(connect());
		scriptRunner.runScript(readSQLFile());
	}

	private BufferedReader readSQLFile() throws FileNotFoundException {
		BufferedReader readerData = new BufferedReader(new FileReader(pathToDataSQLFile));
		return readerData;
	}

	private Connection connect() {
		Connection conn = null;
		try {
			conn = DriverManager.getConnection(url, user, password);
			if (conn == null) {
				System.err.println("Failed to make connection");
			}

		} catch (SQLException e) {
			System.out.println(e.getMessage());
		}
		return conn;
	}
}
