package com.hartmanmark.schooldb;

import java.io.IOException;
import java.sql.SQLException;

import com.hartmanmark.schooldb.console.ConsoleMenu;
import com.hartmanmark.schooldb.exception.InputIsNonChooseRangeException;
import com.hartmanmark.schooldb.exception.InputIsNonIntegerException;
import com.hartmanmark.schooldb.service.Creator;

public class Main {

	public static void main(String[] args) throws SQLException, ClassNotFoundException, IOException, InputIsNonIntegerException, InputIsNonChooseRangeException {
		Creator creator = new Creator();
		creator.createDataBase();
		ConsoleMenu consoleMenu = new ConsoleMenu();
		consoleMenu.readChoose();
	}
}
