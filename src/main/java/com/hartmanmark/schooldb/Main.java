package com.hartmanmark.schooldb;

import java.io.FileNotFoundException;
import java.sql.SQLException;

public class Main {

    public static void main(String[] args) throws SQLException, ClassNotFoundException, FileNotFoundException {
        JDBCPostgreSQLConnection connection = new JDBCPostgreSQLConnection();
        connection.connect();
//        CreateTable createTable = new CreateTable();
//        createTable.createTable();
    }
}
