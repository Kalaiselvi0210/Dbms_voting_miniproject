package javaapplication6;

import java.sql.*;
import javax.swing.*;

public class FirstExample1 {

    // JDBC driver name and database URL
    static final String JDBC_DRIVER = "com.mysql.cj.jdbc.Driver";
    static final String DB_NAME = "voting_dbms";

    public static void main(String[] args) {

        Connection conn = null;
        Statement stmt = null;

        try {
            // STEP 1: Register JDBC driver
            Class.forName(JDBC_DRIVER);

            // STEP 2: Get credentials
            DbCredentials cred = getCredentials();
            if (cred == null) {
                System.out.println("No credentials provided. Exiting...");
                return;
            }

            // STEP 3: Create database if not exists
            System.out.println("Connecting to MySQL server...");
            conn = DriverManager.getConnection(cred.serverUrl(), cred.user, cred.pass);
            stmt = conn.createStatement();
            stmt.executeUpdate("CREATE DATABASE IF NOT EXISTS " + DB_NAME);
            stmt.close();
            conn.close();

            // STEP 4: Connect to voting_dbms database
            System.out.println("Connecting to " + DB_NAME + " database...");
            conn = DriverManager.getConnection(cred.databaseUrl(), cred.user, cred.pass);

            // STEP 5: Create candidates table if not exists
            System.out.println("Creating candidates table...");
            stmt = conn.createStatement();
            stmt.executeUpdate("""
                CREATE TABLE IF NOT EXISTS candidates (
                    id INT PRIMARY KEY AUTO_INCREMENT,
                    candidate_name VARCHAR(100) NOT NULL UNIQUE,
                    votes INT NOT NULL DEFAULT 0
                )
                """);

            // STEP 6: Insert default candidates
            String[] candidates = {"Alice", "Bob", "Charlie"};
            for (String candidate : candidates) {
                String sql = "INSERT INTO candidates(candidate_name, votes) VALUES (?, 0) "
                        + "ON DUPLICATE KEY UPDATE candidate_name = candidate_name";
                try (PreparedStatement ps = conn.prepareStatement(sql)) {
                    ps.setString(1, candidate);
                    ps.executeUpdate();
                }
            }
            System.out.println("Default candidates available: " + String.join(", ", candidates));

            // STEP 7: Cast sample votes
            System.out.println("\nCasting sample votes...");
            String updateSql = "UPDATE candidates SET votes = votes + 1 WHERE candidate_name = 'Alice'";
            stmt.executeUpdate(updateSql);
            updateSql = "UPDATE candidates SET votes = votes + 2 WHERE candidate_name = 'Bob'";
            stmt.executeUpdate(updateSql);
            updateSql = "UPDATE candidates SET votes = votes + 1 WHERE candidate_name = 'Charlie'";
            stmt.executeUpdate(updateSql);
            System.out.println("Sample votes casted successfully.");

            // STEP 8: Display voting results
            System.out.println("\n========== Current Voting Results ==========");
            String selectSql = "SELECT candidate_name, votes FROM candidates ORDER BY votes DESC, candidate_name";
            ResultSet rs = stmt.executeQuery(selectSql);

            int rank = 1;
            while (rs.next()) {
                String candidateName = rs.getString("candidate_name");
                int votes = rs.getInt("votes");
                System.out.println(rank + ". " + candidateName + " -> " + votes + " vote(s)");
                rank++;
            }

            // STEP 9: Clean-up
            rs.close();
            stmt.close();
            conn.close();

        } catch (SQLException se) {
            System.out.println("MySQL Connection Error:");
            se.printStackTrace();

        } catch (Exception e) {
            System.out.println("General Error:");
            e.printStackTrace();

        } finally {
            try {
                if (stmt != null) stmt.close();
            } catch (SQLException se2) {
                se2.printStackTrace();
            }

            try {
                if (conn != null) conn.close();
            } catch (SQLException se) {
                se.printStackTrace();
            }
        }

        System.out.println("\nVoting management program completed!");
    }

    private static DbCredentials getCredentials() {
        JTextField userField = new JTextField("root", 15);
        JPasswordField passField = new JPasswordField(15);
        JPanel panel = new JPanel();
        panel.add(new JLabel("MySQL User:"));
        panel.add(userField);
        panel.add(new JLabel("Password:"));
        panel.add(passField);

        int result = JOptionPane.showConfirmDialog(
                null,
                panel,
                "MySQL Credentials",
                JOptionPane.OK_CANCEL_OPTION,
                JOptionPane.PLAIN_MESSAGE
        );

        if (result == JOptionPane.OK_OPTION) {
            return new DbCredentials(userField.getText().trim(), new String(passField.getPassword()));
        }
        return null;
    }

    private static class DbCredentials {
        String user;
        String pass;

        DbCredentials(String user, String pass) {
            this.user = user;
            this.pass = pass;
        }

        String serverUrl() {
            return "jdbc:mysql://localhost:3306/?allowPublicKeyRetrieval=true&useSSL=false&serverTimezone=UTC";
        }

        String databaseUrl() {
            return "jdbc:mysql://localhost:3306/" + DB_NAME + "?allowPublicKeyRetrieval=true&useSSL=false&serverTimezone=UTC";
        }
    }
}
