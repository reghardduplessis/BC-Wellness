package LibrarySystem.view;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

public class CounselorPanel extends JPanel {
    private JTextField nameField;
    private JTextField specializationField;
    private JTextField availabilityField;
    private JTable counselorTable;
    private DefaultTableModel tableModel;
    private JButton addButton;
    private JButton updateButton;
    private JButton removeButton;

    public CounselorPanel() {
        setLayout(new BorderLayout(10, 10));
        setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        // Form panel
        JPanel formPanel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        nameField = new JTextField(20);
        specializationField = new JTextField(20);
        availabilityField = new JTextField(20);
        addButton = new JButton("Add Counselor");

        gbc.gridx = 0; gbc.gridy = 0; formPanel.add(new JLabel("Counselor Name:"), gbc);
        gbc.gridx = 1; formPanel.add(nameField, gbc);
        gbc.gridx = 0; gbc.gridy = 1; formPanel.add(new JLabel("Specialization:"), gbc);
        gbc.gridx = 1; formPanel.add(specializationField, gbc);
        gbc.gridx = 0; gbc.gridy = 2; formPanel.add(new JLabel("Availability:"), gbc);
        gbc.gridx = 1; formPanel.add(availabilityField, gbc);
        gbc.gridx = 0; gbc.gridy = 3; gbc.gridwidth = 2; formPanel.add(addButton, gbc);

        // Table panel
        String[] columns = {"Name", "Specialization", "Availability"};
        tableModel = new DefaultTableModel(columns, 0);
        counselorTable = new JTable(tableModel);
        JScrollPane tableScrollPane = new JScrollPane(counselorTable);

        // Button panel
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        updateButton = new JButton("Update Counselor");
        removeButton = new JButton("Remove Counselor");
        buttonPanel.add(updateButton);
        buttonPanel.add(removeButton);

        // Add panels
        add(formPanel, BorderLayout.NORTH);
        add(tableScrollPane, BorderLayout.CENTER);
        add(buttonPanel, BorderLayout.SOUTH);
    }

    // Getters
    public JTextField getNameField() { return nameField; }
    public JTextField getSpecializationField() { return specializationField; }
    public JTextField getAvailabilityField() { return availabilityField; }
    public JTable getTable() { return counselorTable; }
    public DefaultTableModel getTableModel() { return tableModel; }
    public JButton getAddButton() { return addButton; }
    public JButton getUpdateButton() { return updateButton; }
    public JButton getRemoveButton() { return removeButton; }
}