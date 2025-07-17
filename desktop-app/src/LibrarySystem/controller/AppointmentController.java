package LibrarySystem.controller;

import LibrarySystem.model.Appointment;
import LibrarySystem.view.AppointmentPanel;
import utils.DBConnection;

import java.sql.*;
import java.util.ArrayList;

public class AppointmentController {

    public static void addAppointment(Appointment a) {
        String sql = "INSERT INTO Appointments (student, counselor, date, time, status) VALUES (?, ?, ?, ?, ?)";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, a.getStudent());
            ps.setString(2, a.getCounselor());
            ps.setString(3, a.getDate());
            ps.setString(4, a.getTime());
            ps.setString(5, a.getStatus());
            ps.executeUpdate();

            System.out.println("Appointment added successfully.");

        } catch (SQLException e) {
            System.err.println("Error adding appointment: " + e.getMessage());
        }
    }

    public static ArrayList<Appointment> getAllAppointments() {
        ArrayList<Appointment> list = new ArrayList<>();
        String sql = "SELECT * FROM Appointments";

        try (Connection conn = DBConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                list.add(new Appointment(
                        rs.getInt("id"),
                        rs.getString("student"),
                        rs.getString("counselor"),
                        rs.getString("date"),
                        rs.getString("time"),
                        rs.getString("status")
                ));
            }

        } catch (SQLException e) {
            System.err.println("Error retrieving appointments: " + e.getMessage());
        }

        return list;
    }

    public static void updateAppointment(int id, Appointment a) {
        String sql = "UPDATE Appointments SET student = ?, counselor = ?, date = ?, time = ?, status = ? WHERE id = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, a.getStudent());
            ps.setString(2, a.getCounselor());
            ps.setString(3, a.getDate());
            ps.setString(4, a.getTime());
            ps.setString(5, a.getStatus());
            ps.setInt(6, id);
            ps.executeUpdate();

            System.out.println("Appointment updated successfully.");

        } catch (SQLException e) {
            System.err.println("Error updating appointment: " + e.getMessage());
        }
    }

    public static void deleteAppointment(int id) {
        String sql = "DELETE FROM Appointments WHERE id = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, id);
            ps.executeUpdate();

            System.out.println("Appointment deleted successfully.");

        } catch (SQLException e) {
            System.err.println("Error deleting appointment: " + e.getMessage());
        }
    }
}