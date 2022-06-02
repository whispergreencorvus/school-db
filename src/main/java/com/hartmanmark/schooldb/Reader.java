package com.hartmanmark.schooldb;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.Random;
import java.util.Scanner;

public class Reader {

    private File pathToFirstNames;
    private File pathToLastNames;
    private FileReader fileReader;
    private Properties properties;
    private String pathToProperties = "/home/user/java/GitLab/Task 7/path.properties";
    private List<String> randomFirstName = new ArrayList<String>();
    private List<String> randomLastName = new ArrayList<String>();
    private Generator generator = new Generator();

    public void read() throws IOException {
        setFileReader(new FileReader(pathToProperties));
        setProperties(new Properties());
        getProperties().load(getFileReader());
        readFirstName();
        readLastName();
        getListOfRandomFirstNames(getPathToFirstName());
        getListOfRandomLastNames(getPathToLastName());
        getFullNames();
        generator.getRandomGroupNames();
    }

    public void getListOfRandomFirstNames(File pathToFirstNames) throws IOException {
        Scanner scannerFirstName = new Scanner(pathToFirstNames);
        while (scannerFirstName.hasNextLine()) {
            randomFirstName.add(scannerFirstName.nextLine());
        }
        scannerFirstName.close();
    }

    public void getFullNames() throws IOException {
        Random random = new Random();
        for (int i = 0; i < 10; i++) {
            String firstName = randomFirstName.get(random.nextInt(randomFirstName.size()));
            String lastName = randomLastName.get(random.nextInt(randomLastName.size()));
            System.out.println(firstName + " " + lastName);
        }
    }

    public void getListOfRandomLastNames(File pathToLastNames) throws IOException {
        Scanner scannerLastName = new Scanner(pathToLastNames);
        while (scannerLastName.hasNextLine()) {
            randomLastName.add(scannerLastName.nextLine());
        }
        scannerLastName.close();
//        Collections.shuffle(randomLastName);
    }

//    public List<String> largerArray() {
//        return randomFirstName.size() > randomLastName.size() ? randomFirstName : randomLastName;
//    }
//
//    public List<String> lessArray() {
//        return randomLastName.size() > randomFirstName.size() ? randomLastName : randomFirstName;
//    }

    private void readFirstName() {
        File firstName = new File(properties.getProperty("firstName"));
        setPathToFirstName(firstName);
    }

    private void readLastName() {
        File lastName = new File(properties.getProperty("lastName"));
        setPathToLastName(lastName);
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
