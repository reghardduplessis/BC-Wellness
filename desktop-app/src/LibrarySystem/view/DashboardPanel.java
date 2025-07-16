package LibrarySystem.view;

import javax.swing.*;
import java.awt.*;

public class DashboardPanel extends JPanel {
    private JLabel welcomeMessage;
    private JLabel description;
    private JLabel appointmentCountLabel;
    private JLabel counselorCountLabel;
    private JLabel feedbackCountLabel;

    public DashboardPanel() {
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        setBackground(new Color(230, 240, 250)); // Soft light blue

        // Welcome Message with centered panel
        JPanel welcomePanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        welcomePanel.setBackground(new Color(230, 240, 250)); // Match background
        welcomeMessage = new JLabel("<html><h2 style='color: #0066cc;'>Welcome to the Counseling Management System!</h2></html>");
        welcomeMessage.setFont(new Font("Arial", Font.BOLD, 24));
        welcomePanel.add(welcomeMessage);
        add(welcomePanel);

        // Fluff Paragraph
        description = new JLabel("<html><p style='color: #555555; text-align: center; font-size: 14px;'>This system is designed to streamline your counseling experience, offering an easy way to manage appointments, track counselors, and gather valuable feedback. Explore the tabs to make the most of this intuitive platform.</p></html>");
        description.setAlignmentX(Component.CENTER_ALIGNMENT);
        add(description);

        // Add vertical spacing
        add(Box.createVerticalStrut(20));

        // Metrics Section
        JPanel metricsPanel = new JPanel(new GridLayout(3, 1, 10, 10));
        metricsPanel.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(173, 216, 230), 1),
            BorderFactory.createEmptyBorder(10, 10, 10, 10)
        ));
        metricsPanel.setBackground(Color.WHITE);
        appointmentCountLabel = new JLabel("Appointments: 0");
        counselorCountLabel = new JLabel("Counselors: 0");
        feedbackCountLabel = new JLabel("Feedback: 0");
        appointmentCountLabel.setFont(new Font("Arial", Font.PLAIN, 16));
        counselorCountLabel.setFont(new Font("Arial", Font.PLAIN, 16));
        feedbackCountLabel.setFont(new Font("Arial", Font.PLAIN, 16));
        appointmentCountLabel.setForeground(new Color(0, 102, 204)); // Darker blue
        counselorCountLabel.setForeground(new Color(0, 102, 204));
        feedbackCountLabel.setForeground(new Color(0, 102, 204));
        metricsPanel.add(appointmentCountLabel);
        metricsPanel.add(counselorCountLabel);
        metricsPanel.add(feedbackCountLabel);

        add(new JLabel("<html><h3 style='color: #0066cc;'>Metrics</h3></html>"));
        add(metricsPanel);
    }

    public void updateCounts(int appointmentCount, int counselorCount, int feedbackCount) {
        appointmentCountLabel.setText("Appointments: " + appointmentCount);
        counselorCountLabel.setText("Counselors: " + counselorCount);
        feedbackCountLabel.setText("Feedback: " + feedbackCount);
    }
}