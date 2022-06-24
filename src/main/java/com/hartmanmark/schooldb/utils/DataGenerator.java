package com.hartmanmark.schooldb.utils;

import java.io.File;
import java.io.IOException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Scanner;

import com.hartmanmark.schooldb.dao.Connector;
import com.hartmanmark.schooldb.dao.StudentDao;
import com.hartmanmark.schooldb.exception.ConnectionIsNullException;

public class DataGenerator {

    private String groupName;
    private final int MAX_NUMBER_OF_STUDENTS_IN_ONE_GROUP = 30;
    private final int MIN_NUMBER_OF_STUDENTS_IN_ONE_GROUP = 15;
    private List<String> randomFirstName = new ArrayList<String>();
    private List<String> randomLastName = new ArrayList<String>();
    private int numberOfStudents;
    private DataInserter inserter = new DataInserter();
    private StudentDao studentDao = new StudentDao();

    public void generate(File pathToFirstName, File pathToLastName)
            throws IOException, SQLException, ClassNotFoundException, ConnectionIsNullException {
        getListOfRandomFirstNames(pathToFirstName);
        getListOfRandomLastNames(pathToLastName);
        inserter.insertCourses();
        createGroups();
        countOfStudents();
        studentDao.createRelation();
    }

    private void countOfStudents() throws SQLException, ClassNotFoundException, IOException, ConnectionIsNullException {
        String countStudentsQuery = "SELECT count(*) from school.students;";
        Integer numberOfStudents = null;
        PreparedStatement statement = Connector.getConnection().prepareStatement(countStudentsQuery);
        ResultSet resultSet = statement.executeQuery();
        while (resultSet.next()) {
            numberOfStudents = Integer.valueOf(resultSet.getString("count"));
        }
        setNumberOfStudents(numberOfStudents);
    }

    private String getRandomAlphaString() {
        String alphaString = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
        StringBuilder randomAlphaString = new StringBuilder();
        for (int i = 0; i < 2; i++) {
            int index = (int) (alphaString.length() * Math.random());
            randomAlphaString.append(alphaString.charAt(index));
        }
        return randomAlphaString.toString();
    }

    private String getRandomNumericString() {
        String AlphaString = "1234567890";
        StringBuilder randomNumericString = new StringBuilder();
        for (int i = 0; i < 2; i++) {
            int index = (int) (AlphaString.length() * Math.random());
            randomNumericString.append(AlphaString.charAt(index));
        }
        return randomNumericString.toString();
    }

    private void createGroups() throws SQLException, ClassNotFoundException, IOException, ConnectionIsNullException {
        for (int i = 1; i < 11; i++) {
            String firstParthOfGroupName = getRandomAlphaString();
            String lastParthOfGroupName = getRandomNumericString();
            groupName = firstParthOfGroupName + '-' + lastParthOfGroupName;
            inserter.insertGroups(groupName);
            putStudentsInOneGroup(i);
        }
    }

    private void getListOfRandomFirstNames(File pathToFirstNames)
            throws IOException, ClassNotFoundException, SQLException {
        Scanner scannerFirstName = new Scanner(pathToFirstNames);
        while (scannerFirstName.hasNextLine()) {
            randomFirstName.add(scannerFirstName.nextLine());
        }
        scannerFirstName.close();
    }

    private void putStudentsInOneGroup(int idGroup)
            throws IOException, SQLException, ClassNotFoundException, ConnectionIsNullException {
        Random random = new Random();
        int range = random.nextInt(MAX_NUMBER_OF_STUDENTS_IN_ONE_GROUP - MIN_NUMBER_OF_STUDENTS_IN_ONE_GROUP)
                + MIN_NUMBER_OF_STUDENTS_IN_ONE_GROUP;
        StringBuilder insertStudentsInOneGroup = new StringBuilder();
        for (int i = 0; i < range; i++) {
            String firstName = randomFirstName.get(random.nextInt(randomFirstName.size()));
            String lastName = randomLastName.get(random.nextInt(randomLastName.size()));
            String sqlQuery = "INSERT INTO school.students(STUDENT_ID, GROUP_ID, FIRST_NAME, LAST_NAME) VALUES (DEFAULT ,'"
                    + idGroup + "' , '" + firstName + "', '" + lastName + "');";
            insertStudentsInOneGroup.append(sqlQuery);
        }
        inserter.insertStudents(insertStudentsInOneGroup);
    }

    private void getListOfRandomLastNames(File pathToLastNames) throws IOException {
        Scanner scannerLastName = new Scanner(pathToLastNames);
        while (scannerLastName.hasNextLine()) {
            randomLastName.add(scannerLastName.nextLine());
        }
        scannerLastName.close();
    }

    public int getNumberOfStudents() {
        return numberOfStudents;
    }

    public void setNumberOfStudents(int numberOfStudents) {
        this.numberOfStudents = numberOfStudents;
    }
}
