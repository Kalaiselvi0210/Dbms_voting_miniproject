package javaapplication6;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class VoterDAO {

    public boolean registerVoter(String name, int age, String gender, String address,
                                 String username, String password, int boothId) {
        String sql = "INSERT INTO voter(name, age, gender, address, username, password, booth_id) VALUES (?, ?, ?, ?, ?, ?, ?)";
        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, name);
            ps.setInt(2, age);
            ps.setString(3, gender);
            ps.setString(4, address);
            ps.setString(5, username);
            ps.setString(6, password);
            ps.setInt(7, boothId);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            System.out.println("Register voter error: " + e.getMessage());
            return false;
        }
    }

    public Voter login(String username, String password) {
        String sql = "SELECT voter_id, name, age, gender, address, username, has_voted, booth_id FROM voter WHERE username = ? AND password = ?";
        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, username);
            ps.setString(2, password);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return new Voter(
                            rs.getInt("voter_id"),
                            rs.getString("name"),
                            rs.getInt("age"),
                            rs.getString("gender"),
                            rs.getString("address"),
                            rs.getString("username"),
                            rs.getBoolean("has_voted"),
                            rs.getInt("booth_id")
                    );
                }
            }
        } catch (SQLException e) {
            System.out.println("Voter login error: " + e.getMessage());
        }
        return null;
    }

    public List<Voter> getAllVoters() {
        List<Voter> list = new ArrayList<>();
        String sql = "SELECT voter_id, name, age, gender, address, username, has_voted, booth_id FROM voter ORDER BY voter_id";
        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                list.add(new Voter(
                        rs.getInt("voter_id"),
                        rs.getString("name"),
                        rs.getInt("age"),
                        rs.getString("gender"),
                        rs.getString("address"),
                        rs.getString("username"),
                        rs.getBoolean("has_voted"),
                        rs.getInt("booth_id")
                ));
            }
        } catch (SQLException e) {
            System.out.println("Get voters error: " + e.getMessage());
        }
        return list;
    }
}
