package com.hartmanmark.schooldb.dao;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

import com.hartmanmark.schooldb.model.Course;
import com.hartmanmark.schooldb.model.Group;

public interface StudentDaoAdditional {

    public List<Course> createListOfCourses()
            throws ClassNotFoundException, IOException, NullPointerException, SQLException;

    public List<Course> findCourse(String courseId)
            throws ClassNotFoundException, IOException, NullPointerException, SQLException;

    public List<Course> createCorsesListPerStudent(String studentId)
            throws ClassNotFoundException, IOException, NullPointerException, SQLException;

    public List<Group> findGroups() throws ClassNotFoundException, IOException, NullPointerException, SQLException;

    public String countPerCourse(String course)
            throws SQLException, ClassNotFoundException, IOException, NullPointerException;

    public String countNumber() throws ClassNotFoundException, SQLException, IOException, NullPointerException;
}
