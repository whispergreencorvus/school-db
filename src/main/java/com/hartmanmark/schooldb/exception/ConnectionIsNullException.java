package com.hartmanmark.schooldb.exception;

@SuppressWarnings("serial")
public class ConnectionIsNullException extends Exception {

    public ConnectionIsNullException(String message) {
        super(message);
    }
}
