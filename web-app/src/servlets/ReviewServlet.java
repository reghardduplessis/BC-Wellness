package servlets;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/ReviewServlet")
public class ReviewServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String action = request.getParameter("action");
        if ("submitReview".equals(action)) {
            String feedback = request.getParameter("feedback");
            if (feedback != null && !feedback.trim().isEmpty()) {
                request.setAttribute("reviewMessage", "Feedback Sent");
            } else {
                request.setAttribute("reviewMessage", "Feedback not sent. Please provide feedback.");
            }
        }
        request.getRequestDispatcher("dashboard.jsp").forward(request, response);
    }
}