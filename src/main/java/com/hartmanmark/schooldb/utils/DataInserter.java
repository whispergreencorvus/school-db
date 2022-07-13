package com.hartmanmark.schooldb.utils;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import org.apache.ibatis.jdbc.ScriptRunner;

import com.hartmanmark.schooldb.dao.Connector;
import com.hartmanmark.schooldb.service.ReaderPropertiesFile;

public class DataInserter {
    
    public void insertStudents(StringBuilder firstName)
            throws SQLException, IOException, NullPointerException {
        try (Connection conn = Connector.getConnection(); Statement stmt = conn.createStatement();) {
            stmt.executeUpdate(firstName.toString());
        } catch (SQLException e) {
            throw new SQLException(e);
        }
    }

    public void insertGroups(String groupName)
            throws SQLException, IOException, NullPointerException {
        try (Connection conn = Connector.getConnection();
                PreparedStatement stmt = conn
                        .prepareStatement("INSERT INTO school.groups(GROUP_ID, GROUP_NAME) VALUES (DEFAULT , ?);")) {
            stmt.setString(1, groupName);
            stmt.executeUpdate();
        } catch (SQLException e) {
            throw new SQLException(e);
        }
    }

    public void insertCourses() throws SQLException, IOException, NullPointerException {
        ScriptRunner scriptRunner = new ScriptRunner(Connector.getConnection());
        scriptRunner.runScript(readSQLFile());
    }

    private BufferedReader readSQLFile() throws IOException {
        return new BufferedReader(new FileReader(ReaderPropertiesFile.readPathProperties("pathToDataSQLFile")));
    }
}
