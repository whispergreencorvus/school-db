package com.hartmanmark.schooldb.validator;

import java.io.IOException;
import java.sql.SQLException;

import com.hartmanmark.schooldb.exception.ConnectionIsNullException;

public class Validator {

    private final String REGEX_MENU = "[^w^[1-7]]+";
    private final String REGEX_INTEGER = "[^w^[0-9]]+";
    private final String REGEX_STRING = "[^wd^[a-zA-Z]]+";

    public void verifyMenuChoose(String input) {
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

    public void verifyInteger(String input) {
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

    public void veryfyRemoveOption(String input, String numberOfStudents)
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
        Integer numOfStudentsInt = Integer.parseInt(numberOfStudents);
        if (inputID > numOfStudentsInt) {
            throw new IllegalArgumentException("Please enter an integer value between 1 and " + numberOfStudents);
        }
    }

    public void veryfyInputString(String input) {
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
