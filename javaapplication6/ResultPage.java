package javaapplication6;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

/**
 * Page to display election results.
 */
public class ResultPage extends JFrame {

    private final ResultDAO resultDAO = new ResultDAO();
    private final DefaultTableModel model = new DefaultTableModel(
            new String[]{"Candidate ID", "Candidate Name", "Party", "Booth", "Total Votes"}, 0);

    public ResultPage(JFrame parent) {
        setTitle("Election Results");
        setSize(800, 450);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        JTable table = new JTable(model);
        JButton refreshBtn = new JButton("Refresh Results");
        refreshBtn.addActionListener(e -> loadResults());

        setLayout(new BorderLayout());
        add(new JScrollPane(table), BorderLayout.CENTER);
        add(refreshBtn, BorderLayout.SOUTH);

        loadResults();
    }

    private void loadResults() {
        model.setRowCount(0);
        List<ResultRecord> list = resultDAO.getAllResults();
        for (ResultRecord r : list) {
            model.addRow(new Object[]{
                    r.getCandidateId(), r.getCandidateName(), r.getParty(), r.getBoothName(), r.getTotalVotes()
            });
        }
    }
}
