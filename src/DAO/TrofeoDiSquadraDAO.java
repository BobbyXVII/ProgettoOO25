package DAO;

import java.sql.*;

import Database.DatabaseConnection;
import Model.TrofeoDiSquadra;
import java.util.ArrayList;
import java.util.List;

public class TrofeoDiSquadraDAO {

    public List<TrofeoDiSquadra> getTrofeiBySquadra(String nomeSquadra) throws SQLException {
        String sql = "SELECT nomeCompetizione, annoSvolgimento FROM trofeo_di_squadra WHERE nomeSquadra = ?";
        List<TrofeoDiSquadra> trofei = new ArrayList<>();

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, nomeSquadra);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                TrofeoDiSquadra trofeo = new TrofeoDiSquadra();
                trofeo.setNomeCompetizione(rs.getString("nomeCompetizione"));
                trofeo.setAnnoSvolgimento(rs.getString("annoSvolgimento"));
                trofei.add(trofeo);
            }
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
        return trofei;
    }


    /*

    public List<TrofeoDiSquadra> getAllTrofeiDiSquadra() throws SQLException {
        String sql = "SELECT * FROM Trofeo_Di_Squadra";
        try (PreparedStatement ps = connection.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            List<TrofeoDiSquadra> trofeiList = new ArrayList<>();
            while (rs.next()) {
                TrofeoDiSquadra trofeo = new TrofeoDiSquadra();
                trofeo.setIdTrofeoDS(rs.getString("ID_Trofeo_DS"));
                trofeo.setNomeCompetizione(rs.getString("nomeCompetizione"));
                trofeo.setAnnoSvolgimento(rs.getString("annoSvolgimento"));
                trofeo.setNomeSquadra(rs.getString("nomeSquadra"));
                trofeiList.add(trofeo);
            }
            return trofeiList;
        }
    }

    public void addTrofeoDiSquadra(TrofeoDiSquadra trofeo) throws SQLException {
        String sql = "INSERT INTO Trofeo_Di_Squadra (ID_Trofeo_DS, nomeCompetizione, annoSvolgimento, nomeSquadra) VALUES (?, ?, ?, ?)";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, trofeo.getIdTrofeoDS());
            ps.setString(2, trofeo.getNomeCompetizione());
            ps.setString(3, trofeo.getAnnoSvolgimento());
            ps.setString(4, trofeo.getNomeSquadra());
            ps.executeUpdate();
        }
    }

    public void updateTrofeoDiSquadra(TrofeoDiSquadra trofeo) throws SQLException {
        String sql = "UPDATE Trofeo_Di_Squadra SET nomeCompetizione = ?, annoSvolgimento = ?, nomeSquadra = ? WHERE ID_Trofeo_DS = ?";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, trofeo.getNomeCompetizione());
            ps.setString(2, trofeo.getAnnoSvolgimento());
            ps.setString(3, trofeo.getNomeSquadra());
            ps.setString(4, trofeo.getIdTrofeoDS());
            ps.executeUpdate();
        }
    }

    public void deleteTrofeoDiSquadra(String idTrofeoDS) throws SQLException {
        String sql = "DELETE FROM Trofeo_Di_Squadra WHERE ID_Trofeo_DS = ?";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, idTrofeoDS);
            ps.executeUpdate();
        }
    }
     */
}
