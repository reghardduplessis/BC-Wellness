package LibrarySystem.view;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

public class FeedbackPanel extends JPanel {
    private JTextField studentField;
    private JComboBox<Integer> ratingCombo;
    private JTextArea commentsArea;
    private JTable feedbackTable;
    private DefaultTableModel tableModel;
    private JButton submitButton;
    private JButton editButton;
    private JButton deleteButton;

    public FeedbackPanel() {
        setLayout(new BorderLayout(10, 10));
        setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        // Form panel
        JPanel formPanel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        studentField = new JTextField(20);
        ratingCombo = new JComboBox<>(new Integer[]{1, 2, 3, 4, 5});
        commentsArea = new JTextArea(5, 20);
        JScrollPane commentsScroll = new JScrollPane(commentsArea);
        submitButton = new JButton("Submit Feedback");

        // Add components to form panel
        gbc.gridx = 0; gbc.gridy = 0; formPanel.add(new JLabel("Student Name:"), gbc);
        gbc.gridx = 1; formPanel.add(studentField, gbc);
        gbc.gridx = 0; gbc.gridy = 1; formPanel.add(new JLabel("Rating (1-5):"), gbc);
        gbc.gridx = 1; formPanel.add(ratingCombo, gbc);
        gbc.gridx = 0; gbc.gridy = 2; formPanel.add(new JLabel("Comments:"), gbc);
        gbc.gridx = 1; formPanel.add(commentsScroll, gbc);
        gbc.gridx = 0; gbc.gridy = 3; gbc.gridwidth = 2; formPanel.add(submitButton, gbc);

        // Table panel
        String[] columns = {"ID", "Student", "Rating", "Comments"};
        tableModel = new DefaultTableModel(columns, 0);
        feedbackTable = new JTable(tableModel);
        JScrollPane tableScrollPane = new JScrollPane(feedbackTable);

        // Button panel
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        editButton = new JButton("Edit Feedback");
        deleteButton = new JButton("Delete Feedback");
        buttonPanel.add(editButton);
        buttonPanel.add(deleteButton);

        // Add panels
        add(formPanel, BorderLayout.NORTH);
        add(tableScrollPane, BorderLayout.CENTER);
        add(buttonPanel, BorderLayout.SOUTH);
    }

    // Getters
    public JTextField getStudentField() { return studentField; }
    public JComboBox<Integer> getRatingCombo() { return ratingCombo; }
    public JTextArea getCommentsArea() { return commentsArea; }
    public JTable getTable() { return feedbackTable; }
    public DefaultTableModel getTableModel() { return tableModel; }
    public JButton getSubmitButton() { return submitButton; }
    public JButton getEditButton() { return editButton; }
    public JButton getDeleteButton() { return deleteButton; }
}