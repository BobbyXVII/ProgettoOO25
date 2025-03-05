package DAO;

import Database.DatabaseConnection;
import Model.Rosa;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class RosaDAO {

    private Connection getConnection() throws SQLException, ClassNotFoundException {
        return DatabaseConnection.getConnection();
    }

    private Rosa mapResultSetToRosa(ResultSet rs) throws SQLException {
        Rosa rosa = new Rosa();
        rosa.setID_Rosa(rs.getInt("ID_Rosa"));
        rosa.setNomeSquadra(rs.getString("nomeSquadra"));
        rosa.setStagione(rs.getString("stagione"));
        return rosa;
    }

    public Rosa findByIdRosa(int idRosa) throws SQLException, ClassNotFoundException {
        String sql = "SELECT ID_Rosa, nomeSquadra, stagione FROM ROSA WHERE ID_Rosa = ?";
        try (Connection conn = getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, idRosa);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                return mapResultSetToRosa(rs);
            } else {
                return null;
            }
        }
    }

    public List<Rosa> findAll() throws SQLException, ClassNotFoundException {
        String sql = "SELECT ID_Rosa, nomeSquadra, stagione FROM ROSA";
        List<Rosa> rosaList = new ArrayList<>();
        try (Connection conn = getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql);
             ResultSet rs = pstmt.executeQuery()) {
            while (rs.next()) {
                rosaList.add(mapResultSetToRosa(rs));
            }
            return rosaList;
        }
    }

    public void create(Rosa rosa) throws SQLException, ClassNotFoundException {
        String sql = "INSERT INTO ROSA (nomeSquadra, stagione) VALUES (?, ?)";
        try (Connection conn = getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, rosa.getNomeSquadra());
            pstmt.setString(2, rosa.getStagione());
            pstmt.executeUpdate();
        }
    }

    public void update(Rosa rosa) throws SQLException, ClassNotFoundException {
        String sql = "UPDATE ROSA SET nomeSquadra = ?, stagione = ? WHERE ID_Rosa = ?";
        try (Connection conn = getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, rosa.getNomeSquadra());
            pstmt.setString(2, rosa.getStagione());
            pstmt.setInt(3, rosa.getID_Rosa());
            pstmt.executeUpdate();
        }
    }

    public void delete(int idRosa) throws SQLException, ClassNotFoundException {
        String sql = "DELETE FROM ROSA WHERE ID_Rosa = ?";
        try (Connection conn = getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, idRosa);
            pstmt.executeUpdate();
        }
    }

    public List<Rosa> findByNomeSquadraStagione(String nomeSquadra, String stagione) throws SQLException, ClassNotFoundException {
        String sql = "SELECT ID_Rosa, nomeSquadra, stagione FROM ROSA WHERE nomeSquadra = ? AND stagione = ?";
        List<Rosa> rosaList = new ArrayList<>();
        try (Connection conn = getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, nomeSquadra);
            pstmt.setString(2, stagione);
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                rosaList.add(mapResultSetToRosa(rs));
            }
            return rosaList;
        }
    }

    // Metodi aggiuntivi (se necessario)
    // ...
}