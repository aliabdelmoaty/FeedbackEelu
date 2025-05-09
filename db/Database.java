package db;

import java.sql.*;

public class Database {
    private static final String URL = "jdbc:sqlserver://localhost:1433;encrypt=true;trustServerCertificate=true";
    private static final String DB_NAME = "houseplant";
    private static final String USER = "sa";
    private static final String PASSWORD = "123456";
    private static Connection connection;

    public static Connection getConnection() throws SQLException {
        if (connection == null || connection.isClosed()) {
            // First connect without database name to create it if needed
            connection = DriverManager.getConnection(URL, USER, PASSWORD);
            createDatabaseIfNotExists();
            // Then connect to the specific database
            connection = DriverManager.getConnection(URL + ";databaseName=" + DB_NAME, USER, PASSWORD);
        }
        return connection;
    }

    private static void createDatabaseIfNotExists() throws SQLException {
        String createDBQuery = "IF NOT EXISTS (SELECT * FROM sys.databases WHERE name = '" + DB_NAME + "') " +
                "BEGIN " +
                "    CREATE DATABASE " + DB_NAME + " " +
                "END";

        try (Statement stmt = connection.createStatement()) {
            stmt.execute(createDBQuery);
        }
    }

    public static void initializeDatabase() {
        try {
            // Create Students table
            String createStudentsTable = """
                    IF NOT EXISTS (SELECT * FROM sys.tables WHERE name = 'Students')
                    CREATE TABLE Students (
                        student_id VARCHAR(8) PRIMARY KEY,
                        email VARCHAR(100) UNIQUE NOT NULL,
                        password VARCHAR(100) NOT NULL,
                        full_name VARCHAR(100) NOT NULL
                    )""";

            // Create Feedback table
            String createFeedbackTable = """
                    IF NOT EXISTS (SELECT * FROM sys.tables WHERE name = 'Feedback')
                    CREATE TABLE Feedback (
                        feedback_id INT IDENTITY(1,1) PRIMARY KEY,
                        student_id VARCHAR(8) FOREIGN KEY REFERENCES Students(student_id),
                        feedback_text TEXT NOT NULL,
                        created_at DATETIME DEFAULT GETDATE(),
                        updated_at DATETIME DEFAULT GETDATE()
                    )""";

            try (Connection conn = getConnection();
                    Statement stmt = conn.createStatement()) {
                stmt.execute(createStudentsTable);
                stmt.execute(createFeedbackTable);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void closeConnection() {
        try {
            if (connection != null && !connection.isClosed()) {
                connection.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
