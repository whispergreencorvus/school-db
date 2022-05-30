package com.hartmanmark.schooldb;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.Reader;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import org.apache.ibatis.jdbc.ScriptRunner;

public class JDBCPostgreSQLConnection {

    private final String url = "jdbc:postgresql://localhost/postgres";
    private final String user = "postgres";
    private final String password = "123";

    public void connect() throws ClassNotFoundException, SQLException, FileNotFoundException {
        Connection con = DriverManager.getConnection(url, user, password);      
        ScriptRunner sr = new ScriptRunner(con);
        Reader readerSchema = new BufferedReader(new FileReader("/home/user/java/GitLab/Task 7/Shema.sql"));
        Reader readerData = new BufferedReader(new FileReader("/home/user/java/GitLab/Task 7/Data.sql"));
        sr.runScript(readerSchema);
        sr.runScript(readerData);
        
        
        
        
        
        
        
        
        
        
        
        
        
        
        
        
        
        
        
//        Connection conn = null;
//        try {
//            conn = DriverManager.getConnection(url, user, password);
//            if (conn == null) {
//                System.err.println("Failed to make connection");
//            }
//        } catch (SQLException e) {
//            System.out.println(e.getMessage());
//        }
//        return conn;
    }
}
