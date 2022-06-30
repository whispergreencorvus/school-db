package com.hartmanmark.schooldb.utils;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

import com.hartmanmark.schooldb.dao.Connector;
import com.hartmanmark.schooldb.exception.ConnectionIsNullException;
import com.hartmanmark.schooldb.output.ConsoleMenu;

public class FinderGroups {

    private String query = "WITH tab AS (SELECT school.groups.group_name, COUNT(*) AS count_ "
            + "FROM school.students INNER JOIN school.groups ON school.students.group_id = school.groups.group_id "
            + "GROUP BY school.groups.group_name), cte AS (SELECT group_name,"
            + "count_, ROW_NUMBER() OVER( PARTITION BY count_ ORDER BY group_name) "
            + "AS rn FROM tab) SELECT * FROM tab WHERE count_ IN (SELECT count_ FROM cte WHERE rn = 2) "
            + "ORDER BY count_;";
    private String exit = "For return input [exit]";

    public String findGroups() throws ClassNotFoundException, SQLException, IOException, ConnectionIsNullException {
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

    private String printGroups() throws ClassNotFoundException, IOException, ConnectionIsNullException, SQLException {
        StringBuilder builder = new StringBuilder();
        try {
            Connection conn = Connector.getConnection();
            PreparedStatement stmt = conn.prepareStatement(query);
            ResultSet resultSet = stmt.executeQuery();
            while (resultSet.next()) {
                builder.append(resultSet.getString(1) + "   " + resultSet.getString(2) + "\n");
            }
            builder.append(exit);
            resultSet.close();
        } catch (SQLException e) {
            throw new SQLException(e);
        }
        return builder.toString();
    }
}
