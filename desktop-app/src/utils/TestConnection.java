package utils;

import java.sql.Connection;

public class TestConnection {
    public static void main(String[] args) {
        Connection conn = DBConnection.getConnection();
        if (conn != null) {
            System.out.println("Connected to Derby successfully!");
        } else {
            System.out.println("Failed to connect.");
        }
    }
}
