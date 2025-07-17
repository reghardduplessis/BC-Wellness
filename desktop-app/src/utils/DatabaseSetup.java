package utils;

import java.sql.*;
import utils.DBConnection; // adjust package name as needed

public class DatabaseSetup {

    public static void ensureTablesExist() throws SQLException {
        Connection conn = DBConnection.getConnection();

        createUsersTableIfMissing(conn);
        createFeedbackTableIfMissing(conn);
        createCounselorsTableIfMissing(conn);
        createAppointmentsTableIfMissing(conn);
    }

    private static boolean tableExists(Connection conn, String tableName) throws SQLException {
        DatabaseMetaData meta = conn.getMetaData();
        try (ResultSet rs = meta.getTables(null, null, tableName.toUpperCase(), null)) {
            return rs.next();
        }
    }

    private static void createUsersTableIfMissing(Connection conn) throws SQLException {
        if (!tableExists(conn, "users")) {
            String sql = "CREATE TABLE users (" +
                    "student_number VARCHAR(10) PRIMARY KEY, " +
                    "email VARCHAR(100), " +
                    "password VARCHAR(100), " +
                    "name VARCHAR(50), " +
                    "surname VARCHAR(50), " +
                    "created_at TIMESTAMP)";
            try (Statement stmt = conn.createStatement()) {
                stmt.executeUpdate(sql);
                System.out.println("Users table created.");
            }
        } else {
            System.out.println("Users table already exists.");
        }
    }

    private static void createFeedbackTableIfMissing(Connection conn) throws SQLException {
        if (!tableExists(conn, "feedback")) {
            String sql = "CREATE TABLE Feedback (" +
                    "id INT PRIMARY KEY GENERATED ALWAYS AS IDENTITY, " +
                    "student VARCHAR(255) NOT NULL, " +
                    "rating INT NOT NULL, " +
                    "comments VARCHAR(500))";
            try (Statement stmt = conn.createStatement()) {
                stmt.executeUpdate(sql);
                System.out.println("Feedback table created.");
            }
        } else {
            System.out.println("Feedback table already exists.");
        }
    }

    private static void createCounselorsTableIfMissing(Connection conn) throws SQLException {
        if (!tableExists(conn, "counselors")) {
            String sql = "CREATE TABLE Counselors (" +
                    "id INT PRIMARY KEY GENERATED ALWAYS AS IDENTITY, " +
                    "name VARCHAR(100), " +
                    "specialization VARCHAR(100), " +
                    "availability VARCHAR(100))";
            try (Statement stmt = conn.createStatement()) {
                stmt.executeUpdate(sql);
                System.out.println("Counselors table created.");
            }
        } else {
            System.out.println("Counselors table already exists.");
        }
    }

    private static void createAppointmentsTableIfMissing(Connection conn) throws SQLException {
        if (!tableExists(conn, "appointments")) {
            String sql = "CREATE TABLE Appointments (" +
                    "id INT PRIMARY KEY GENERATED ALWAYS AS IDENTITY, " +
                    "student VARCHAR(100), " +
                    "counselor VARCHAR(100), " +
                    "date VARCHAR(20), " +
                    "time VARCHAR(20), " +
                    "status VARCHAR(20))";
            try (Statement stmt = conn.createStatement()) {
                stmt.executeUpdate(sql);
                System.out.println("Appointments table created.");
            }
        } else {
            System.out.println("Appointments table already exists.");
        }
    }

    public static void main(String[] args) {
        try {
            ensureTablesExist();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}