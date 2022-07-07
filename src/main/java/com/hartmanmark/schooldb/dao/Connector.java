package com.hartmanmark.schooldb.dao;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import com.hartmanmark.schooldb.exception.ConnectionIsNullException;
import com.hartmanmark.schooldb.service.Reader;

public class Connector {

    public static Connection getConnection()
            throws ClassNotFoundException, IOException, ConnectionIsNullException, SQLException {
        Connection connection = null;
        try {
            connection = DriverManager.getConnection(Reader.readDataBaseProperties("url").toString(),
                    Reader.readDataBaseProperties("user").toString(),
                    Reader.readDataBaseProperties("password").toString());
            if (connection == null) {
                throw new NullPointerException("Failed to make connection. Connection is NULL");
            }
        } catch (SQLException e) {
            throw new SQLException(e);
        }
        return connection;
    }
}
