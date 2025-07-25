package LibrarySystem.controller;

import LibrarySystem.model.*;
import LibrarySystem.view.*;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

public class ControllerHub {
    private DataModel model;
    private DashboardView view;
    private AppointmentPanel appointmentPanel;
    private CounselorPanel counselorPanel;
    private FeedbackPanel feedbackPanel;
    private DashboardPanel dashboardPanel;

    // Controller instances
    private CounselorController counselorController;
    private AppointmentController appointmentController;
    private FeedbackController feedbackController;

    // Define column names manually
    private static final String[] APPOINTMENT_COLUMNS = {"ID", "Student", "Counselor", "Date", "Time", "Status"};
    private static final String[] COUNSELOR_COLUMNS = {"ID", "Name", "Specialization", "Availability"};
    private static final String[] FEEDBACK_COLUMNS = {"ID", "Student", "Rating", "Comments"};

    public ControllerHub(DataModel model, DashboardView view) {
        this.model = model;
        this.view = view;
        this.appointmentPanel = view.getAppointmentPanel();
        this.counselorPanel = view.getCounselorPanel();
        this.feedbackPanel = view.getFeedbackPanel();
        this.dashboardPanel = view.getDashboardPanel();

        // Initialize controllers with the same DataModel instance and this DashboardController
        this.counselorController = new CounselorController(model, this);
        this.appointmentController = new AppointmentController(model);
        this.feedbackController = new FeedbackController(model);

        model.initializeSampleData();
        setupAppointmentListeners();
        setupCounselorListeners();
        setupFeedbackListeners();
        updateDashboard();

        updateViews();
    }

    private void updateDashboard() {
        int appointmentCount = model.getAppointmentTableModel().getRowCount();
        int counselorCount = model.getCounselorTableModel().getRowCount();
        int feedbackCount = model.getFeedbackTableModel().getRowCount();
        dashboardPanel.updateCounts(appointmentCount, counselorCount, feedbackCount);
    }

    private void setupAppointmentListeners() {
        DefaultTableModel appointmentModel = model.getAppointmentTableModel();
        if (appointmentModel != null) {
            appointmentPanel.getTableModel().setDataVector(appointmentModel.getDataVector(), new Vector<>(List.of(APPOINTMENT_COLUMNS)));
        } else {
            System.out.println("Error: Appointment table model is null");
        }
        appointmentPanel.getCounselorCombo().setModel(new DefaultComboBoxModel<>(getCounselorNames().toArray(new String[0])));

        appointmentPanel.getTableModel().addTableModelListener(e -> {
            if (e.getColumn() == -1) return; // Ignore structural changes
            int row = e.getFirstRow();
            Appointment app = new Appointment(
                    row + 1,
                    (String) appointmentPanel.getTableModel().getValueAt(row, 1),
                    (String) appointmentPanel.getTableModel().getValueAt(row, 2),
                    (String) appointmentPanel.getTableModel().getValueAt(row, 3),
                    (String) appointmentPanel.getTableModel().getValueAt(row, 4),
                    (String) appointmentPanel.getTableModel().getValueAt(row, 5)
            );
            appointmentController.updateAppointment(row + 1, app);
            updateDashboard();
        });

        appointmentPanel.getTable().getSelectionModel().addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting() && appointmentPanel.getTable().getSelectedRow() >= 0) {
                int row = appointmentPanel.getTable().getSelectedRow();
                appointmentPanel.getStudentField().setText((String) appointmentPanel.getTableModel().getValueAt(row, 1));
                appointmentPanel.getCounselorCombo().setSelectedItem(appointmentPanel.getTableModel().getValueAt(row, 2));
                appointmentPanel.getDateField().setText((String) appointmentPanel.getTableModel().getValueAt(row, 3));
                appointmentPanel.getTimeField().setText((String) appointmentPanel.getTableModel().getValueAt(row, 4));
                appointmentPanel.getStatusCombo().setSelectedItem(appointmentPanel.getTableModel().getValueAt(row, 5));
            }
        });

        appointmentPanel.getBookButton().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (validateAppointmentForm()) {
                    Appointment app = new Appointment(
                            model.getNextAppointmentId(), // Use DataModel to get the next ID
                            appointmentPanel.getStudentField().getText(),
                            (String) appointmentPanel.getCounselorCombo().getSelectedItem(),
                            appointmentPanel.getDateField().getText(),
                            appointmentPanel.getTimeField().getText(),
                            (String) appointmentPanel.getStatusCombo().getSelectedItem()
                    );
                    appointmentController.addAppointment(app);
                    clearAppointmentForm();
                    updateAppointmentView();
                    updateDashboard();
                    JOptionPane.showMessageDialog(view, "Appointment booked successfully!");
                }
            }
        });

        appointmentPanel.getUpdateButton().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int selectedRow = appointmentPanel.getTable().getSelectedRow();
                if (selectedRow >= 0 && validateAppointmentForm()) {
                    int id = (Integer) appointmentPanel.getTableModel().getValueAt(selectedRow, 0);
                    Appointment app = new Appointment(
                            id, // Use the ID from the table
                            appointmentPanel.getStudentField().getText(),
                            (String) appointmentPanel.getCounselorCombo().getSelectedItem(),
                            appointmentPanel.getDateField().getText(),
                            appointmentPanel.getTimeField().getText(),
                            (String) appointmentPanel.getStatusCombo().getSelectedItem()
                    );
                    try {
                        appointmentController.updateAppointment(id, app);
                        updateAppointmentView();
                        updateDashboard();
                        JOptionPane.showMessageDialog(view, "Appointment updated successfully!");
                    } catch (Exception ex) {
                        JOptionPane.showMessageDialog(view, "Error updating appointment: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                    }
                } else {
                    JOptionPane.showMessageDialog(view, "Please select an appointment to update.", "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        appointmentPanel.getCancelButton().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int selectedRow = appointmentPanel.getTable().getSelectedRow();
                if (selectedRow >= 0) {
                    int confirm = JOptionPane.showConfirmDialog(view, "Are you sure you want to cancel this appointment?", "Confirm Cancel", JOptionPane.YES_NO_OPTION);
                    if (confirm == JOptionPane.YES_OPTION) {
                        int id = (Integer) appointmentPanel.getTableModel().getValueAt(selectedRow, 0);
                        System.out.println("Attempting to cancel Appointment ID: " + id);
                        appointmentController.deleteAppointment(id);
                        updateAppointmentView();
                        updateDashboard();
                        JOptionPane.showMessageDialog(view, "Appointment canceled successfully!");
                    }
                } else {
                    JOptionPane.showMessageDialog(view, "Please select an appointment to cancel.", "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });
    }

    private void setupCounselorListeners() {
        DefaultTableModel counselorModel = model.getCounselorTableModel();
        if (counselorModel != null) {
            counselorPanel.getTableModel().setDataVector(counselorModel

                    .getDataVector(), new Vector<>(List.of(COUNSELOR_COLUMNS)));
        } else {
            System.out.println("Error: Counselor table model is null");
        }

        counselorPanel.getTableModel().addTableModelListener(e -> {
            if (e.getColumn() == -1) return; // Ignore structural changes
            int row = e.getFirstRow();
            Counselor counselor = new Counselor(
                    row + 1,
                    (String) counselorPanel.getTableModel().getValueAt(row, 1),
                    (String) counselorPanel.getTableModel().getValueAt(row, 2),
                    (String) counselorPanel.getTableModel().getValueAt(row, 3)
            );
            counselorController.updateCounselor(row + 1, counselor);
            updateDashboard();
        });

        counselorPanel.getTable().getSelectionModel().addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting() && counselorPanel.getTable().getSelectedRow() >= 0) {
                int row = counselorPanel.getTable().getSelectedRow();
                counselorPanel.getNameField().setText((String) counselorPanel.getTableModel().getValueAt(row, 1));
                counselorPanel.getSpecializationField().setText((String) counselorPanel.getTableModel().getValueAt(row, 2));
                counselorPanel.getAvailabilityField().setText((String) counselorPanel.getTableModel().getValueAt(row, 3));
            }
        });

        counselorPanel.getAddButton().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (validateCounselorForm()) {
                    Counselor counselor = new Counselor(
                            0,
                            counselorPanel.getNameField().getText(),
                            counselorPanel.getSpecializationField().getText(),
                            counselorPanel.getAvailabilityField().getText()
                    );
                    System.out.println("Adding new counselor: " + counselor.getName());
                    counselorController.addCounselor(counselor);
                    clearCounselorForm();
                    updateCounselorView();
                    updateDashboard();
                    refreshCounselorDropDown(); // Manual refresh
                    JOptionPane.showMessageDialog(view, "Counselor added successfully!");
                }
            }
        });

        counselorPanel.getUpdateButton().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int selectedRow = counselorPanel.getTable().getSelectedRow();
                if (selectedRow >= 0 && validateCounselorForm()) {
                    int id = (Integer) counselorPanel.getTableModel().getValueAt(selectedRow, 0);
                    System.out.println("Attempting to update Counselor ID: " + id);
                    Counselor counselor = new Counselor(
                            id,
                            counselorPanel.getNameField().getText(),
                            counselorPanel.getSpecializationField().getText(),
                            counselorPanel.getAvailabilityField().getText()
                    );
                    counselorController.updateCounselor(id, counselor);
                    updateCounselorView();
                    updateDashboard();
                    JOptionPane.showMessageDialog(view, "Counselor updated successfully!");
                } else {
                    JOptionPane.showMessageDialog(view, "Please select a counselor to update.", "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        counselorPanel.getRemoveButton().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int selectedRow = counselorPanel.getTable().getSelectedRow();
                if (selectedRow >= 0) {
                    int confirm = JOptionPane.showConfirmDialog(view, "Are you sure you want to remove this counselor?", "Confirm Remove", JOptionPane.YES_NO_OPTION);
                    if (confirm == JOptionPane.YES_OPTION) {
                        int id = (Integer) counselorPanel.getTableModel().getValueAt(selectedRow, 0);
                        System.out.println("Attempting to remove Counselor ID: " + id);
                        counselorController.deleteCounselor(id);
                        updateCounselorView();
                        updateDashboard();
                        JOptionPane.showMessageDialog(view, "Counselor removed successfully!");
                    }
                } else {
                    JOptionPane.showMessageDialog(view, "Please select a counselor to remove.", "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });
    }

    private void setupFeedbackListeners() {
        DefaultTableModel feedbackModel = model.getFeedbackTableModel();
        if (feedbackModel != null) {
            feedbackPanel.getTableModel().setDataVector(feedbackModel.getDataVector(), new Vector<>(List.of(FEEDBACK_COLUMNS)));
        } else {
            System.out.println("Error: Feedback table model is null");
        }

        feedbackPanel.getTableModel().addTableModelListener(e -> {
            if (e.getColumn() == -1) return; // Ignore structural changes
            int row = e.getFirstRow();
            Feedback feedback = new Feedback(
                    row + 1,
                    feedbackPanel.getTableModel().getValueAt(row, 1).toString(),
                    Integer.parseInt(feedbackPanel.getTableModel().getValueAt(row, 2).toString()),
                    feedbackPanel.getTableModel().getValueAt(row, 3).toString()
            );
            feedbackController.updateFeedback(row + 1, feedback);
            updateDashboard();
        });

        feedbackPanel.getTable().getSelectionModel().addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting() && feedbackPanel.getTable().getSelectedRow() >= 0) {
                int row = feedbackPanel.getTable().getSelectedRow();
                feedbackPanel.getStudentField().setText(feedbackPanel.getTableModel().getValueAt(row, 1).toString());
                feedbackPanel.getRatingCombo().setSelectedItem(Integer.parseInt(feedbackPanel.getTableModel().getValueAt(row, 2).toString()));
                feedbackPanel.getCommentsArea().setText(feedbackPanel.getTableModel().getValueAt(row, 3).toString());
            }
        });

        feedbackPanel.getSubmitButton().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (validateFeedbackForm()) {
                    Feedback feedback = new Feedback(
                            model.getNextFeedbackId(), // Use DataModel to get the next ID
                            feedbackPanel.getStudentField().getText(),
                            (Integer) feedbackPanel.getRatingCombo().getSelectedItem(),
                            feedbackPanel.getCommentsArea().getText()
                    );
                    feedbackController.addFeedback(feedback);
                    clearFeedbackForm();
                    updateFeedbackView();
                    updateDashboard();
                    JOptionPane.showMessageDialog(view, "Feedback submitted successfully!");
                }
            }
        });

        feedbackPanel.getEditButton().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int selectedRow = feedbackPanel.getTable().getSelectedRow();
                if (selectedRow >= 0 && validateFeedbackForm()) {
                    int id = (Integer) feedbackPanel.getTableModel().getValueAt(selectedRow, 0);
                    System.out.println("Attempting to update Feedback ID: " + id);
                    Feedback feedback = new Feedback(
                            id,
                            feedbackPanel.getStudentField().getText(),
                            (Integer) feedbackPanel.getRatingCombo().getSelectedItem(),
                            feedbackPanel.getCommentsArea().getText()
                    );
                    feedbackController.updateFeedback(id, feedback);
                    updateFeedbackView();
                    updateDashboard();
                    JOptionPane.showMessageDialog(view, "Feedback updated successfully!");
                } else {
                    JOptionPane.showMessageDialog(view, "Please select feedback to edit.", "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        feedbackPanel.getDeleteButton().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int selectedRow = feedbackPanel.getTable().getSelectedRow();
                if (selectedRow >= 0) {
                    int confirm = JOptionPane.showConfirmDialog(view, "Are you sure you want to delete this feedback?", "Confirm Delete", JOptionPane.YES_NO_OPTION);
                    if (confirm == JOptionPane.YES_OPTION) {
                        int id = (Integer) feedbackPanel.getTableModel().getValueAt(selectedRow, 0);
                        System.out.println("Attempting to delete Feedback ID: " + id);
                        feedbackController.deleteFeedback(id);
                        updateFeedbackView();
                        updateDashboard();
                        JOptionPane.showMessageDialog(view, "Feedback deleted successfully!");
                    }
                } else {
                    JOptionPane.showMessageDialog(view, "Please select feedback to delete.", "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });
    }

    private boolean validateAppointmentForm() {
        if (appointmentPanel.getStudentField().getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(view, "Student name cannot be empty.", "Validation Error", JOptionPane.ERROR_MESSAGE);
            return false;
        }
        if (appointmentPanel.getCounselorCombo().getSelectedIndex() == 0) {
            JOptionPane.showMessageDialog(view, "Please select a counselor.", "Validation Error", JOptionPane.ERROR_MESSAGE);
            return false;
        }
        if (!appointmentPanel.getDateField().getText().matches("\\d{4}-\\d{2}-\\d{2}")) {
            JOptionPane.showMessageDialog(view, "Date must be in YYYY-MM-DD format.", "Validation Error", JOptionPane.ERROR_MESSAGE);
            return false;
        }
        if (!appointmentPanel.getTimeField().getText().matches("\\d{2}:\\d{2}")) {
            JOptionPane.showMessageDialog(view, "Time must be in HH:MM format.", "Validation Error", JOptionPane.ERROR_MESSAGE);
            return false;
        }
        return true;
    }

    private boolean validateCounselorForm() {
        if (counselorPanel.getNameField().getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(view, "Counselor name cannot be empty.", "Validation Error", JOptionPane.ERROR_MESSAGE);
            return false;
        }
        if (counselorPanel.getSpecializationField().getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(view, "Specialization cannot be empty.", "Validation Error", JOptionPane.ERROR_MESSAGE);
            return false;
        }
        if (counselorPanel.getAvailabilityField().getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(view, "Availability cannot be empty.", "Validation Error", JOptionPane.ERROR_MESSAGE);
            return false;
        }
        return true;
    }

    private boolean validateFeedbackForm() {
        if (feedbackPanel.getStudentField().getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(view, "Student name cannot be empty.", "Validation Error", JOptionPane.ERROR_MESSAGE);
            return false;
        }
        if (feedbackPanel.getCommentsArea().getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(view, "Comments cannot be empty.", "Validation Error", JOptionPane.ERROR_MESSAGE);
            return false;
        }
        return true;
    }

    private void clearAppointmentForm() {
        appointmentPanel.getStudentField().setText("");
        appointmentPanel.getCounselorCombo().setSelectedIndex(0);
        appointmentPanel.getDateField().setText("");
        appointmentPanel.getTimeField().setText("");
        appointmentPanel.getStatusCombo().setSelectedIndex(0);
    }

    private void clearCounselorForm() {
        counselorPanel.getNameField().setText("");
        counselorPanel.getSpecializationField().setText("");
        counselorPanel.getAvailabilityField().setText("");
    }

    private void clearFeedbackForm() {
        feedbackPanel.getStudentField().setText("");
        feedbackPanel.getRatingCombo().setSelectedIndex(0);
        feedbackPanel.getCommentsArea().setText("");
    }

    private void updateAppointmentView() {
        DefaultTableModel appointmentModel = model.getAppointmentTableModel();
        if (appointmentModel != null) {
            appointmentPanel.getTableModel().setDataVector(appointmentModel.getDataVector(), new Vector<>(List.of(APPOINTMENT_COLUMNS)));
        }
    }

    private void updateCounselorView() {
        DefaultTableModel counselorModel = model.getCounselorTableModel();
        if (counselorModel != null) {
            counselorPanel.getTableModel().setDataVector(counselorModel.getDataVector(), new Vector<>(List.of(COUNSELOR_COLUMNS)));
        }
    }

    private void updateFeedbackView() {
        DefaultTableModel feedbackModel = model.getFeedbackTableModel();
        if (feedbackModel != null) {
            feedbackPanel.getTableModel().setDataVector(feedbackModel.getDataVector(), new Vector<>(List.of(FEEDBACK_COLUMNS)));
        }
    }

    private List<String> getCounselorNames() {
        List<String> names = new ArrayList<>();
        names.add("Select Counselor"); // Default option
        for (Counselor counselor : model.getCounselors()) {
            names.add(counselor.getName());
        }
        return names;
    }

    public void refreshCounselorDropDown() {
        SwingUtilities.invokeLater(() -> {
            List<String> names = getCounselorNames();
            appointmentPanel.getCounselorCombo().setModel(new DefaultComboBoxModel<>(names.toArray(new String[0])));
            System.out.println("Refreshed counselor drop-down with " + names.size() + " options: " + String.join(", ", names));
        });
    }

    private void updateViews() {
        updateAppointmentView();
        updateCounselorView();
        updateFeedbackView();
    }
}