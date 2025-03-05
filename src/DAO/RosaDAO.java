package DAO;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import Model.Rosa;

public class RosaDAO {
    private Connection connection;

    public RosaDAO(Connection connection) {
        this.connection = connection;
    }

    public Rosa getRosaById(int idRosa) throws SQLException {
        String sql = "SELECT * FROM Rosa WHERE ID_Rosa = ?";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setInt(1, idRosa);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return new Rosa(
                        rs.getInt("ID_Rosa"),
                        rs.getString("nomeSquadra"),
                        rs.getString("stagione")
                );
            }
            return null;
        }
    }

    public List<Rosa> getAllRose() throws SQLException {
        String sql = "SELECT * FROM Rosa";
        try (PreparedStatement ps = connection.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            List<Rosa> rose = new ArrayList<>();
            while (rs.next()) {
                rose.add(new Rosa(
                        rs.getInt("ID_Rosa"),
                        rs.getString("nomeSquadra"),
                        rs.getString("stagione")
                ));
            }
            return rose;
        }
    }

    public void addRosa(Rosa rosa) throws SQLException {
        String sql = "INSERT INTO Rosa (nomeSquadra, stagione) VALUES (?, ?)";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, rosa.getNomeSquadra());
            ps.setString(2, rosa.getStagione());
            ps.executeUpdate();
        }
    }

    public void updateRosa(Rosa rosa) throws SQLException {
        String sql = "UPDATE Rosa SET nomeSquadra = ?, stagione = ? WHERE ID_Rosa = ?";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, rosa.getNomeSquadra());
            ps.setString(2, rosa.getStagione());
            ps.setInt(3, rosa.getIdRosa());
            ps.executeUpdate();
        }
    }

    public void deleteRosa(int idRosa) throws SQLException {
        String sql = "DELETE FROM Rosa WHERE ID_Rosa = ?";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setInt(1, idRosa);
            ps.executeUpdate();
        }
    }
}