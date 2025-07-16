<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%--<%@ page import="javax.servlet.http.HttpSession" %>--%>
<%
    // String studentName = "Steve";

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
    <title>BC Student Wellness Management System</title>
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

        .main-content {
            flex: 1;
            padding: 40px 20px;
            text-align: center;
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

        .else-nav {
            color: white;
            text-decoration: none;
            margin-left: 20px;
            font-weight: bold;
            padding: 8px 12px;
            border-radius: 4px;
            transition: background-color 0.2s ease-in-out;
        }

        .else-nav:hover {
            background-color: #2167f3;
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
            background-color: #ccd6e4;
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

        /* Header */
        .header {
            background-color: #ccd6e4;
            padding: 60px 30px 40px 30px;
            text-align: center;
            box-shadow: 0 2px 4px rgba(0,0,0,0.1);
        }

        .header h1 {
            font-size: 36px;
            margin-bottom: 10px;
            color: #2196F3;
        }

        .header p {
            font-size: 18px;
            color: #555;
            max-width: 700px;
            margin: 0 auto;
        }

        .btn {
            padding: 12px 25px;
            margin: 10px;
            background-color: #2196F3;
            color: white;
            border: none;
            border-radius: 5px;
            font-size: 16px;
            cursor: pointer;
            transition: background 0.3s ease;
        }

        .btn:hover {
            background-color: #2167f3;
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
        <% if (studentName != null && !studentName.isEmpty()) { %>
        <div class="dropdown-toggle" onclick="toggleDropdown()">
            Welcome, <%= studentName %> ⏷
        </div>
        <div class="dropdown-menu" id="dropdownMenu">
            <a href="dashboard.jsp">Dashboard</a>
            <form action="LogoutServlet" method="post">
                <button type="submit">Logout</button>
            </form>
        </div>
        <% } else { %>
        <a class="else-nav" href="login.jsp">Login</a>
        <% } %>
    </div>
</div>

<!-- Header Section -->
<div class="header">
    <h1>Welcome to the BC Student Wellness Program</h1>
    <p>
        Our mission is to support your well-being throughout your academic journey.
        Whether you're scheduling a counseling session, sharing feedback, or seeking support —
        this platform is here to connect you with the help you need.
    </p>
</div>

<!-- Main Content -->
<div class="main-content">
    <% if (studentName == null) { %>
    <a href="login.jsp"><button class="btn">Login</button></a>
    <a href="register.jsp"><button class="btn">Register</button></a>
    <% } else { %>
    <a href="dashboard.jsp"><button class="btn">Go to Dashboard</button></a>
    <% } %>
</div>

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
