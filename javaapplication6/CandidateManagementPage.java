package javaapplication6;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

public class CandidateManagementPage extends JFrame {

    private final CandidateDAO candidateDAO = new CandidateDAO();
    private final BoothDAO boothDAO = new BoothDAO();

    private final JTextField nameField = new JTextField();
    private final JTextField partyField = new JTextField();
    private final JTextField ageField = new JTextField();
    private final JTextField symbolField = new JTextField();
    private final JComboBox<Booth> boothCombo = new JComboBox<>();

    private final DefaultTableModel model = new DefaultTableModel(
            new String[]{"ID", "Name", "Party", "Age", "Symbol", "Booth"}, 0);
    private final JTable table = new JTable(model);

    public CandidateManagementPage(JFrame parent) {
        setTitle("Candidate Management");
        setSize(850, 500);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        JPanel form = new JPanel(new GridLayout(2, 6, 8, 8));
        form.setBorder(BorderFactory.createTitledBorder("Add Candidate"));

        JButton addBtn = new JButton("Add Candidate");
        JButton deleteBtn = new JButton("Delete Selected");

        form.add(new JLabel("Name"));
        form.add(new JLabel("Party"));
        form.add(new JLabel("Age"));
        form.add(new JLabel("Symbol"));
        form.add(new JLabel("Booth"));
        form.add(new JLabel("Action"));

        form.add(nameField);
        form.add(partyField);
        form.add(ageField);
        form.add(symbolField);
        form.add(boothCombo);
        form.add(addBtn);

        addBtn.addActionListener(e -> addCandidate());
        deleteBtn.addActionListener(e -> deleteCandidate());

        loadBooths();
        loadCandidates();

        JPanel bottom = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        bottom.add(deleteBtn);

        setLayout(new BorderLayout(10, 10));
        add(form, BorderLayout.NORTH);
        add(new JScrollPane(table), BorderLayout.CENTER);
        add(bottom, BorderLayout.SOUTH);
    }

    private void loadBooths() {
        boothCombo.removeAllItems();
        for (Booth b : boothDAO.getAllBooths()) boothCombo.addItem(b);
    }

    private void loadCandidates() {
        model.setRowCount(0);
        List<Candidate> list = candidateDAO.getAllCandidates();
        for (Candidate c : list) {
            model.addRow(new Object[]{c.getCandidateId(), c.getName(), c.getParty(), c.getAge(), c.getSymbol(), c.getBoothId()});
        }
    }

    private void addCandidate() {
        try {
            String name = nameField.getText().trim();
            String party = partyField.getText().trim();
            int age = Integer.parseInt(ageField.getText().trim());
            String symbol = symbolField.getText().trim();
            Booth booth = (Booth) boothCombo.getSelectedItem();

            if (name.isEmpty() || party.isEmpty() || symbol.isEmpty() || booth == null) {
                JOptionPane.showMessageDialog(this, "Please fill all fields.");
                return;
            }
            if (age < 21) {
                JOptionPane.showMessageDialog(this, "Candidate age must be 21+.");
                return;
            }

            boolean ok = candidateDAO.addCandidate(name, party, age, symbol, booth.getBoothId());
            JOptionPane.showMessageDialog(this, ok ? "Candidate added." : "Failed to add candidate.");
            if (ok) {
                nameField.setText("");
                partyField.setText("");
                ageField.setText("");
                symbolField.setText("");
                loadCandidates();
            }
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Enter valid age.");
        }
    }

    private void deleteCandidate() {
        int row = table.getSelectedRow();
        if (row == -1) {
            JOptionPane.showMessageDialog(this, "Select a candidate row first.");
            return;
        }

        int id = (int) model.getValueAt(row, 0);
        boolean ok = candidateDAO.deleteCandidate(id);
        JOptionPane.showMessageDialog(this, ok ? "Candidate deleted." : "Delete failed.");
        if (ok) loadCandidates();
    }
}
