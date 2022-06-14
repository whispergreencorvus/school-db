package com.hartmanmark.schooldb.service;

import com.hartmanmark.schooldb.exception.InputIsNonChooseRangeException;
import com.hartmanmark.schooldb.exception.InputIsNonIntegerException;

public class Validator {

	public static void verify(String input) throws InputIsNonIntegerException, InputIsNonChooseRangeException {
		if (input == null) {
			throw new NumberFormatException("null");
		}
		if (input.matches("[a-zA-Z]")) {
			throw new InputIsNonIntegerException("Please enter an integer value");
		}
		if (input.matches("[0-9&&^[890]]")) {
			throw new InputIsNonChooseRangeException("Please enter an integer value between 1 and 7 ");
		}
	}
}
