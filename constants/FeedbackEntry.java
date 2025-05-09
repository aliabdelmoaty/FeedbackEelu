package constants;

import java.sql.Timestamp;

public class FeedbackEntry {
    final int id;
    final String text;
    final String studentName;
     final Timestamp createdAt;
     final Timestamp updatedAt;
    final String studentId;

    FeedbackEntry(int id, String text, String studentName, Timestamp createdAt, Timestamp updatedAt, String studentId) {
        this.id = id;
        this.text = text;
        this.studentName = studentName;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.studentId = studentId;
    }
    public int getId() {
        return id;}

    public Timestamp getUpdatedAt() {
        return updatedAt;
    }

    public Timestamp getCreatedAt() {
        return createdAt;
    }
    public String getText() {
        return text;
    }
    public String getStudentName() {
        return studentName;
    }
    public String getStudentId() {
        return studentId;
    }
    
}
