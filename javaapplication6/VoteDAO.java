package javaapplication6;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class VoteDAO {

    public enum VoteStatus { SUCCESS, ALREADY_VOTED, INVALID_CANDIDATE, ERROR }

    public VoteStatus castVote(int voterId, int candidateId, int boothId) {
        String voterSql = "SELECT has_voted, booth_id FROM voter WHERE voter_id = ? FOR UPDATE";
        String resultSql = "UPDATE result SET total_votes = total_votes + 1 WHERE candidate_id = ? AND booth_id = ?";
        String updateVoterSql = "UPDATE voter SET has_voted = 1 WHERE voter_id = ?";

        Connection con = null;
        try {
            con = DBConnection.getConnection();
            con.setAutoCommit(false);

            boolean hasVoted;
            int voterBooth;
            try (PreparedStatement ps = con.prepareStatement(voterSql)) {
                ps.setInt(1, voterId);
                try (ResultSet rs = ps.executeQuery()) {
                    if (!rs.next()) {
                        con.rollback();
                        return VoteStatus.ERROR;
                    }
                    hasVoted = rs.getBoolean("has_voted");
                    voterBooth = rs.getInt("booth_id");
                }
            }

            if (hasVoted) {
                con.rollback();
                return VoteStatus.ALREADY_VOTED;
            }

            if (voterBooth != boothId) {
                con.rollback();
                return VoteStatus.INVALID_CANDIDATE;
            }

            int updated;
            try (PreparedStatement ps = con.prepareStatement(resultSql)) {
                ps.setInt(1, candidateId);
                ps.setInt(2, boothId);
                updated = ps.executeUpdate();
            }
            if (updated == 0) {
                con.rollback();
                return VoteStatus.INVALID_CANDIDATE;
            }

            try (PreparedStatement ps = con.prepareStatement(updateVoterSql)) {
                ps.setInt(1, voterId);
                ps.executeUpdate();
            }

            con.commit();
            return VoteStatus.SUCCESS;
        } catch (SQLException e) {
            System.out.println("Cast vote error: " + e.getMessage());
            try { if (con != null) con.rollback(); } catch (SQLException ignored) {}
            return VoteStatus.ERROR;
        } finally {
            try {
                if (con != null) {
                    con.setAutoCommit(true);
                    con.close();
                }
            } catch (SQLException ignored) {}
        }
    }
}
