package com.hartmanmark.schooldb.output;

import java.io.IOException;
import java.sql.SQLException;

import com.hartmanmark.schooldb.dao.StudentDao;
import com.hartmanmark.schooldb.dao.StudentDaoAdditional;
import com.hartmanmark.schooldb.dao.StudentDaoImpl;
import com.hartmanmark.schooldb.input.InputData;
import com.hartmanmark.schooldb.service.ConsoleReader;
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
    private StudentDao studentDao = new StudentDaoImpl(validator);
    private StudentDaoAdditional daoAdditional = new StudentDaoImpl(validator);
    private StudentService studentService = new StudentService();
    private ConsoleReader reader = new ConsoleReader();
    private InputData inputData = new InputData(studentDao, studentService, reader, daoAdditional);

    public void runConsole() throws ClassNotFoundException, SQLException, IOException, NullPointerException {
        System.out.print(separator + "\n" + welcom);
        String number = null;
        while (true) {
            try {
                printMenu(options);
                number = reader.read();
                validator.verifyMenuChoose(number);
                if (number.equals("1")) {
                    System.out.print(inputData.findGroups());
                } else if (number.equals("2")) {
                    System.out.print(inputData.findStudentsInCourse());
                } else if (number.equals("3")) {
                    System.out.print(inputData.addStudent());
                } else if (number.equals("4")) {
                    System.out.print(inputData.deleteStudent());
                } else if (number.equals("5")) {
                    System.out.print(inputData.addStudentToTheCourse());
                } else if (number.equals("6")) {
                    System.out.print(inputData.removeStudentFromTheCourse());
                } else if (number.equals("7")) {
                    System.out.println("Exit");
                    break;
                }
            } catch (IllegalArgumentException e) {
                throw new IllegalArgumentException(e);
            }
        }
    }

    private void printMenu(String[] options) {
        for (String option : options) {
            System.out.println(option);
        }
        System.out.print("Choose your option : ");
    }
}
