package com.hartmanmark.schooldb.utils;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.sql.SQLException;

import org.apache.ibatis.jdbc.ScriptRunner;

import com.hartmanmark.schooldb.dao.Connector;
import com.hartmanmark.schooldb.exception.ConnectionIsNullException;
import com.hartmanmark.schooldb.service.Reader;

public class DataBaseCreator {
    private Reader reader = new Reader();

    public void createDataBase() throws ClassNotFoundException, SQLException, IOException, ConnectionIsNullException {
        ScriptRunner scriptRunner = new ScriptRunner(Connector.getConnection());
        scriptRunner.runScript(readSQLFile());
        reader.read();
    }

    private BufferedReader readSQLFile() throws ClassNotFoundException, IOException, SQLException {
        BufferedReader readerSchema = new BufferedReader(new FileReader(Reader.readPathProperties("pathToShemaSQLFile")));
        return readerSchema;
    }
}
