package com.hartmanmark.schooldb;

import java.io.IOException;
import java.sql.SQLException;

import com.hartmanmark.schooldb.dao.Creator;
import com.hartmanmark.schooldb.exception.ConnectionIsNullException;
import com.hartmanmark.schooldb.exception.InputIsMoreThanOneCharacterException;
import com.hartmanmark.schooldb.exception.InputIsNonChooseRangeException;
import com.hartmanmark.schooldb.exception.InputIsNonIntegerException;
import com.hartmanmark.schooldb.output.ConsoleMenu;

public class Main {

    public static void main(String[] args)
            throws SQLException, ClassNotFoundException, IOException, InputIsNonIntegerException,
            InputIsNonChooseRangeException, ConnectionIsNullException, InputIsMoreThanOneCharacterException {
        Creator creator = new Creator();
        creator.createDataBase();
        ConsoleMenu consoleMenu = new ConsoleMenu();
        consoleMenu.readChoose();
    }
}
