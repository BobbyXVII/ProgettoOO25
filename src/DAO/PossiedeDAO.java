package DAO;

import Model.Possiede;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class PossiedeDAO {
    private Connection connection;

    public PossiedeDAO(Connection connection) {
        this.connection = connection;
    }

    public void insert(Possiede possiede) throws SQLException {
        String query = "INSERT INTO Possiede (ID, nomeSkill) VALUES (?, ?)";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setInt(1, possiede.getId());
            stmt.setString(2, possiede.getNomeSkill());
            stmt.executeUpdate();
        }
    }

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
