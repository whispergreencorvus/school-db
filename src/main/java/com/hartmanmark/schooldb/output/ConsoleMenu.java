package com.hartmanmark.schooldb.output;

import java.io.IOException;
import java.sql.SQLException;
import java.util.Scanner;

import com.hartmanmark.schooldb.exception.ConnectionIsNullException;
import com.hartmanmark.schooldb.exception.InputDataIsEmptyException;
import com.hartmanmark.schooldb.exception.InputDataIsNumericException;
import com.hartmanmark.schooldb.exception.InputDataIsSymbolicException;
import com.hartmanmark.schooldb.exception.InputIsMoreThanOneCharacterException;
import com.hartmanmark.schooldb.exception.InputIsNonChooseRangeException;
import com.hartmanmark.schooldb.exception.InputIsNonIntegerException;
import com.hartmanmark.schooldb.utils.Appender;
import com.hartmanmark.schooldb.utils.Remove;
import com.hartmanmark.schooldb.validator.Validator;

public class ConsoleMenu {

    private String input;
    private final String[] options = { "", "1 - Find all groups with less or equals student count",
            "2 - Find all students related to course with given name", "3 - Add new student",
            "4 - Delete student by STUDENT_ID", "5 - Add a student to the course (from a list)",
            "6 - Remove the student from one of his or her courses", "7 - Exit", "" };
    private final String welcom = "Welcom to simple sql-jdbc-school app.";
    private final String redLine = String.format("%100s", "").replace(' ', '-') + "\n";

    public void runConsole()
            throws InputIsNonIntegerException, InputIsNonChooseRangeException, InputIsMoreThanOneCharacterException,
            ClassNotFoundException, SQLException, IOException, ConnectionIsNullException, InputDataIsEmptyException,
            InputDataIsNumericException, InputDataIsSymbolicException {
        System.err.println(redLine);
        System.out.print(welcom);
        while (true) {
            try {
                printMenu(options);
                Scanner scanner = new Scanner(System.in);
                input = scanner.nextLine();
                Validator.verifyMenuChoose(input);
                if (input.equals("1")) {
                    System.err.println("method №" + input);
                } else if (input.equals("2")) {
                    System.err.println("method №" + input);
                } else if (input.equals("3")) {
                    Appender.add();
                    break;
                } else if (input.equals("4")) {
                    Remove.remove();
                    break;
                } else if (input.equals("5")) {
                    System.err.println("method №" + input);
                } else if (input.equals("6")) {
                    System.err.println("method №" + input);
                } else if (input.equals("7")) {
                    System.err.println("Exit");
                    break;
                }
                scanner.close();
            } catch (NumberFormatException e) {
                System.err.println(e.getMessage());
            } catch (InputDataIsEmptyException e) {
                System.err.println(e.getMessage());
            } catch (InputIsNonIntegerException e) {
                System.err.println(e.getMessage());
            } catch (InputDataIsSymbolicException e) {
                System.err.println(e.getMessage());
            } catch (InputIsNonChooseRangeException e) {
                System.err.println(e.getMessage());
            } catch (InputIsMoreThanOneCharacterException e) {
                System.err.println(e.getMessage());
            }
        }
    }

    private void printMenu(String[] options) {
        for (String option : options) {
            System.out.println(option);
        }
        System.out.print("Choose your option : ");
    }
}
