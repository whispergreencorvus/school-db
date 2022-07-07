package com.hartmanmark.schooldb.utils;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Scanner;

import com.hartmanmark.schooldb.dao.Connector;
import com.hartmanmark.schooldb.exception.ConnectionIsNullException;
import com.hartmanmark.schooldb.validator.Validator;

public class AdderStudentsToDataBase {

    private String firstNamePrint = "Please enter student first name: ";
    private String lastNamePrint = "Please enter student last name: ";
    private String creadedStudent = "A new student has been successfully added: ";
    private Validator validator;
    private String firstName = null;
    private String lastName = null;

    public AdderStudentsToDataBase(Validator validator) {
        this.validator = validator;
    }

    public String printFirstName() {
        return firstNamePrint + "\n";
    }

    public String enterFirstName() {
        scannerFirstName();
        validator.veryfyInputString(firstName);
        return lastNamePrint;
    }

    public String enterLastName() throws ClassNotFoundException, SQLException, IOException, ConnectionIsNullException {
        scannerLastName();
        validator.veryfyInputString(lastName);
        try {
            addStudentToDataBase(firstName, lastName);
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException(e);
        }
        return "\n" + creadedStudent + "[" + firstName + " " + lastName + "]";
    }

    private void scannerLastName() {
        Scanner scanner = new Scanner(System.in);
        lastName = scanner.nextLine();
    }

    private void scannerFirstName() {
        Scanner scanner = new Scanner(System.in);
        firstName = scanner.nextLine();
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
