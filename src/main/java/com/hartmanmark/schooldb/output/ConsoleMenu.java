package com.hartmanmark.schooldb.output;

import java.io.IOException;
import java.sql.SQLException;
import java.util.Scanner;

import com.hartmanmark.schooldb.dao.AdderStudentsToDataBase;
import com.hartmanmark.schooldb.dao.AdderStudentsToTheCourse;
import com.hartmanmark.schooldb.dao.FinderGroups;
import com.hartmanmark.schooldb.dao.FinderStudents;
import com.hartmanmark.schooldb.dao.RemoverStudentFromTheCourse;
import com.hartmanmark.schooldb.dao.RemoverStudentsFromDataBase;
import com.hartmanmark.schooldb.exception.ConnectionIsNullException;
import com.hartmanmark.schooldb.validator.Validator;

public class ConsoleMenu {

    private String input;
    private final String[] options = { "", "1 - Find all groups with less or equals student count",
            "2 - Find all students related to course with given name", "3 - Add new student",
            "4 - Delete student by STUDENT_ID", "5 - Add a student to the course (from a list)",
            "6 - Remove the student from one of his or her courses", "7 - Exit", "" };
    private final String welcom = "Welcom to simple sql-jdbc-school app.";
    private final String separator = String.format("%100s", "").replace(' ', '-') + "\n";
    private Validator validator = new Validator();
    private FinderGroups finderGroups = new FinderGroups();
    private FinderStudents finderStudents = new FinderStudents(validator);
    private AdderStudentsToDataBase adderStudentsToDataBase = new AdderStudentsToDataBase(validator);
    private AdderStudentsToTheCourse adderStudentsToTheCourse = new AdderStudentsToTheCourse(validator);
    private RemoverStudentsFromDataBase removerStudentsFromDataBase = new RemoverStudentsFromDataBase(validator);
    private RemoverStudentFromTheCourse removerStudentFromTheCourse = new RemoverStudentFromTheCourse(validator);

    public void runConsole() throws ClassNotFoundException, SQLException, IOException, ConnectionIsNullException {
        System.out.print(separator + "\n" + welcom);
        while (true) {
            try {
                printMenu(options);
                Scanner scanner = new Scanner(System.in);
                input = scanner.nextLine();
                validator.verifyMenuChoose(input);
                if (input.equals("1")) {
                    System.out.println(finderGroups.findGroups());
                } else if (input.equals("2")) {
                    System.out.println(finderStudents.printStudents());
                    System.out.println(finderStudents.chooseCourse(scanSubmenu()));
                } else if (input.equals("3")) {
                    System.out.println(adderStudentsToDataBase.printFirstName());
                    System.out.println(adderStudentsToDataBase.enterFirstName(scanSubmenu()));
                    System.out.println(adderStudentsToDataBase.enterLastName(scanSubmenu()));
                } else if (input.equals("4")) {
                    System.out.println(removerStudentsFromDataBase.printNamberOfStudents());
                    System.out.println(removerStudentsFromDataBase.chooseStudentId(scanSubmenu()));
                } else if (input.equals("5")) {
                    System.out.println(adderStudentsToTheCourse.printStudents());
                    System.out.println(adderStudentsToTheCourse.printCoursesId(scanSubmenu()));
                    System.out.println(adderStudentsToTheCourse.chooseCourseId(scanSubmenu()));
                } else if (input.equals("6")) {
                    System.out.println(removerStudentFromTheCourse.printStudents());
                    System.out.println(removerStudentFromTheCourse.chooseStudentId(scanSubmenu()));
                    System.out.println(removerStudentFromTheCourse.chooseCourseId(scanSubmenu()));
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
        String inputInOption = scanner.nextLine();
        return inputInOption;
    }
    
    private void printMenu(String[] options) {
        for (String option : options) {
            System.out.println(option);
        }
        System.out.print("Choose your option : ");
    }
}
