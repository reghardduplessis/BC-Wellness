package LibrarySystem.controller;

import LibrarySystem.model.Appointment;
import utils.DBConnection;

import java.sql.*;
import java.util.ArrayList;

public class AppointmentController {

    public static void addAppointment(Appointment a) {
        try {
            Connection conn = DBConnection.getConnection();
            String sql = "INSERT INTO Appointments (student, counselor, date, time, status) VALUES (?, ?, ?, ?, ?)";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, a.getStudent());
            ps.setString(2, a.getCounselor());
            ps.setString(3, a.getDate());
            ps.setString(4, a.getTime());
            ps.setString(5, a.getStatus());
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static ArrayList<Appointment> getAllAppointments() {
        ArrayList<Appointment> list = new ArrayList<>();
        try {
            Connection conn = DBConnection.getConnection();
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM Appointments");

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
            e.printStackTrace();
        }
        return list;
    }

    // TODO: updateAppointment(), deleteAppointment() etc.
}
