package com.hartmanmark.schooldb;

import java.io.IOException;
import java.sql.SQLException;

import com.hartmanmark.schooldb.dao.Connector;
import com.hartmanmark.schooldb.output.ConsoleMenu;
import com.hartmanmark.schooldb.service.ReaderPropertiesFile;
import com.hartmanmark.schooldb.utils.DataBaseCreator;
import com.hartmanmark.schooldb.utils.DataGenerator;
import com.hartmanmark.schooldb.utils.DataInserter;

public class Main {

    public static void main(String[] args)
            throws SQLException, ClassNotFoundException, IOException, NullPointerException {
        DataBaseCreator creator = new DataBaseCreator();
        creator.createDataBase();
        ConsoleMenu consoleMenu = new ConsoleMenu();
        consoleMenu.runConsole();
    }
}
