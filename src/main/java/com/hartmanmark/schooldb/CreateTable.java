//package com.hartmanmark.schooldb;
//
//import java.sql.SQLException;
//import java.sql.Statement;
//
//public class CreateTable {
//
//    private String createTableStudents = "CREATE TABLE school.students " 
//            + "(STUDENT_ID INT PRIMARY KEY," 
//            + " GROUP_ID INT, "
//            + " FIRST_NAME TEXT, " 
//            + " LAST_NAME TEXT)";
//    private String createTableCourses = "CREATE TABLE school.courses " 
//            + "(COURSES_ID INT PRIMARY KEY,"        
//            + " COURSE_NAME TEXT, " 
//            + " COURSE_DESCRIPTION TEXT)";
//    private String createTableGroups = "CREATE TABLE school.groups " 
//            + "(GROUP_ID INT PRIMARY KEY,"        
//            + " GROUP_NAME TEXT)";
//    private String dropTableIfExists = "DROP TABLE IF EXISTS school.courses, school.students, school.groups";
//    private JDBCPostgreSQLConnection connection = new JDBCPostgreSQLConnection();
//
//    public void createTable() throws SQLException {
//        try {
//            Statement statement = connection.connect().createStatement();
//            statement.execute(dropTableIfExists);
//            statement.execute(createTableStudents);
//            statement.execute(createTableCourses);
//            statement.execute(createTableGroups);
//            System.out.println(createTableStudents);
//            System.out.println(createTableCourses);
//            System.out.println(createTableGroups);
//        } catch (SQLException e) {
//            printSQLException(e);
//        }
//    }
//
//    public static void printSQLException(SQLException exception) {
//        for (Throwable e : exception) {
//            if (e instanceof SQLException) {
//                e.printStackTrace(System.err);
//                System.err.println("SQLState: " + ((SQLException) e).getSQLState());
//                System.err.println("Error Code: " + ((SQLException) e).getErrorCode());
//                System.err.println("Message: " + e.getMessage());
//                Throwable throwable = exception.getCause();
//                while (throwable != null) {
//                    System.out.println("Cause: " + throwable);
//                    throwable = throwable.getCause();
//                }
//            }
//        }
//    }
//}
