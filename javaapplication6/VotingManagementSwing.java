package javaapplication6;

import javax.swing.*;

/**
 * Application entry point.
 */
public class VotingManagementSwing {
    public static void main(String[] args) {
        try {
            // Load MySQL JDBC Driver
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            JOptionPane.showMessageDialog(null,
                    "MySQL JDBC Driver not found. Please add mysql-connector-j-8.4.0.jar to classpath.",
                    "Driver Error",
                    JOptionPane.ERROR_MESSAGE);
            return;
        }

        SwingUtilities.invokeLater(() -> {
            LoginPage page = new LoginPage();
            page.setVisible(true);
        });
    }
}
