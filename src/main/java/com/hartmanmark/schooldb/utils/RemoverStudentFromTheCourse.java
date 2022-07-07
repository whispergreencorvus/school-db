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

    private String enterStudentId = "Please enter student ID: ";
    private String enterCourseId = "Please enter number of the course: ";
    private String selectStudentsQuery = "SELECT student_id, first_name, last_name FROM school.students ORDER BY student_id ;";
    private String selectCourseQuery = "SELECT school.courses.course_name FROM school.courses WHERE school.courses.course_id = ";
    private String selectStudentQuery = "SELECT school.students.first_name, school.students.last_name FROM school.students WHERE school.students.student_id = ";
    private String studentId;
    private String courseId;
    private String removedCourse;
    private String studentWithRemovedCourse;
    private Validator validator;

    public RemoverStudentFromTheCourse(Validator validator) {
        this.validator = validator;
    }

    public String printStudents() throws ClassNotFoundException, IOException, ConnectionIsNullException, SQLException {
        return "\n" + createStudentsListToPrint(selectStudentsQuery) + "\n" + enterStudentId + "\n";
    }

    public String chooseStudentId()
            throws ClassNotFoundException, IOException, ConnectionIsNullException, SQLException {
        scannerStudentId();
        return "\n" + createCorsesPerStudentListToPrint(studentId) + "\n" + enterCourseId + "\n";
    }

    public String chooseCourseId() throws ClassNotFoundException, SQLException, IOException, ConnectionIsNullException {
        scanneCourseId();
        removeStudentFromTheCourse(studentId, courseId);
        findRemovedCourse();
        findStudentWithRemovedCourse();
        return "Student " + studentWithRemovedCourse + "\nwas succesfully removed from course: " + removedCourse;
    }

    private void scanneCourseId() {
        Scanner scannerCourseId = new Scanner(System.in);
        courseId = scannerCourseId.nextLine();
    }

    private void scannerStudentId() {
        Scanner scannerStudentId = new Scanner(System.in);
        studentId = scannerStudentId.nextLine();
    }

    private void findRemovedCourse()
            throws ClassNotFoundException, IOException, ConnectionIsNullException, SQLException {
        try (Connection conn = Connector.getConnection();
                PreparedStatement stmt = conn.prepareStatement(selectCourseQuery + courseId + ";")) {
            try (ResultSet resultSet = stmt.executeQuery()) {
                while (resultSet.next()) {
                    removedCourse = resultSet.getString(1);
                }
            }
        } catch (SQLException e) {
            throw new SQLException(e);
        }
    }

    private void findStudentWithRemovedCourse()
            throws ClassNotFoundException, IOException, ConnectionIsNullException, SQLException {
        try (Connection conn = Connector.getConnection();
                PreparedStatement stmt = conn.prepareStatement(selectStudentQuery + studentId + ";")) {
            try (ResultSet resultSet = stmt.executeQuery()) {
                while (resultSet.next()) {
                    studentWithRemovedCourse = resultSet.getString(1) + resultSet.getString(2);
                }
            }
        } catch (SQLException e) {
            throw new SQLException(e);
        }
    }

    private void removeStudentFromTheCourse(String studentId, String courseId)
            throws ClassNotFoundException, SQLException, IOException, ConnectionIsNullException {
        validator.verifyInteger(courseId);
        Integer studentIdInt = Integer.parseInt(studentId);
        Integer courseIdInt = Integer.parseInt(courseId);
        try (Connection conn = Connector.getConnection();
                PreparedStatement stmt = conn.prepareStatement(
                        "DELETE FROM school.students_courses WHERE student_id = ? AND course_id = ? ;")) {
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
                    builder.append(
                            "\n" + resultSet.getInt(1) + " " + resultSet.getString(2) + " " + resultSet.getString(3));
                }
            }
        } catch (SQLException e) {
            throw new SQLException(e);
        }
        return builder.toString();
    }

    private String createCorsesPerStudentListToPrint(String studentId)
            throws ClassNotFoundException, IOException, ConnectionIsNullException, SQLException {
        validator.verifyInteger(studentId);
        Integer studentIdInt = Integer.parseInt(studentId);
        StringBuilder builder = new StringBuilder();
        try (Connection conn = Connector.getConnection();
                PreparedStatement stmt = conn.prepareStatement("SELECT school.students_courses.course_id, course_name\n"
                        + "FROM school.students JOIN school.students_courses \n"
                        + "ON  school.students_courses.student_id = school.students.student_id JOIN school.courses\n"
                        + "ON school.students_courses.course_id = school.courses.course_id\n"
                        + "WHERE school.students_courses.student_id = ? ORDER BY course_id;")) {
            stmt.setInt(1, studentIdInt);
            try (ResultSet resultSet = stmt.executeQuery()) {
                while (resultSet.next()) {
                    builder.append("\n" + resultSet.getInt(1) + " " + resultSet.getString(2));
                }
            }
        } catch (SQLException e) {
            throw new SQLException(e);
        }
        return builder.toString();
    }
}
