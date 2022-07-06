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

public class FinderStudents {

    private Validator validator;
    private int numberOfStudents;
    private String course;
    private String exit = "To return enter [exit]";
    private String findedStudents = String.format("%100s", "").replace(' ', '-');
    private String nameOfColumns = "Last names:     First names:";
    private String separator = "-----------     -----------";
    private String enterCourse = "Enter the course (Chemistry, Biology, Physics, Astronomy, History, "
            + "Anthropology, Geography, Mathematics, Philosophy, Linguistics)";
    private String studentsPerGroup = "SELECT school.students.last_name, school.students.first_name "
            + "FROM school.students JOIN school.students_courses "
            + "ON school.students.student_id = school.students_courses.student_id JOIN school.courses\n"
            + "ON school.courses.course_id = school.students_courses.course_id "
            + "WHERE school.courses.course_name = '";
    private String countQuery = "SELECT count(*) FROM school.students JOIN school.students_courses\n"
            + "ON school.students.student_id = school.students_courses.student_id JOIN school.courses\n"
            + "ON school.courses.course_id = school.students_courses.course_id \n"
            + "WHERE school.courses.course_name = '";

    public void find() throws ClassNotFoundException, SQLException, IOException, ConnectionIsNullException {
        Scanner scanner = new Scanner(System.in);
        System.out.println(enterCourse + "\n" + exit);
        course = scanner.nextLine();
        if (course.equalsIgnoreCase("exit")) {
            return;
        }
        try {
            findStudents();
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException(e);
        }
    }

    private void findStudents() throws ClassNotFoundException, IOException, ConnectionIsNullException, SQLException {
        validator.veryfyInputString(course);
        StringBuilder builder = new StringBuilder();
        try (Connection conn = Connector.getConnection();
                PreparedStatement stmt = conn
                        .prepareStatement(studentsPerGroup + course + "'ORDER BY school.students.last_name;")) {
            try (ResultSet resultSet = stmt.executeQuery()) {
                countOfStudents();
                builder.append(nameOfColumns + "\n" + separator + "\n");
                while (resultSet.next()) {
                    builder.append(resultSet.getString(1) + "     " + resultSet.getString(2) + "\n");
                }
                builder.append(separator + "\nStudents on the course: " + numberOfStudents);
            }
        } catch (SQLException e) {
            throw new SQLException(e);
        }
        setFindedStudents(builder.toString());
    }

    private void countOfStudents() throws SQLException, ClassNotFoundException, IOException, ConnectionIsNullException {
        try (Connection conn = Connector.getConnection();
                PreparedStatement stmt = conn.prepareStatement(countQuery + course + "';")) {
            try (ResultSet resultSet = stmt.executeQuery()) {
                while (resultSet.next()) {
                    numberOfStudents = resultSet.getInt(1);
                }
            }
        } catch (SQLException e) {
            throw new SQLException(e);
        }
    }

    public FinderStudents(Validator validator) {
        this.validator = validator;
    }

    public String getFindedStudents() {
        return findedStudents;
    }

    public void setFindedStudents(String findedStudents) {
        this.findedStudents = findedStudents;
    }
}
