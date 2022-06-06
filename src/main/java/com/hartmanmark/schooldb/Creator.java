package com.hartmanmark.schooldb;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import org.apache.ibatis.jdbc.ScriptRunner;

public class Creator {

	private String url = "jdbc:postgresql://localhost/postgres";
	private String user = "postgres";
	private String password = "123";
	private String pathToShemaSQLFile = "/home/user/java/GitLab/Task 7/Shema.sql";
	private Reader reader = new Reader();

	public void createDataBase() throws ClassNotFoundException, SQLException, IOException {
		ScriptRunner scriptRunner = new ScriptRunner(connectToDataBase());
		scriptRunner.runScript(readSQLFile());
		reader.read();
	}

	private Connection connectToDataBase() throws SQLException {
		Connection connection = DriverManager.getConnection(url, user, password);
		return connection;
	}

	private BufferedReader readSQLFile() throws FileNotFoundException {
		BufferedReader readerSchema = new BufferedReader(new FileReader(pathToShemaSQLFile));
		return readerSchema;
	}
}
