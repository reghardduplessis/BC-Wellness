<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="javax.servlet.http.HttpSession" %>
<%

    if (session == null || session.getAttribute("studentName") == null) {
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
            cursor: pointer;
            border: none;
        }

        .action-card:hover {
            background-color: #bbdefb;
            transform: translateY(-2px);
        }

        .modal {
            display: none;
            position: fixed;
            top: 0;
            left: 0;
            width: 100%;
            height: 100%;
            background-color: rgba(0,0,0,0.5);
            z-index: 1000;
        }

        .modal-content {
            background-color: #fff;
            margin: 15% auto;
            padding: 20px;
            border-radius: 8px;
            width: 80%;
            max-width: 400px;
            text-align: center;
            box-shadow: 0 4px 8px rgba(0,0,0,0.2);
        }

        .modal-content label {
            display: block;
            margin: 10px 0 5px;
        }

        .modal-content input {
            width: 100%;
            padding: 8px;
            margin-bottom: 10px;
            box-sizing: border-box;
        }

        .modal-content input[type="submit"] {
            background-color: #2196F3;
            color: white;
            border: none;
            padding: 10px;
            border-radius: 5px;
            cursor: pointer;
        }

        .modal-content input[type="submit"]:hover {
            background-color: #2196F3;
        }

        .close {
            color: #aaa;
            float: right;
            font-size: 28px;
            font-weight: bold;
            cursor: pointer;
        }

        .close:hover,
        .close:focus {
            color: black;
            text-decoration: none;
        }

        .upcoming-section {
            margin: 50px auto;
            max-width: 900px;
            padding: 20px;
            background-color: #e3f2fd;
            border-radius: 8px;
            box-shadow: 0 2px 6px rgba(0,0,0,0.08);
            color: #0d47a1;
        }

        .upcoming-section h2 {
            text-align: center;
            color: #0d47a1;
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
            background-color: #e3f2fd;
            width: 100%;
            max-width: 600px;
            text-align: center;
        }

        .review-message {
            margin-top: 20px;
            text-align: center;
            color: #2196F3;
        }

        .counselor-list {
            margin-top: 20px;
            text-align: center;
            border: 1px solid #ccc;
            border-radius: 8px;
            padding: 20px;
            background-color: #e3f2fd;
            max-width: 600px;
            margin-left: auto;
            margin-right: auto;
            color: #0d47a1;
        }

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
            <button onclick="openModal('appointmentModal')" class="action-card">📅 Book Appointment</button>
            <button onclick="openModal('reviewModal')" class="action-card">📝 Submit Review</button>
            <button onclick="openModal('counselorModal')" class="action-card">🔍 Find Counselor</button>
        </div>
    </div>

    <!-- Review Message -->
    <% if (request.getAttribute("reviewMessage") != null) { %>
    <div class="review-message">
        <%= request.getAttribute("reviewMessage") %>
    </div>
    <% } %>

    <!-- Counselor List -->
    <% if (request.getAttribute("counselors") != null) { %>
    <div class="counselor-list">
        <h4>Counselors</h4>
        <%= request.getAttribute("counselors") %>
    </div>
    <% } %>

    <!-- Upcoming Appointments Section -->
    <div class="upcoming-section">
        <h2>Upcoming Appointments</h2>
        <div class="appointments-list">
            <div class="appointment-card">
                <% if (request.getAttribute("upcomingAppointments") != null) { %>
                <%= request.getAttribute("upcomingAppointments") %>
                <% } else { %>
                <strong>No upcoming appointments.</strong>
                <p>Once you book, your next session will appear here.</p>
                <% } %>
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

<!-- Modals -->
<div id="appointmentModal" class="modal">
    <div class="modal-content">
        <span class="close" onclick="closeModal('appointmentModal')">×</span>
        <h3>Book Appointment</h3>
        <form action="AppointmentServlet" method="post">
            <input type="hidden" name="action" value="book" />
            <label for="appointmentName">Name:</label>
            <input type="text" name="appointmentName" required />
            <label for="appointmentDate">Date (YYYY-MM-DD):</label>
            <input type="text" name="appointmentDate" pattern="\d{4}-\d{2}-\d{2}" required />
            <label for="appointmentTime">Time (HH:MM):</label>
            <input type="text" name="appointmentTime" pattern="\d{2}:\d{2}" required />
            <input type="submit" value="Submit" />
        </form>
    </div>
</div>

<div id="reviewModal" class="modal">
    <div class="modal-content">
        <span class="close" onclick="closeModal('reviewModal')">×</span>
        <h3>Submit Review</h3>
        <form action="ReviewServlet" method="post">
            <input type="hidden" name="action" value="submitReview" />
            <label for="feedback">Feedback:</label>
            <input type="text" name="feedback" required />
            <input type="submit" value="Submit" />
        </form>
    </div>
</div>

<div id="counselorModal" class="modal">
    <div class="modal-content">
        <span class="close" onclick="closeModal('counselorModal')">×</span>
        <h3>Find Counselor</h3>
        <form action="CounselorServlet" method="post">
            <input type="hidden" name="action" value="findCounselor" />
            <input type="submit" value="Show Counselors" />
        </form>
    </div>
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

    function openModal(modalId) {
        document.getElementById(modalId).style.display = "block";
    }

    function closeModal(modalId) {
        document.getElementById(modalId).style.display = "none";
    }
</script>

</body>
</html>