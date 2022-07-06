package com.hartmanmark.schooldb.utils;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

import com.hartmanmark.schooldb.dao.Connector;
import com.hartmanmark.schooldb.exception.ConnectionIsNullException;
import com.hartmanmark.schooldb.validator.Validator;

public class AdderStudentsToTheCourse {

    private String studentIdPrint = "Please enter student ID: ";
    private String coursePrint = "Please enter number of the course: ";
    private String courseQuery = "SELECT course_id, course_name FROM school.courses ORDER BY course_id ;";
    private String studentsQuery = "SELECT student_id, first_name, last_name FROM school.students ORDER BY student_id ;";
    private String outputStudentQuery = "SELECT school.students.first_name, school.students.last_name FROM school.students WHERE school.students.student_id = ";
    private String outputCourseQuery = "SELECT school.courses.course_name FROM school.courses WHERE school.courses.course_id = ";
    private String separator = String.format("%100s", "").replace(' ', '-');
    private Validator validator;
    private String result;
    private String course;
    private String studentId;
    private String courseId;

    public void add() throws ClassNotFoundException, SQLException, IOException, ConnectionIsNullException {
        Scanner scanner = new Scanner(System.in);
        System.out.println(createStudentsListToPrint(studentsQuery) + studentIdPrint);
        studentId = scanner.nextLine();
        if (studentId.equalsIgnoreCase("exit")) {
            setResult(separator);
            return;
        }
        System.out.println(createCourseListToPrint(courseQuery) + coursePrint);
        courseId = scanner.nextLine();
        if (courseId.equalsIgnoreCase("exit")) {
            setResult(separator);
            return;
        }
        findAddedCourse();
        findAddedStuden();
        try {
            addStudentToTheCourse();
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException(e);
        }
    }

    private void addStudentToTheCourse()
            throws ClassNotFoundException, SQLException, IOException, ConnectionIsNullException {
        validator.verifyInteger(studentId);
        validator.verifyInteger(courseId);
        Integer studentIdInt = Integer.parseInt(studentId);
        Integer courseIdInt = Integer.parseInt(courseId);
        try (Connection conn = Connector.getConnection();
                PreparedStatement stmt = conn.prepareStatement(
                        "INSERT INTO school.students_courses (id, student_id, course_id) VALUES (DEFAULT, ? , ? );")) {
            stmt.setInt(1, studentIdInt);
            stmt.setInt(2, courseIdInt);
            stmt.executeUpdate();
        } catch (SQLException e) {
            throw new SQLException(e);
        }
    }

    private String createStudentsListToPrint(String query)
            throws ClassNotFoundException, IOException, ConnectionIsNullException, SQLException {
        StringBuilder builder = new StringBuilder();
        try (Connection conn = Connector.getConnection(); PreparedStatement stmt = conn.prepareStatement(query)) {
            try (ResultSet resultSet = stmt.executeQuery()) {
                while (resultSet.next()) {
                    builder.append(resultSet.getString(1) + " " + resultSet.getString(2) + " " + resultSet.getString(3)
                            + "\n");
                }
            }
        } catch (SQLException e) {
            throw new SQLException(e);
        }
        return builder.toString();
    }

    private String createCourseListToPrint(String query)
            throws ClassNotFoundException, IOException, ConnectionIsNullException, SQLException {
        StringBuilder builder = new StringBuilder();
        try (Connection conn = Connector.getConnection(); PreparedStatement stmt = conn.prepareStatement(query)) {
            try (ResultSet resultSet = stmt.executeQuery()) {
                while (resultSet.next()) {
                    builder.append(resultSet.getString(1) + " " + resultSet.getString(2) + "\n");
                }
            }
        } catch (SQLException e) {
            throw new SQLException(e);
        }
        return builder.toString();
    }

    private void findAddedStuden() throws ClassNotFoundException, IOException, ConnectionIsNullException, SQLException {
        try (Connection conn = Connector.getConnection();
                PreparedStatement stmt = conn.prepareStatement(outputStudentQuery + studentId + ";")) {
            try (ResultSet resultSet = stmt.executeQuery()) {
                while (resultSet.next()) {
                    setResult("Student: " + resultSet.getString(1) + " " + resultSet.getString(2)
                            + "\nwas succesefully added to the: " + course);
                }
            }
        } catch (SQLException e) {
            throw new SQLException(e);
        }
    }

    private void findAddedCourse() throws ClassNotFoundException, IOException, ConnectionIsNullException, SQLException {
        try (Connection conn = Connector.getConnection();
                PreparedStatement stmt = conn.prepareStatement(outputCourseQuery + courseId + ";")) {
            try (ResultSet resultSet = stmt.executeQuery()) {
                while (resultSet.next()) {
                    course = resultSet.getString(1);
                }
            }
        } catch (SQLException e) {
            throw new SQLException(e);
        }
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public AdderStudentsToTheCourse(Validator validator) {
        this.validator = validator;
    }
}
