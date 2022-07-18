package com.hartmanmark.schooldb;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.hartmanmark.schooldb.dao.Connector;
import com.hartmanmark.schooldb.dao.StudentDaoImpl;
import com.hartmanmark.schooldb.service.ReaderPropertiesFile;
import com.hartmanmark.schooldb.utils.DataBaseCreator;
import com.hartmanmark.schooldb.validator.Validator;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)

class StudentDaoImplTest {

    @ExtendWith(MockitoExtension.class)

    @Mock
    Connector connector;

    @Mock
    Validator validator;

    @Mock
    ReaderPropertiesFile reader;

    @InjectMocks
    StudentDaoImpl studentDaoImpl;

    @BeforeAll
    void createDB() throws NullPointerException, SQLException, IOException {
        DataBaseCreator creator = new DataBaseCreator();
        creator.createDataBase();
    }

    private int count(String query) throws SQLException, IOException {
        int number = 0;
        try (Connection conn = Connector.getConnection(); PreparedStatement stmt = conn.prepareStatement(query)) {
            try (ResultSet resultSet = stmt.executeQuery()) {
                while (resultSet.next()) {
                    number = resultSet.getInt(1);
                }
            }
        } catch (SQLException e) {
            throw new SQLException(e);
        }
        return number;
    }

    @Test
    void addToTheCourse_shouldAddStudentToTheCourse_whenUserInputStudentIdAndCourseId() throws ClassNotFoundException, NullPointerException, IOException, SQLException {
        String studentId = "35";
        String courseId = "4";
        studentDaoImpl.addToTheCourse(studentId, courseId);
        List<String> result = new ArrayList<>();
        try (Connection conn = Connector.getConnection();
                PreparedStatement stmt = conn.prepareStatement(
                        "SELECT school.students_courses.course_id, school.students_courses.student_id "
                                + "FROM school.students_courses  WHERE school.students_courses.course_id = ? "
                                + "AND school.students_courses.student_id = ?;")) {
            int courseIdInt = Integer.parseInt(courseId);
            int studentIdInt = Integer.parseInt(studentId);
            stmt.setInt(1, courseIdInt);
            stmt.setInt(2, studentIdInt);
            try (ResultSet resultSet = stmt.executeQuery()) {
                while (resultSet.next()) {
                    result.add(0, resultSet.getString(1));
                    result.add(1, resultSet.getString(2));
                }
            }
        } catch (SQLException e) {
            throw new SQLException(e);
        }
        assertEquals(courseId, result.get(0));
        assertEquals(studentId, result.get(1));
    }

    @Test
    void removeFromTheCourse_shouldDeleteStudentFromTheCourse_whenUserInputStudentIdAndCourseId()
            throws ClassNotFoundException, NullPointerException, IOException, SQLException {
        String studentId = "35";
        String courseId = "4";
        String query = "SELECT count(*) from school.students_courses;";
        int numberOfStudentsBeforeDelete = count(query);
        studentDaoImpl.removeFromTheCourse(studentId, courseId);
        int numberOfStudentsAfterDelete = count(query);
        assertEquals(numberOfStudentsBeforeDelete - 1, numberOfStudentsAfterDelete);
    }

    @Test
    void create_shouldCreateStudent_whenUserInputFirstNameAndLastName() throws ClassNotFoundException, NullPointerException, IOException, SQLException {
        String firstName = "Thomas     ";
        String lastName = "Lawrence   ";
        studentDaoImpl.create(firstName, lastName);
        List<String> result = new ArrayList<>();
        try (Connection conn = Connector.getConnection();
                PreparedStatement stmt = conn
                        .prepareStatement("SELECT school.students.first_name, school.students.last_name "
                                + "FROM school.students  WHERE school.students.first_name = ? "
                                + "AND school.students.last_name = ?;")) {
            stmt.setString(1, firstName);
            stmt.setString(2, lastName);
            try (ResultSet resultSet = stmt.executeQuery()) {
                while (resultSet.next()) {
                    result.add(0, resultSet.getString(1));
                    result.add(1, resultSet.getString(2));
                }
            }
        } catch (SQLException e) {
            throw new SQLException(e);
        }
        assertEquals(firstName, result.get(0));
        assertEquals(lastName, result.get(1));
    }

    @Test
    void removeStudent_shouldDeleteStudent_whenUserInputStudentId() throws ClassNotFoundException, NullPointerException, IOException, SQLException {
        String studentId = "25";
        String query = "SELECT count(*) from school.students;";
        int numberOfStudentsBeforeDelete = count(query);
        studentDaoImpl.removeStudent(studentId);
        int numberOfStudentsAfterDelete = count(query);
        assertEquals(numberOfStudentsBeforeDelete - 1, numberOfStudentsAfterDelete);
    }
}
