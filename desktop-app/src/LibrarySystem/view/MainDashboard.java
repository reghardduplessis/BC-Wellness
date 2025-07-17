package LibrarySystem.view;

import LibrarySystem.controller.DashboardController;
import LibrarySystem.model.DataModel;

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
        if (utils.DBConnection.getConnection() == null) {
            System.err.println("Database connection failed. Exiting.");
            System.exit(1);
        }

        /* Set the Nimbus look and feel (optional) */
        try {
            for (UIManager.LookAndFeelInfo info : UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(MainDashboard.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }

        /* Create and display the form */
        SwingUtilities.invokeLater(() -> {
            DataModel model = new DataModel();     // will later load DB data
            DashboardView view = new DashboardView();
            new DashboardController(model, view);  // wires logic + UI
            view.setVisible(true);
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    // End of variables declaration//GEN-END:variables
}
