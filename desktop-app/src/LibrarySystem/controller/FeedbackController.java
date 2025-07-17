package LibrarySystem.controller;

import LibrarySystem.model.Feedback;
import utils.*;
import java.sql.*;

public class FeedbackController {

    public static void addFeedback(Feedback f) {
        String sql = "INSERT INTO Feedback (student, rating, comments) VALUES (?, ?, ?)";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, f.getStudent());
            ps.setInt(2, f.getRating());
            ps.setString(3, f.getComments());

            ps.executeUpdate();
            System.out.println("Feedback added successfully.");

        } catch (SQLException e) {
            System.err.println("Error adding feedback: " + e.getMessage());
            e.printStackTrace();
        }
    }

    public static ArrayList<Feedback> getAllFeedback() {
        ArrayList<Feedback> list = new ArrayList<>();
        String sql = "SELECT * FROM Feedback";

        try (Connection conn = DBConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                list.add(new Feedback(
                        rs.getInt("id"),
                        rs.getString("student"),
                        rs.getInt("rating"),
                        rs.getString("comments")
                ));
            }

        } catch (SQLException e) {
            System.err.println("Error retrieving feedback: " + e.getMessage());
            e.printStackTrace();
        }

        return list;
    }

    public static void updateFeedback(int id, Feedback f) {
        String sql = "UPDATE Feedback SET student = ?, rating = ?, comments = ? WHERE id = ?";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, f.getStudent());
            ps.setInt(2, f.getRating());
            ps.setString(3, f.getComments());
            ps.setInt(4, id);

            int rowsUpdated = ps.executeUpdate();
            if (rowsUpdated > 0) {
                System.out.println("Feedback updated successfully.");
            } else {
                System.out.println("No feedback found with ID: " + id);
            }

        } catch (SQLException e) {
            System.err.println("Error updating feedback: " + e.getMessage());
            e.printStackTrace();
        }
    }

    public static void deleteFeedback(int id) {
        String sql = "DELETE FROM Feedback WHERE id = ?";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, id);

            int rowsDeleted = ps.executeUpdate();
            if (rowsDeleted > 0) {
                System.out.println("Feedback deleted successfully.");
            } else {
                System.out.println("No feedback found with ID: " + id);
            }

        } catch (SQLException e) {
            System.err.println("Error deleting feedback: " + e.getMessage());
            e.printStackTrace();
        }
    }
}