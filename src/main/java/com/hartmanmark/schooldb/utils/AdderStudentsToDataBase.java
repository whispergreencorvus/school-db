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
    private String addedStudent = String.format("%100s", "").replace(' ', '-');
    private String exit = "To return enter [exit]";
    private Validator validator;

    public void add() throws ClassNotFoundException, SQLException, IOException, ConnectionIsNullException {
        System.out.println(firstNamePrint + "\n" + exit);
        Scanner scanner = new Scanner(System.in);
        String firstName = null;
        String lastName = null;
        firstName = scanner.nextLine();
        if (firstName.equalsIgnoreCase("exit")) {
            return;
        }
        validator.veryfyInputString(firstName);
        System.out.println(lastNamePrint);
        lastName = scanner.nextLine();
        if (lastName.equalsIgnoreCase("exit")) {
            return;
        }
        validator.veryfyInputString(lastName);
        try {
            addStudent(firstName, lastName);
        } catch (IllegalArgumentException e) {
            System.err.println(e.getMessage());
        }
    }

    private void addStudent(String firstName, String lastName)
            throws ClassNotFoundException, SQLException, IOException, ConnectionIsNullException {
        try {
            Connection conn = Connector.getConnection();
            PreparedStatement stmt = conn
                    .prepareStatement("INSERT INTO school.students (STUDENT_ID, GROUP_ID, FIRST_NAME, LAST_NAME)"
                            + " VALUES (DEFAULT, NULL,?,?);");
            stmt.setString(1, firstName);
            stmt.setString(2, lastName);
            stmt.executeUpdate();
        } catch (SQLException e) {
            throw new SQLException(e);
        }
        setAddedStudent("\n" + creadedStudent + "[" + firstName + " " + lastName + "]");
    }

    public AdderStudentsToDataBase(Validator validator) {
        this.validator = validator;
    }

    public String getAddedStudent() {
        return addedStudent;
    }

    public void setAddedStudent(String student) {
        this.addedStudent = student;
    }
}
