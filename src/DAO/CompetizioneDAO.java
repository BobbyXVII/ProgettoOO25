package DAO;

import java.sql.*;
import Model.Competizione;
import java.util.ArrayList;
import java.util.List;

public class CompetizioneDAO {
    private Connection connection;

    public CompetizioneDAO(Connection connection) {
        this.connection = connection;
    }

    public Competizione getCompetizioneByDetails(String nomeCompetizione, String annoSvolgimento) throws SQLException {
        String sql = "SELECT * FROM Competizione WHERE nomeCompetizione = ? AND annoSvolgimento = ?";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, nomeCompetizione);
            ps.setString(2, annoSvolgimento);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                Competizione competizione = new Competizione();
                competizione.setNomeCompetizione(rs.getString("nomeCompetizione"));
                competizione.setAnnoSvolgimento(rs.getString("annoSvolgimento"));
                competizione.setTipCompetizione(rs.getString("tipCompetizione"));
                competizione.setNazionalita(rs.getString("nazionalita"));
                return competizione;
            }
            return null;
        }
    }

    public List<Competizione> getAllCompetizioni() throws SQLException {
        String sql = "SELECT * FROM Competizione";
        try (PreparedStatement ps = connection.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            List<Competizione> competizioniList = new ArrayList<>();
            while (rs.next()) {
                Competizione competizione = new Competizione();
                competizione.setNomeCompetizione(rs.getString("nomeCompetizione"));
                competizione.setAnnoSvolgimento(rs.getString("annoSvolgimento"));
                competizione.setTipCompetizione(rs.getString("tipCompetizione"));
                competizione.setNazionalita(rs.getString("nazionalita"));
                competizioniList.add(competizione);
            }
            return competizioniList;
        }
    }

    public void addCompetizione(Competizione competizione) throws SQLException {
        String sql = "INSERT INTO Competizione (nomeCompetizione, annoSvolgimento, tipCompetizione, nazionalita) VALUES (?, ?, ?, ?)";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, competizione.getNomeCompetizione());
            ps.setString(2, competizione.getAnnoSvolgimento());
            ps.setString(3, competizione.getTipCompetizione());
            ps.setString(4, competizione.getNazionalita());
            ps.executeUpdate();
        }
    }

    public void updateCompetizione(Competizione competizione) throws SQLException {
        String sql = "UPDATE Competizione SET tipCompetizione = ?, nazionalita = ? WHERE nomeCompetizione = ? AND annoSvolgimento = ?";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, competizione.getTipCompetizione());
            ps.setString(2, competizione.getNazionalita());
            ps.setString(3, competizione.getNomeCompetizione());
            ps.setString(4, competizione.getAnnoSvolgimento());
            ps.executeUpdate();
        }
    }

    public void deleteCompetizione(String nomeCompetizione, String annoSvolgimento) throws SQLException {
        String sql = "DELETE FROM Competizione WHERE nomeCompetizione = ? AND annoSvolgimento = ?";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, nomeCompetizione);
            ps.setString(2, annoSvolgimento);
            ps.executeUpdate();
        }
    }
}
