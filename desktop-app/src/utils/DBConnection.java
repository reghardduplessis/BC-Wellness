package utils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnection {
    private static Connection connection;

    //Edit as needed for your database
    private static final String URL = "jdbc:derby://localhost:1527/Student_WellnessDB;create=true";
    private static final String USER = "app";
    private static final String PASSWORD = "1234";

    private DBConnection() {}

    public static Connection getConnection() {
            if (connection == null) {
            try {
                connection = DriverManager.getConnection(URL, USER, PASSWORD);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return connection;
    }
}