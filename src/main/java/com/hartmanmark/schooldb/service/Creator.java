package com.hartmanmark.schooldb.service;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.sql.SQLException;

import org.apache.ibatis.jdbc.ScriptRunner;

import com.hartmanmark.schooldb.connector.Connector;
import com.hartmanmark.schooldb.reader.Reader;

public class Creator {
	private Reader reader = new Reader();

	public void createDataBase() throws ClassNotFoundException, SQLException, IOException {
		ScriptRunner scriptRunner = new ScriptRunner(Connector.connect());
		scriptRunner.runScript(readSQLFile());
		reader.read();
	}

	private BufferedReader readSQLFile() throws ClassNotFoundException, IOException, SQLException {
		BufferedReader readerSchema = new BufferedReader(new FileReader(Reader.readPropertyFile("pathToShemaSQLFile")));
		return readerSchema;
	}
}
