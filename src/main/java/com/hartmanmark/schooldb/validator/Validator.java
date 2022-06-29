package com.hartmanmark.schooldb.validator;

import java.io.IOException;
import java.sql.SQLException;

import com.hartmanmark.schooldb.exception.ConnectionIsNullException;
import com.hartmanmark.schooldb.utils.RemoverStudentsFromDataBase;

public class Validator {

    private static final String REGEX_MENU = "[^w^[1-7]]+";
    private static final String REGEX_INTEGER = "[^w^[0-9]]+";
    private static final String REGEX_STRING = "[^wd^[a-zA-Z]]+";


    public static void verifyMenuChoose(String input) {
        if (input == null) {
            throw new IllegalArgumentException("Input data is null");
        }
        if (input.isEmpty()) {
            throw new IllegalArgumentException("Input string is empty. Try again.");
        }
        if (input.matches(REGEX_MENU)) {
            throw new IllegalArgumentException("Please enter an integer value between 1 and 7");
        }
        if (input.toCharArray().length > 1) {
            throw new IllegalArgumentException("Input must be contain only one character");
        }
    }

    public static void verifyInteger(String input) {
        if (input == null) {
            throw new IllegalArgumentException("Input data is null");
        }
        if (input.isEmpty()) {
            throw new IllegalArgumentException("Input string is empty. Try again.");
        }
        if (input.matches(REGEX_INTEGER)) {
            throw new IllegalArgumentException("Please enter an integer value.");
        }
    }

    public static void veryfyRemoveOption(String input)
            throws ClassNotFoundException, SQLException, IOException, ConnectionIsNullException {
        if (input == null) {
            throw new IllegalArgumentException("Input data is null");
        }
        if (input.isEmpty()) {
            throw new IllegalArgumentException("Input string is empty. Try again.");
        }
        if (input.matches(REGEX_INTEGER)) {
            throw new IllegalArgumentException("Please enter an integer value. Try again.");
        }
        int inputID = Integer.parseInt(input);
        int numberOfStudents = RemoverStudentsFromDataBase.numberOfIdStudents();
        if (inputID > numberOfStudents) {
            throw new IllegalArgumentException("Please enter an integer value between 1 and " + numberOfStudents);
        }
    }

    public static void veryfyInputString(String input) {
        if (input == null) {
            throw new IllegalArgumentException("Input data is null");
        }
        if (input.isEmpty()) {
            throw new IllegalArgumentException("Input string is empty. Try again.");
        }
        if (input.matches(REGEX_STRING)) {
            throw new IllegalArgumentException("Please enter an string value. Try again.");
        }
    }
}
