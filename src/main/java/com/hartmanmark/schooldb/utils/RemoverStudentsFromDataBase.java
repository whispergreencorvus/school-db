package com.hartmanmark.schooldb.utils;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

import com.hartmanmark.schooldb.dao.Connector;
import com.hartmanmark.schooldb.exception.ConnectionIsNullException;
import com.hartmanmark.schooldb.validator.Validator;

public class RemoverStudentsFromDataBase {

    private String removeStudent = "Please enter an student ID between 1 and ";
    private String exit = "For return input [exit]";
    private String removedStudent;
    private String numberOfStudents;
    private Validator validator;

    public void remove() throws ClassNotFoundException, SQLException, IOException, ConnectionIsNullException {
        numberOfIdStudents();
        System.out.println(removeStudent + getNumberOfStudents() + "\n" + exit);
        Scanner scanner = new Scanner(System.in);
        String studentId;
        studentId = scanner.nextLine();
        if (studentId.equalsIgnoreCase("exit")) {
            setRemovedStudent(String.format("%100s", "").replace(' ', '-'));
            return;
        }
        try {
            removeStudent(studentId);
        } catch (IllegalArgumentException e) {
            System.err.println(e.getMessage());
        }
    }

    private void removeStudent(String studentId)
            throws ClassNotFoundException, SQLException, IOException, ConnectionIsNullException {
        validator.veryfyRemoveOption(studentId, getNumberOfStudents());
        Integer studentIdInt = Integer.parseInt(studentId);
        try {
            Connection conn = Connector.getConnection();
            PreparedStatement stmt = conn.prepareStatement("DELETE FROM school.students WHERE STUDENT_ID = ?");
            stmt.setInt(1, studentIdInt);
            stmt.executeUpdate();
        } catch (SQLException e) {
            throw new SQLException(e);
        }
        setRemovedStudent("Student by ID = [" + studentId + "] removed");
    }

    public void numberOfIdStudents()
            throws ClassNotFoundException, SQLException, IOException, ConnectionIsNullException {
        Integer numberOfStudents = null;
        Connection conn = Connector.getConnection();
        PreparedStatement stmt = conn.prepareStatement("SELECT count(*) from school.students;");
        ResultSet resultSet = stmt.executeQuery();
        while (resultSet.next()) {
            numberOfStudents = Integer.valueOf(resultSet.getString("count"));
        }
        resultSet.close();
        setNumberOfStudents(numberOfStudents.toString());
    }

    public RemoverStudentsFromDataBase(Validator validator) {
        this.validator = validator;
    }

    public String getNumberOfStudents() {
        return numberOfStudents;
    }

    public void setNumberOfStudents(String numberOfStudents) {
        this.numberOfStudents = numberOfStudents;
    }

    public String getRemovedStudent() {
        return removedStudent;
    }

    public void setRemovedStudent(String output) {
        this.removedStudent = output;
    }
}
