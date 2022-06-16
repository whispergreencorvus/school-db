package com.hartmanmark.schooldb.dao;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Random;

import org.apache.ibatis.jdbc.ScriptRunner;

import com.hartmanmark.schooldb.exception.ConnectionIsNullException;
import com.hartmanmark.schooldb.service.Reader;

public class Inserter {

    private final int MAX_NUMBER_OF_COURSES = 11;
    private final int MIN_NUMBER_OF_COURSES = 1;

    public void insertStudents(StringBuilder firstName)
            throws SQLException, ClassNotFoundException, IOException, ConnectionIsNullException {
        Statement statement = Connector.connect().createStatement();
        statement.execute(firstName.toString());
    }

    public void insertGroups(String groupName)
            throws SQLException, ClassNotFoundException, IOException, ConnectionIsNullException {
        String str = "INSERT INTO school.groups(GROUP_ID, GROUP_NAME) VALUES (DEFAULT , '" + groupName + "');";
        Statement statement = Connector.connect().createStatement();
        statement.execute(str);
    }

    public void insertCourses() throws SQLException, ClassNotFoundException, IOException, ConnectionIsNullException {
        ScriptRunner scriptRunner = new ScriptRunner(Connector.connect());
        scriptRunner.runScript(readSQLFile());
    }

    public void joinStudentsAndCourses()
            throws ClassNotFoundException, SQLException, IOException, ConnectionIsNullException {
        Random random = new Random();
        int rangeOfCourse = random.nextInt(MAX_NUMBER_OF_COURSES - MIN_NUMBER_OF_COURSES) + MIN_NUMBER_OF_COURSES;
        String str = "INSERT INTO school.students_courses(STUDENT_ID, COURSE_ID) VALUES (DEFAULT , '" + rangeOfCourse
                + "');";
        Statement statement = Connector.connect().createStatement();
        statement.execute(str);
    }

    private BufferedReader readSQLFile() throws ClassNotFoundException, IOException, SQLException {
        BufferedReader readerData = new BufferedReader(new FileReader(Reader.readPropertyFile("pathToDataSQLFile")));
        return readerData;
    }
}
