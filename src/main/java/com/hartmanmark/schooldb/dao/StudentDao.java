package com.hartmanmark.schooldb.dao;

import java.io.IOException;
import java.sql.Connection;
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

    public void createRelation() throws ClassNotFoundException, SQLException, IOException, ConnectionIsNullException {
        countOfStudents();
        createStudents();
        countStudentsCoursesID();
        createRelationStudentsCourses();
    }

    private void createRelationStudentsCourses()
            throws ClassNotFoundException, SQLException, IOException, ConnectionIsNullException {
        Random random = new Random();
        StringBuilder string = new StringBuilder();
        for (int i = 1; i < numberIdInStudentsCoursesTable + 1; i++) {
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
        try (PreparedStatement statement = Connector.getConnection().prepareStatement(stringBuilder.toString())) {
            statement.execute();
        } catch (SQLException e) {
            throw new SQLException(e);
        }
    }

    private int countOfStudents() throws SQLException, ClassNotFoundException, IOException, ConnectionIsNullException {
        String str = "SELECT count(*) from school.students;";
        try (Connection conn = Connector.getConnection(); PreparedStatement stmt = conn.prepareStatement(str)) {
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    numberOfStudents = Integer.valueOf(rs.getString("count"));
                }
            }
        } catch (SQLException e) {
            throw new SQLException(e);
        }
        return numberOfStudents;
    }

    private int countStudentsCoursesID()
            throws SQLException, ClassNotFoundException, IOException, ConnectionIsNullException {
        String countQuery = "SELECT count(*) from school.students_courses;";
        try (Connection conn = Connector.getConnection(); PreparedStatement stmt = conn.prepareStatement(countQuery);) {
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    numberIdInStudentsCoursesTable = Integer.valueOf(rs.getString("count"));
                }
            } catch (SQLException e) {
                throw new SQLException(e);
            }
            return numberIdInStudentsCoursesTable;
        }
    }
}
