package com.hartmanmark.schooldb.input;

import java.io.IOException;
import java.sql.SQLException;
import java.util.Scanner;

import com.hartmanmark.schooldb.dao.StudentDao;
import com.hartmanmark.schooldb.dao.StudentDaoImpl;
import com.hartmanmark.schooldb.service.StudentService;
import com.hartmanmark.schooldb.validator.Validator;

public class Input {

    private String enterCourse = "Enter the course (Chemistry, Biology, Physics, Astronomy, History, "
            + "Anthropology, Geography, Mathematics, Philosophy, Linguistics)";
    private String enterFirstName = "Please enter student first name: ";
    private String enterLastName = "Please enter student last name: ";
    private String enterStudentIdBetween = "Please enter an student ID between 1 and ";
    private String enterStudentId = "\nPlease enter student ID: ";
    private String enterCOurseId = "\nPlease enter course ID: ";
    private String studentsOnCourse = "Students on the course: ";
    private Validator validator = new Validator();
    private StudentDao studentDao = new StudentDaoImpl(validator);
    private StudentService studentService = new StudentService();

    public void findGroups() throws ClassNotFoundException, NullPointerException, IOException, SQLException {
        System.out.println(studentService.printFindedGroups(studentDao.findGroups()));
    }

    public void findStudentsInCourse() throws ClassNotFoundException, NullPointerException, IOException, SQLException {
        System.out.println(enterCourse);
        String course = scanSubmenu();
        System.out.println(studentService.printAll(studentDao.findInCourse(course)));
        System.out.println(studentsOnCourse + studentDao.countPerCourse(course));
    }

    public void addStudent() throws ClassNotFoundException, NullPointerException, SQLException, IOException {
        System.out.println(enterFirstName + "\n");
        String firstName = scanSubmenu();
        System.out.println(enterLastName + "\n");
        String lastName = scanSubmenu();
        System.out.println(studentService.printCreated(studentDao.create(firstName, lastName)));
    }

    public void deleteStudent() throws ClassNotFoundException, NullPointerException, SQLException, IOException {
        System.out.println(enterStudentIdBetween + studentDao.countNumber());
        String studentId = scanSubmenu();
        System.out.println(studentService.printRemovedStudent(studentDao.findStudent(studentId)));
        studentDao.removeStudent(studentId);
    }

    public void addStudentToTheCourse() throws ClassNotFoundException, NullPointerException, IOException, SQLException {
        System.out.println(studentService.printAll(studentDao.createListOfStudents()) + enterStudentId);
        String studentId = scanSubmenu();
        System.out.println(studentService.printCourses(studentDao.createListOfCourses()) + enterCOurseId);
        String courseId = scanSubmenu();
        studentDao.addToTheCourse(studentId, courseId);
        System.out
                .println(studentService.printAdded(studentDao.findStudent(studentId), studentDao.findCourse(courseId)));
    }

    public void removeStudentFromTheCourse()
            throws ClassNotFoundException, NullPointerException, SQLException, IOException {
        System.out.println(studentService.printAll(studentDao.createListOfStudents()) + enterStudentId);
        String studentId = scanSubmenu();
        System.out.println(studentService.printCourses(studentDao.createCorsesListPerStudent(studentId)));
        String courseId = scanSubmenu();
        studentDao.removeFromTheCourse(studentId, courseId);
        System.out.println(
                studentService.printRemoved(studentDao.findStudent(studentId), studentDao.findCourse(courseId)));
    }

    private String scanSubmenu() {
        return new Scanner(System.in).nextLine();
    }

}
