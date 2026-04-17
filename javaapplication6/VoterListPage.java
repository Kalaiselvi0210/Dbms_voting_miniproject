package javaapplication6;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

/**
 * Admin page to view all voters.
 */
public class VoterListPage extends JFrame {

    private final VoterDAO voterDAO = new VoterDAO();
    private final DefaultTableModel model = new DefaultTableModel(
            new String[]{"Voter ID", "Name", "Age", "Gender", "Address", "Username", "Has Voted", "Booth ID"}, 0);

    public VoterListPage(JFrame parent) {
        setTitle("Voter List");
        setSize(900, 450);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        JTable table = new JTable(model);
        JButton refreshBtn = new JButton("Refresh");
        refreshBtn.addActionListener(e -> loadVoters());

        setLayout(new BorderLayout());
        add(new JScrollPane(table), BorderLayout.CENTER);
        add(refreshBtn, BorderLayout.SOUTH);

        loadVoters();
    }

    private void loadVoters() {
        model.setRowCount(0);
        List<Voter> voters = voterDAO.getAllVoters();
        for (Voter v : voters) {
            model.addRow(new Object[]{
                    v.getVoterId(), v.getName(), v.getAge(), v.getGender(), v.getAddress(),
                    v.getUsername(), v.isHasVoted(), v.getBoothId()
            });
        }
    }
}
