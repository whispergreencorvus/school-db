package com.hartmanmark.schooldb.utils;

import java.sql.Statement;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Scanner;

import com.hartmanmark.schooldb.dao.Connector;
import com.hartmanmark.schooldb.exception.ConnectionIsNullException;
import com.hartmanmark.schooldb.output.ConsoleMenu;
import com.hartmanmark.schooldb.validator.Validator;

public class AdderStudentsToDataBase {

    private static String firstNamePrint = "Please enter student first name: ";
    private static String lastNamePrint = "Please enter student last name: ";
    private static String exit = "For return input [exit]";
    private static String creadedStudent = "A new student has been successfully added: ";
    private static String addNext = "To add next student enter student first name: ";

    public static void add() throws ClassNotFoundException, SQLException, IOException, ConnectionIsNullException {
        System.out.println(firstNamePrint + "\n" + exit);
        Scanner scanner = new Scanner(System.in);
        String firstName = null;
        String lastName = null;
        while (true) {
            firstName = scanner.nextLine();
            if (firstName.equalsIgnoreCase("exit")) {
                ConsoleMenu consoleMenu = new ConsoleMenu();
                consoleMenu.runConsole();
                scanner.close();
                break;
            }
            System.out.println(lastNamePrint + "\n" + exit);
            lastName = scanner.nextLine();
            if (lastName.equalsIgnoreCase("exit")) {
                ConsoleMenu consoleMenu = new ConsoleMenu();
                consoleMenu.runConsole();
                scanner.close();
                break;
            }
            try {
                addStudent(firstName, lastName);
            } catch (IllegalArgumentException e) {
                System.err.println(e.getMessage());
            }
        }
    }

    private static void addStudent(String firstName, String lastName)
            throws ClassNotFoundException, SQLException, IOException, ConnectionIsNullException {
        Validator.veryfyInputString(firstName);
        Validator.veryfyInputString(lastName);
        try (Connection conn = Connector.getConnection(); Statement stmt = conn.createStatement();) {
            String insertQuery = "INSERT INTO school.students (STUDENT_ID, GROUP_ID, FIRST_NAME, LAST_NAME) VALUES (DEFAULT, NULL,'"
                    + firstName + "','" + lastName + "' );";
            stmt.executeUpdate(insertQuery);
        } catch (SQLException e) {
            throw new SQLException(e);
        }
        System.out.println(creadedStudent + "[" + firstName + " " + lastName + "]" + "\n" + addNext + "\n" + exit);
    }
}
