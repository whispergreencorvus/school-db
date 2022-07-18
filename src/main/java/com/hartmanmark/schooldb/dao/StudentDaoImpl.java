package com.hartmanmark.schooldb.dao;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.hartmanmark.schooldb.model.Course;
import com.hartmanmark.schooldb.model.Group;
import com.hartmanmark.schooldb.model.Student;
import com.hartmanmark.schooldb.validator.Validator;

public class StudentDaoImpl implements StudentDao, StudentDaoAdditional {

    private Validator validator;
    private String courseQuery = "SELECT course_id, course_name FROM school.courses ORDER BY course_id ;";
    private String findGroupsQuery = "WITH tab AS (SELECT school.groups.group_name, COUNT(*) AS count_ "
            + "FROM  school.students "
            + "INNER JOIN school.groups ON school.students.group_id = school.groups.group_id "
            + "GROUP BY school.groups.group_name " + "ORDER BY count_ ) SELECT * FROM tab "
            + "WHERE count_ IN ( SELECT count_ FROM tab GROUP BY count_ HAVING count(*) > 1);";
    private String studentsPerGroupQuery = "SELECT school.students.student_id, school.students.first_name, school.students.last_name  "
            + "FROM school.students JOIN school.students_courses "
            + "ON school.students.student_id = school.students_courses.student_id JOIN school.courses\n"
            + "ON school.courses.course_id = school.students_courses.course_id "
            + "WHERE school.courses.course_name = '";
    private String countQuery = "SELECT count(*) FROM school.students JOIN school.students_courses\n"
            + "ON school.students.student_id = school.students_courses.student_id JOIN school.courses\n"
            + "ON school.courses.course_id = school.students_courses.course_id \n"
            + "WHERE school.courses.course_name = '";
    private String addToDBQuery = "INSERT INTO school.students (STUDENT_ID, GROUP_ID, FIRST_NAME, LAST_NAME)"
            + " VALUES (DEFAULT, NULL,?,?) RETURNING STUDENT_ID, FIRST_NAME, LAST_NAME;";
    private String orderByIdQuery = "'ORDER BY school.students.student_id;";
    private String addToCourseTheQuery = "INSERT INTO school.students_courses (id, student_id, course_id) "
            + "VALUES (DEFAULT, ? , ? );";
    private String listOfStudentQuery = "SELECT school.students.student_id, school.students.first_name, school.students.last_name "
            + "FROM school.students ORDER BY student_id ;";
    private String selectCourseQuery = "SELECT school.courses.course_id, school.courses.course_name "
            + "FROM school.courses WHERE school.courses.course_id = ";
    private String countOfStudentsQuery = "SELECT count(*) from school.students;";
    private String deleteQuery = "DELETE FROM school.students WHERE STUDENT_ID = ? RETURNING * ;";
    private String findStudentQuery = "SELECT school.students.student_id, school.students.first_name, school.students.last_name "
            + "FROM school.students WHERE school.students.student_id = ";
    private String deleteFromTheCourseQuery = "DELETE FROM school.students_courses "
            + "WHERE school.students_courses.student_id = ? AND school.students_courses.course_id = ? ;";
    private String createStudentsListPerCourseQuery = "SELECT school.students_courses.course_id, course_name\n"
            + "FROM school.students JOIN school.students_courses \n"
            + "ON  school.students_courses.student_id = school.students.student_id JOIN school.courses\n"
            + "ON school.students_courses.course_id = school.courses.course_id\n"
            + "WHERE school.students_courses.student_id = ? ORDER BY course_id;";

    public StudentDaoImpl(Validator validator) {
        this.validator = validator;
    }

    public String countNumber() throws ClassNotFoundException, SQLException, IOException, NullPointerException {
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

    public List<Student> createListOfStudents()
            throws ClassNotFoundException, IOException, NullPointerException, SQLException {
        List<Student> students = new ArrayList<>();
        try (Connection conn = Connector.getConnection();
                PreparedStatement stmt = conn.prepareStatement(listOfStudentQuery)) {
            try (ResultSet resultSet = stmt.executeQuery()) {
                while (resultSet.next()) {
                    students.add(new Student(resultSet.getString(1), resultSet.getString(2), resultSet.getString(3)));
                }
            }
        } catch (SQLException e) {
            throw new SQLException(e);
        }
        return students;
    }

    public List<Course> createListOfCourses()
            throws ClassNotFoundException, IOException, NullPointerException, SQLException {
        List<Course> courses = new ArrayList<>();
        try (Connection conn = Connector.getConnection(); PreparedStatement stmt = conn.prepareStatement(courseQuery)) {
            try (ResultSet resultSet = stmt.executeQuery()) {
                while (resultSet.next()) {
                    courses.add(new Course(resultSet.getString(1), resultSet.getString(2)));
                }
            }
        } catch (SQLException e) {
            throw new SQLException(e);
        }
        return courses;
    }

    public void addToTheCourse(String studentId, String courseId)
            throws ClassNotFoundException, IOException, NullPointerException, SQLException {
        validator.verifyInteger(studentId);
        validator.verifyInteger(courseId);
        Integer studentIdInt = Integer.parseInt(studentId);
        Integer courseIdInt = Integer.parseInt(courseId);
        try (Connection conn = Connector.getConnection();
                PreparedStatement stmt = conn.prepareStatement(addToCourseTheQuery)) {
            stmt.setInt(1, studentIdInt);
            stmt.setInt(2, courseIdInt);
            stmt.executeUpdate();
        } catch (SQLException e) {
            throw new SQLException(e);
        }
    }

    public List<Student> create(String firstName, String lastName)
            throws ClassNotFoundException, SQLException, IOException, NullPointerException {
        validator.veryfyInputString(firstName);
        validator.veryfyInputString(lastName);
        List<Student> students = new ArrayList<>();
        try (Connection conn = Connector.getConnection();
                PreparedStatement stmt = conn.prepareStatement(addToDBQuery)) {
            stmt.setString(1, firstName);
            stmt.setString(2, lastName);
            try (ResultSet resultSet = stmt.executeQuery()) {
                while (resultSet.next()) {
                    students.add(new Student(resultSet.getString(1), resultSet.getString(2), resultSet.getString(3)));
                }
            }
        } catch (SQLException e) {
            throw new SQLException(e);
        }
        return students;
    }

    public List<Group> findGroups() throws ClassNotFoundException, IOException, NullPointerException, SQLException {
        List<Group> groups = new ArrayList<>();
        try (Connection conn = Connector.getConnection();
                PreparedStatement stmt = conn.prepareStatement(findGroupsQuery)) {
            try (ResultSet resultSet = stmt.executeQuery()) {
                while (resultSet.next()) {
                    groups.add(new Group(resultSet.getString(1), resultSet.getString(2)));
                }
            }
        } catch (SQLException e) {
            throw new SQLException(e);
        }
        return groups;
    }

    public List<Student> findInCourse(String course)
            throws ClassNotFoundException, IOException, NullPointerException, SQLException {
        validator.veryfyInputString(course);
        List<Student> students = new ArrayList<>();
        try (Connection conn = Connector.getConnection();
                PreparedStatement stmt = conn.prepareStatement(studentsPerGroupQuery + course + orderByIdQuery)) {
            try (ResultSet resultSet = stmt.executeQuery()) {
                while (resultSet.next()) {
                    students.add(new Student(resultSet.getString(1), resultSet.getString(2), resultSet.getString(3)));
                }
            }
        } catch (SQLException e) {
            throw new SQLException(e);
        }
        return students;
    }

    public String countPerCourse(String course)
            throws SQLException, ClassNotFoundException, IOException, NullPointerException {
        String quantity = null;
        try (Connection conn = Connector.getConnection();
                PreparedStatement stmt = conn.prepareStatement(countQuery + course + "';")) {
            try (ResultSet resultSet = stmt.executeQuery()) {
                while (resultSet.next()) {
                    quantity = Integer.toString(resultSet.getInt(1));
                }
            }
        } catch (SQLException e) {
            throw new SQLException(e);
        }
        return quantity;
    }

    public List<Student> removeStudent(String studentId)
            throws ClassNotFoundException, SQLException, IOException, NullPointerException {
        Integer studentIdInt = Integer.parseInt(studentId);
        List<Student> students = new ArrayList<>();
        try (Connection conn = Connector.getConnection(); PreparedStatement stmt = conn.prepareStatement(deleteQuery)) {
            stmt.setInt(1, studentIdInt);
            try (ResultSet resultSet = stmt.executeQuery()) {
                while (resultSet.next()) {
                    students.add(new Student(resultSet.getString(1), resultSet.getString(3), resultSet.getString(4)));
                }
            }
        } catch (SQLException e) {
            throw new SQLException(e);
        }
        return students;
    }

    public List<Student> findStudent(String studentId)
            throws ClassNotFoundException, SQLException, IOException, NullPointerException {
        validator.veryfyRemoveOption(studentId, countNumber());
        List<Student> students = new ArrayList<>();
        try (Connection conn = Connector.getConnection();
                PreparedStatement stmt = conn.prepareStatement(findStudentQuery + studentId + ";")) {
            try (ResultSet resultSet = stmt.executeQuery()) {
                while (resultSet.next()) {
                    students.add(new Student(resultSet.getString(1), resultSet.getString(2), resultSet.getString(3)));
                }
            }
        } catch (SQLException e) {
            throw new SQLException(e);
        }
        return students;
    }

    public List<Course> findCourse(String courseId)
            throws ClassNotFoundException, IOException, NullPointerException, SQLException {
        List<Course> courses = new ArrayList<>();
        try (Connection conn = Connector.getConnection();
                PreparedStatement stmt = conn.prepareStatement(selectCourseQuery + courseId + ";")) {
            try (ResultSet resultSet = stmt.executeQuery()) {
                while (resultSet.next()) {
                    courses.add(new Course(resultSet.getString(1), resultSet.getString(2)));
                }
            }
        } catch (SQLException e) {
            throw new SQLException(e);
        }
        return courses;
    }

    public void removeFromTheCourse(String studentId, String courseId)
            throws ClassNotFoundException, SQLException, IOException, NullPointerException {
        validator.verifyInteger(courseId);
        validator.verifyInteger(studentId);
        Integer studentIdInt = Integer.parseInt(studentId);
        Integer courseIdInt = Integer.parseInt(courseId);
        try (Connection conn = Connector.getConnection();
                PreparedStatement stmt = conn.prepareStatement(deleteFromTheCourseQuery)) {
            stmt.setInt(1, studentIdInt);
            stmt.setInt(2, courseIdInt);
            stmt.executeUpdate();
        } catch (SQLException e) {
            throw new SQLException(e);
        }
    }

    public List<Course> createCorsesListPerStudent(String studentId)
            throws ClassNotFoundException, IOException, NullPointerException, SQLException {
        Integer studentIdInt = Integer.parseInt(studentId);
        List<Course> courses = new ArrayList<>();
        try (Connection conn = Connector.getConnection();
                PreparedStatement stmt = conn.prepareStatement(createStudentsListPerCourseQuery)) {
            stmt.setInt(1, studentIdInt);
            try (ResultSet resultSet = stmt.executeQuery()) {
                while (resultSet.next()) {
                    courses.add(new Course(resultSet.getString(1), resultSet.getString(2)));
                }
            }
        } catch (SQLException e) {
            throw new SQLException(e);
        }
        return courses;
    }
}
