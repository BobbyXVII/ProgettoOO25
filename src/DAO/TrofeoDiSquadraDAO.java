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

    public List<TrofeoDiSquadra> getTrofeiByCom(String nomeCompetizione) throws SQLException {
        String sql = "SELECT nomeSquadra, annoSvolgimento FROM trofeo_di_squadra WHERE nomeCompetizione = ?";
        List<TrofeoDiSquadra> trofei = new ArrayList<>();

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, nomeCompetizione);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                TrofeoDiSquadra trofeo = new TrofeoDiSquadra();
                trofeo.setNomeSquadra(rs.getString("nomeSquadra"));
                trofeo.setAnnoSvolgimento(rs.getString("annoSvolgimento"));
                trofei.add(trofeo);
            }
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
        return trofei;
    }
}
