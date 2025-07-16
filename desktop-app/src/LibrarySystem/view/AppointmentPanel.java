package LibrarySystem.view;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

public class AppointmentPanel extends JPanel {
    private JTextField studentField;
    private JComboBox<String> counselorCombo;
    private JTextField dateField;
    private JTextField timeField;
    private JComboBox<String> statusCombo;
    private JTable appointmentTable;
    private DefaultTableModel tableModel;
    private JButton bookButton;
    private JButton updateButton;
    private JButton cancelButton;

    public AppointmentPanel() {
        setLayout(new BorderLayout(10, 10));
        setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        // Form panel
        JPanel formPanel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        studentField = new JTextField(20);
        counselorCombo = new JComboBox<>();
        dateField = new JTextField(10);
        timeField = new JTextField(5);
        statusCombo = new JComboBox<>(new String[]{"Scheduled", "Completed", "Canceled"});
        bookButton = new JButton("Book Appointment");
        updateButton = new JButton("Update Appointment");
        cancelButton = new JButton("Cancel Appointment");

        // Add components to form panel
        gbc.gridx = 0; gbc.gridy = 0; formPanel.add(new JLabel("Student Name:"), gbc);
        gbc.gridx = 1; formPanel.add(studentField, gbc);
        gbc.gridx = 0; gbc.gridy = 1; formPanel.add(new JLabel("Counselor:"), gbc);
        gbc.gridx = 1; formPanel.add(counselorCombo, gbc);
        gbc.gridx = 0; gbc.gridy = 2; formPanel.add(new JLabel("Date (YYYY-MM-DD):"), gbc);
        gbc.gridx = 1; formPanel.add(dateField, gbc);
        gbc.gridx = 0; gbc.gridy = 3; formPanel.add(new JLabel("Time (HH:MM):"), gbc);
        gbc.gridx = 1; formPanel.add(timeField, gbc);
        gbc.gridx = 0; gbc.gridy = 4; formPanel.add(new JLabel("Status:"), gbc);
        gbc.gridx = 1; formPanel.add(statusCombo, gbc);
        gbc.gridx = 0; gbc.gridy = 5; gbc.gridwidth = 2; formPanel.add(bookButton, gbc);

        // Table panel
        String[] columns = {"ID", "Student", "Counselor", "Date", "Time", "Status"};
        tableModel = new DefaultTableModel(columns, 0);
        appointmentTable = new JTable(tableModel);
        JScrollPane tableScrollPane = new JScrollPane(appointmentTable);

        // Button panel
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        buttonPanel.add(updateButton);
        buttonPanel.add(cancelButton);

        // Add panels
        add(formPanel, BorderLayout.NORTH);
        add(tableScrollPane, BorderLayout.CENTER);
        add(buttonPanel, BorderLayout.SOUTH);

        counselorCombo.addItem("Select Counselor");
    }

    // Getters
    public JTextField getStudentField() { return studentField; }
    public JComboBox<String> getCounselorCombo() { return counselorCombo; }
    public JTextField getDateField() { return dateField; }
    public JTextField getTimeField() { return timeField; }
    public JComboBox<String> getStatusCombo() { return statusCombo; }
    public JTable getTable() { return appointmentTable; }
    public DefaultTableModel getTableModel() { return tableModel; }
    public JButton getBookButton() { return bookButton; }
    public JButton getUpdateButton() { return updateButton; }
    public JButton getCancelButton() { return cancelButton; }
}