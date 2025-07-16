package servlets;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/CounselorServlet")
public class CounselorServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String action = request.getParameter("action");
        if ("findCounselor".equals(action)) {
            request.setAttribute("counselors", "1. Dr. Jane Smith - jane.smith@example.com\n2. Dr. John Doe - john.doe@example.com");
        }
        request.getRequestDispatcher("dashboard.jsp").forward(request, response);
    }
}