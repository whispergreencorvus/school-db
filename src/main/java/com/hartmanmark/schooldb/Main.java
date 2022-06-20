package com.hartmanmark.schooldb;

import java.io.IOException;
import java.sql.SQLException;

import com.hartmanmark.schooldb.exception.ConnectionIsNullException;
import com.hartmanmark.schooldb.exception.InputDataIsEmptyException;
import com.hartmanmark.schooldb.exception.InputDataIsNumericException;
import com.hartmanmark.schooldb.exception.InputDataIsSymbolicException;
import com.hartmanmark.schooldb.exception.InputIsMoreThanOneCharacterException;
import com.hartmanmark.schooldb.exception.InputIsNonChooseRangeException;
import com.hartmanmark.schooldb.exception.InputIsNonIntegerException;
import com.hartmanmark.schooldb.output.ConsoleMenu;
import com.hartmanmark.schooldb.utils.CreatorDataBase;

public class Main {

    public static void main(String[] args)
            throws SQLException, ClassNotFoundException, IOException, InputIsNonIntegerException,
            InputIsNonChooseRangeException, ConnectionIsNullException, InputIsMoreThanOneCharacterException,
            InputDataIsEmptyException, InputDataIsNumericException, InputDataIsSymbolicException {
        CreatorDataBase creator = new CreatorDataBase();
        creator.createDataBase();
        ConsoleMenu consoleMenu = new ConsoleMenu();
        consoleMenu.runConsole();
    }
}
