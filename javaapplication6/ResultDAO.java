package javaapplication6;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ResultDAO {
    public List<ResultRecord> getAllResults() {
        List<ResultRecord> list = new ArrayList<>();
        String sql = "SELECT c.candidate_id, c.name AS candidate_name, c.party, b.booth_name, r.total_votes " +
                "FROM result r JOIN candidate c ON r.candidate_id = c.candidate_id " +
                "JOIN voter_booth b ON r.booth_id = b.booth_id " +
                "ORDER BY r.total_votes DESC, c.candidate_id";

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                list.add(new ResultRecord(
                        rs.getInt("candidate_id"),
                        rs.getString("candidate_name"),
                        rs.getString("party"),
                        rs.getString("booth_name"),
                        rs.getInt("total_votes")
                ));
            }
        } catch (SQLException e) {
            System.out.println("Get results error: " + e.getMessage());
        }
        return list;
    }
}
