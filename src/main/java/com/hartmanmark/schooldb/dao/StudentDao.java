package com.hartmanmark.schooldb.dao;

import java.io.IOException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Random;

import com.hartmanmark.schooldb.exception.ConnectionIsNullException;

public class StudentDao {

    private final int MAX_NUMBER_OF_COURSES = 11;
    private final int MIN_NUMBER_OF_COURSES = 1;
    private final int MAX_NUMBER_OF_COURSES_PER_STUDENT = 4;
    private final int MIX_NUMBER_OF_COURSES_PER_STUDENT = 1;
    private int numberOfStudents;
    private int numberIdInStudentsCoursesTable;

    public void join() throws ClassNotFoundException, SQLException, IOException, ConnectionIsNullException {
        countOfStudents();
        createStudents();
        countStudentsCoursesID();
        joinStudentsAndCourses();
    }

    private void joinStudentsAndCourses()
            throws ClassNotFoundException, SQLException, IOException, ConnectionIsNullException {
        Random random = new Random();
        StringBuilder string = new StringBuilder();
        for (int i = 1; i < numberIdInStudentsCoursesTable + 1; i++) {
            int rangeOfCourse = random.nextInt(MAX_NUMBER_OF_COURSES - MIN_NUMBER_OF_COURSES) + MIN_NUMBER_OF_COURSES;
            String s = "UPDATE school.students_courses SET COURSE_ID = " + rangeOfCourse + " WHERE ID = " + i + ";";
            string.append(s);
        }
        PreparedStatement statement = Connector.getConnection().prepareStatement(string.toString());
        statement.execute();
    }

    private void createStudents() throws ClassNotFoundException, SQLException, IOException, ConnectionIsNullException {
        StringBuilder stringBuilder = new StringBuilder();
        Random random = new Random();
        for (int j = 1; j < numberOfStudents + 1; j++) {
            int rangeCoursesInOneStudent = random
                    .nextInt(MAX_NUMBER_OF_COURSES_PER_STUDENT - MIX_NUMBER_OF_COURSES_PER_STUDENT)
                    + MIX_NUMBER_OF_COURSES_PER_STUDENT;
            for (int i = 0; i < rangeCoursesInOneStudent; i++) {
                String str = "INSERT INTO school.students_courses(STUDENT_ID, COURSE_ID) VALUES (" + j + ", DEFAULT);";
                stringBuilder.append(str);
            }
        }
        PreparedStatement statement = Connector.getConnection().prepareStatement(stringBuilder.toString());
        statement.execute();
    }

    private void countOfStudents() throws SQLException, ClassNotFoundException, IOException, ConnectionIsNullException {
        String str = "SELECT count(*) from school.students;";
        Integer numberOfStudents = null;
        PreparedStatement statement = Connector.getConnection().prepareStatement(str);
        ResultSet resultSet = statement.executeQuery();
        while (resultSet.next()) {
            numberOfStudents = Integer.valueOf(resultSet.getString("count"));
        }
        setNumberOfStudents(numberOfStudents);
    }

    private void countStudentsCoursesID()
            throws SQLException, ClassNotFoundException, IOException, ConnectionIsNullException {
        String str = "SELECT count(*) from school.students_courses;";
        Integer numberIdInStudentsCoursesTable = null;
        PreparedStatement statement = Connector.getConnection().prepareStatement(str);
        ResultSet resultSet = statement.executeQuery();
        while (resultSet.next()) {
            numberIdInStudentsCoursesTable = Integer.valueOf(resultSet.getString("count"));
        }
        setNumberIdInStudentsCoursesTable(numberIdInStudentsCoursesTable);
    }

    public void setNumberOfStudents(int numberOfStudents) {
        this.numberOfStudents = numberOfStudents;
    }

    public void setNumberIdInStudentsCoursesTable(int numberIdInStudentsCoursesTable) {
        this.numberIdInStudentsCoursesTable = numberIdInStudentsCoursesTable;
    }
}
