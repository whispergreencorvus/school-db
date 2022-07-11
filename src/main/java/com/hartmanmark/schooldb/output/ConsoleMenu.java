package com.hartmanmark.schooldb.output;

import java.io.IOException;
import java.sql.SQLException;
import java.util.Scanner;

import com.hartmanmark.schooldb.dao.StudentDao;
import com.hartmanmark.schooldb.dao.StudentDaoImpl;
import com.hartmanmark.schooldb.service.StudentService;
import com.hartmanmark.schooldb.validator.Validator;

public class ConsoleMenu {

    private String[] options = { "", "1 - Find all groups with less or equals student count",
            "2 - Find all students related to course with given name", "3 - Add new student",
            "4 - Delete student by STUDENT_ID", "5 - Add a student to the course (from a list)",
            "6 - Remove the student from one of his or her courses", "7 - Exit", "" };
    private String welcom = "Welcom to simple sql-jdbc-school app.";
    private String separator = String.format("%100s", "").replace(' ', '-') + "\n";
    private Validator validator = new Validator();
    private String enterFirstName = "Please enter student first name: ";
    private String enterLastName = "Please enter student last name: ";
    private StudentService studentService = new StudentService();
    private String enterCourse = "Enter the course (Chemistry, Biology, Physics, Astronomy, History, "
            + "Anthropology, Geography, Mathematics, Philosophy, Linguistics)";
    private String enterStudentIdBetween = "Please enter an student ID between 1 and ";
    private String enterStudentId = "\nPlease enter student ID: ";
    private String enterCOurseId = "\nPlease enter course ID: ";
    private StudentDao studentDao = new StudentDaoImpl(validator);
    private String studentsOnCourse = "Students on the course: ";

    public void runConsole() throws ClassNotFoundException, SQLException, IOException, NullPointerException {

        System.out.print(separator + "\n" + welcom);
        String input;
        while (true) {
            try {
                printMenu(options);
                Scanner scanner = new Scanner(System.in);
                input = scanner.nextLine();
                validator.verifyMenuChoose(input);
                if (input.equals("1")) {
                    System.out.println(studentService.printFindedGroups(studentDao.findGroups()));
                } else if (input.equals("2")) {
                    System.out.println(enterCourse);
                    String course = scanSubmenu();
                    System.out.println(studentService.printAll(studentDao.findInCourse(course)));
                    System.out.println(studentsOnCourse + studentDao.countPerCourse(course));
                } else if (input.equals("3")) {
                    System.out.println(enterFirstName + "\n");
                    String firstName = scanSubmenu();
                    System.out.println(enterLastName + "\n");
                    String lastName = scanSubmenu();
                    System.out.println(
                            studentService.printAddedToDB(studentDao.addToDB(firstName, lastName)));
                } else if (input.equals("4")) {
                    System.out.println(enterStudentIdBetween + studentDao.countNumber());
                    String studentId = scanSubmenu();
                    System.out.println(studentService.printRemovedStudent(studentDao.findStudent(studentId)));
                    studentDao.removeStudent(studentId);
                } else if (input.equals("5")) {
                    System.out.println(studentService.printAll(studentDao.createListOfStudents()) + enterStudentId);
                    String studentId = scanSubmenu();
                    System.out.println(studentService.printCourses(studentDao.createListOfCourses()) + enterCOurseId);
                    String courseId = scanSubmenu();
                    studentDao.addToTheCourse(studentId, courseId);
                    System.out.println(studentService.printAdded(studentDao.findStudent(studentId),
                            studentDao.findCourse(courseId)));
                } else if (input.equals("6")) {
                    System.out.println(studentService.printAll(studentDao.createListOfStudents()) + enterStudentId);
                    String studentId = scanSubmenu();
                    System.out.println(studentService.printCourses(studentDao.createCorsesListPerStudent(studentId)));
                    String courseId = scanSubmenu();
                    studentDao.removeFromTheCourse(studentId, courseId);
                    System.out.println(studentService.printRemoved(studentDao.findStudent(studentId),
                            studentDao.findCourse(courseId)));
                } else if (input.equals("7")) {
                    System.out.println("Exit");
                    break;
                }
            } catch (IllegalArgumentException e) {
                throw new IllegalArgumentException(e);
            }
        }
    }

    private String scanSubmenu() {
        Scanner scanner = new Scanner(System.in);
        return scanner.nextLine();
    }

    private void printMenu(String[] options) {
        for (String option : options) {
            System.out.println(option);
        }
        System.out.print("Choose your option : ");
    }
}
