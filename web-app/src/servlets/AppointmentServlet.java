package servlets;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/AppointmentServlet")
public class AppointmentServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String action = request.getParameter("action");
        if ("book".equals(action)) {
            String name = request.getParameter("appointmentName");
            String date = request.getParameter("appointmentDate");
            String time = request.getParameter("appointmentTime");
            if (name != null && date != null && time != null && !name.trim().isEmpty() && !date.trim().isEmpty() && !time.trim().isEmpty()) {
                String appointment = "Appointment for " + name + " on " + date + " at " + time;
                request.setAttribute("upcomingAppointments", appointment);
            } else {
                request.setAttribute("upcomingAppointments", "Invalid appointment details. Please fill all fields.");
            }
        }
        request.getRequestDispatcher("dashboard.jsp").forward(request, response);
    }
}
