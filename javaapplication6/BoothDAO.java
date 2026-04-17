package javaapplication6;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class BoothDAO {
    public List<Booth> getAllBooths() {
        List<Booth> booths = new ArrayList<>();
        String sql = "SELECT booth_id, booth_name, location FROM voter_booth ORDER BY booth_id";

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                booths.add(new Booth(rs.getInt("booth_id"), rs.getString("booth_name"), rs.getString("location")));
            }
        } catch (SQLException e) {
            System.out.println("Booth load error: " + e.getMessage());
        }
        return booths;
    }
}
