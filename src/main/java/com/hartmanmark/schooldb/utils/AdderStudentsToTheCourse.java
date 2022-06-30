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

public class AdderStudentsToTheCourse {

    private static String studentIdPrint = "Please enter student ID: ";
    private static String coursePrint = "Please enter number of the course: ";
    private static String exit = "For return input [exit]";
    private static String courseQuery = "SELECT course_id, course_name FROM school.courses ORDER BY course_id ;";
    private static String studentsQuery = "SELECT student_id, first_name, last_name FROM school.students ORDER BY student_id ;";

    public static void add() throws ClassNotFoundException, SQLException, IOException, ConnectionIsNullException {
        Scanner scanner = new Scanner(System.in);
        String studentId = null;
        String course = null;
        while (true) {
            print(studentsQuery);
            System.out.println(studentIdPrint + "\n" + exit);
            studentId = scanner.nextLine();
            if (studentId.equalsIgnoreCase("exit")) {
                ConsoleMenu consoleMenu = new ConsoleMenu();
                consoleMenu.runConsole();
                scanner.close();
                break;
            }
            print(courseQuery);
            System.out.println(coursePrint + "\n" + exit);
            course = scanner.nextLine();
            if (course.equalsIgnoreCase("exit")) {
                ConsoleMenu consoleMenu = new ConsoleMenu();
                consoleMenu.runConsole();
                scanner.close();
                break;
            }
            try {
                addStudent(studentId, course);
            } catch (IllegalArgumentException e) {
                throw new IllegalArgumentException(e);
            }
        }
    }

    private static void addStudent(String studentId, String courseId)
            throws ClassNotFoundException, SQLException, IOException, ConnectionIsNullException {
        Validator.verifyInteger(studentId);
        Validator.verifyInteger(courseId);
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
}
