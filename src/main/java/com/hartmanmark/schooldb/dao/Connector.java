package com.hartmanmark.schooldb.dao;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import com.hartmanmark.schooldb.service.ReaderPropertiesFile;

public class Connector {

    public static Connection getConnection() throws IOException, SQLException {
        Connection connection = null;
        try {
            connection = DriverManager.getConnection(ReaderPropertiesFile.readDataBaseProperties("url").toString(),
                    ReaderPropertiesFile.readDataBaseProperties("user").toString(),
                    ReaderPropertiesFile.readDataBaseProperties("password").toString());
        } catch (SQLException e) {
            throw new SQLException(e);
        }
        return connection;
    }
}
