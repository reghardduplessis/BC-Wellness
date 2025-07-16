<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%--<%@ page import="javax.servlet.http.HttpSession" %>--%>
<%
    HttpSession sessions = request.getSession(false);
    if (sessions == null || session.getAttribute("studentName") == null) {
        response.sendRedirect("login.jsp?error=Please+login+first");
        return;
    }

    String studentName = (String) session.getAttribute("studentName");
%>
<!DOCTYPE html>
<html>
<head>
    <title>Dashboard - BC Wellness</title>
    <style>
        html, body {
            height: 100%;
            margin: 0;
            padding: 0;
            font-family: "Segoe UI", sans-serif;
            background-color: #ccd6e4;
            display: flex;
            flex-direction: column;
        }

        /* Navbar */
        .navbar {
            background-color: #2196F3;
            padding: 15px 30px;
            color: white;
            display: flex;
            justify-content: space-between;
            align-items: center;
            position: relative;
        }

        .else-nav a {
            color: white;
            text-decoration: none;
            margin-left: 20px;
            font-weight: bold;
            padding: 8px 12px;
            border-radius: 4px;
            transition: background-color 0.2s ease-in-out;
        }

        .else-nav a:hover {
            background-color: rgba(255, 255, 255, 0.2);
        }

        .brand {
            font-weight: bold;
            font-size: 18px;
        }

        .user-dropdown {
            position: relative;
            cursor: pointer;
        }

        .dropdown-toggle {
            font-weight: bold;
        }

        .dropdown-menu {
            display: none;
            position: absolute;
            right: 0;
            top: 100%;
            background-color: white;
            box-shadow: 0 2px 6px rgba(0, 0, 0, 0.2);
            border-radius: 5px;
            overflow: hidden;
            z-index: 1000;
            min-width: 160px;
        }

        .dropdown-menu a,
        .dropdown-menu button {
            display: block;
            width: 100%;
            padding: 12px 20px;
            text-align: left;
            text-decoration: none;
            background: none;
            border: none;
            color: #333;
            font-size: 14px;
        }

        .dropdown-menu a:hover,
        .dropdown-menu button:hover {
            background-color: #2167f3;
            color: white;
        }


        .navbar form {
            display: inline;
        }

        h2 {
            margin-bottom: 20px;
            color: #2196F3;
        }
        .btn-logout {
            padding: 10px 20px;
            background: #2196F3;
            color: white;
            border: none;
            border-radius: 5px;
            cursor: pointer;
        }
        .btn-logout:hover {
            background: #2167f3;
        }

        .main-content {
            flex: 1;
            display: flex;
            flex-direction: column;
            justify-content: flex-start;
            align-items: center;
            padding: 20px;
        }

        .dashboard-header {
            text-align: center;
            margin-top: 30px;
            margin-bottom: 40px;
        }

        .dashboard-header h1 {
            font-size: 28px;
            color: #2196F3;
        }

        .dashboard-header p {
            font-size: 16px;
            color: #555;
        }

        /* Quick Actions */
        .quick-actions {
            margin: 0 auto;
            max-width: 900px;
            text-align: center;
        }

        .actions-grid {
            display: flex;
            justify-content: center;
            gap: 20px;
            margin-top: 20px;
            flex-wrap: wrap;
        }

        .action-card {
            background-color: #e3f2fd;
            padding: 20px 30px;
            text-decoration: none;
            color: #0d47a1;
            font-weight: bold;
            border-radius: 8px;
            transition: all 0.2s ease;
            min-width: 200px;
            box-shadow: 0 2px 4px rgba(0,0,0,0.1);
        }

        .action-card:hover {
            background-color: #bbdefb;
            transform: translateY(-2px);
        }

        /* Upcoming Appointments */
        .upcoming-section {
            margin: 50px auto;
            max-width: 900px;
            padding: 20px;
            background-color: #ffffff;
            border-radius: 8px;
            box-shadow: 0 2px 6px rgba(0,0,0,0.08);
        }

        .upcoming-section h2 {
            text-align: center;
            color: #333;
        }

        .appointments-list {
            margin-top: 20px;
            display: flex;
            justify-content: center;
        }

        .appointment-card {
            padding: 20px;
            border: 1px solid #ccc;
            border-radius: 8px;
            background-color: #f9f9f9;
            width: 100%;
            max-width: 600px;
            text-align: center;
        }

        /* Footer */
        .footer {
            background-color: #2196F3;
            color: #ffffff;
            text-align: center;
            padding: 20px;
            font-size: 14px;
        }
    </style>
</head>
<body>

<!-- Navbar -->
<div class="navbar">
    <div class="brand">BC Wellness System</div>
    <div class="user-dropdown">
        <div class="dropdown-toggle" onclick="toggleDropdown()">
            Welcome, <%= studentName %> ⏷
        </div>
        <div class="dropdown-menu" id="dropdownMenu">
            <a class="else-nav" href="index.jsp">Home</a>
            <form action="LogoutServlet" method="post">
                <button type="submit">Logout</button>
            </form>
        </div>
    </div>
</div>

<!-- Main Content Area -->
<main class="main-content">

    <!-- Dashboard Header -->
    <div class="dashboard-header">
        <h1>Welcome to Your Wellness Dashboard, <%= studentName %> 👋</h1>
        <p>Here’s how you can take care of your well-being today.</p>
    </div>

    <!-- Quick Actions Section -->
    <div class="quick-actions">
        <h2>Quick Actions</h2>
        <div class="actions-grid">
            <a href="appointments.jsp" class="action-card">📅 Book Appointment</a>
            <a href="reviews.jsp" class="action-card">📝 Submit Review</a>
            <a href="counselors.jsp" class="action-card">🔍 Find Counselors</a>
        </div>
    </div>

    <!-- Upcoming Appointments Section -->
    <div class="upcoming-section">
        <h2>Upcoming Appointments</h2>
        <div class="appointments-list">
            <!-- Placeholder for dynamic content -->
            <div class="appointment-card">
                <strong>No upcoming appointments.</strong>
                <p>Once you book, your next session will appear here.</p>
            </div>
        </div>
    </div>
    
        <form action="LogoutServlet" method="post">
            <input type="submit" class="btn-logout" value="Logout" />
        </form>

</main>

<!-- Footer -->
<div class="footer">
    © 2025 Belgium Campus | Student Wellness Management System
</div>

<script>
    function toggleDropdown() {
        const menu = document.getElementById("dropdownMenu");
        menu.style.display = (menu.style.display === "block") ? "none" : "block";
    }

    window.onclick = function(event) {
        const dropdown = document.getElementById("dropdownMenu");
        if (!event.target.matches('.dropdown-toggle')) {
            if (dropdown && dropdown.style.display === "block") {
                dropdown.style.display = "none";
            }
        }
    };
</script>

</body>
</html>
