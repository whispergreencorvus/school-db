package com.hartmanmark.schooldb;

import java.io.IOException;
import java.sql.SQLException;

public class Main {

	public static void main(String[] args) throws SQLException, ClassNotFoundException, IOException {
		Creator creator = new Creator();
		creator.createDataBase();
	}
}
