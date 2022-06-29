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

    private static String query = "WITH tab AS (\n" + "    SELECT school.groups.group_name, \n"
            + "           COUNT(*) AS count_\n" + "    FROM       school.students\n" + "    INNER JOIN school.groups \n"
            + "            ON school.students.group_id = school.groups.group_id \n"
            + "    GROUP BY school.groups.group_name\n" + "), cte AS (\n" + "    SELECT group_name,\n"
            + "           count_,\n" + "           ROW_NUMBER() OVER(\n" + "               PARTITION BY count_ \n"
            + "               ORDER     BY group_name\n" + "           ) AS rn\n" + "    FROM tab\n" + ")\n"
            + "SELECT * \n" + "FROM tab \n" + "WHERE count_ IN (SELECT count_ FROM cte WHERE rn = 2)"
                    + "ORDER BY count_;";
    private static String exit = "For return input [exit]";

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
            System.out.println(exit);
        } catch (SQLException e) {
            throw new SQLException(e);
        }
    }
}
