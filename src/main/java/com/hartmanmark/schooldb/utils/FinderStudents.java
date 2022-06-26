package com.hartmanmark.schooldb.utils;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Scanner;

import com.hartmanmark.schooldb.dao.Connector;
import com.hartmanmark.schooldb.exception.ConnectionIsNullException;
import com.hartmanmark.schooldb.output.ConsoleMenu;
import com.hartmanmark.schooldb.validator.Validator;

public class FinderStudents {

    private static int numberOfStudents;
    private static String nameOfColumns = "Last names:          First names:";
    private static String separator = "-----------          -----------";
    private static String exit = "For return input [exit]: ";
    private static String enterCourse = "Enter the course (Chemistry, Biology, Physics, Astronomy, History, "
            + "Anthropology, Geography, Mathematics, Philosophy, Linguistics)";
    private static String studentsPerGroup = "SELECT school.students.last_name, school.students.first_name\n"
            + "FROM school.students\n" + "JOIN school.students_courses\n"
            + "ON school.students.student_id = school.students_courses.student_id \n" + "JOIN school.courses\n"
            + "ON school.courses.course_id = school.students_courses.course_id \n"
            + "WHERE school.courses.course_name = '";
    private static String countQuery = "SELECT count(*)\n" + "FROM school.students\n" + "JOIN school.students_courses\n"
            + "ON school.students.student_id = school.students_courses.student_id \n" + "JOIN school.courses\n"
            + "ON school.courses.course_id = school.students_courses.course_id \n"
            + "WHERE school.courses.course_name = '";

    public static void findStudents() throws ClassNotFoundException, SQLException, IOException, ConnectionIsNullException {
        Scanner scanner = new Scanner(System.in);
        String course = null;
        while (true) {
            System.out.println(enterCourse + "\n" + exit);
            course = scanner.nextLine();

            if (course.equalsIgnoreCase("exit")) {
                ConsoleMenu consoleMenu = new ConsoleMenu();
                consoleMenu.runConsole();
                scanner.close();
                break;
            }
            try {
                printStudents(course);
            } catch (IllegalArgumentException e) {
                System.err.println(e.getMessage());
            }
        }
    }

    private static void printStudents(String course)
            throws ClassNotFoundException, IOException, ConnectionIsNullException, SQLException {
        Validator.veryfyInputString(course);
        try (Connection conn = Connector.getConnection(); Statement stmt = conn.createStatement();) {
            String insertQuery = studentsPerGroup + course + "'ORDER BY school.students.last_name;";
            ResultSet resultSet = stmt.executeQuery(insertQuery);
            ResultSetMetaData rsmd = resultSet.getMetaData();
            int columnsNumber = rsmd.getColumnCount();
            countOfStudents(course);
            System.out.println(nameOfColumns + "\n" + separator);
            while (resultSet.next()) {
                for (int i = 1; i <= columnsNumber; i++) {
                    if (i > 1) {
                        System.out.print(" ");
                    }
                    String columnValue = resultSet.getString(i);
                    System.out.print(columnValue);
                }
                System.out.println("");
            }
            System.out.println(separator + "\n" + "Students on the course: " + numberOfStudents);
            resultSet.close();
        } catch (SQLException e) {
            throw new SQLException(e);
        }
    }

    private static void countOfStudents(String course)
            throws SQLException, ClassNotFoundException, IOException, ConnectionIsNullException {
        Integer numberOfStudents = null;
        PreparedStatement statement = Connector.getConnection().prepareStatement(countQuery + course + "';");
        ResultSet resultSet = statement.executeQuery();
        while (resultSet.next()) {
            numberOfStudents = Integer.valueOf(resultSet.getString("count"));
        }
        setNumberOfStudents(numberOfStudents);
    }

    public static void setNumberOfStudents(int numberOfStudents) {
        FinderStudents.numberOfStudents = numberOfStudents;
    }
}
