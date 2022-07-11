package com.hartmanmark.schooldb.dao;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

import com.hartmanmark.schooldb.model.Course;
import com.hartmanmark.schooldb.model.Group;
import com.hartmanmark.schooldb.model.Student;

public interface StudentDao {

    public List<Group> findGroups() throws ClassNotFoundException, IOException, NullPointerException, SQLException;

    public List<Student> findInCourse(String course)
            throws ClassNotFoundException, IOException, NullPointerException, SQLException;

    public String countNumber() throws ClassNotFoundException, SQLException, IOException, NullPointerException;

    public void removeFromTheCourse(String studentId, String courseId)
            throws ClassNotFoundException, SQLException, IOException, NullPointerException;

    public void removeStudent(String studentId)
            throws ClassNotFoundException, SQLException, IOException, NullPointerException;

    public List<Student> addToDB(String firstName, String lastName)
            throws ClassNotFoundException, SQLException, IOException, NullPointerException;

    public List<Student> findStudent(String studentId)
            throws ClassNotFoundException, SQLException, IOException, NullPointerException;

    public String countPerCourse(String course)
            throws SQLException, ClassNotFoundException, IOException, NullPointerException;

    public void addToTheCourse(String firstName, String lastName)
            throws ClassNotFoundException, IOException, NullPointerException, SQLException;

    public List<Course> findCourse(String courseId)
            throws ClassNotFoundException, IOException, NullPointerException, SQLException;

    public List<Course> createListOfCourses()
            throws ClassNotFoundException, IOException, NullPointerException, SQLException;

    public List<Course> createCorsesListPerStudent(String studentId)
            throws ClassNotFoundException, IOException, NullPointerException, SQLException;

    public List<Student> createListOfStudents()
            throws ClassNotFoundException, IOException, NullPointerException, SQLException;
}
