package com.hartmanmark.schooldb.reader;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.sql.SQLException;
import java.util.Properties;

import com.hartmanmark.schooldb.service.Generator;

public class Reader {

	private File pathToFirstNames;
	private File pathToLastNames;
	private Generator generator = new Generator();
	private static FileReader fileReader;
	private static Properties properties;
	private static File file;
	private static String pathToProperties = "/home/user/java/GitLab/Task 7/task-7/path.properties";

	public void read() throws IOException, ClassNotFoundException, SQLException {
		readFirstName();
		readLastName();
		generator.generate(getPathToFirstName(), getPathToLastName());
	}

	public static File readPropertyFile(String keyProperty) throws IOException, ClassNotFoundException, SQLException {
		fileReader = new FileReader(pathToProperties);
		properties = new Properties();
		properties.load(fileReader);
		file = new File(properties.getProperty(keyProperty));
		return file;
	}

	private void readFirstName() throws ClassNotFoundException, IOException, SQLException {
		setPathToFirstName(readPropertyFile("firstName"));
	}

	private void readLastName() throws ClassNotFoundException, IOException, SQLException {
		setPathToLastName(readPropertyFile("lastName"));
	}

	public File getPathToFirstName() {
		return pathToFirstNames;
	}

	public void setPathToFirstName(File pathToFirstName) {
		this.pathToFirstNames = pathToFirstName;
	}

	public File getPathToLastName() {
		return pathToLastNames;
	}

	public void setPathToLastName(File pathToLastName) {
		this.pathToLastNames = pathToLastName;
	}
}
