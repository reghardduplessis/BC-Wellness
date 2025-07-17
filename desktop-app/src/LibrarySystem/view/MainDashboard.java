package LibrarySystem.view;

import LibrarySystem.controller.DashboardController;
import LibrarySystem.model.DataModel;
import utils.DBConnection;
import utils.DatabaseSetup;
import java.sql.SQLException;
import javax.swing.*;

public class MainDashboard extends JFrame {

    public MainDashboard() {
        SwingUtilities.invokeLater(() -> {
            DataModel model = new DataModel();
            DashboardView view = new DashboardView();
            new DashboardController(model, view);
            view.setVisible(true);
        });
    }

    public static void main(String args[]) {
        // Try to connect before showing UI
        if (DBConnection.getConnection() == null) {
            System.err.println("Database connection failed. Exiting.");
            System.exit(1);
        }

        // Ensure necessary tables exist
        try {
            DatabaseSetup.ensureTablesExist();
        } catch (SQLException e) {
            System.err.println("Table setup failed: " + e.getMessage());
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Database initialization failed. Exiting.", "Error", JOptionPane.ERROR_MESSAGE);
            System.exit(1);
        }

        // Set Nimbus look and feel (optional)
        try {
            for (UIManager.LookAndFeelInfo info : UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (Exception ex) {
            java.util.logging.Logger.getLogger(MainDashboard.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }

        // Launch GUI
        SwingUtilities.invokeLater(() -> {
            DataModel model = new DataModel();
            DashboardView view = new DashboardView();
            new DashboardController(model, view);
            view.setVisible(true);
        });
    }
}
