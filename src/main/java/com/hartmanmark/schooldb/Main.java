package com.hartmanmark.schooldb;

import java.io.IOException;
import java.sql.SQLException;

import com.hartmanmark.schooldb.output.ConsoleMenu;
import com.hartmanmark.schooldb.utils.DataBaseCreator;

public class Main {

    public static void main(String[] args)
            throws SQLException, ClassNotFoundException, IOException, NullPointerException {
        DataBaseCreator creator = new DataBaseCreator();
        creator.createDataBase();
        ConsoleMenu consoleMenu = new ConsoleMenu();
        consoleMenu.runConsole();
    }
}
