package DAO;

import java.sql.*;
import Database.DatabaseConnection;
import Model.TrofeoIndividuale;
import java.util.ArrayList;
import java.util.List;

public class TrofeoIndividualeDAO {

    public List<String> getAllIndTrophy() throws SQLException {
        List<String> trophyList = new ArrayList<>();
        String sql = "SELECT nomeAssegnazione FROM trofeo_individuale";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                String trophy = rs.getString("nomeAssegnazione");
                trophyList.add(trophy);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
        return trophyList;
    }

    public void addTrofeoIndividuale(TrofeoIndividuale trofeo) throws SQLException {
        String getCountSql = "SELECT COUNT(*) FROM Trofeo_Individuale";
        int nextId = 1;

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement countStmt = conn.prepareStatement(getCountSql);
             ResultSet rs = countStmt.executeQuery()) {
            if (rs.next()) {
                nextId = rs.getInt(1) + 1;
            }
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }

        String generatedId = String.format("IN%06d", nextId);

        String insertSql = "INSERT INTO Trofeo_Individuale (ID_Trofeo_IN, nomeAssegnazione, dataSvolgimento) VALUES (?, ?, ?)";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(insertSql)) {
            ps.setString(1, generatedId);
            ps.setString(2, trofeo.getNomeAssegnazione());
            ps.setDate(3, new java.sql.Date(trofeo.getDataSvolgimento().getTime()));
            ps.executeUpdate();
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public String getLastTrofeoIndividualeId() throws SQLException {
        String sql = "SELECT ID_Trofeo_IN FROM Trofeo_Individuale ORDER BY ID_Trofeo_IN DESC LIMIT 1";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            if (rs.next()) {
                return rs.getString("ID_Trofeo_IN");
            }
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }

        return null;
    }

    public String getNomeAssegnazioneById(String idTrofeoIN) throws SQLException {
        String sql = "SELECT nomeAssegnazione FROM Trofeo_Individuale WHERE ID_Trofeo_IN = ?";
        String nomeAssegnazione = null;

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, idTrofeoIN);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    nomeAssegnazione = rs.getString("nomeAssegnazione");
                }
            }
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }

        return nomeAssegnazione;
    }

    public String getIdTrofeoByNomeAssegnazione(String nomeAssegnazioneIN) throws SQLException {
        String sql = "SELECT ID_Trofeo_IN FROM Trofeo_Individuale WHERE nomeAssegnazione = ?";
        String idTrofeoIN = null;

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, nomeAssegnazioneIN);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    idTrofeoIN = rs.getString("ID_Trofeo_IN");
                }
            }
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }

        return idTrofeoIN;
    }

    public List<String> getAllTrophyNames() throws SQLException {
        String sql = "SELECT DISTINCT nomeAssegnazione FROM Trofeo_Individuale";
        List<String> trophyNames = new ArrayList<>();

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                String nomeAssegnazione = rs.getString("nomeAssegnazione");
                trophyNames.add(nomeAssegnazione);
            }
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }

        return trophyNames;
    }
}
