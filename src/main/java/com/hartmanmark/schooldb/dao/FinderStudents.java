package com.hartmanmark.schooldb.dao;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.hartmanmark.schooldb.exception.ConnectionIsNullException;
import com.hartmanmark.schooldb.validator.Validator;

public class FinderStudents {

    private Validator validator;
    private int numberOfStudents;
    private String course;
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

    public FinderStudents(Validator validator) {
        this.validator = validator;
    }

    public String printStudents() {
        return enterCourse + "\n";
    }

    public String chooseCourse(String input)
            throws ClassNotFoundException, IOException, ConnectionIsNullException, SQLException {
        course = input;
        return findStudents();
    }

    private String findStudents() throws ClassNotFoundException, IOException, ConnectionIsNullException, SQLException {
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
        return builder.toString();
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
}
