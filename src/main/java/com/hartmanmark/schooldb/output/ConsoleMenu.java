package com.hartmanmark.schooldb.output;

import java.util.Scanner;

import com.hartmanmark.schooldb.exception.InputIsMoreThanOneCharacterException;
import com.hartmanmark.schooldb.exception.InputIsNonChooseRangeException;
import com.hartmanmark.schooldb.exception.InputIsNonIntegerException;
import com.hartmanmark.schooldb.validator.InputValidator;

public class ConsoleMenu {
    private String input;

    private final String[] options = { "", "1 - Find all groups with less or equals student count",
            "2 - Find all students related to course with given name", "3 - Add new student",
            "4 - Delete student by STUDENT_ID", "5 - Add a student to the course (from a list)",
            "6 - Remove the student from one of his or her courses", "7 - Exit", "" };
    private final String welcom = "Welcom to simple sql-jdbc-school app.";
    private final String redLine = String.format("%100s", "").replace(' ', '-') + "\n";

    public void readChoose()
            throws InputIsNonIntegerException, InputIsNonChooseRangeException, InputIsMoreThanOneCharacterException {
        System.err.println(redLine); // for me. in final version will remove
        System.out.print(welcom);
        try (Scanner scanner = new Scanner(System.in)) {
            while (true) {
                printMenu(options);
                InputValidator.verify(input = scanner.nextLine());
                if (input.equals("1")) {
                    System.err.println("method №" + input);// for me. in final version will remove
                } else if (input.equals("2")) {
                    System.err.println("method №" + input);// for me. in final version will remove
                } else if (input.equals("3")) {
                    System.err.println("method №" + input);// for me. in final version will remove
                } else if (input.equals("4")) {
                    System.err.println("method №" + input);// for me. in final version will remove
                } else if (input.equals("5")) {
                    System.err.println("method №" + input);// for me. in final version will remove
                } else if (input.equals("6")) {
                    System.err.println("method №" + input);// for me. in final version will remove
                } else if (input.equals("7")) {
                    System.err.println("Exit");// for me. in final version will remove
                    break;
                }
            }
        }
    }

    public void printMenu(String[] options) {
        for (String option : options) {
            System.out.println(option);
        }
        System.out.print("Choose your option : ");
    }
}
