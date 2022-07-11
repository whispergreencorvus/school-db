package com.hartmanmark.schooldb.utils;

import java.io.File;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Scanner;

import com.hartmanmark.schooldb.dao.Connector;

public class DataGenerator {

    private static final int MAX_NUMBER_OF_STUDENTS_IN_ONE_GROUP = 30;
    private static final int MIN_NUMBER_OF_STUDENTS_IN_ONE_GROUP = 15;
    private List<String> randomFirstName = new ArrayList<>();
    private List<String> randomLastName = new ArrayList<>();
    private DataInserter inserter = new DataInserter();
    private Random random;

    private static final int MAX_NUMBER_OF_COURSES = 11;
    private static final int MIN_NUMBER_OF_COURSES = 1;
    private static final int MAX_NUMBER_OF_COURSES_PER_STUDENT = 4;
    private static final int MIX_NUMBER_OF_COURSES_PER_STUDENT = 1;

    public void generate(File pathToFirstName, File pathToLastName)
            throws IOException, SQLException, ClassNotFoundException, NullPointerException {
        getListOfRandomFirstNames(pathToFirstName);
        getListOfRandomLastNames(pathToLastName);
        inserter.insertCourses();
        createGroups();
        countOfStudents();
        createStudents();
        createRelationStudentsCourses();
    }

    private void createRelationStudentsCourses()
            throws ClassNotFoundException, SQLException, IOException, NullPointerException {
        random = new Random();
        StringBuilder string = new StringBuilder();
        int r = Integer.parseInt(countOfStudents());
        for (int i = 1; i < r + 1; i++) {
            int rangeOfCourse = random.nextInt(MAX_NUMBER_OF_COURSES - MIN_NUMBER_OF_COURSES) + MIN_NUMBER_OF_COURSES;
            String s = "UPDATE school.students_courses SET COURSE_ID = " + rangeOfCourse + " WHERE ID = " + i + ";";
            string.append(s);
        }
        try (PreparedStatement statement = Connector.getConnection().prepareStatement(string.toString())) {
            statement.execute();
        } catch (SQLException e) {
            throw new SQLException(e);
        }
    }

    private void createStudents() throws ClassNotFoundException, SQLException, IOException, NullPointerException {
        StringBuilder stringBuilder = new StringBuilder();
        random = new Random();
        int r = Integer.parseInt(countOfStudents());
        for (int j = 1; j < r + 1; j++) {
            int rangeCoursesInOneStudent = random
                    .nextInt(MAX_NUMBER_OF_COURSES_PER_STUDENT - MIX_NUMBER_OF_COURSES_PER_STUDENT)
                    + MIX_NUMBER_OF_COURSES_PER_STUDENT;
            for (int i = 0; i < rangeCoursesInOneStudent; i++) {
                String str = "INSERT INTO school.students_courses(STUDENT_ID, COURSE_ID) VALUES (" + j + ", DEFAULT);";
                stringBuilder.append(str);
            }
        }
        try (PreparedStatement statement = Connector.getConnection().prepareStatement(stringBuilder.toString())) {
            statement.execute();
        } catch (SQLException e) {
            throw new SQLException(e);
        }
    }

    private String countOfStudents() throws SQLException, ClassNotFoundException, IOException, NullPointerException {
        String query = "SELECT count(*) from school.students;";
        String quontity = null;
        try (Connection conn = Connector.getConnection(); PreparedStatement stmt = conn.prepareStatement(query)) {
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    quontity = rs.getString("count");
                }
            }
        } catch (SQLException e) {
            throw new SQLException(e);
        }
        return quontity;
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

    private void createGroups() throws SQLException, ClassNotFoundException, IOException, NullPointerException {
        for (int i = 1; i < 11; i++) {
            String firstParthOfGroupName = getRandomAlphaString();
            String lastParthOfGroupName = getRandomNumericString();
            String groupName = firstParthOfGroupName + '-' + lastParthOfGroupName;
            inserter.insertGroups(groupName);
            putStudentsInOneGroup(i);
        }
    }

    private void getListOfRandomFirstNames(File pathToFirstNames)
            throws IOException, ClassNotFoundException, SQLException {
        try (Scanner scannerFirstName = new Scanner(pathToFirstNames)) {
            while (scannerFirstName.hasNextLine()) {
                randomFirstName.add(scannerFirstName.nextLine());
            }
        }
    }

    private void putStudentsInOneGroup(int idGroup)
            throws IOException, SQLException, ClassNotFoundException, NullPointerException {
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
        try (Scanner scannerLastName = new Scanner(pathToLastNames)) {
            while (scannerLastName.hasNextLine()) {
                randomLastName.add(scannerLastName.nextLine());
            }
        }
    }
}
