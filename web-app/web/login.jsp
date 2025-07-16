<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
    <title>Login</title>
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

        .login-header {
            background-color: #2196F3;
            color: white;
            text-align: center;
            padding: 40px 20px 30px 20px;
            box-shadow: 0 2px 4px rgba(0, 0, 0, 0.15);
        }

        .login-header h1 {
            font-size: 28px;
            margin-bottom: 10px;
        }

        .login-header p {
            font-size: 16px;
            max-width: 600px;
            margin: 0 auto;
            line-height: 1.6;
        }

        .main-content {
            flex: 1;
            display: flex;
            justify-content: center;
            align-items: center;
            padding: 40px 20px;
        }

        .login-container {
            width: 100%;
            max-width: 400px;
            background: white;
            border-radius: 10px;
            box-shadow: 0 0 10px rgba(0,0,0,0.2);
            padding: 40px;
            text-align: center;
        }

        h2 {
            text-align: center;
        }
        input[type="email"], input[type="password"] {
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

<header class="login-header">
    <h1>You’re So Close to Your Potential!</h1>
    <p>Log in to manage your wellness appointments and unlock your best self.</p>
</header>

<main class="main-content">
    <div class="login-container">
        <h2>Student Login</h2>

        <form action="LoginServlet" method="post">
            <label for="email">Email:</label>
            <input type="email" name="email" required />

            <label for="password">Password:</label>
            <input type="password" name="password" required />

            <input type="submit" value="Login" class="btn" />
        </form>

        <p style="text-align:center; margin-top: 10px;">
            Don't have an account? <a href="register.jsp">Register here</a>
        </p>
    </div>
</main>

<!-- Footer -->
<div class="footer">
    © 2025 Belgium Campus | Student Wellness Management System
</div>

</body>
</html>

