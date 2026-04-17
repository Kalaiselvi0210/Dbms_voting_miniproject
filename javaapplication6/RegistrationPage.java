package javaapplication6;

import javax.swing.*;
import java.awt.*;
import java.util.List;

public class RegistrationPage extends JFrame {

    private final JFrame backFrame;
    private final VoterDAO voterDAO = new VoterDAO();
    private final BoothDAO boothDAO = new BoothDAO();

    private final JTextField nameField = new JTextField();
    private final JTextField ageField = new JTextField();
    private final JTextField genderField = new JTextField();
    private final JTextField addressField = new JTextField();
    private final JTextField usernameField = new JTextField();
    private final JPasswordField passwordField = new JPasswordField();
    private final JComboBox<Booth> boothCombo = new JComboBox<>();

    public RegistrationPage(JFrame backFrame) {
        this.backFrame = backFrame;

        setTitle("Voter Registration");
        setSize(520, 500);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        JPanel panel = new JPanel(new GridLayout(9, 2, 8, 8));
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        loadBooths();

        JButton registerBtn = new JButton("Register");
        JButton backBtn = new JButton("Back");
        registerBtn.addActionListener(e -> register());
        backBtn.addActionListener(e -> goBack());

        panel.add(new JLabel("Name:")); panel.add(nameField);
        panel.add(new JLabel("Age:")); panel.add(ageField);
        panel.add(new JLabel("Gender:")); panel.add(genderField);
        panel.add(new JLabel("Address:")); panel.add(addressField);
        panel.add(new JLabel("Username:")); panel.add(usernameField);
        panel.add(new JLabel("Password:")); panel.add(passwordField);
        panel.add(new JLabel("Booth:")); panel.add(boothCombo);
        panel.add(registerBtn); panel.add(backBtn);

        add(panel);
    }

    private void loadBooths() {
        List<Booth> booths = boothDAO.getAllBooths();
        boothCombo.removeAllItems();
        for (Booth b : booths) boothCombo.addItem(b);
    }

    private void register() {
        try {
            String name = nameField.getText().trim();
            int age = Integer.parseInt(ageField.getText().trim());
            String gender = genderField.getText().trim();
            String address = addressField.getText().trim();
            String username = usernameField.getText().trim();
            String password = new String(passwordField.getPassword());
            Booth booth = (Booth) boothCombo.getSelectedItem();

            if (name.isEmpty() || gender.isEmpty() || address.isEmpty() || username.isEmpty() || password.isEmpty() || booth == null) {
                JOptionPane.showMessageDialog(this, "Please fill all fields.");
                return;
            }
            if (age < 18) {
                JOptionPane.showMessageDialog(this, "Age must be 18 or above.");
                return;
            }

            boolean ok = voterDAO.registerVoter(name, age, gender, address, username, password, booth.getBoothId());
            if (ok) {
                JOptionPane.showMessageDialog(this, "Registration successful.");
                goBack();
            } else {
                JOptionPane.showMessageDialog(this, "Registration failed. Username may already exist.");
            }
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Please enter valid numeric age.");
        }
    }

    private void goBack() {
        dispose();
        if (backFrame != null) backFrame.setVisible(true);
    }
}
