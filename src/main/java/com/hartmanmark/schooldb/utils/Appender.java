package com.hartmanmark.schooldb.utils;

import java.io.IOException;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Scanner;

import com.hartmanmark.schooldb.dao.Connector;
import com.hartmanmark.schooldb.exception.ConnectionIsNullException;
import com.hartmanmark.schooldb.exception.InputDataIsEmptyException;
import com.hartmanmark.schooldb.exception.InputDataIsNumericException;
import com.hartmanmark.schooldb.exception.InputDataIsSymbolicException;
import com.hartmanmark.schooldb.exception.InputIsMoreThanOneCharacterException;
import com.hartmanmark.schooldb.exception.InputIsNonChooseRangeException;
import com.hartmanmark.schooldb.exception.InputIsNonIntegerException;
import com.hartmanmark.schooldb.output.ConsoleMenu;
import com.hartmanmark.schooldb.validator.Validator;

public class Appender {

    private static String firstNamePrint = "Please enter student first name: ";
    private static String lastNamePrint = "Please enter student last name: ";
    private static String exit = "For return input [exit]";
    private static String creadedStudent = "A new student has been successfully added: ";
    private static String addNext = "To add next student enter student first name: ";

    public static void add() throws ClassNotFoundException, InputIsNonIntegerException, InputIsNonChooseRangeException,
            InputIsMoreThanOneCharacterException, SQLException, IOException, ConnectionIsNullException,
            InputDataIsEmptyException, InputDataIsNumericException, InputDataIsSymbolicException {
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
            } catch (NumberFormatException e) {
                System.err.println(e.getMessage());
            } catch (InputDataIsEmptyException e) {
                System.err.println(e.getMessage());
            } catch (InputDataIsNumericException e) {
                System.err.println(e.getMessage());
            } catch (InputDataIsSymbolicException e) {
                System.err.println(e.getMessage());
            }
        }
    }

    private static void addStudent(String firstName, String lastName)
            throws InputDataIsEmptyException, ClassNotFoundException, SQLException, IOException,
            ConnectionIsNullException, InputDataIsNumericException, InputDataIsSymbolicException {
        Validator.veryfyAddOption(firstName);
        Validator.veryfyAddOption(lastName);
        String str = "INSERT INTO school.students (STUDENT_ID, GROUP_ID, FIRST_NAME, LAST_NAME) VALUES (DEFAULT, NULL,'"
                + firstName + "','" + lastName + "' );";
        PreparedStatement statement = Connector.getConnection().prepareStatement(str);
        statement.execute();
        System.out.println(creadedStudent + "[" + firstName + " " + lastName + "]" + "\n" + addNext);
    }
}
