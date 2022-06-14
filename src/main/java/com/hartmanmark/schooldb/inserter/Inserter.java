package com.hartmanmark.schooldb.inserter;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.sql.SQLException;
import java.sql.Statement;

import org.apache.ibatis.jdbc.ScriptRunner;

import com.hartmanmark.schooldb.connector.Connector;
import com.hartmanmark.schooldb.reader.Reader;

public class Inserter {

	public void insertStudents(StringBuilder firstName) throws SQLException, ClassNotFoundException, IOException {
		Statement statement = Connector.connect().createStatement();
		statement.execute(firstName.toString());
	}

	public void insertGroups(String groupName) throws SQLException, ClassNotFoundException, IOException {
		String str = "INSERT INTO school.groups(GROUP_ID, GROUP_NAME) VALUES (DEFAULT , '" + groupName + "');";
		Statement statement = Connector.connect().createStatement();
		statement.execute(str);
	}

	public void insertCourses() throws SQLException, ClassNotFoundException, IOException {
		ScriptRunner scriptRunner = new ScriptRunner(Connector.connect());
		scriptRunner.runScript(readSQLFile());
	}

	private BufferedReader readSQLFile() throws ClassNotFoundException, IOException, SQLException {
		BufferedReader readerData = new BufferedReader(new FileReader(Reader.readPropertyFile("pathToDataSQLFile")));
		return readerData;
	}
}
