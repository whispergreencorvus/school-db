package com.hartmanmark.schooldb.dao;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import com.hartmanmark.schooldb.exception.ConnectionIsNullException;
import com.hartmanmark.schooldb.validator.Validator;

public class AdderStudentsToDataBase {

    private String firstNamePrint = "Please enter student first name: ";
    private String lastNamePrint = "Please enter student last name: ";
    private String creadedStudent = "A new student has been successfully added: ";
    private Validator validator;
    private String firstName;

    public AdderStudentsToDataBase(Validator validator) {
        this.validator = validator;
    }

    public String printFirstName() {
        return firstNamePrint + "\n";
    }

    public String enterFirstName(String input) {
        firstName = input;
        validator.veryfyInputString(firstName);
        return lastNamePrint;
    }

    public String enterLastName(String input)
            throws ClassNotFoundException, SQLException, IOException, ConnectionIsNullException {
        String lastName = input;
        validator.veryfyInputString(lastName);
        try {
            addStudentToDataBase(firstName, lastName);
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException(e);
        }
        return "\n" + creadedStudent + "[" + firstName + " " + lastName + "]";
    }

    private void addStudentToDataBase(String firstName, String lastName)
            throws ClassNotFoundException, SQLException, IOException, ConnectionIsNullException {
        try (Connection conn = Connector.getConnection();
                PreparedStatement stmt = conn
                        .prepareStatement("INSERT INTO school.students (STUDENT_ID, GROUP_ID, FIRST_NAME, LAST_NAME)"
                                + " VALUES (DEFAULT, NULL,?,?);")) {
            stmt.setString(1, firstName);
            stmt.setString(2, lastName);
            stmt.executeUpdate();
        } catch (SQLException e) {
            throw new SQLException(e);
        }
    }
}
