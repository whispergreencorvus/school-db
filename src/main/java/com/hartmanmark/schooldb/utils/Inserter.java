package com.hartmanmark.schooldb.utils;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.sql.SQLException;
import java.sql.Statement;
import org.apache.ibatis.jdbc.ScriptRunner;

import com.hartmanmark.schooldb.dao.Connector;
import com.hartmanmark.schooldb.exception.ConnectionIsNullException;
import com.hartmanmark.schooldb.service.Reader;

public class Inserter {

    public void insertStudents(StringBuilder firstName)
            throws SQLException, ClassNotFoundException, IOException, ConnectionIsNullException {
        Statement statement = Connector.getConnection().createStatement();
        statement.execute(firstName.toString());
    }

    public void insertGroups(String groupName)
            throws SQLException, ClassNotFoundException, IOException, ConnectionIsNullException {
        String str = "INSERT INTO school.groups(GROUP_ID, GROUP_NAME) VALUES (DEFAULT , '" + groupName + "');";
        Statement statement = Connector.getConnection().createStatement();
        statement.execute(str);
    }

    public void insertCourses() throws SQLException, ClassNotFoundException, IOException, ConnectionIsNullException {
        ScriptRunner scriptRunner = new ScriptRunner(Connector.getConnection());
        scriptRunner.runScript(readSQLFile());
    }

    private BufferedReader readSQLFile() throws ClassNotFoundException, IOException, SQLException {
        BufferedReader readerData = new BufferedReader(new FileReader(Reader.readPathProperties("pathToDataSQLFile")));
        return readerData;
    }
}
