package com.hartmanmark.schooldb.dao;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import com.hartmanmark.schooldb.exception.ConnectionIsNullException;
import com.hartmanmark.schooldb.service.Reader;

public class Connector {

    public static Connection getConnection() throws ClassNotFoundException, IOException, ConnectionIsNullException {
        Connection connection = null;
        try {
            connection = DriverManager.getConnection(Reader.readDataBaseProperties("url").toString(),
                    Reader.readDataBaseProperties("user").toString(),
                    Reader.readDataBaseProperties("password").toString());
            verify(connection);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return connection;
    }

    private static void verify(Connection connection) throws ConnectionIsNullException {
        if (connection == null) {
            throw new ConnectionIsNullException("Failed to make connection. Connection is NULL");
        }
    }
}
