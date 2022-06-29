package com.hartmanmark.schooldb.output;

import java.io.IOException;
import java.sql.SQLException;
import java.util.Scanner;

import com.hartmanmark.schooldb.exception.ConnectionIsNullException;
import com.hartmanmark.schooldb.utils.AdderStudentsToDataBase;
import com.hartmanmark.schooldb.utils.AdderStudentsToTheCourse;
import com.hartmanmark.schooldb.utils.FinderGroups;
import com.hartmanmark.schooldb.utils.FinderStudents;
import com.hartmanmark.schooldb.utils.RemoverStudentsFromDataBase;
import com.hartmanmark.schooldb.utils.RemoverStudentFromTheCourse;
import com.hartmanmark.schooldb.validator.Validator;

public class ConsoleMenu {

    private String input;
    private final String[] options = { "", "1 - Find all groups with less or equals student count",
            "2 - Find all students related to course with given name", "3 - Add new student",
            "4 - Delete student by STUDENT_ID", "5 - Add a student to the course (from a list)",
            "6 - Remove the student from one of his or her courses", "7 - Exit", "" };
    private final String welcom = "Welcom to simple sql-jdbc-school app.";
    private final String redLine = String.format("%100s", "").replace(' ', '-') + "\n";

    public void runConsole() throws ClassNotFoundException, SQLException, IOException, ConnectionIsNullException {
        System.err.println(redLine);
        System.out.print(welcom);
        while (true) {
            try {
                printMenu(options);
                Scanner scanner = new Scanner(System.in);
                input = scanner.nextLine();
                Validator.verifyMenuChoose(input);
                if (input.equals("1")) {
                    FinderGroups.findGroups();
                    break;
                } else if (input.equals("2")) {
                    FinderStudents.findStudents();
                    break;
                } else if (input.equals("3")) {
                    AdderStudentsToDataBase.add();
                    break;
                } else if (input.equals("4")) {
                    RemoverStudentsFromDataBase.remove();
                    break;
                } else if (input.equals("5")) {
                    AdderStudentsToTheCourse.add();
                    break;
                } else if (input.equals("6")) {
                    RemoverStudentFromTheCourse.remove();
                    break;
                } else if (input.equals("7")) {
                    System.err.println("Exit");
                    break;
                }
                scanner.close();
            } catch (IllegalArgumentException e) {
                System.err.println(e.getMessage());
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
