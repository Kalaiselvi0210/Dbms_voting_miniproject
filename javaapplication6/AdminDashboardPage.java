package javaapplication6;

import javax.swing.*;
import java.awt.*;

public class AdminDashboardPage extends JFrame {

    private final JFrame loginFrame;

    public AdminDashboardPage(JFrame loginFrame) {
        this.loginFrame = loginFrame;

        setTitle("Admin Dashboard");
        setSize(450, 320);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        JPanel panel = new JPanel(new GridLayout(5, 1, 10, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        JButton manageCandidatesBtn = new JButton("Candidate Management");
        JButton viewVotersBtn = new JButton("View Voters");
        JButton viewResultsBtn = new JButton("View Results");
        JButton logoutBtn = new JButton("Logout");

        manageCandidatesBtn.addActionListener(e -> new CandidateManagementPage(this).setVisible(true));
        viewVotersBtn.addActionListener(e -> new VoterListPage(this).setVisible(true));
        viewResultsBtn.addActionListener(e -> new ResultPage(this).setVisible(true));
        logoutBtn.addActionListener(e -> logout());

        panel.add(new JLabel("Welcome Admin", SwingConstants.CENTER));
        panel.add(manageCandidatesBtn);
        panel.add(viewVotersBtn);
        panel.add(viewResultsBtn);
        panel.add(logoutBtn);

        add(panel);
    }

    private void logout() {
        dispose();
        if (loginFrame != null) loginFrame.setVisible(true);
    }
}
