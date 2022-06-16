package com.hartmanmark.schooldb.exception;

@SuppressWarnings("serial")
public class InputIsMoreThanOneCharacterException extends Exception {

    public InputIsMoreThanOneCharacterException(String message) {
        super(message);
    }
}
