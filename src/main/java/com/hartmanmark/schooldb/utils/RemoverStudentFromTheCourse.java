package com.hartmanmark.schooldb.utils;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.Scanner;

import com.hartmanmark.schooldb.dao.Connector;
import com.hartmanmark.schooldb.exception.ConnectionIsNullException;
import com.hartmanmark.schooldb.output.ConsoleMenu;
import com.hartmanmark.schooldb.validator.Validator;

public class RemoverStudentFromTheCourse {

    private static String studentIdPrint = "Please enter student ID: ";
    private static String coursePrint = "Please enter number of the course: ";
    private static String exit = "For return input [exit]";
    private static String studentsQuery = "SELECT student_id, first_name, last_name FROM school.students ORDER BY student_id ;";

    public static void remove() throws ClassNotFoundException, SQLException, IOException, ConnectionIsNullException {
        Scanner scanner = new Scanner(System.in);
        String studentId = null;
        String course = null;
        while (true) {
            print(studentsQuery);
            System.out.println("\n" + studentIdPrint + "\n" + exit);
            studentId = scanner.nextLine();
            if (studentId.equalsIgnoreCase("exit")) {
                ConsoleMenu consoleMenu = new ConsoleMenu();
                consoleMenu.runConsole();
                scanner.close();
                break;
            }
            printCorsesPerStudent(studentId);
            System.out.println("\n" + coursePrint + "\n" + exit);
            course = scanner.nextLine();
            if (course.equalsIgnoreCase("exit")) {
                ConsoleMenu consoleMenu = new ConsoleMenu();
                consoleMenu.runConsole();
                scanner.close();
                break;
            }
            try {
                removeStudent(studentId, course);
            } catch (IllegalArgumentException e) {
                System.err.println(e.getMessage());
            }
        }
    }

    private static void removeStudent(String studentId, String courseId)
            throws ClassNotFoundException, SQLException, IOException, ConnectionIsNullException {
        Validator.verifyInteger(courseId);
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

    private static void print(String query)
            throws ClassNotFoundException, IOException, ConnectionIsNullException, SQLException {
        try {
            Connection conn = Connector.getConnection();
            PreparedStatement stmt = conn.prepareStatement(query);
            ResultSet resultSet = stmt.executeQuery();
            ResultSetMetaData rsmd = resultSet.getMetaData();
            int columnsNumber = rsmd.getColumnCount();
            while (resultSet.next()) {
                for (int i = 1; i <= columnsNumber; i++) {
                    if (i > 1) {
                        System.out.print(" ");
                    }
                    String columnValue = resultSet.getString(i);
                    System.out.print(columnValue);
                }
                System.out.println("");
            }
            resultSet.close();
        } catch (SQLException e) {
            throw new SQLException(e);
        }
    }

    private static void printCorsesPerStudent(String studentId)
            throws ClassNotFoundException, IOException, ConnectionIsNullException, SQLException {
        Validator.verifyInteger(studentId);
        Integer studentIdInt = Integer.parseInt(studentId);
        try {
            Connection conn = Connector.getConnection();
            PreparedStatement stmt = conn.prepareStatement("SELECT school.students_courses.course_id, course_name\n"
                    + "FROM school.students JOIN school.students_courses \n"
                    + "ON  school.students_courses.student_id = school.students.student_id JOIN school.courses\n"
                    + "ON school.students_courses.course_id = school.courses.course_id\n"
                    + "WHERE school.students_courses.student_id = ? ORDER BY course_id;");
            stmt.setInt(1, studentIdInt);
            ResultSet resultSet = stmt.executeQuery();
            ResultSetMetaData rsmd = resultSet.getMetaData();
            int columnsNumber = rsmd.getColumnCount();
            while (resultSet.next()) {
                for (int i = 1; i <= columnsNumber; i++) {
                    if (i > 1) {
                        System.out.print(" ");
                    }
                    String columnValue = resultSet.getString(i);
                    System.out.print(columnValue);
                }
                System.out.println("");
            }
            resultSet.close();
        } catch (SQLException e) {
            throw new SQLException(e);
        }
    }
}
