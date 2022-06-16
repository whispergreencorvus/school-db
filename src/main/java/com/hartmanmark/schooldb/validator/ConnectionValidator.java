package com.hartmanmark.schooldb.validator;

import java.sql.Connection;

import com.hartmanmark.schooldb.exception.ConnectionIsNullException;

public class ConnectionValidator {

    public static void verify(Connection connection) throws ConnectionIsNullException {
        if (connection == null) {
            throw new ConnectionIsNullException("Failed to make connection. Connection is NULL");
        }
    }
}
