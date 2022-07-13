package com.hartmanmark.schooldb.service;

import java.util.List;

import com.hartmanmark.schooldb.model.Course;
import com.hartmanmark.schooldb.model.Group;
import com.hartmanmark.schooldb.model.Student;

public class StudentService {

    private String creadedStudent = "A new student has been successfully added: \nID ";
    private String title = "\n Groups:        Students:\n";
    private String separatorGroups = "  -----            --";
    private String studentColumns = "ID        Last names:       First names:\n";
    private String courseColumns = "ID        Course names:\n";
    private String separatorStudents = String.format("%40s", "").replace(' ', '-');
    private String separatorCourses = String.format("%23s", "").replace(' ', '-');
    private String lastName = "\nLast name: ";
    private String firstName = "]\nFirst name: ";
    private String studentId = "Student by ID [";

    public String printCreated(List<Student> student) {
        return "\n" + creadedStudent + "[" + student.get(0).getStudentId() + firstName + student.get(0).getFirstName()
                + lastName + student.get(0).getLastName();
    }

    public String printFindedGroups(List<Group> groups) {
        StringBuilder result = new StringBuilder();
        result.append(title);
        for (Group group : groups) {
            result.append("  " + group.getGroupName() + "            " + group.getNamberOfStudents() + "\n");
        }
        return result.append(separatorGroups).toString();
    }

    public String printAdded(List<Student> student, List<Course> course) {
        return studentId + student.get(0).getStudentId() + firstName + student.get(0).getFirstName() + lastName
                + student.get(0).getLastName() + "\nwas succesefully added to the course: "
                + course.get(0).getCourseName();
    }

    public String printRemoved(List<Student> student, List<Course> course) {
        return studentId + student.get(0).getStudentId() + firstName + student.get(0).getFirstName() + lastName
                + student.get(0).getLastName() + "\nwas succesefully removed from the course: "
                + course.get(0).getCourseName();
    }

    public String printRemovedStudent(List<Student> student) {
        return studentId + student.get(0).getStudentId() + firstName + student.get(0).getFirstName() + lastName
                + student.get(0).getLastName() + "\nwas succesefully removed.";
    }

    public String printCourses(List<Course> courses) {
        StringBuilder result = new StringBuilder();
        result.append(courseColumns + separatorCourses + "\n");
        for (Course course : courses) {
            result.append(course.getCourseId() + "   |     " + course.getCourseName() + "\n");
        }
        return result.append(separatorCourses).toString();
    }

    public String printAll(List<Student> students) {
        StringBuilder result = new StringBuilder();
        result.append(studentColumns + separatorStudents + "\n");
        for (Student student : students) {
            result.append(student.getStudentId() + "   |      " + student.getLastName() + "  |   "
                    + student.getFirstName() + "\n");
        }
        return result.append(separatorStudents).toString();
    }
}
