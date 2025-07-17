package LibrarySystem.model;

import utils.DBConnection;

import javax.swing.table.DefaultTableModel;
import java.sql.*;
import java.util.*;

public class DataModel {
    private List<Appointment> appointments = new ArrayList<>();
    private List<Counselor> counselors = new ArrayList<>();
    private List<Feedback> feedbacks = new ArrayList<>();

    public DefaultTableModel getAppointmentTableModel() {
        String[] columns = {"ID", "Student", "Counselor", "Date", "Time", "Status"};
        DefaultTableModel model = new DefaultTableModel(columns, 0); // Explicitly set columns
        for (Appointment app : appointments) {
            model.addRow(new Object[]{app.getId(), app.getStudent(), app.getCounselor(), app.getDate(), app.getTime(), app.getStatus()});
        }
        return model;
    }

    public DefaultTableModel getCounselorTableModel() {
        String[] columns = {"ID", "Name", "Specialization", "Availability"};
        DefaultTableModel model = new DefaultTableModel(columns, 0); // Explicitly set columns
        for (Counselor counselor : counselors) {
            model.addRow(new Object[]{counselor.getId(), counselor.getName(), counselor.getSpecialization(), counselor.getAvailability()});
        }
        return model;
    }

    public DefaultTableModel getFeedbackTableModel() {
        String[] columns = {"ID", "Student", "Rating", "Comments"};
        DefaultTableModel model = new DefaultTableModel(columns, 0); // Explicitly set columns
        for (Feedback feedback : feedbacks) {
            model.addRow(new Object[]{feedback.getId(), feedback.getStudent(), feedback.getRating(), feedback.getComments()});
        }
        return model;
    }

    // Appointment management methods
    public void addAppointment(Appointment appointment) {
        appointments.add(appointment);
    }

    public void updateAppointment(int id, Appointment appointment) {
        String sql = "UPDATE Appointments SET student = ?, counselor = ?, date = ?, time = ?, status = ? WHERE id = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, appointment.getStudent());
            stmt.setString(2, appointment.getCounselor());
            stmt.setString(3, appointment.getDate());
            stmt.setString(4, appointment.getTime());
            stmt.setString(5, appointment.getStatus());
            stmt.setInt(6, id);

            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void removeAppointment(int id) {
        appointments.removeIf(app -> app.getId() == id);

        String sql = "DELETE FROM Appointments WHERE id = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id);
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Counselor management methods
    public void addCounselor(Counselor counselor) {
        counselors.add(counselor);
    }

    public void updateCounselor(int id, Counselor updatedCounselor) {
        for (int i = 0; i < counselors.size(); i++) {
            if (counselors.get(i).getId() == id) {
                counselors.set(i, updatedCounselor);
                return;
            }
        }
    }

    public void removeCounselor(int id) {
        counselors.removeIf(coun -> coun.getId() == id);
    }

    // Feedback management methods
    public void addFeedback(Feedback feedback) {
        feedbacks.add(feedback);
    }

    public void updateFeedback(int id, Feedback updatedFeedback) {
        for (int i = 0; i < feedbacks.size(); i++) {
            if (feedbacks.get(i).getId() == id) {
                feedbacks.set(i, updatedFeedback);
                return;
            }
        }
    }

    public void removeFeedback(int id) {
        feedbacks.removeIf(fb -> fb.getId() == id);
    }

    // Sample data initialization for testing
    public void initializeSampleData() {
        appointments.add(new Appointment(1, "John Doe", "Dr. Smith", "2025-07-20", "10:00", "Scheduled"));
        counselors.add(new Counselor(1, "Dr. Smith", "Career Counseling", "Mon-Fri, 9-5"));
        feedbacks.add(new Feedback(1, "Jane Doe", 4, "Great session!"));
    }

    // Getters for appointments, counselors, and feedbacks
    public List<Counselor> getCounselors() {
        return new ArrayList<>(counselors); // Return a copy to prevent external modification
    }

    public List<Appointment> getAppointments() {
        return new ArrayList<>(appointments);
    }

    public List<Feedback> getFeedbacks() {
        return new ArrayList<>(feedbacks);
    }

    // Method to get the next available ID for a new Counselor
    public int getNextCounselorId() {
        return counselors.stream()
                .mapToInt(Counselor::getId)
                .max()
                .orElse(0) + 1;
    }

    // Method to get the next available ID for a new appointment
    public int getNextAppointmentId() {
        return appointments.stream()
                .mapToInt(Appointment::getId)
                .max()
                .orElse(0) + 1;
    }

    // Method to get the next available ID for a new feedback
    public int getNextFeedbackId() {
        return feedbacks.stream()
                .mapToInt(Feedback::getId)
                .max()
                .orElse(0) + 1;
    }
}



