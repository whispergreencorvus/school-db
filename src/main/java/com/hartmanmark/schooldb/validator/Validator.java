package com.hartmanmark.schooldb.validator;

import java.io.IOException;
import java.sql.SQLException;

import com.hartmanmark.schooldb.exception.ConnectionIsNullException;
import com.hartmanmark.schooldb.utils.RemoveStudents;

public class Validator {

    private static final String REGEX_MENU = "[^w^[1-7]]+";
    private static final String REGEX_REMOVE_OPTION = "[^w^[0-9]]+";
    private static final String REGEX_ADD_OPTION = "[^wd^[a-zA-Z]]+";
    private static final String REGEX_ADD = "[^wd^[a-zA-Z]]+";

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

    public static void veryfyRemoveOption(String input)
            throws ClassNotFoundException, SQLException, IOException, ConnectionIsNullException {
        if (input == null) {
            throw new IllegalArgumentException("Input data is null");
        }
        if (input.isEmpty()) {
            throw new IllegalArgumentException("Input string is empty. Try again.");
        }
        if (input.matches(REGEX_REMOVE_OPTION)) {
            throw new IllegalArgumentException("Please enter an integer value. Try again.");
        }
        int inputID = Integer.parseInt(input);
        int numberOfStudents = RemoveStudents.numberOfIdStudents();
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
        if (input.matches(REGEX_ADD_OPTION)) {
            throw new IllegalArgumentException("Please enter an string value. Try again.");
        }
    }
}
