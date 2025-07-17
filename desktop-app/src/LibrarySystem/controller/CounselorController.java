package LibrarySystem.controller;

import LibrarySystem.model.Counselor;
import utils.DBConnection;

import java.sql.*;
import java.util.ArrayList;

public class CounselorController {

    public static void addCounselor(Counselor c) {
        try {
            Connection conn = DBConnection.getConnection();
            String sql = "INSERT INTO Counselors (name, specialization, availability) VALUES (?, ?, ?)";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, c.getName());
            ps.setString(2, c.getSpecialization());
            ps.setString(3, c.getAvailability());
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static ArrayList<Counselor> getAllCounselors() {
        ArrayList<Counselor> list = new ArrayList<>();
        try (Connection conn = DBConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery("SELECT * FROM Counselors")) {

            while (rs.next()) {
                list.add(new Counselor(
                        rs.getInt("id"),
                        rs.getString("name"),
                        rs.getString("specialization"),
                        rs.getString("availability")
                ));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    public static void updateCounselor(Counselor counselor) {
        String sql = "UPDATE Counselors SET name=?, specialization=?, availability=? WHERE id=?";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, counselor.getName());
            pstmt.setString(2, counselor.getSpecialization());
            pstmt.setString(3, counselor.getAvailability());
            pstmt.setInt(4, counselor.getId());

            int rows = pstmt.executeUpdate();
            if (rows > 0) {
                System.out.println("[SUCCESS] Counselor updated.");
            } else {
                System.out.println("[INFO] No counselor found with ID: " + counselor.getId());
            }

        } catch (SQLException e) {
            System.err.println("[ERROR] Failed to update counselor: " + e.getMessage());
        }
    }

    public static void deleteCounselor(int id) {
        String sql = "DELETE FROM Counselors WHERE id=?";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, id);
            int rows = pstmt.executeUpdate();

            if (rows > 0) {
                System.out.println("[SUCCESS] Counselor deleted.");
            } else {
                System.out.println("[INFO] No counselor found with ID: " + id);
            }

        } catch (SQLException e) {
            System.err.println("[ERROR] Failed to delete counselor: " + e.getMessage());
        }
    }

    // Get the next available counselor ID
    public static int getNextCounselorId() {
        try (Connection conn = DBConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery("SELECT MAX(id) FROM Counselors")) {

            if (rs.next()) {
                return rs.getInt(1) + 1;
            }

        } catch (SQLException e) {
            System.err.println("[ERROR] Failed to get next counselor ID: " + e.getMessage());
        }
        return 1; // Start from 1 if table is empty
    }

}
