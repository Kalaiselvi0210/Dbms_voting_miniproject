package javaapplication6;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

/**
 * Voter page: view candidates and cast vote once.
 */
public class VotingPage extends JFrame {

    private final JFrame loginFrame;
    private final Voter voter;

    private final CandidateDAO candidateDAO = new CandidateDAO();
    private final VoteDAO voteDAO = new VoteDAO();

    private final DefaultTableModel model = new DefaultTableModel(
            new String[]{"Candidate ID", "Name", "Party", "Age", "Symbol", "Booth ID"}, 0);
    private final JTable table = new JTable(model);

    public VotingPage(JFrame loginFrame, Voter voter) {
        this.loginFrame = loginFrame;
        this.voter = voter;

        setTitle("Voting Page - " + voter.getName());
        setSize(860, 500);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        JLabel welcome = new JLabel("Welcome " + voter.getName() + " (Booth: " + voter.getBoothId() + ")", SwingConstants.CENTER);
        JButton voteBtn = new JButton("Vote Selected Candidate");
        JButton logoutBtn = new JButton("Logout");

        voteBtn.addActionListener(e -> castVote());
        logoutBtn.addActionListener(e -> logout());

        JPanel top = new JPanel(new BorderLayout());
        top.add(welcome, BorderLayout.CENTER);

        JPanel bottom = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        bottom.add(voteBtn);
        bottom.add(logoutBtn);

        add(top, BorderLayout.NORTH);
        add(new JScrollPane(table), BorderLayout.CENTER);
        add(bottom, BorderLayout.SOUTH);

        loadCandidates();
        if (voter.isHasVoted()) {
            voteBtn.setEnabled(false);
            JOptionPane.showMessageDialog(this, "You have already voted.");
        }
    }

    private void loadCandidates() {
        model.setRowCount(0);
        List<Candidate> list = candidateDAO.getCandidatesByBooth(voter.getBoothId());
        for (Candidate c : list) {
            model.addRow(new Object[]{c.getCandidateId(), c.getName(), c.getParty(), c.getAge(), c.getSymbol(), c.getBoothId()});
        }
    }

    private void castVote() {
        if (voter.isHasVoted()) {
            JOptionPane.showMessageDialog(this, "You have already voted.");
            return;
        }

        int row = table.getSelectedRow();
        if (row == -1) {
            JOptionPane.showMessageDialog(this, "Please select a candidate.");
            return;
        }

        int candidateId = (int) model.getValueAt(row, 0);
        VoteDAO.VoteStatus status = voteDAO.castVote(voter.getVoterId(), candidateId, voter.getBoothId());

        switch (status) {
            case SUCCESS -> {
                voter.setHasVoted(true);
                JOptionPane.showMessageDialog(this, "Vote cast successfully. Thank you!");
            }
            case ALREADY_VOTED -> JOptionPane.showMessageDialog(this, "You have already voted.");
            case INVALID_CANDIDATE -> JOptionPane.showMessageDialog(this, "Invalid candidate for your booth.");
            default -> JOptionPane.showMessageDialog(this, "Vote failed due to system error.");
        }
    }

    private void logout() {
        dispose();
        if (loginFrame != null) loginFrame.setVisible(true);
    }
}
