<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
    <title>Register - BC Wellness System</title>
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

        .register-header {
            background-color: #2196F3;
            color: white;
            text-align: center;
            padding: 40px 20px 30px 20px;
            box-shadow: 0 2px 4px rgba(0, 0, 0, 0.15);
        }

        .register-header h1 {
            font-size: 32px;
            margin-bottom: 10px;
            font-weight: bold;
        }

        .register-header p {
            font-size: 16px;
            max-width: 700px;
            margin: 0 auto;
            line-height: 1.6;
        }

        .main-content {
            flex: 1;
            display: flex;
            justify-content: center;
            align-items: center;
            padding: 20px;
        }

        .register-container {
            width: 100%;
            max-width: 450px;
            background: white;
            border-radius: 10px;
            box-shadow: 0 0 10px rgba(0,0,0,0.2);
            padding: 40px;
        }

        h2 {
            text-align: center;
        }

        input[type="text"],
        input[type="email"],
        input[type="password"] {
            width: 100%;
            padding: 10px;
            margin: 8px 0 15px 0;
            border: 1px solid #ccc;
            border-radius: 5px;
        }
        .btn {
            width: 100%;
            padding: 10px;
            background: #2196F3;
            color: white;
            border: none;
            border-radius: 5px;
            cursor: pointer;
        }
        .btn:hover {
            background: #2167f3;
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

<header class="register-header">
    <h1>Join the Wellness Journey</h1>
    <p>Empower your academic success by taking care of your mental well-being. Your wellness journey starts here.</p>
</header>

<main class="main-content">
    <div class="register-container">
        <h2>Create Student Account</h2>

        <form action="RegisterServlet" method="post">
            <label for="studentNumber">Student Number:</label>
            <input type="text" name="studentNumber" required />

            <label for="name">First Name:</label>
            <input type="text" name="name" required />

            <label for="surname">Surname:</label>
            <input type="text" name="surname" required />

            <label for="email">Email:</label>
            <input type="email" name="email" required />

            <label for="phone">Phone:</label>
            <input type="text" name="phone" pattern="[0-9]{10}" required title="Enter a 10-digit phone number" />

            <label for="password">Password:</label>
            <input type="password" name="password" required minlength="6" />

            <input type="submit" value="Register" class="btn" />
        </form>

        <p style="text-align:center; margin-top: 10px;">
            Already have an account? <a href="login.jsp">Login here</a>
        </p>
    </div>
</main>

<!-- Footer -->
<div class="footer">
    © 2025 Belgium Campus | Student Wellness Management System
</div>

</body>
</html>
