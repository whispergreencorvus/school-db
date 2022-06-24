package com.hartmanmark.schooldb.utils;

import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Scanner;

import com.hartmanmark.schooldb.dao.Connector;
import com.hartmanmark.schooldb.exception.ConnectionIsNullException;
import com.hartmanmark.schooldb.output.ConsoleMenu;
import com.hartmanmark.schooldb.validator.Validator;

public class RemoveStudents {

    private static String removeStudent = "Please enter an student ID between 1 and ";
    private static String exit = "For return input [exit]";
    private static String removeNextStudent = "To remove next student, enter an student ID between 1 and ";

    public static void remove() throws ClassNotFoundException, SQLException, IOException, ConnectionIsNullException {
        System.out.println(removeStudent + numberOfIdStudents() + "\n" + exit);
        Scanner scanner = new Scanner(System.in);
        String studentId;
        while (true) {
            studentId = scanner.nextLine();
            if (studentId.equalsIgnoreCase("exit")) {
                ConsoleMenu consoleMenu = new ConsoleMenu();
                consoleMenu.runConsole();
                scanner.close();
                break;
            }
            try {
                removeStudent(studentId);
            } catch (IllegalArgumentException e) {
                System.err.println(e.getMessage());
            }
        }
    }

    private static void removeStudent(String studentId)
            throws ClassNotFoundException, SQLException, IOException, ConnectionIsNullException {
        Validator.veryfyRemoveOption(studentId);
        try (Connection conn = Connector.getConnection(); Statement stmt = conn.createStatement();) {
            String deleteQuery = "DELETE FROM school.students WHERE STUDENT_ID=" + studentId;
            stmt.executeUpdate(deleteQuery);
        } catch (SQLException e) {
            throw new SQLException(e);
        }
        System.out.println("Student by ID = [" + studentId + "] removed");
        System.out.println(removeNextStudent + numberOfIdStudents() + "\n" + exit);
    }

    public static int numberOfIdStudents()
            throws ClassNotFoundException, SQLException, IOException, ConnectionIsNullException {
        Integer numberOfStudents = null;
        Connection conn = Connector.getConnection();
        Statement stmt = conn.createStatement();
        String countQuery = "SELECT count(*) from school.students;";
        ResultSet resultSet = stmt.executeQuery(countQuery);
        while (resultSet.next()) {
            numberOfStudents = Integer.valueOf(resultSet.getString("count"));
        }
        resultSet.close();
        return numberOfStudents;
    }
}
