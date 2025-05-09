package showAll;

import javax.swing.*;

import constants.*;

import java.awt.*;
import java.sql.*;


public class ShowAllFeedback extends JFrame {
    private JList<FeedbackEntry> feedbackList;
    private DefaultListModel<FeedbackEntry> listModel;
    private JPanel buttonPanel;
    private ActionButton updateButton;
    private ActionButton deleteButton;
    private String studentId;

    public ShowAllFeedback(String studentId) {
        setTitle("All Feedback");
        setSize(900, 700);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(true);
        initializeComponents();
        Actions.loadAllFeedback(studentId, listModel, this);
    }

    private void initializeComponents() {
        // Set the look and feel to system default
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
            e.printStackTrace();
        }

        JPanel mainPanel = new JPanel(new BorderLayout(10, 10));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        mainPanel.setBackground(new Color(245, 245, 245));

        // Title Panel
        JPanel titlePanel = new JPanel(new BorderLayout());
        titlePanel.setBackground(new Color(245, 245, 245));
        JLabel titleLabel = new JLabel("All Student Feedback", SwingConstants.CENTER);
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 24));
        titlePanel.add(titleLabel, BorderLayout.CENTER);
        mainPanel.add(titlePanel, BorderLayout.NORTH);

        // Feedback list
        listModel = new DefaultListModel<>();
        feedbackList = new JList<>(listModel);
        feedbackList.setCellRenderer(new FeedbackListRenderer());
        feedbackList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        feedbackList.setBorder(BorderFactory.createEmptyBorder());
        feedbackList.setFont(new Font("Segoe UI", Font.PLAIN, 14));

        // Add list selection listener for control the update and delete button
        feedbackList.addListSelectionListener(e -> {
            boolean hasSelection = feedbackList.getSelectedValue() != null;
            updateButton.setVisible(hasSelection);
            deleteButton.setVisible(hasSelection);
        });
        
        JScrollPane scrollPane = new JScrollPane(feedbackList);
        scrollPane.setBorder(BorderFactory.createEmptyBorder());
        mainPanel.add(scrollPane, BorderLayout.CENTER);

        // Button panel
        buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 10));
        buttonPanel.setBackground(new Color(245, 245, 245));

        updateButton = new ActionButton("Update",e ->{});
        deleteButton = new ActionButton("Delete",e ->{});
        updateButton.setVisible(false);
        deleteButton.setVisible(false);

        updateButton.addActionListener(e -> {
            FeedbackEntry selected = feedbackList.getSelectedValue();
            if (selected != null) {
                showUpdateDialog(selected);
            }
        });

        deleteButton.addActionListener(e -> {
            FeedbackEntry selected = feedbackList.getSelectedValue();
            if (selected != null) {
                int confirm = JOptionPane.showConfirmDialog(
                        this,
                        "Are you sure you want to delete this feedback?",
                        "Confirm Delete",
                        JOptionPane.YES_NO_OPTION,
                        JOptionPane.WARNING_MESSAGE);

                if (confirm == JOptionPane.YES_OPTION) {
                    try {
                        Actions.deleteFeedback(selected.getId());
                        Actions.loadAllFeedback(studentId, listModel, this); // Reload the list after deletion
                    } catch (SQLException ex) {
                        JOptionPane.showMessageDialog(
                                this,
                                "Error deleting feedback: " + ex.getMessage(),
                                "Error",
                                JOptionPane.ERROR_MESSAGE);
                    }
                }
            }
        });

        buttonPanel.add(updateButton);
        buttonPanel.add(deleteButton);
        mainPanel.add(buttonPanel, BorderLayout.SOUTH);

        add(mainPanel);
    }

    private void showUpdateDialog(FeedbackEntry feedback) {
        JDialog dialog = new JDialog(this, "Update Feedback", true);
        dialog.setLayout(new BorderLayout(10, 10));
        dialog.getContentPane().setBackground(new Color(245, 245, 245));

        JPanel contentPanel = new JPanel(new BorderLayout(10, 10));
        contentPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        contentPanel.setBackground(new Color(245, 245, 245));

        JTextArea textArea = new JTextArea(feedback.getText(), 10, 40);
        textArea.setLineWrap(true);
        textArea.setWrapStyleWord(true);
        textArea.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        textArea.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(200, 200, 200)),
                BorderFactory.createEmptyBorder(5, 5, 5, 5)));

        JScrollPane scrollPane = new JScrollPane(textArea);
        scrollPane.setBorder(BorderFactory.createEmptyBorder());
        contentPanel.add(scrollPane, BorderLayout.CENTER);

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 10, 0));
        buttonPanel.setBackground(new Color(245, 245, 245));

        ActionButton saveButton = new ActionButton("Save",e -> {
            String newText = textArea.getText().trim();
            if (!newText.isEmpty()) {
                try {
                    Actions.updateFeedback(feedback.getId(), newText);
                    dialog.dispose();
                    Actions.loadAllFeedback(studentId, listModel, this); // Reload the list to show updated feedback
                } catch (SQLException ex) {
                    JOptionPane.showMessageDialog(dialog,
                            "Error updating feedback: " + ex.getMessage(),
                            "Error",
                            JOptionPane.ERROR_MESSAGE);
                }
            } else {
                JOptionPane.showMessageDialog(dialog,
                        "Feedback text cannot be empty",
                        "Error",
                        JOptionPane.ERROR_MESSAGE);
            }
        });
        ActionButton cancelButton = new ActionButton("Cancel",e ->{
            dialog.dispose();
        });

        buttonPanel.add(saveButton);
        buttonPanel.add(cancelButton);
        contentPanel.add(buttonPanel, BorderLayout.SOUTH);

        dialog.add(contentPanel);
        dialog.pack();
        dialog.setLocationRelativeTo(this);
        dialog.setVisible(true);
    }

}   
