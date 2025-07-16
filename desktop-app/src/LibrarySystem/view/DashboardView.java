package LibrarySystem.view;

import javax.swing.*;
import java.awt.*;

public class DashboardView extends JFrame {
    private JTabbedPane tabbedPane;
    private AppointmentPanel appointmentPanel;
    private CounselorPanel counselorPanel;
    private FeedbackPanel feedbackPanel;
    private DashboardPanel dashboardPanel;

    public DashboardView() {
        try {
            UIManager.setLookAndFeel("javax.swing.plaf.nimbus.NimbusLookAndFeel");
            UIManager.put("nimbusBase", new Color(135, 206, 235)); // Light sky blue
            UIManager.put("nimbusBlueGrey", new Color(173, 216, 230)); // Light blue
            SwingUtilities.updateComponentTreeUI(this);
        } catch (Exception e) {
            e.printStackTrace();
        }

        setTitle("Counseling Management System");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(800, 600);
        getContentPane().setBackground(new Color(230, 240, 250)); // Soft light blue background

        // Initialize panels
        dashboardPanel = new DashboardPanel();
        appointmentPanel = new AppointmentPanel();
        counselorPanel = new CounselorPanel();
        feedbackPanel = new FeedbackPanel();

        // Set up tabbed pane
        tabbedPane = new JTabbedPane();
        tabbedPane.addTab("Dashboard", dashboardPanel);
        tabbedPane.addTab("Appointments", appointmentPanel);
        tabbedPane.addTab("Counselors", counselorPanel);
        tabbedPane.addTab("Feedback", feedbackPanel);

        // Style the tabbed pane
        tabbedPane.setFont(new Font("Arial", Font.BOLD, 14));
        tabbedPane.setBackground(new Color(230, 240, 250)); // Soft light blue
        tabbedPane.setForeground(new Color(0, 102, 204)); // Darker blue for text

        // Add tabbed pane to frame
        add(tabbedPane, BorderLayout.CENTER);

        setLocationRelativeTo(null); // Center the window
    }

    public AppointmentPanel getAppointmentPanel() {
        return appointmentPanel;
    }

    public CounselorPanel getCounselorPanel() {
        return counselorPanel;
    }

    public FeedbackPanel getFeedbackPanel() {
        return feedbackPanel;
    }

    public DashboardPanel getDashboardPanel() {
        return dashboardPanel;
    }
}