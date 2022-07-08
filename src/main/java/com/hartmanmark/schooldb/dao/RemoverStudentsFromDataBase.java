package com.hartmanmark.schooldb.dao;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.hartmanmark.schooldb.exception.ConnectionIsNullException;
import com.hartmanmark.schooldb.validator.Validator;

public class RemoverStudentsFromDataBase {

    private String removeStudent = "Please enter an student ID between 1 and ";
    private String countOfStudentsQuery = "SELECT count(*) from school.students;";
    private String firstName;
    private String lastName;
    private String studentId;
    private Validator validator;

    public RemoverStudentsFromDataBase(Validator validator) {
        this.validator = validator;
    }

    public String printNamberOfStudents() throws ClassNotFoundException, SQLException, IOException, ConnectionIsNullException {
        return removeStudent + countNumberOfIdStudents() + "\n";
    }

    public String chooseStudentId(String input)
            throws ClassNotFoundException, SQLException, IOException, ConnectionIsNullException {
        studentId = input;
        findRemovedStudent();
        return removeStudent();
    }

    private String removeStudent() throws ClassNotFoundException, SQLException, IOException, ConnectionIsNullException {
        validator.veryfyRemoveOption(studentId, countNumberOfIdStudents());
        Integer studentIdInt = Integer.parseInt(studentId);
        try (Connection conn = Connector.getConnection();
                PreparedStatement stmt = conn.prepareStatement("DELETE FROM school.students WHERE STUDENT_ID = ?")) {
            stmt.setInt(1, studentIdInt);
            stmt.executeUpdate();
        } catch (SQLException e) {
            throw new SQLException(e);
        }
        return "Student by ID [" + studentId + "]\n" + "first name: " + firstName + "\nlast name: " + lastName
                + "\nwas succesefully removed.";
    }

    private String countNumberOfIdStudents()
            throws ClassNotFoundException, SQLException, IOException, ConnectionIsNullException {
        String numberOfIdStudents = null;
        try (Connection conn = Connector.getConnection();
                PreparedStatement stmt = conn.prepareStatement(countOfStudentsQuery)) {
            try (ResultSet resultSet = stmt.executeQuery()) {
                while (resultSet.next()) {
                    numberOfIdStudents = resultSet.getString("count");
                }
            }
        } catch (SQLException e) {
            throw new SQLException(e);
        }
        return numberOfIdStudents;
    }

    private void findRemovedStudent()
            throws ClassNotFoundException, SQLException, IOException, ConnectionIsNullException {
        try (Connection conn = Connector.getConnection();
                PreparedStatement stmt = conn
                        .prepareStatement("SELECT school.students.first_name, school.students.last_name "
                                + "FROM school.students WHERE school.students.student_id = " + studentId)) {
            try (ResultSet resultSet = stmt.executeQuery()) {
                while (resultSet.next()) {
                    firstName = resultSet.getString(1);
                    lastName = resultSet.getString(2);
                }
            }
        } catch (SQLException e) {
            throw new SQLException(e);
        }
    }
}
