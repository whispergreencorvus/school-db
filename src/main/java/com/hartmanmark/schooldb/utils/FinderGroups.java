package com.hartmanmark.schooldb.utils;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.hartmanmark.schooldb.dao.Connector;
import com.hartmanmark.schooldb.exception.ConnectionIsNullException;

public class FinderGroups {

    private String query = "WITH tab AS (SELECT school.groups.group_name, COUNT(*) AS count_ "
            + "FROM school.students INNER JOIN school.groups ON school.students.group_id = school.groups.group_id "
            + "GROUP BY school.groups.group_name), cte AS (SELECT group_name,"
            + "count_, ROW_NUMBER() OVER( PARTITION BY count_ ORDER BY group_name) "
            + "AS rn FROM tab) SELECT * FROM tab WHERE count_ IN (SELECT count_ FROM cte WHERE rn = 2) "
            + "ORDER BY count_;";
    private String exit = "\n Groups:        Students:\n";
    private String separator = "  -----            --";

    public String findGroups() throws ClassNotFoundException, IOException, ConnectionIsNullException, SQLException {
        StringBuilder builder = new StringBuilder();
        try {
            Connection conn = Connector.getConnection();
            PreparedStatement stmt = conn.prepareStatement(query);
            ResultSet resultSet = stmt.executeQuery();
            builder.append(exit);
            while (resultSet.next()) {
                builder.append("  " + resultSet.getString(1) + "            " + resultSet.getString(2) + "\n");
            }
            builder.append(separator);
            resultSet.close();
        } catch (SQLException e) {
            throw new SQLException(e);
        }
        return builder.toString();
    }
}
