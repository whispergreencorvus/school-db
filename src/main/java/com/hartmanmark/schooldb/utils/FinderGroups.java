package com.hartmanmark.schooldb.utils;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.Scanner;

import com.hartmanmark.schooldb.dao.Connector;
import com.hartmanmark.schooldb.exception.ConnectionIsNullException;
import com.hartmanmark.schooldb.output.ConsoleMenu;

public class FinderGroups {

    private static String query = "WITH tab AS (SELECT school.groups.group_name, COUNT(*) AS count_ "
            + "FROM school.students INNER JOIN school.groups ON school.students.group_id = school.groups.group_id "
            + "GROUP BY school.groups.group_name), cte AS (SELECT group_name,"
            + "count_, ROW_NUMBER() OVER( PARTITION BY count_ ORDER BY group_name) "
            + "AS rn FROM tab) SELECT * FROM tab WHERE count_ IN (SELECT count_ FROM cte WHERE rn = 2) "
            + "ORDER BY count_;";
    private static String exit = "For return input [exit]";

    public static String findGroups()
            throws ClassNotFoundException, SQLException, IOException, ConnectionIsNullException {
        Scanner scanner = new Scanner(System.in);
        String course = null;
        String str = null;
        while (true) {
            try {
                str = printGroups();
            } catch (IllegalArgumentException e) {
                throw new IllegalArgumentException(e);
            }
            course = scanner.nextLine();
            if (course.equalsIgnoreCase("exit")) {
                ConsoleMenu consoleMenu = new ConsoleMenu();
                consoleMenu.runConsole();
                scanner.close();
                break;
            }
        }
        return str;
    }

    private static String printGroups()
            throws ClassNotFoundException, IOException, ConnectionIsNullException, SQLException {
        StringBuilder str = new StringBuilder();
        try {
            Connection conn = Connector.getConnection();
            PreparedStatement stmt = conn.prepareStatement(query);
            ResultSet resultSet = stmt.executeQuery();
            ResultSetMetaData rsmd = resultSet.getMetaData();
            int columnsNumber = rsmd.getColumnCount();
            while (resultSet.next()) {
                for (int i = 1; i <= columnsNumber; i++) {
                    if (i > 1) {
                        str.append("    ");
                    }
                    String columnValue = resultSet.getString(i);
                    str.append(columnValue);
                }
                str.append("\n");
            }
            resultSet.close();
            str.append(exit + "\n");
        } catch (SQLException e) {
            throw new SQLException(e);
        }
        return str.toString();
    }
}
