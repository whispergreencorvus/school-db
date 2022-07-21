package com.hartmanmark.schooldb.dao;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

import com.hartmanmark.schooldb.model.Student;

public interface StudentDao {

    public List<Student> findInCourse(String course)
            throws ClassNotFoundException, IOException, NullPointerException, SQLException;

    public String removeFromTheCourse(String studentId, String courseId)
            throws ClassNotFoundException, SQLException, IOException, NullPointerException;

    public List<Student> removeStudent(String studentId)
            throws ClassNotFoundException, SQLException, IOException, NullPointerException;

    public String create(String firstName, String lastName)
            throws ClassNotFoundException, SQLException, IOException, NullPointerException;

    public List<Student> findStudent(String studentId)
            throws ClassNotFoundException, SQLException, IOException, NullPointerException;

    public String addToTheCourse(String firstName, String lastName)
            throws ClassNotFoundException, IOException, NullPointerException, SQLException;

    public List<Student> createListOfStudents()
            throws ClassNotFoundException, IOException, NullPointerException, SQLException;
}
