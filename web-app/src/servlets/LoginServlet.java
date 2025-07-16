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

            // Get DB connection
            try (Connection conn = DBUtils.getConnection()) {
                String sql = "SELECT * FROM users WHERE email = ?";
                try (PreparedStatement stmt = conn.prepareStatement(sql)) {
                    stmt.setString(1, email);
                    ResultSet rs = stmt.executeQuery();

                    if (rs.next()) {
                        String storedHash = rs.getString("password");

                        if (hashedPassword.equals(storedHash)) {
                            // Login successful
                            HttpSession session = request.getSession();
                            session.setAttribute("user", email);
                            session.setAttribute("studentName", rs.getString("name")); // if available in DB
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
            e.printStackTrace();
            request.setAttribute("error", "Database error: " + e.getMessage());
            request.getRequestDispatcher("login.jsp").forward(request, response);
        }
    }
}
