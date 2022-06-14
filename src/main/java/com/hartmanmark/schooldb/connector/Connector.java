package com.hartmanmark.schooldb.connector;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import com.hartmanmark.schooldb.reader.Reader;

public class Connector {

	public static Connection connect() throws ClassNotFoundException, IOException {
		Connection conn = null;
		try {
			conn = DriverManager.getConnection(Reader.readPropertyFile("url").toString(),
					Reader.readPropertyFile("user").toString(), Reader.readPropertyFile("password").toString());
			if (conn == null) {
				System.err.println("Failed to make connection");
			}
		} catch (SQLException e) {
			System.out.println(e.getMessage());
		}
		return conn;
	}
}
