package com.hartmanmark.schooldb.service;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.sql.SQLException;
import java.util.Properties;

import com.hartmanmark.schooldb.utils.DataGenerator;

public class ReaderPropertiesFile {

    private DataGenerator generator = new DataGenerator();
    private static FileReader fileReader;
    private static Properties properties;
    private static String pathToProperties = "/home/user/java/GitLab/Task 7/task-7/path.properties";
    private static String pathToDataBaseProperties = "/home/user/java/GitLab/Task 7/task-7/database.properties";

    public void read() throws IOException, SQLException, NullPointerException {
        generator.generate(readPathProperties("firstName"), readPathProperties("lastName"));
    }

    public static File readPathProperties(String keyProperty) throws IOException {
        fileReader = new FileReader(pathToProperties);
        properties = new Properties();
        properties.load(fileReader);
        return new File(properties.getProperty(keyProperty));
    }

    public static File readDataBaseProperties(String keyProperty) throws IOException {
        fileReader = new FileReader(pathToDataBaseProperties);
        properties = new Properties();
        properties.load(fileReader);
        return new File(properties.getProperty(keyProperty));
    }
}
