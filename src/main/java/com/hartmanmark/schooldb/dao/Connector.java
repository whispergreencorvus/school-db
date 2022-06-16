package com.hartmanmark.schooldb.dao;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import com.hartmanmark.schooldb.exception.ConnectionIsNullException;
import com.hartmanmark.schooldb.service.Reader;
import com.hartmanmark.schooldb.validator.ConnectionValidator;

public class Connector {

    public static Connection connect() throws ClassNotFoundException, IOException, ConnectionIsNullException {
        Connection connection = null;
        try {
            connection = DriverManager.getConnection(Reader.readPropertyFile("url").toString(),
                    Reader.readPropertyFile("user").toString(), Reader.readPropertyFile("password").toString());
            ConnectionValidator.verify(connection);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return connection;
    }
}
