package com.hartmanmark.schooldb.validator;

public class Validator {

    private static final String REGEX_MENU = "[^w^[1-7]]+";
    private static final String REGEX_INTEGER = "[^w^[0-9]]+";
    private static final String REGEX_STRING = "[^wd^[a-zA-Z]]+";
    private String dataIsNull = "Input data is null";
    private String emptyString = "Input string is empty.";
    private String enterIntiger = "Please enter an integer value.";
    private String oneCharacter = "Input must be contain only one character";
    private String valueRange = "Please enter an integer value between 1 and 7";
    private String valueBetween = "Please enter an integer value between 1 and ";

    public void verifyMenuChoose(String input) {
        if (input == null) {
            throw new IllegalArgumentException(dataIsNull);
        }
        if (input.isEmpty()) {
            throw new IllegalArgumentException(emptyString);
        }
        if (input.matches(REGEX_MENU)) {
            throw new IllegalArgumentException(valueRange);
        }
        if (input.toCharArray().length > 1) {
            throw new IllegalArgumentException(oneCharacter);
        }
    }

    public void verifyInteger(String input) {
        if (input == null) {
            throw new IllegalArgumentException(dataIsNull);
        }
        if (input.isEmpty()) {
            throw new IllegalArgumentException(emptyString);
        }
        if (input.matches(REGEX_INTEGER)) {
            throw new IllegalArgumentException(enterIntiger);
        }
    }

    public void veryfyRemoveOption(String input, String numberOfStudents) {
        if (input == null) {
            throw new IllegalArgumentException(dataIsNull);
        }
        if (input.isEmpty()) {
            throw new IllegalArgumentException(emptyString);
        }
        if (input.matches(REGEX_INTEGER)) {
            throw new IllegalArgumentException(enterIntiger);
        }
        int inputID = Integer.parseInt(input);
        Integer numOfStudentsInt = Integer.parseInt(numberOfStudents);
        if (inputID > numOfStudentsInt) {
            throw new IllegalArgumentException(valueBetween + numberOfStudents);
        }
    }

    public void veryfyInputString(String input) {
        if (input == null) {
            throw new IllegalArgumentException(dataIsNull);
        }
        if (input.isEmpty()) {
            throw new IllegalArgumentException(emptyString);
        }
        if (input.matches(REGEX_STRING)) {
            throw new IllegalArgumentException(enterIntiger);
        }
    }
}
