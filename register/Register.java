package register;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.sql.*;
import java.util.regex.Pattern;

public class Register extends JFrame {
    private JTextField studentIdField, emailField, fullNameField;
    private JPasswordField passwordField;
    private JButton registerButton, loginButton;
    private static final Color PRIMARY_COLOR = new Color(41, 128, 185);
    private static final Color BACKGROUND_COLOR = new Color(236, 240, 241);
    private static final Font LABEL_FONT = new Font("Segoe UI", Font.PLAIN, 14);
    private static final Font BUTTON_FONT = new Font("Segoe UI", Font.BOLD, 14);

    public Register() {
        setTitle("Student Registration");
        setSize(500, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        getContentPane().setBackground(BACKGROUND_COLOR);
        initializeComponents();
    }

    private void initializeComponents() {
        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.setBackground(BACKGROUND_COLOR);
        mainPanel.setBorder(new EmptyBorder(20, 20, 20, 20));

        // Title Panel
        JPanel titlePanel = new JPanel();
        titlePanel.setBackground(BACKGROUND_COLOR);
        JLabel titleLabel = new JLabel("Student Registration");
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 24));
        titleLabel.setForeground(PRIMARY_COLOR);
        titlePanel.add(titleLabel);

        // Form Panel
        JPanel formPanel = new JPanel(new GridBagLayout());
        formPanel.setBackground(BACKGROUND_COLOR);
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        // Student ID
        gbc.gridx = 0;
        gbc.gridy = 0;
        JLabel studentIdLabel = new JLabel("Student ID");
        studentIdLabel.setFont(LABEL_FONT);
        formPanel.add(studentIdLabel, gbc);
        gbc.gridx = 1;
        studentIdField = createStyledTextField("Enter 7-digit student ID");
        formPanel.add(studentIdField, gbc);

        // Email
        gbc.gridx = 0;
        gbc.gridy = 1;
        JLabel emailLabel = new JLabel("Email");
        emailLabel.setFont(LABEL_FONT);
        formPanel.add(emailLabel, gbc);
        gbc.gridx = 1;
        emailField = createStyledTextField("*******@student.eelu.edu.eg");
        formPanel.add(emailField, gbc);

        // Password
        gbc.gridx = 0;
        gbc.gridy = 2;
        JLabel passwordLabel = new JLabel("Password");
        passwordLabel.setFont(LABEL_FONT);
        formPanel.add(passwordLabel, gbc);
        gbc.gridx = 1;
        passwordField = createStyledPasswordField();
        formPanel.add(passwordField, gbc);

        // Full Name
        gbc.gridx = 0;
        gbc.gridy = 3;
        JLabel fullNameLabel = new JLabel("Full Name");
        fullNameLabel.setFont(LABEL_FONT);
        formPanel.add(fullNameLabel, gbc);
        gbc.gridx = 1;
        fullNameField = createStyledTextField("Enter your full name");
        formPanel.add(fullNameField, gbc);

        // Buttons Panel
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 0));
        buttonPanel.setBackground(BACKGROUND_COLOR);
        registerButton = createStyledButton("Register", PRIMARY_COLOR);
        loginButton = createStyledButton("Login", new Color(52, 152, 219));
        buttonPanel.add(registerButton);
        buttonPanel.add(loginButton);

        // Add action listeners
        registerButton.addActionListener(e -> handleRegistration());
        loginButton.addActionListener(e -> openLoginPage());

        // Add components to main panel
        mainPanel.add(titlePanel, BorderLayout.NORTH);
        mainPanel.add(formPanel, BorderLayout.CENTER);
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

    private void handleRegistration() {
        String studentId = studentIdField.getText().trim();
        String email = emailField.getText().trim();
        String password = new String(passwordField.getPassword());
        String fullName = fullNameField.getText().trim();

        // Validation
        if (!validateInput(studentId, email, password, fullName)) {
            return;
        }

        try {
            if (registerUser(studentId, email, password, fullName)) {
                JOptionPane.showMessageDialog(this, "Registration successful!");
                openLoginPage();
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(this, "Error: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private boolean validateInput(String studentId, String email, String password, String fullName) {
        // Check empty fields
        if (studentId.isEmpty() || email.isEmpty() || password.isEmpty() || fullName.isEmpty()) {
            JOptionPane.showMessageDialog(this, "All fields are required!");
            return false;
        }

        // Validate Student ID (7 digits)
        if (!Pattern.matches("\\d{7}", studentId)) {
            JOptionPane.showMessageDialog(this, "Student ID must be 7 digits!");
            return false;
        }

        // Validate Email format (2200818@student.eelu.edu.eg)
        if (!Pattern.matches("^\\d{7}@student\\.eelu\\.edu\\.eg$", email)) {
            JOptionPane.showMessageDialog(this, "Email must be in format: *******@student.eelu.edu.eg!");
            return false;
        }

        // Check if email matches student ID
        String emailPrefix = email.split("@")[0];
        if (!emailPrefix.equals(studentId)) {
            JOptionPane.showMessageDialog(this, "Email prefix must match your student ID!");
            return false;
        }

        // Validate Password (minimum 6 characters, at least one number and one letter)
        if (!Pattern.matches("^(?=.*[A-Za-z])(?=.*\\d)[A-Za-z\\d]{6,}$", password)) {
            JOptionPane.showMessageDialog(this,
                    "Password must be at least 6 characters and contain both letters and numbers!");
            return false;
        }

        // Validate Full Name (only letters and spaces)
        if (!Pattern.matches("^[a-zA-Z\\s]+$", fullName)) {
            JOptionPane.showMessageDialog(this, "Full name can only contain letters and spaces!");
            return false;
        }

        return true;
    }

    private boolean registerUser(String studentId, String email, String password, String fullName) throws SQLException {
        String query = "INSERT INTO Students (student_id, email, password, full_name) VALUES (?, ?, ?, ?)";

        try (Connection conn = db.Database.getConnection();
                PreparedStatement pstmt = conn.prepareStatement(query)) {

            pstmt.setString(1, studentId);
            pstmt.setString(2, email);
            pstmt.setString(3, password); // In a real application, you should hash the password
            pstmt.setString(4, fullName);

            return pstmt.executeUpdate() > 0;
        }
    }

    private void openLoginPage() {
        this.dispose();
        new loginScreen.Login();
    }

    // public static void main(String[] args) {
    // SwingUtilities.invokeLater(() -> {
    // new Register().setVisible(true);
    // });
    // }
}
