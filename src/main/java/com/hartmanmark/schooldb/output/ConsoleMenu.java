package com.hartmanmark.schooldb.output;

import java.io.IOException;
import java.sql.SQLException;
import java.util.Scanner;

import com.hartmanmark.schooldb.dao.StudentDao;
import com.hartmanmark.schooldb.dao.StudentDaoImpl;
import com.hartmanmark.schooldb.input.Input;
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
    private Input input = new Input();

    public void runConsole() throws ClassNotFoundException, SQLException, IOException, NullPointerException {
        System.out.print(separator + "\n" + welcom);
        String inputData;
        while (true) {
            try {
                printMenu(options);
                Scanner scanner = new Scanner(System.in);
                inputData = scanner.nextLine();
                validator.verifyMenuChoose(inputData);
                if (inputData.equals("1")) {
                    input.findGroups();
                } else if (inputData.equals("2")) {
                    input.findStudentsInCourse();
                } else if (inputData.equals("3")) {
                    input.addStudent();
                } else if (inputData.equals("4")) {
                    input.deleteStudent();
                } else if (inputData.equals("5")) {
                    input.addStudentToTheCourse();
                } else if (inputData.equals("6")) {
                    input.removeStudentFromTheCourse();
                } else if (inputData.equals("7")) {
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
