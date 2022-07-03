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

public class RemoverStudentFromTheCourse {

    private String studentIdPrint = "Please enter student ID: ";
    private String coursePrint = "Please enter number of the course: ";
    private String exit = "For return input [exit]";
    private String studentsQuery = "SELECT student_id, first_name, last_name FROM school.students ORDER BY student_id ;";
    private Validator validator;

    public void remove() throws ClassNotFoundException, SQLException, IOException, ConnectionIsNullException {
        Scanner scanner = new Scanner(System.in);
        String studentId = null;
        String course = null;
        System.out.println(printStudents(studentsQuery) + "\n" + studentIdPrint + "\n" + exit);
        studentId = scanner.nextLine();
        if (studentId.equalsIgnoreCase("exit")) {
            return;
        }
        System.out.println(printCorsesPerStudent(studentId) + "\n" + coursePrint + "\n" + exit);
        course = scanner.nextLine();
        if (course.equalsIgnoreCase("exit")) {
            return;
        }
        try {
            removeStudent(studentId, course);
        } catch (IllegalArgumentException e) {
            System.err.println(e.getMessage());
        }
    }

    private void removeStudent(String studentId, String courseId)
            throws ClassNotFoundException, SQLException, IOException, ConnectionIsNullException {
        validator.verifyInteger(courseId);
        Integer studentIdInt = Integer.parseInt(studentId);
        Integer courseIdInt = Integer.parseInt(courseId);
        try {
            Connection conn = Connector.getConnection();
            PreparedStatement stmt = conn
                    .prepareStatement("DELETE FROM school.students_courses WHERE student_id = ? AND course_id = ? ;");
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
                        "\n" + resultSet.getInt(1) + " " + resultSet.getString(2) + " " + resultSet.getString(3));
            }
            resultSet.close();
        } catch (SQLException e) {
            throw new SQLException(e);
        }
        return builder.toString();
    }

    private String printCorsesPerStudent(String studentId)
            throws ClassNotFoundException, IOException, ConnectionIsNullException, SQLException {
        validator.verifyInteger(studentId);
        Integer studentIdInt = Integer.parseInt(studentId);
        StringBuilder builder = new StringBuilder();
        try {
            Connection conn = Connector.getConnection();
            PreparedStatement stmt = conn.prepareStatement("SELECT school.students_courses.course_id, course_name\n"
                    + "FROM school.students JOIN school.students_courses \n"
                    + "ON  school.students_courses.student_id = school.students.student_id JOIN school.courses\n"
                    + "ON school.students_courses.course_id = school.courses.course_id\n"
                    + "WHERE school.students_courses.student_id = ? ORDER BY course_id;");
            stmt.setInt(1, studentIdInt);
            ResultSet resultSet = stmt.executeQuery();
            while (resultSet.next()) {
                builder.append("\n" + resultSet.getInt(1) + " " + resultSet.getString(2));
            }
            resultSet.close();
        } catch (SQLException e) {
            throw new SQLException(e);
        }
        return builder.toString();
    }

    public RemoverStudentFromTheCourse(Validator validator) {
        this.validator = validator;
    }
}
