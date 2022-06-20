package com.hartmanmark.schooldb.validator;

import com.hartmanmark.schooldb.exception.InputDataIsAlphabeticException;
import com.hartmanmark.schooldb.exception.InputDataIsEmptyException;
import com.hartmanmark.schooldb.exception.InputDataIsNumericException;
import com.hartmanmark.schooldb.exception.InputDataIsSymbolicException;
import com.hartmanmark.schooldb.exception.InputIsMoreThanOneCharacterException;
import com.hartmanmark.schooldb.exception.InputIsNonChooseRangeException;
import com.hartmanmark.schooldb.exception.InputIsNonIntegerException;

public class Validator {

    public static void verifyMenuChoose(String input) throws InputIsNonIntegerException, InputIsNonChooseRangeException,
            InputIsMoreThanOneCharacterException, InputDataIsEmptyException, InputDataIsSymbolicException {
        if (input == null) {
            throw new NumberFormatException("Input data is null");
        }
        if (input.isEmpty()) {
            throw new InputDataIsEmptyException("Input string is empty. Try again.");
        }
        if (input.matches("[a-zA-Z]+")) {
            throw new InputIsNonIntegerException("Please enter an integer value");
        }
        if (input.matches("[^w^[1-7]]+")) {
            throw new InputDataIsSymbolicException("Input data is symbolic. Try again.");
        }
        if (input.matches("[0-9&&^[890]]")) {
            throw new InputIsNonChooseRangeException("Please enter an integer value between 1 and 7 ");
        }
        if (input.toCharArray().length > 1) {
            throw new InputIsMoreThanOneCharacterException("Input must be contain only one character");
        }
    }

    public static void veryfyRemoveOption(String input)
            throws InputDataIsEmptyException, InputDataIsSymbolicException, InputDataIsAlphabeticException {
        if (input == null) {
            throw new NumberFormatException("Input data is null");
        }
        if (input.isEmpty()) {
            throw new InputDataIsEmptyException("Input string is empty. Try again.");
        }
        if (input.matches("[^w^[1-9]]+")) {
            throw new InputDataIsSymbolicException("Input data is symbolic. Try again.");
        }
        if (input.matches("[a-zA-Z]+")) {
            throw new InputDataIsAlphabeticException("Input data is alphabetic. Try again.");
        }
    }

    public static void veryfyAddOption(String input)
            throws InputDataIsEmptyException, InputDataIsNumericException, InputDataIsSymbolicException {
        if (input == null) {
            throw new NumberFormatException("Input data is null");
        }
        if (input.isEmpty()) {
            throw new InputDataIsEmptyException("Input string is empty. Try again.");
        }
        if (input.matches("[0-9]+")) {
            throw new InputDataIsNumericException("Input data is numeric. Try again.");
        }
        if (input.matches("[^w^[a-zA-Z]]+")) {
            throw new InputDataIsSymbolicException("Input data is symbolic. Try again.");
        }
    }
}
