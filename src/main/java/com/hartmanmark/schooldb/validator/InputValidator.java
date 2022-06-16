package com.hartmanmark.schooldb.validator;

import com.hartmanmark.schooldb.exception.InputIsMoreThanOneCharacterException;
import com.hartmanmark.schooldb.exception.InputIsNonChooseRangeException;
import com.hartmanmark.schooldb.exception.InputIsNonIntegerException;

public class InputValidator {

    public static void verify(String input)
            throws InputIsNonIntegerException, InputIsNonChooseRangeException, InputIsMoreThanOneCharacterException {
        if (input == null) {
            throw new NumberFormatException("NULL");
        }
        if (input.matches("[a-zA-Z]")) {
            throw new InputIsNonIntegerException("Please enter an integer value");
        }
        if (input.matches("[0-9&&^[890]]")) {
            throw new InputIsNonChooseRangeException("Please enter an integer value between 1 and 7 ");
        }
        if (input.toCharArray().length > 1) {
            throw new InputIsMoreThanOneCharacterException("Input must be contain only one character");
        }
    }
}
