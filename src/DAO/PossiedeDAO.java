package DAO;

import Database.DatabaseConnection;
import Model.Possiede;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class PossiedeDAO {

    public void insertNewSKills(Possiede possiede) throws SQLException {
        String sql = "INSERT INTO Possiede (ID, nomeSkill) VALUES (?, ?)";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, possiede.getId());
            stmt.setString(2, possiede.getNomeSkill());
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    public boolean skillExists(int id, String nomeSkill) throws SQLException {
        String sql = "SELECT COUNT(*) FROM Possiede WHERE ID = ? AND nomeSkill = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);
            stmt.setString(2, nomeSkill);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return rs.getInt(1) > 0;
            }
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return false;
    }

    public List<String> getSkillsByCalciatoreId(int id) throws SQLException {
        List<String> skills = new ArrayList<>();
        String sql = "SELECT nomeSkill FROM Possiede WHERE ID = ?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                skills.add(rs.getString("nomeSkill"));
            }
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return skills;
    }

    public List<Possiede> getSkillsByCalciatoreIdT(int calciatoreId) throws SQLException {
        List<Possiede> skills = new ArrayList<>();
        String sql = "SELECT nomeSkill FROM Possiede WHERE ID = ?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, calciatoreId);
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    skills.add(new Possiede(calciatoreId, rs.getString("nomeSkill")));
                }
            }
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return skills;
    }

    public void deleteSkill(int id, String nomeSkill) throws SQLException {
        String sql = "DELETE FROM Possiede WHERE ID = ? AND nomeSkill = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql))  {
            stmt.setInt(1, id);
            stmt.setString(2, nomeSkill);
            stmt.executeUpdate();
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
}
