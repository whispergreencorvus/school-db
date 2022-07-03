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
    private Validator validator;
    private String result = String.format("%100s", "").replace(' ', '-');
    private String course;

    public void add() throws ClassNotFoundException, SQLException, IOException, ConnectionIsNullException {
        Scanner scanner = new Scanner(System.in);
        String studentId = null;
        String courseId = null;
        System.out.println(printStudents(studentsQuery) + studentIdPrint);
        studentId = scanner.nextLine();
        if (studentId.equalsIgnoreCase("exit")) {
            return;
        }
        getAddedStuden(studentId);
        System.out.println(printCourses(courseQuery) + coursePrint);
        courseId = scanner.nextLine();
        if (courseId.equalsIgnoreCase("exit")) {
            return;
        }
        try {
            addStudent(studentId, courseId);
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException(e);
        }
    }

    private void addStudent(String studentId, String courseId)
            throws ClassNotFoundException, SQLException, IOException, ConnectionIsNullException {
        validator.verifyInteger(studentId);
        validator.verifyInteger(courseId);
        Integer studentIdInt = Integer.parseInt(studentId);
        Integer courseIdInt = Integer.parseInt(courseId);
        try {
            Connection conn = Connector.getConnection();
            PreparedStatement stmt = conn.prepareStatement(
                    "INSERT INTO school.students_courses (id, student_id, course_id) VALUES (DEFAULT, ? , ? );");
            stmt.setInt(1, studentIdInt);
            stmt.setInt(2, courseIdInt);
            stmt.executeUpdate();
        } catch (SQLException e) {
            throw new SQLException(e);
        }
    }

    private String printStudents(String query)
            throws ClassNotFoundException, IOException, ConnectionIsNullException, SQLException {
        StringBuilder builder = new StringBuilder();
        try {
            Connection conn = Connector.getConnection();
            PreparedStatement stmt = conn.prepareStatement(query);
            ResultSet resultSet = stmt.executeQuery();
            while (resultSet.next()) {
                builder.append(
                        resultSet.getString(1) + " " + resultSet.getString(2) + " " + resultSet.getString(3) + "\n");
            }
            resultSet.close();
        } catch (SQLException e) {
            throw new SQLException(e);
        }
        return builder.toString();
    }

    private String printCourses(String query)
            throws ClassNotFoundException, IOException, ConnectionIsNullException, SQLException {
        StringBuilder builder = new StringBuilder();
        try {
            Connection conn = Connector.getConnection();
            PreparedStatement stmt = conn.prepareStatement(query);
            ResultSet resultSet = stmt.executeQuery();
            while (resultSet.next()) {
                builder.append(resultSet.getString(1) + " " + resultSet.getString(2) + "\n");
            }
            resultSet.close();
        } catch (SQLException e) {
            throw new SQLException(e);
        }
        return builder.toString();
    }

    private void getAddedStuden(String studentId)
            throws ClassNotFoundException, IOException, ConnectionIsNullException, SQLException {
        getAddedCourse(studentId);
        try {
            Connection conn = Connector.getConnection();
            PreparedStatement stmt = conn.prepareStatement(outputStudentQuery + studentId + ";");
            ResultSet resultSet = stmt.executeQuery();
            while (resultSet.next()) {
                setResult("Student: " + resultSet.getString(1) + " " + resultSet.getString(2)
                        + "\nwas succesefully added to the: " + getCourse());
            }
            resultSet.close();
        } catch (SQLException e) {
            throw new SQLException(e);
        }
    }

    private void getAddedCourse(String courseId)
            throws ClassNotFoundException, IOException, ConnectionIsNullException, SQLException {
        try {
            Connection conn = Connector.getConnection();
            PreparedStatement stmt = conn.prepareStatement(outputCourseQuery + courseId + ";");
            ResultSet resultSet = stmt.executeQuery();
            while (resultSet.next()) {
                setCourse(resultSet.getString(1));
            }
            resultSet.close();
        } catch (SQLException e) {
            throw new SQLException(e);
        }
    }

    public String getCourse() {
        return course;
    }

    public void setCourse(String course) {
        this.course = course;
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
