package LibrarySystem.model;

import utils.DBConnection;

import javax.swing.table.DefaultTableModel;
import java.sql.*;
import java.util.*;

public class DataModel {
    private List<Appointment> appointments = new ArrayList<>();
    private List<Counselor> counselors = new ArrayList<>();
    private List<Feedback> feedbacks = new ArrayList<>();

    public DataModel() {
        loadDataFromDatabase(); // Load data from database on initialization
    }

    // Load data from database into in-memory lists
    private void loadDataFromDatabase() {
        loadAppointments();
        loadCounselors();
        loadFeedbacks();
    }

    private void loadAppointments() {
        appointments.clear();
        String sql = "SELECT * FROM Appointments";
        try (Connection conn = DBConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                appointments.add(new Appointment(
                        rs.getInt("id"),
                        rs.getString("student"),
                        rs.getString("counselor"),
                        rs.getString("date"),
                        rs.getString("time"),
                        rs.getString("status")
                ));
            }
            System.out.println("Loaded " + appointments.size() + " appointments from database.");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void loadCounselors() {
        counselors.clear();
        String sql = "SELECT * FROM Counselors";
        try (Connection conn = DBConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                counselors.add(new Counselor(
                        rs.getInt("id"),
                        rs.getString("name"),
                        rs.getString("specialization"),
                        rs.getString("availability")
                ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void loadFeedbacks() {
        feedbacks.clear();
        String sql = "SELECT * FROM Feedback";
        try (Connection conn = DBConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                feedbacks.add(new Feedback(
                        rs.getInt("id"),
                        rs.getString("student"),
                        rs.getInt("rating"),
                        rs.getString("comments")
                ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public DefaultTableModel getAppointmentTableModel() {
        String[] columns = {"ID", "Student", "Counselor", "Date", "Time", "Status"};
        DefaultTableModel model = new DefaultTableModel(columns, 0);
        for (Appointment app : appointments) {
            model.addRow(new Object[]{app.getId(), app.getStudent(), app.getCounselor(), app.getDate(), app.getTime(), app.getStatus()});
        }
        return model;
    }

    public DefaultTableModel getCounselorTableModel() {
        String[] columns = {"ID", "Name", "Specialization", "Availability"};
        DefaultTableModel model = new DefaultTableModel(columns, 0);
        for (Counselor counselor : counselors) {
            model.addRow(new Object[]{counselor.getId(), counselor.getName(), counselor.getSpecialization(), counselor.getAvailability()});
        }
        return model;
    }

    public DefaultTableModel getFeedbackTableModel() {
        String[] columns = {"ID", "Student", "Rating", "Comments"};
        DefaultTableModel model = new DefaultTableModel(columns, 0);
        for (Feedback feedback : feedbacks) {
            model.addRow(new Object[]{feedback.getId(), feedback.getStudent(), feedback.getRating(), feedback.getComments()});
        }
        return model;
    }

    // Appointment management methods
    public int addAppointment(Appointment appointment) {
        String sql = "INSERT INTO Appointments (student, counselor, date, time, status) VALUES (?, ?, ?, ?, ?)";
        int generatedId = -1;
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            stmt.setString(1, appointment.getStudent());
            stmt.setString(2, appointment.getCounselor());
            stmt.setString(3, appointment.getDate());
            stmt.setString(4, appointment.getTime());
            stmt.setString(5, appointment.getStatus());
            stmt.executeUpdate();

            // Retrieve the generated ID
            try (ResultSet rs = stmt.getGeneratedKeys()) {
                if (rs.next()) {
                    generatedId = rs.getInt(1);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        // Refresh in-memory list
        loadAppointments();
        return generatedId;
    }

    public void updateAppointment(int id, Appointment appointment) {
        String sql = "UPDATE Appointments SET student = ?, counselor = ?, date = ?, time = ?, status = ? WHERE id = ?";
        boolean updated = false;
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, appointment.getStudent());
            stmt.setString(2, appointment.getCounselor());
            stmt.setString(3, appointment.getDate());
            stmt.setString(4, appointment.getTime());
            stmt.setString(5, appointment.getStatus());
            stmt.setInt(6, id);
            int rowsAffected = stmt.executeUpdate();
            updated = rowsAffected > 0;
            System.out.println("Update for ID " + id + " affected " + rowsAffected + " rows.");
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("SQL Error updating appointment ID " + id + ": " + e.getMessage());
        }
        if (updated) {
            loadAppointments();
        } else {
            System.out.println("No appointment found with ID " + id + " for update.");
            loadAppointments(); // Reload anyway to ensure consistency
        }
    }

    public void removeAppointment(int id) {
        String sql = "DELETE FROM Appointments WHERE id = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);
            int rowsAffected = stmt.executeUpdate();
            System.out.println("Deleted " + rowsAffected + " rows for Appointment ID " + id);
        } catch (SQLException e) {
            System.out.println("Error deleting appointment ID " + id + ": " + e.getMessage());
            e.printStackTrace();
        }
        loadAppointments();
    }

    // Counselor management methods
    public int addCounselor(Counselor counselor) throws SQLException {
        String sql = "INSERT INTO Counselors (name, specialization, availability) VALUES (?, ?, ?)";
        int generatedId = -1;
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            stmt.setString(1, counselor.getName());
            stmt.setString(2, counselor.getSpecialization());
            stmt.setString(3, counselor.getAvailability());
            stmt.executeUpdate();

            try (ResultSet rs = stmt.getGeneratedKeys()) {
                if (rs.next()) {
                    generatedId = rs.getInt(1);
                }
            }
            System.out.println("Added counselor with ID: " + generatedId + ", Name: " + counselor.getName());
        } catch (SQLException e) {
            System.out.println("SQL Error adding counselor: " + e.getMessage());
            e.printStackTrace();
            throw e; // Re-throw to be caught in the controller
        }
        loadCounselors();
        System.out.println("Counselors list size after load: " + counselors.size());
        return generatedId;
    }

    public void updateCounselor(int id, Counselor updatedCounselor) {
        String sql = "UPDATE Counselors SET name = ?, specialization = ?, availability = ? WHERE id = ?";
        boolean updated = false;
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, updatedCounselor.getName());
            stmt.setString(2, updatedCounselor.getSpecialization());
            stmt.setString(3, updatedCounselor.getAvailability());
            stmt.setInt(4, id);
            int rowsAffected = stmt.executeUpdate();
            updated = rowsAffected > 0;
            System.out.println("Update for Counselor ID " + id + " affected " + rowsAffected + " rows.");
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("SQL Error updating counselor ID " + id + ": " + e.getMessage());
        }
        if (updated) {
            loadCounselors();
        } else {
            System.out.println("No counselor found with ID " + id + " for update.");
            loadCounselors(); // Reload anyway to ensure consistency
        }
    }

    public void removeCounselor(int id) {
        String sql = "DELETE FROM Counselors WHERE id = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);
            int rowsAffected = stmt.executeUpdate();
            System.out.println("Deleted " + rowsAffected + " rows for Counselor ID " + id);
        } catch (SQLException e) {
            System.out.println("Error deleting counselor ID " + id + ": " + e.getMessage());
            e.printStackTrace();
        }
        loadCounselors();
    }

    // Feedback management methods
    public int addFeedback(Feedback feedback) {
        String sql = "INSERT INTO Feedback (student, rating, comments) VALUES (?, ?, ?)";
        int generatedId = -1;
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            stmt.setString(1, feedback.getStudent());
            stmt.setInt(2, feedback.getRating());
            stmt.setString(3, feedback.getComments());
            stmt.executeUpdate();

            // Retrieve the generated ID
            try (ResultSet rs = stmt.getGeneratedKeys()) {
                if (rs.next()) {
                    generatedId = rs.getInt(1);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        // Refresh in-memory list
        loadFeedbacks();
        return generatedId;
    }

    public void updateFeedback(int id, Feedback updatedFeedback) {
        String sql = "UPDATE Feedback SET student = ?, rating = ?, comments = ? WHERE id = ?";
        boolean updated = false;
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, updatedFeedback.getStudent());
            stmt.setInt(2, updatedFeedback.getRating());
            stmt.setString(3, updatedFeedback.getComments());
            stmt.setInt(4, id);
            int rowsAffected = stmt.executeUpdate();
            updated = rowsAffected > 0;
            System.out.println("Update for Feedback ID " + id + " affected " + rowsAffected + " rows.");
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("SQL Error updating feedback ID " + id + ": " + e.getMessage());
        }
        if (updated) {
            loadFeedbacks();
        } else {
            System.out.println("No feedback found with ID " + id + " for update.");
            loadFeedbacks(); // Reload anyway to ensure consistency
        }
    }

    public void removeFeedback(int id) {
        String sql = "DELETE FROM Feedback WHERE id = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);
            int rowsAffected = stmt.executeUpdate();
            System.out.println("Deleted " + rowsAffected + " rows for Feedback ID " + id);
        } catch (SQLException e) {
            System.out.println("Error deleting feedback ID " + id + ": " + e.getMessage());
            e.printStackTrace();
        }
        loadFeedbacks();
    }

    // Sample data initialization for testing
    public void initializeSampleData() {
        try (Connection conn = DBConnection.getConnection();
             Statement stmt = conn.createStatement()) {
            ResultSet rs = stmt.executeQuery("SELECT COUNT(*) FROM Appointments");
            if (rs.next() && rs.getInt(1) == 0) {
                String sql = "INSERT INTO Appointments (student, counselor, date, time, status) VALUES (?, ?, ?, ?, ?)";
                try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
                    pstmt.setString(1, "John Doe");
                    pstmt.setString(2, "Dr. Smith");
                    pstmt.setString(3, "2025-07-20");
                    pstmt.setString(4, "10:00");
                    pstmt.setString(5, "Scheduled");
                    pstmt.executeUpdate();
                }
            }
            rs = stmt.executeQuery("SELECT COUNT(*) FROM Counselors");
            if (rs.next() && rs.getInt(1) == 0) {
                String sql = "INSERT INTO Counselors (name, specialization, availability) VALUES (?, ?, ?)";
                try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
                    pstmt.setString(1, "Dr. Smith");
                    pstmt.setString(2, "Career Counseling");
                    pstmt.setString(3, "Mon-Fri, 9-5");
                    pstmt.executeUpdate();
                }
            }
            rs = stmt.executeQuery("SELECT COUNT(*) FROM Feedback");
            if (rs.next() && rs.getInt(1) == 0) {
                String sql = "INSERT INTO Feedback (student, rating, comments) VALUES (?, ?, ?)";
                try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
                    pstmt.setString(1, "Jane Doe");
                    pstmt.setInt(2, 4);
                    pstmt.setString(3, "Great session!");
                    pstmt.executeUpdate();
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        loadDataFromDatabase(); // Refresh in-memory lists after adding sample data
    }

    // Getters for appointments, counselors, and feedbacks
    public List<Counselor> getCounselors() {
        return new ArrayList<>(counselors);
    }

    public List<Appointment> getAppointments() {
        return new ArrayList<>(appointments);
    }

    public List<Feedback> getFeedbacks() {
        return new ArrayList<>(feedbacks);
    }

    public int getNextCounselorId() {
        String sql = "SELECT MAX(id) FROM Counselors";
        try (Connection conn = DBConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            if (rs.next()) {
                return rs.getInt(1) + 1;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 1; // Default to 1 if no counselors exist
    }

    public int getNextAppointmentId() {
        String sql = "SELECT MAX(id) FROM Appointments";
        try (Connection conn = DBConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            if (rs.next()) {
                return rs.getInt(1) + 1;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 1; // Default to 1 if no appointments exist
    }

    public int getNextFeedbackId() {
        String sql = "SELECT MAX(id) FROM Feedback";
        try (Connection conn = DBConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            if (rs.next()) {
                return rs.getInt(1) + 1;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 1; // Default to 1 if no feedbacks exist
    }
}


