package com.hartmanmark.schooldb.utils;

import java.io.IOException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

import com.hartmanmark.schooldb.dao.Connector;
import com.hartmanmark.schooldb.exception.ConnectionIsNullException;
import com.hartmanmark.schooldb.exception.InputDataIsAlphabeticException;
import com.hartmanmark.schooldb.exception.InputDataIsEmptyException;
import com.hartmanmark.schooldb.exception.InputDataIsNumericException;
import com.hartmanmark.schooldb.exception.InputDataIsSymbolicException;
import com.hartmanmark.schooldb.exception.InputIsMoreThanOneCharacterException;
import com.hartmanmark.schooldb.exception.InputIsNonChooseRangeException;
import com.hartmanmark.schooldb.exception.InputIsNonIntegerException;
import com.hartmanmark.schooldb.output.ConsoleMenu;
import com.hartmanmark.schooldb.validator.Validator;

public class Remove {

    private static String enter = "Please enter an student ID between 1 and ";
    private static String exit = "For return input [exit]";

    public static void remove() throws ClassNotFoundException, SQLException, IOException, ConnectionIsNullException,
            InputIsNonIntegerException, InputIsNonChooseRangeException, InputIsMoreThanOneCharacterException,
            InputDataIsEmptyException, InputDataIsNumericException, InputDataIsSymbolicException {
        System.out.println(enter + numberOfIdStudents() + "\n" + exit);
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
            } catch (NumberFormatException e) {
                System.err.println(e.getMessage());
            } catch (InputDataIsEmptyException e) {
                System.err.println(e.getMessage());
            } catch (InputDataIsSymbolicException e) {
                System.err.println(e.getMessage());
            } catch (InputDataIsAlphabeticException e) {
                System.err.println(e.getMessage());
            }
        }
    }

    private static void removeStudent(String studentId)
            throws ClassNotFoundException, SQLException, IOException, ConnectionIsNullException,
            InputDataIsEmptyException, InputDataIsSymbolicException, InputDataIsAlphabeticException {
        Validator.veryfyRemoveOption(studentId);
        String str = "DELETE FROM school.students WHERE STUDENT_ID=" + studentId;
        PreparedStatement statement = Connector.getConnection().prepareStatement(str);
        statement.execute();
        System.out.println("Student by ID = [" + studentId + "] removed");
        System.out.println("To remove next student, enter an student ID between 1 and " + numberOfIdStudents() + "\n" + exit);
    }

    private static int numberOfIdStudents()
            throws NumberFormatException, SQLException, ClassNotFoundException, IOException, ConnectionIsNullException {
        String str = "SELECT count(*) from school.students;";
        Integer numberOfStudents = null;
        PreparedStatement statement = Connector.getConnection().prepareStatement(str);
        ResultSet resultSet = statement.executeQuery();
        while (resultSet.next()) {
            numberOfStudents = Integer.valueOf(resultSet.getString("count"));
        }
        return numberOfStudents;
    }
}
