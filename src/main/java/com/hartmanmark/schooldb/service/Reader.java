package com.hartmanmark.schooldb.service;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.sql.SQLException;
import java.util.Properties;

import com.hartmanmark.schooldb.utils.DataGenerator;

public class Reader {

    private File pathToFirstNames;
    private File pathToLastNames;
    private DataGenerator generator = new DataGenerator();
    private static FileReader fileReader;
    private static Properties properties;
    private static File file;
    private static String pathToProperties = "/home/user/java/GitLab/Task 7/task-7/path.properties";
    private static String pathToDataBaseProperties = "/home/user/java/GitLab/Task 7/task-7/database.properties";

    public void read() throws IOException, ClassNotFoundException, SQLException, NullPointerException {
        readFirstName();
        readLastName();
        generator.generate(getPathToFirstName(), getPathToLastName());
    }

    public static File readPathProperties(String keyProperty) throws IOException {
        fileReader = new FileReader(pathToProperties);
        properties = new Properties();
        properties.load(fileReader);
        file = new File(properties.getProperty(keyProperty));
        return file;
    }

    public static File readDataBaseProperties(String keyProperty)
            throws IOException {
        fileReader = new FileReader(pathToDataBaseProperties);
        properties = new Properties();
        properties.load(fileReader);
        file = new File(properties.getProperty(keyProperty));
        return file;
    }

    private void readFirstName() throws IOException {
        setPathToFirstName(readPathProperties("firstName"));
    }

    private void readLastName() throws IOException {
        setPathToLastName(readPathProperties("lastName"));
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
