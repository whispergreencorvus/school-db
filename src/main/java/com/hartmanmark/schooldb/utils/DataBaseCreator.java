package com.hartmanmark.schooldb.utils;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.sql.SQLException;
import org.apache.ibatis.jdbc.ScriptRunner;

import com.hartmanmark.schooldb.dao.Connector;
import com.hartmanmark.schooldb.service.ReaderPropertiesFile;

public class DataBaseCreator {

    private ReaderPropertiesFile reader = new ReaderPropertiesFile();

    public void createDataBase() throws SQLException, IOException, NullPointerException {
        ScriptRunner scriptRunner = new ScriptRunner(Connector.getConnection());
        scriptRunner.runScript(readSQLFile());
        reader.read();
    }

    private BufferedReader readSQLFile() throws IOException {
        return new BufferedReader(new FileReader(reader.readPathProperties("pathToShemaSQLFile")));
    }
}
