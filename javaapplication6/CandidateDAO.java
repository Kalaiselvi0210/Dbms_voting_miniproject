package javaapplication6;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CandidateDAO {

    public boolean addCandidate(String name, String party, int age, String symbol, int boothId) {
        String insertCandidate = "INSERT INTO candidate(name, party, age, symbol, booth_id) VALUES (?, ?, ?, ?, ?)";
        String insertResult = "INSERT INTO result(candidate_id, total_votes, booth_id) VALUES (?, 0, ?)";

        Connection con = null;
        try {
            con = DBConnection.getConnection();
            con.setAutoCommit(false);

            int candidateId;
            try (PreparedStatement ps = con.prepareStatement(insertCandidate, Statement.RETURN_GENERATED_KEYS)) {
                ps.setString(1, name);
                ps.setString(2, party);
                ps.setInt(3, age);
                ps.setString(4, symbol);
                ps.setInt(5, boothId);
                ps.executeUpdate();

                try (ResultSet rs = ps.getGeneratedKeys()) {
                    if (!rs.next()) {
                        con.rollback();
                        return false;
                    }
                    candidateId = rs.getInt(1);
                }
            }

            try (PreparedStatement ps = con.prepareStatement(insertResult)) {
                ps.setInt(1, candidateId);
                ps.setInt(2, boothId);
                ps.executeUpdate();
            }

            con.commit();
            return true;
        } catch (SQLException e) {
            System.out.println("Add candidate error: " + e.getMessage());
            try {
                if (con != null) con.rollback();
            } catch (SQLException ignored) {
            }
            return false;
        } finally {
            try {
                if (con != null) {
                    con.setAutoCommit(true);
                    con.close();
                }
            } catch (SQLException ignored) {
            }
        }
    }

    public boolean deleteCandidate(int candidateId) {
        String sql = "DELETE FROM candidate WHERE candidate_id = ?";
        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, candidateId);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            System.out.println("Delete candidate error: " + e.getMessage());
            return false;
        }
    }

    public List<Candidate> getAllCandidates() {
        List<Candidate> list = new ArrayList<>();
        String sql = "SELECT candidate_id, name, party, age, symbol, booth_id FROM candidate ORDER BY candidate_id";
        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                list.add(new Candidate(
                        rs.getInt("candidate_id"),
                        rs.getString("name"),
                        rs.getString("party"),
                        rs.getInt("age"),
                        rs.getString("symbol"),
                        rs.getInt("booth_id")
                ));
            }
        } catch (SQLException e) {
            System.out.println("Get candidates error: " + e.getMessage());
        }
        return list;
    }

    public List<Candidate> getCandidatesByBooth(int boothId) {
        List<Candidate> list = new ArrayList<>();
        String sql = "SELECT candidate_id, name, party, age, symbol, booth_id FROM candidate WHERE booth_id = ? ORDER BY candidate_id";
        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, boothId);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    list.add(new Candidate(
                            rs.getInt("candidate_id"),
                            rs.getString("name"),
                            rs.getString("party"),
                            rs.getInt("age"),
                            rs.getString("symbol"),
                            rs.getInt("booth_id")
                    ));
                }
            }
        } catch (SQLException e) {
            System.out.println("Get booth candidates error: " + e.getMessage());
        }
        return list;
    }
}
