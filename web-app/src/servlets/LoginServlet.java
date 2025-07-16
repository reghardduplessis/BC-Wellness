package servlets;

import utils.AuthenticateUtils;
import utils.DBUtils;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

@WebServlet("/LoginServlet")
public class LoginServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        // Get form data
        String email = request.getParameter("email");
        String password = request.getParameter("password");

        // Input validation
        if (email == null || password == null || email.trim().isEmpty() || password.trim().isEmpty()) {
            request.setAttribute("error", "Email and password are required.");
            request.getRequestDispatcher("login.jsp").forward(request, response);
            return;
        }

        try {
            // Hash the input password
            String hashedPassword = AuthenticateUtils.hashPassword(password);

            System.out.println("Attempting to connect to the database...");

            // Get DB connection
            try (Connection conn = DBUtils.getConnection()) {
                System.out.println("Successfully connected to the database.");

                String sql = "SELECT student_number, name, password FROM users WHERE email = ?";
                try (PreparedStatement stmt = conn.prepareStatement(sql)) {
                    stmt.setString(1, email);
                    ResultSet rs = stmt.executeQuery();

                    if (rs.next()) {
                        String storedHash = rs.getString("password");

                        // Debug logging to check if hashes match
                        System.out.println("Entered hash: " + hashedPassword);
                        System.out.println("Stored hash: " + storedHash);

                        if (hashedPassword.equals(storedHash)) {
                            // Login successful
                            HttpSession session = request.getSession(true);
                            session.setAttribute("user", email);
                            session.setAttribute("studentName", rs.getString("name"));
                            session.setAttribute("studentNumber", rs.getString("student_number"));
                            response.sendRedirect("dashboard.jsp");
                        } else {
                            request.setAttribute("error", "Invalid email or password.");
                            request.getRequestDispatcher("login.jsp").forward(request, response);
                        }
                    } else {
                        request.setAttribute("error", "Invalid email or password.");
                        request.getRequestDispatcher("login.jsp").forward(request, response);
                    }
                }
            }
        } catch (SQLException e) {
            System.err.println("Failed to connect or query database: " + e.getMessage());
            request.setAttribute("error", "Database error: " + e.getMessage());
            request.getRequestDispatcher("login.jsp").forward(request, response);
        }
    }
}