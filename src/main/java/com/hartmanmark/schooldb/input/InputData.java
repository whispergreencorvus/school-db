package com.hartmanmark.schooldb.input;

import java.io.IOException;
import java.sql.SQLException;

import com.hartmanmark.schooldb.dao.StudentDao;
import com.hartmanmark.schooldb.dao.StudentDaoAdditional;
import com.hartmanmark.schooldb.service.ConsoleReader;
import com.hartmanmark.schooldb.service.StudentService;

public class InputData {

    private String enterCourse = "Enter the course (Chemistry, Biology, Physics, Astronomy, History, "
            + "Anthropology, Geography, Mathematics, Philosophy, Linguistics)";
    private String enterFirstName = "Please enter student first name: ";
    private String enterLastName = "Please enter student last name: ";
    private String enterStudentIdBetween = "Please enter an student ID between 1 and ";
    private String enterStudentId = "\nPlease enter student ID: ";
    private String enterCOurseId = "\nPlease enter course ID: ";
    private String studentsOnCourse = "Students on the course: ";
    private StudentDao studentDao;
    private StudentService studentService;
    private ConsoleReader reader;
    private StudentDaoAdditional daoAdditional;

    public InputData(StudentDao studentDaoImpl, StudentService studentService, ConsoleReader reader,
            StudentDaoAdditional daoAdditional) {
        super();
        this.studentDao = studentDaoImpl;
        this.studentService = studentService;
        this.reader = reader;
        this.daoAdditional = daoAdditional;
    }

    public String findGroups() throws ClassNotFoundException, NullPointerException, IOException, SQLException {
        return studentService.printFindedGroups(daoAdditional.findGroups()) + "\n";
    }

    public String findStudentsInCourse()
            throws ClassNotFoundException, NullPointerException, IOException, SQLException {
        System.out.println(enterCourse);
        String course = reader.read();
        return studentService.printAll(studentDao.findInCourse(course)) + "\n" + studentsOnCourse
                + daoAdditional.countPerCourse(course) + "\n";
    }

    public String addStudent() throws ClassNotFoundException, NullPointerException, SQLException, IOException {
        System.out.println(enterFirstName + "\n");
        String firstName = reader.read();
        System.out.println(enterLastName + "\n");
        String lastName = reader.read();
        return studentService.printCreated(studentDao.create(firstName, lastName)) + "\n";
    }

    public String deleteStudent() throws ClassNotFoundException, NullPointerException, SQLException, IOException {
        System.out.println(enterStudentIdBetween + daoAdditional.countNumber());
        String studentId = reader.read();
        String deletedStudent = studentService.printRemovedStudent(studentDao.findStudent(studentId)) + "\n";
        studentDao.removeStudent(studentId);
        return deletedStudent;
    }

    public String addStudentToTheCourse()
            throws ClassNotFoundException, NullPointerException, IOException, SQLException {
        System.out.println(studentService.printAll(studentDao.createListOfStudents()) + enterStudentId);
        String studentId = reader.read();
        System.out.println(studentService.printCourses(daoAdditional.createListOfCourses()) + enterCOurseId);
        String courseId = reader.read();
        studentDao.addToTheCourse(studentId, courseId);
        return studentService.printAdded(studentDao.findStudent(studentId), daoAdditional.findCourse(courseId)) + "\n";
    }

    public String removeStudentFromTheCourse()
            throws ClassNotFoundException, NullPointerException, SQLException, IOException {
        System.out.println(studentService.printAll(studentDao.createListOfStudents()) + enterStudentId);
        String studentId = reader.read();
        System.out.println(studentService.printCourses(daoAdditional.createCorsesListPerStudent(studentId)));
        String courseId = reader.read();
        studentDao.removeFromTheCourse(studentId, courseId);
        return studentService.printRemoved(studentDao.findStudent(studentId), daoAdditional.findCourse(courseId))
                + "\n";
    }
}
