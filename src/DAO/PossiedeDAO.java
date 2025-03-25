package DAO;

import Database.DatabaseConnection;
import Model.Persona;
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
}

    /*

    public Possiede getByIdAndSkill(int id, String nomeSkill) throws SQLException {
        String query = "SELECT * FROM Possiede WHERE ID = ? AND nomeSkill = ?";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setInt(1, id);
            stmt.setString(2, nomeSkill);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return new Possiede(
                        rs.getInt("ID"),
                        rs.getString("nomeSkill")
                );
            }
        }
        return null;
    }

    public List<Possiede> getAll() throws SQLException {
        List<Possiede> possiedeList = new ArrayList<>();
        String query = "SELECT * FROM Possiede";
        try (Statement stmt = connection.createStatement(); ResultSet rs = stmt.executeQuery(query)) {
            while (rs.next()) {
                possiedeList.add(new Possiede(
                        rs.getInt("ID"),
                        rs.getString("nomeSkill")
                ));
            }
        }
        return possiedeList;
    }

    public void delete(int id, String nomeSkill) throws SQLException {
        String query = "DELETE FROM Possiede WHERE ID = ? AND nomeSkill = ?";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setInt(1, id);
            stmt.setString(2, nomeSkill);
            stmt.executeUpdate();
        }
    }
}

     */
