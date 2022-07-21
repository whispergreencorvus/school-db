package com.hartmanmark.schooldb;

import static org.junit.Assert.assertThrows;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
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
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;

import com.hartmanmark.schooldb.dao.Connector;
import com.hartmanmark.schooldb.dao.StudentDaoImpl;
import com.hartmanmark.schooldb.service.ReaderPropertiesFile;
import com.hartmanmark.schooldb.service.StudentService;
import com.hartmanmark.schooldb.utils.DataBaseCreator;
import com.hartmanmark.schooldb.validator.Validator;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)

class StudentDaoImplTest {

    @ExtendWith(MockitoExtension.class)

    @Mock
    Connector connector;

    @Spy
    Validator validator;

    @Mock
    ReaderPropertiesFile reader;

    @InjectMocks
    StudentDaoImpl studentDaoImpl;

    @InjectMocks
    StudentService studentService;

    @BeforeAll
    void createDB() throws NullPointerException, SQLException, IOException {
        DataBaseCreator creator = new DataBaseCreator();
        creator.createDataBase();
    }

    private String findStudentIdInCourse(String id) throws SQLException, IOException {
        String studentId = null;
        try (Connection conn = Connector.getConnection();
                PreparedStatement stmt = conn.prepareStatement(
                        "SELECT school.students_courses.id FROM school.students_courses WHERE school.students_courses.id = ?")) {
            int idAsInt = Integer.parseInt(id);
            stmt.setInt(1, idAsInt);
            try (ResultSet resultSet = stmt.executeQuery()) {
                while (resultSet.next()) {
                    studentId = resultSet.getString(1);
                }
            }
        } catch (SQLException e) {
            throw new SQLException(e);
        }
        return studentId;
    }

    private String findStudent(String id) throws SQLException, IOException {
        String studentId = null;
        try (Connection conn = Connector.getConnection();
                PreparedStatement stmt = conn.prepareStatement(
                        "SELECT school.students.student_id FROM school.students WHERE school.students.student_id = ?")) {
            int idAsInt = Integer.parseInt(id);
            stmt.setInt(1, idAsInt);
            try (ResultSet resultSet = stmt.executeQuery()) {
                while (resultSet.next()) {
                    studentId = resultSet.getString(1);
                }
            }
        } catch (SQLException e) {
            throw new SQLException(e);
        }
        return studentId;
    }

    @Test
    void addToTheCourse_getIllegalArgumentException_whenInputDataIsNull()
            throws ClassNotFoundException, NullPointerException, IOException, SQLException {
        assertThrows(IllegalArgumentException.class, () -> {
            studentDaoImpl.addToTheCourse(null, null);
        });
    }

    @Test
    void addToTheCourse_getNotNullIsTrue_whenUserInputStudentIdAndCourseId()
            throws ClassNotFoundException, NullPointerException, IOException, SQLException {
        assertNotNull(studentDaoImpl.addToTheCourse("35", "4"));
    }

    @Test
    void addToTheCourse_shouldFindStudentFromTheCourse_whenUserAddStudentToTheCourse()
            throws ClassNotFoundException, NullPointerException, IOException, SQLException {
        String createdId = studentDaoImpl.addToTheCourse("35", "4");
        assertEquals(createdId, findStudentIdInCourse(createdId));
    }

    @Test
    void create_getIllegalArgumentException_whenInputDataIsNull()
            throws ClassNotFoundException, NullPointerException, IOException, SQLException {
        assertThrows(IllegalArgumentException.class, () -> {
            studentDaoImpl.create(null, null);
        });
    }

    @Test
    void create_shouldFindStudentId_whenUserCreateStudent()
            throws ClassNotFoundException, NullPointerException, IOException, SQLException {
        String firstName = "Thomas";
        String lastName = "Lawrence";
        int studentId = Integer.parseInt(studentDaoImpl.create(firstName, lastName));
        int findedStudentId = 0;
        try (Connection conn = Connector.getConnection();
                PreparedStatement stmt = conn.prepareStatement(
                        "SELECT school.students.student_id FROM school.students WHERE school.students.student_id = ? ;")) {
            stmt.setInt(1, studentId);
            try (ResultSet resultSet = stmt.executeQuery()) {
                while (resultSet.next()) {
                    findedStudentId = resultSet.getInt(1);
                }
            }
        } catch (SQLException e) {
            throw new SQLException(e);
        }
        assertEquals(studentId, findedStudentId);
    }

    @Test
    void findInCourse_getIllegalArgumentException_whenInputDataIsNull() {
        assertThrows(IllegalArgumentException.class, () -> {
            studentDaoImpl.findInCourse(null);
        });
    }

    @Test
    void findStudent_getIllegalArgumentException_whenInputDataIsNull() {
        assertThrows(IllegalArgumentException.class, () -> {
            studentDaoImpl.findStudent(null);
        });
    }

    @Test
    void findCourse_getIllegalArgumentException_whenInputDataIsNull() {
        assertThrows(IllegalArgumentException.class, () -> {
            studentDaoImpl.findCourse(null);
        });
    }

    @Test
    void removeFromTheCourse_getIllegalArgumentException_whenInputDataIsNull()
            throws ClassNotFoundException, NullPointerException, IOException, SQLException {
        assertThrows(IllegalArgumentException.class, () -> {
            studentDaoImpl.removeFromTheCourse(null, null);
        });
    }

    @Test
    void removeFromTheCourse_getNull_whenUserDeleteStudentFromTheCourse()
            throws ClassNotFoundException, NullPointerException, IOException, SQLException {
        studentDaoImpl.addToTheCourse("35", "4");
        assertNull(findStudentIdInCourse(studentDaoImpl.removeFromTheCourse("35", "4")));
    }

    @Test
    void removeStudent_shouldDeleteStudent_whenUserInputStudentId()
            throws ClassNotFoundException, NullPointerException, IOException, SQLException {
        String studentId = "25";
        studentDaoImpl.removeStudent(studentId);
        assertNull(findStudent(studentId));
    }
}
