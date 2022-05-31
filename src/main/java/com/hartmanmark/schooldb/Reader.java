package com.hartmanmark.schooldb;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.Properties;

public class Reader {

    private File pathToFirstName;
    private File pathToLastName;
    private FileReader fileReader;
    private Properties properties;
    private String pathToProperties = "/home/user/java/GitLab/Task 7/path.properties";

    public void read() throws IOException {
        setFileReader(new FileReader(pathToProperties));
        setProperties(new Properties());
        getProperties().load(getFileReader());
        readFirstName();
        readLastName();
    }

    private void readFirstName() {
        File firstName = new File(properties.getProperty("firstName"));
        setPathToFirstName(firstName);
    }

    private void readLastName() {
        File lastName = new File(properties.getProperty("lastName"));
        setPathToLastName(lastName);
    }

    public File getPathToFirstName() {
        return pathToFirstName;
    }

    public void setPathToFirstName(File pathToFirstName) {
        this.pathToFirstName = pathToFirstName;
    }

    public File getPathToLastName() {
        return pathToLastName;
    }

    public void setPathToLastName(File pathToLastName) {
        this.pathToLastName = pathToLastName;
    }

    public FileReader getFileReader() {
        return fileReader;
    }

    public void setFileReader(FileReader fileReader) {
        this.fileReader = fileReader;
    }

    public Properties getProperties() {
        return properties;
    }

    public void setProperties(Properties properties) {
        this.properties = properties;
    }

    public String getPathToProperties() {
        return pathToProperties;
    }

    public void setPathToProperties(String pathToProperties) {
        this.pathToProperties = pathToProperties;
    }
}
