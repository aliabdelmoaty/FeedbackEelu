package loginScreen;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.sql.*;

public class Login extends JFrame {
    private JTextField userIdField;
    private JPasswordField passwordField;
    private JButton loginButton, registerButton;
    private static final Color PRIMARY_COLOR = new Color(41, 128, 185);
    private static final Color BACKGROUND_COLOR = new Color(236, 240, 241);
    private static final Font LABEL_FONT = new Font("Segoe UI", Font.PLAIN, 14);
    private static final Font BUTTON_FONT = new Font("Segoe UI", Font.BOLD, 14);

    public Login() {
        setTitle("Student Login");
        setSize(500, 500);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        getContentPane().setBackground(BACKGROUND_COLOR);
        initializeComponents();
        setVisible(true);
    }

    private void initializeComponents() {
        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.setBackground(BACKGROUND_COLOR);
        mainPanel.setBorder(new EmptyBorder(20, 20, 20, 20));

        // Title Panel
        JPanel titlePanel = new JPanel();
        titlePanel.setBackground(BACKGROUND_COLOR);
        JLabel titleLabel = new JLabel("Student Login");
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 24));
        titleLabel.setForeground(PRIMARY_COLOR);
        titlePanel.add(titleLabel);

        // Form Panel
        JPanel formPanel = new JPanel(new GridBagLayout());
        formPanel.setBackground(BACKGROUND_COLOR);
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        // User ID/Email
        gbc.gridx = 0;
        gbc.gridy = 0;
        JLabel userIdLabel = new JLabel("Student ID or Email");
        userIdLabel.setFont(LABEL_FONT);
        formPanel.add(userIdLabel, gbc);

        gbc.gridx = 1;
        userIdField = createStyledTextField("Enter your student ID or email");
        formPanel.add(userIdField, gbc);

        // Password
        gbc.gridx = 0;
        gbc.gridy = 1;
        JLabel passwordLabel = new JLabel("Password");
        passwordLabel.setFont(LABEL_FONT);
        formPanel.add(passwordLabel, gbc);

        gbc.gridx = 1;
        passwordField = createStyledPasswordField();
        formPanel.add(passwordField, gbc);

        mainPanel.add(formPanel, BorderLayout.CENTER);

        // Buttons Panel
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 0));
        buttonPanel.setBackground(BACKGROUND_COLOR);
        loginButton = createStyledButton("Login", PRIMARY_COLOR);
        registerButton = createStyledButton("Register", new Color(52, 152, 219));
        buttonPanel.add(loginButton);
        buttonPanel.add(registerButton);

        // Add action listeners
        loginButton.addActionListener(e -> handleLogin());
        registerButton.addActionListener(e -> openRegistrationPage());

        mainPanel.add(buttonPanel, BorderLayout.SOUTH);
        add(mainPanel);
    }

    private JTextField createStyledTextField(String placeholder) {
        JTextField field = new JTextField(20);
        field.setPreferredSize(new Dimension(300, 35));
        field.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        field.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(PRIMARY_COLOR, 1),
                BorderFactory.createEmptyBorder(5, 10, 5, 10)));

        // Add placeholder text
        field.putClientProperty("JTextField.placeholderText", placeholder);

        return field;
    }

    private JPasswordField createStyledPasswordField() {
        JPasswordField field = new JPasswordField(20);
        field.setPreferredSize(new Dimension(300, 35));
        field.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        field.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(PRIMARY_COLOR, 1),
                BorderFactory.createEmptyBorder(5, 10, 5, 10)));

        // Add placeholder text
        field.putClientProperty("JTextField.placeholderText", "Enter your password");

        return field;
    }

    private JButton createStyledButton(String text, Color color) {
        JButton button = new JButton(text);
        button.setFont(BUTTON_FONT);
        button.setForeground(Color.WHITE);
        button.setBackground(color);
        button.setFocusPainted(false);
        button.setBorderPainted(false);
        button.setPreferredSize(new Dimension(120, 40));

        // Add hover effect
        button.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                button.setBackground(color.darker());
            }

            public void mouseExited(java.awt.event.MouseEvent evt) {
                button.setBackground(color);
            }
        });

        return button;
    }

    private void handleLogin() {
        String userId = userIdField.getText().trim();
        String password = new String(passwordField.getPassword());

        if (userId.isEmpty() || password.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please fill in all fields!");
            return;
        }

        try {
            String studentId = validateCredentials(userId, password);
            if (studentId != null) {
                JOptionPane.showMessageDialog(this, "Login successful!");
                openFeedbackPage(studentId);
            } else {
                JOptionPane.showMessageDialog(this, "Invalid credentials!", "Error", JOptionPane.ERROR_MESSAGE);
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(this, "Error: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private String validateCredentials(String userId, String password) throws SQLException {
        String query = "SELECT student_id FROM Students WHERE (student_id = ? OR email = ?) AND password = ?";

        try (Connection conn = db.Database.getConnection();
                PreparedStatement pstmt = conn.prepareStatement(query)) {

            pstmt.setString(1, userId);
            pstmt.setString(2, userId);
            pstmt.setString(3, password);

            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    return rs.getString("student_id");
                }
            }
        }
        return null;
    }

    private void openFeedbackPage(String studentId) {
        this.dispose();
        new feedback.FeedBack(studentId).setVisible(true);
    }

    private void openRegistrationPage() {
        this.dispose();
        new register.Register().setVisible(true);
    }

    // public static void main(String[] args) {
    // SwingUtilities.invokeLater(() -> {
    // new Login().setVisible(true);
    // });
    // }
}
