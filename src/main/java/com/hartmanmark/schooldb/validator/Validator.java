package com.hartmanmark.schooldb.validator;

public class Validator {

    private static final String REGEX_MENU = "[^w^[1-7]]+";
    private static final String REGEX_SYMBOLS = "^(?=.*[-+_!@#$%^&*., ?]).+$";
    private static final String REGEX_NUMERIC = "^(?=.*[0-9]).+$";
    private static final String REGEX_LETTER = "^(?=.*[A-Za-z]).+$";
    private String dataIsNull = "Input data is null";
    private String dataIsSymbol = "Input data contains symbol";
    private String dataIsNumeric = "Input data contains numeric";
    private String dataIsLetter = "Input data contains letter";
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
        if (input.matches(REGEX_SYMBOLS)) {
            throw new IllegalArgumentException(enterIntiger);
        }
        if (input.matches(REGEX_LETTER)) {
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
        if (input.matches(REGEX_SYMBOLS)) {
            throw new IllegalArgumentException(dataIsSymbol);
        }
        if (input.matches(REGEX_LETTER)) {
            throw new IllegalArgumentException(dataIsLetter);
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
        if (input.matches(REGEX_SYMBOLS)) {
            throw new IllegalArgumentException(dataIsSymbol);
        }
        if (input.matches(REGEX_NUMERIC)) {
            throw new IllegalArgumentException(dataIsNumeric);
        }
    }
}
