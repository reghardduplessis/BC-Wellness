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

@WebServlet("/RegisterServlet")
public class RegisterServlet extends HttpServlet {
    private static final String CONFIG_PATH = "/WEB-INF/config.properties";

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        // Collect request parameters
        String student_number = request.getParameter("student_number");
        String email = request.getParameter("email");
        String password = request.getParameter("password");
        String phone = request.getParameter("phone");
        String name = request.getParameter("name");
        String surname = request.getParameter("surname");


        // Load configuration
//        Properties props = new Properties();
//        props.load(getServletContext().getResourceAsStream(CONFIG_PATH));
//        String DB_URL = props.getProperty("db.url");
//        String DB_USER = props.getProperty("db.user");
//        String DB_PASSWORD = props.getProperty("db.password");

        // Input validation
        if (student_number == null || email == null || password == null || phone == null || name == null || surname == null ||
                student_number.trim().isEmpty() || email.trim().isEmpty() || password.trim().isEmpty() ||
                phone.trim().isEmpty() || name.trim().isEmpty() || surname.trim().isEmpty()) {
            request.setAttribute("error", "All fields are required.");
            request.getRequestDispatcher("register.jsp").forward(request, response);
            return;
        }

        if (!AuthenticateUtils.isValidEmail(email)) {
            request.setAttribute("error", "Invalid email format.");
            request.getRequestDispatcher("register.jsp").forward(request, response);
            return;
        }

        if (!AuthenticateUtils.isStrongPassword(password)) {
            request.setAttribute("error", "Password must include 1 uppercase, 1 lowercase, 1 number, and 1 special character.");
            request.getRequestDispatcher("register.jsp").forward(request, response);
            return;
        }

        String hashedPassword = AuthenticateUtils.hashPassword(password);

        try (Connection conn = DBUtils.getConnection()) {

            // Check if the email already exists
            String checkSql = "SELECT COUNT(*) FROM users WHERE email = ?";
            try (PreparedStatement checkStmt = conn.prepareStatement(checkSql)) {
                checkStmt.setString(1, email);
                ResultSet rs = checkStmt.executeQuery();
                if (rs.next() && rs.getInt(1) > 0) {
                    request.setAttribute("error", "Email already exists.");
                    request.getRequestDispatcher("register.jsp").forward(request, response);
                    return;
                }
            }
            // Insert new user
            String insertSql = "INSERT INTO users (student_number, email, password, phone, name, surname) VALUES (?, ?, ?, ?, ?, ?)";
            try (PreparedStatement stmt = conn.prepareStatement(insertSql)) {
                stmt.setString(1, student_number);
                stmt.setString(2, email);
                stmt.setString(3, hashedPassword);
                stmt.setString(4, phone);
                stmt.setString(5, name);
                stmt.setString(6, surname);

                int rows = stmt.executeUpdate();
                if (rows > 0) {
                    response.sendRedirect("login.jsp?success=Registration successful. Please log in.");
                } else {
                    request.setAttribute("error", "Registration failed.");
                    request.getRequestDispatcher("register.jsp").forward(request, response);
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
            request.setAttribute("error", "Database error: " + e.getMessage());
            request.getRequestDispatcher("register.jsp").forward(request, response);
        }
    }
}

/*
package servlets;

import utils.AuthenticateUtils;
import utils.DBUtils;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;
import java.sql.*;

@WebServlet("/RegisterServlet")
public class RegisterServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        // Collect request parameters
        String student_number = request.getParameter("student_number");
        String email = request.getParameter("email");
        String password = request.getParameter("password");
        String phone = request.getParameter("phone");
        String name = request.getParameter("name");
        String surname = request.getParameter("surname");

        // Input validation
        if (student_number == null || email == null || password == null || phone == null || name == null || surname == null ||
                student_number.trim().isEmpty() || email.trim().isEmpty() || password.trim().isEmpty() ||
                phone.trim().isEmpty() || name.trim().isEmpty() || surname.trim().isEmpty()) {

            request.setAttribute("error", "All fields are required.");
            request.getRequestDispatcher("register.jsp").forward(request, response);
            return;
        }

        if (!AuthenticateUtils.isValidEmail(email)) {
            request.setAttribute("error", "Invalid email format.");
            request.getRequestDispatcher("register.jsp").forward(request, response);
            return;
        }

        if (!AuthenticateUtils.isStrongPassword(password)) {
            request.setAttribute("error", "Password must include 1 uppercase, 1 lowercase, 1 number, and 1 special character.");
            request.getRequestDispatcher("register.jsp").forward(request, response);
            return;
        }

        String hashedPassword = AuthenticateUtils.hashPassword(password);

        try (Connection conn = DBUtils.getConnection()) {

            // Check if the email already exists
            String checkSql = "SELECT COUNT(*) FROM users WHERE email = ?";
            try (PreparedStatement checkStmt = conn.prepareStatement(checkSql)) {
                checkStmt.setString(1, email);
                ResultSet rs = checkStmt.executeQuery();
                if (rs.next() && rs.getInt(1) > 0) {
                    request.setAttribute("error", "Email already exists.");
                    request.getRequestDispatcher("register.jsp").forward(request, response);
                    return;
                }
            }

            // Insert new user
            String insertSql = "INSERT INTO users (student_number, email, password, phone, name, surname) VALUES (?, ?, ?, ?, ?, ?)";
            try (PreparedStatement stmt = conn.prepareStatement(insertSql)) {
                stmt.setString(1, student_number);
                stmt.setString(2, email);
                stmt.setString(3, hashedPassword);
                stmt.setString(4, phone);
                stmt.setString(5, name);
                stmt.setString(6, surname);

                int rows = stmt.executeUpdate();
                if (rows > 0) {
                    response.sendRedirect("login.jsp?success=Registration successful. Please log in.");
                } else {
                    request.setAttribute("error", "Registration failed.");
                    request.getRequestDispatcher("register.jsp").forward(request, response);
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
            request.setAttribute("error", "Database error: " + e.getMessage());
            request.getRequestDispatcher("register.jsp").forward(request, response);
        }
    }
}
 */
