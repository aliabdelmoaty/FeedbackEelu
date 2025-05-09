package constants;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import javax.swing.*;
import db.Database;

public class Actions {
    public static void addFeedback(String studentId, String feedbackText, JFrame frame) {
        System.out.println(feedbackText);
        if (studentId == null || feedbackText == null || feedbackText.isEmpty()) {
            showError(frame, "Student ID and Feedback Text cannot be null");
            return;
        }

        try {
            saveFeedback(studentId, feedbackText);
            showSuccess(frame, "Feedback added successfully");
        } catch (Exception e) {
            showError(frame, "Error adding feedback");
            e.printStackTrace();
        }
    }

    public static List<FeedbackEntry> showAllFeedback(String studentId, JFrame frame) {
        try {
            return getAllFeedback(studentId);
        } catch (SQLException e) {
            e.printStackTrace();
            showError(frame, "Error showing all feedback");
            return new ArrayList<>();
        }
    }

    private static void saveFeedback(String studentId, String feedbackText) throws SQLException {
        String query = "INSERT INTO Feedback (student_id, feedback_text) VALUES (?, ?)";
        try (Connection conn = Database.getConnection();
                PreparedStatement pstmt = conn.prepareStatement(query)) {
            pstmt.setString(1, studentId);
            pstmt.setString(2, feedbackText);
            pstmt.executeUpdate();
        }
    }

    private static List<FeedbackEntry> getAllFeedback(String studentId) throws SQLException {
        List<FeedbackEntry> feedbacks = new ArrayList<>();
        String query = """
                SELECT f.feedback_id, f.feedback_text, f.created_at, f.updated_at, s.full_name
                FROM Feedback f
                JOIN Students s ON f.student_id = s.student_id
                ORDER BY f.created_at DESC""";

        try (Connection conn = Database.getConnection();
                PreparedStatement pstmt = conn.prepareStatement(query);
                ResultSet rs = pstmt.executeQuery()) {

            while (rs.next()) {
                feedbacks.add(new FeedbackEntry(
                        rs.getInt("feedback_id"),
                        rs.getString("feedback_text"),
                        rs.getString("full_name"),
                        rs.getTimestamp("created_at"),
                        rs.getTimestamp("updated_at"),
                        studentId));
            }
        }
        return feedbacks;
    }

    public static void loadAllFeedback(String studentId, DefaultListModel<FeedbackEntry> listModel, JFrame frame) {
        listModel.clear();
        try {
            List<FeedbackEntry> feedbacks = getAllFeedback(studentId);
            feedbacks.forEach(listModel::addElement);
        } catch (SQLException ex) {
            showError(frame, "Error: " + ex.getMessage());
        }
    }

    public static void deleteFeedback(int feedbackId) throws SQLException {
        String query = "DELETE FROM Feedback WHERE feedback_id = ?";
        try (Connection conn = Database.getConnection();
                PreparedStatement pstmt = conn.prepareStatement(query)) {
            pstmt.setInt(1, feedbackId);
            pstmt.executeUpdate();
        }
    }

    public static void updateFeedback(int feedbackId, String newText) throws SQLException {
        String query = """
                UPDATE Feedback
                SET feedback_text = ?, updated_at = CURRENT_TIMESTAMP
                WHERE feedback_id = ?""";

        try (Connection conn = Database.getConnection();
                PreparedStatement pstmt = conn.prepareStatement(query)) {
            pstmt.setString(1, newText);
            pstmt.setInt(2, feedbackId);
            pstmt.executeUpdate();
        }
    }

    private static void showError(JFrame frame, String message) {
        JOptionPane.showMessageDialog(frame, message, "Error", JOptionPane.ERROR_MESSAGE);
    }

    private static void showSuccess(JFrame frame, String message) {
        JOptionPane.showMessageDialog(frame, message, "Success", JOptionPane.INFORMATION_MESSAGE);
    }
}
