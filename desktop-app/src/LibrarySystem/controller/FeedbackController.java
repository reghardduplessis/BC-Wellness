package LibrarySystem.controller;

import LibrarySystem.model.Feedback;
import utils.DBConnection;

import java.sql.*;
import java.util.ArrayList;

public class FeedbackController {

    public static void addFeedback(Feedback f) {
        try {
            Connection conn = DBConnection.getConnection();
            String sql = "INSERT INTO Feedback (student, rating, comments) VALUES (?, ?, ?)";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, f.getStudent());
            ps.setInt(2, f.getRating());
            ps.setString(3, f.getComments());
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static ArrayList<Feedback> getAllFeedback() {
        ArrayList<Feedback> list = new ArrayList<>();
        try {
            Connection conn = DBConnection.getConnection();
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM Feedback");

            while (rs.next()) {
                list.add(new Feedback(
                        rs.getInt("id"),
                        rs.getString("student"),
                        rs.getInt("rating"),
                        rs.getString("comments")
                ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    // TODO: updateFeedback(), deleteFeedback()
}
