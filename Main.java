import db.Database;
import loginScreen.Login;

import javax.swing.SwingUtilities;

public class Main {
    public static void main(String[] args) {
        System.out.println("Starting application...");

        Database.initializeDatabase();

        SwingUtilities.invokeLater(() -> {
            System.out.println("Opening login screen...");
            // setVisible(true);
            new Login();
            System.out.println("Login screen opened successfully.");
        });
    }
}
