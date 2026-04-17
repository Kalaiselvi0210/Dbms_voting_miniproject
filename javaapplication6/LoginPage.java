package javaapplication6;

import javax.swing.*;
import java.awt.*;

/**
 * Login page for both admin and voter.
 */
public class LoginPage extends JFrame {

    private final JTextField adminUserField = new JTextField();
    private final JPasswordField adminPassField = new JPasswordField();

    private final JTextField voterUserField = new JTextField();
    private final JPasswordField voterPassField = new JPasswordField();

    private final AdminDAO adminDAO = new AdminDAO();
    private final VoterDAO voterDAO = new VoterDAO();

    public LoginPage() {
        setTitle("Voting DBMS - Login");
        setSize(700, 430);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel main = new JPanel(new GridLayout(1, 2, 12, 12));
        main.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        JPanel adminPanel = createAdminPanel();
        JPanel voterPanel = createVoterPanel();

        main.add(adminPanel);
        main.add(voterPanel);
        add(main);
    }

    private JPanel createAdminPanel() {
        JPanel panel = new JPanel(new GridLayout(5, 1, 8, 8));
        panel.setBorder(BorderFactory.createTitledBorder("Admin Login"));

        JButton loginBtn = new JButton("Login as Admin");
        loginBtn.addActionListener(e -> doAdminLogin());

        panel.add(new JLabel("Username:"));
        panel.add(adminUserField);
        panel.add(new JLabel("Password:"));
        panel.add(adminPassField);
        panel.add(loginBtn);
        return panel;
    }

    private JPanel createVoterPanel() {
        JPanel panel = new JPanel(new GridLayout(6, 1, 8, 8));
        panel.setBorder(BorderFactory.createTitledBorder("Voter Login"));

        JButton loginBtn = new JButton("Login as Voter");
        JButton registerBtn = new JButton("New Voter? Register");

        loginBtn.addActionListener(e -> doVoterLogin());
        registerBtn.addActionListener(e -> {
            new RegistrationPage(this).setVisible(true);
            setVisible(false);
        });

        panel.add(new JLabel("Username:"));
        panel.add(voterUserField);
        panel.add(new JLabel("Password:"));
        panel.add(voterPassField);
        panel.add(loginBtn);
        panel.add(registerBtn);
        return panel;
    }

    private void doAdminLogin() {
        String username = adminUserField.getText().trim();
        String password = new String(adminPassField.getPassword());
        if (username.isEmpty() || password.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Enter admin username and password.");
            return;
        }

        if (adminDAO.login(username, password)) {
            JOptionPane.showMessageDialog(this, "Admin login successful.");
            new AdminDashboardPage(this).setVisible(true);
            setVisible(false);
        } else {
            JOptionPane.showMessageDialog(this, "Invalid admin credentials.");
        }
    }

    private void doVoterLogin() {
        String username = voterUserField.getText().trim();
        String password = new String(voterPassField.getPassword());
        if (username.isEmpty() || password.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Enter voter username and password.");
            return;
        }

        Voter voter = voterDAO.login(username, password);
        if (voter == null) {
            JOptionPane.showMessageDialog(this, "Invalid voter credentials.");
            return;
        }

        new VotingPage(this, voter).setVisible(true);
        setVisible(false);
    }
}
