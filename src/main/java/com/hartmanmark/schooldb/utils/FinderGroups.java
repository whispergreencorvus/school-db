package com.hartmanmark.schooldb.utils;

import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Scanner;

import com.hartmanmark.schooldb.dao.Connector;
import com.hartmanmark.schooldb.exception.ConnectionIsNullException;
import com.hartmanmark.schooldb.output.ConsoleMenu;

public class FinderGroups {

    private static String query = "SELECT school.groups.group_name, COUNT (school.groups.group_name)\n"
            + "FROM school.students\n" + "JOIN school.groups\n"
            + "ON school.students.group_id = school.groups.group_id \n" + "GROUP BY school.groups.group_name;";
    private static String nameOfColumns = "Groups:          Count:";
    private static String separator =     "-----            --";

    public static void findGroups()
            throws ClassNotFoundException, SQLException, IOException, ConnectionIsNullException {
        Scanner scanner = new Scanner(System.in);
        String course = null;
        while (true) {
            try {
                printGroups();
            } catch (IllegalArgumentException e) {
                System.err.println(e.getMessage());
            }
            course = scanner.nextLine();
            if (course.equalsIgnoreCase("exit")) {
                ConsoleMenu consoleMenu = new ConsoleMenu();
                consoleMenu.runConsole();
                scanner.close();
                break;
            }
        }
    }

    private static void printGroups()
            throws ClassNotFoundException, IOException, ConnectionIsNullException, SQLException {
        try (Connection conn = Connector.getConnection(); Statement stmt = conn.createStatement();) {
            ResultSet resultSet = stmt.executeQuery(query);
            ResultSetMetaData rsmd = resultSet.getMetaData();
            int columnsNumber = rsmd.getColumnCount();
            System.out.println(nameOfColumns + "\n" + separator);
            while (resultSet.next()) {
                for (int i = 1; i <= columnsNumber; i++) {
                    if (i > 1) {
                        System.out.print("            ");
                    }
                    String columnValue = resultSet.getString(i);
                    System.out.print(columnValue);
                }
                System.out.println("");
            }
            resultSet.close();
        } catch (SQLException e) {
            throw new SQLException(e);
        }
    }

}
