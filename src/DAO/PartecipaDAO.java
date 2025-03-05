package DAO;

import java.sql.*;
import Model.Partecipa;
import java.util.ArrayList;
import java.util.List;

public class PartecipaDAO {
    private Connection connection;

    public PartecipaDAO(Connection connection) {
        this.connection = connection;
    }

    public Partecipa getPartecipaById(String nomeCompetizione, String annoSvolgimento, String nomeSquadra) throws SQLException {
        String sql = "SELECT * FROM Partecipa WHERE nomeCompetizione = ? AND annoSvolgimento = ? AND nomeSquadra = ?";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, nomeCompetizione);
            ps.setString(2, annoSvolgimento);
            ps.setString(3, nomeSquadra);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                Partecipa partecipazione = new Partecipa();
                partecipazione.setNomeCompetizione(rs.getString("nomeCompetizione"));
                partecipazione.setAnnoSvolgimento(rs.getString("annoSvolgimento"));
                partecipazione.setNomeSquadra(rs.getString("nomeSquadra"));
                partecipazione.setPosizioneFinale(rs.getInt("posizioneFinale"));
                return partecipazione;
            }
            return null;
        }
    }

    public List<Partecipa> getAllPartecipazioni() throws SQLException {
        String sql = "SELECT * FROM Partecipa";
        try (PreparedStatement ps = connection.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            List<Partecipa> partecipazioniList = new ArrayList<>();
            while (rs.next()) {
                Partecipa partecipazione = new Partecipa();
                partecipazione.setNomeCompetizione(rs.getString("nomeCompetizione"));
                partecipazione.setAnnoSvolgimento(rs.getString("annoSvolgimento"));
                partecipazione.setNomeSquadra(rs.getString("nomeSquadra"));
                partecipazione.setPosizioneFinale(rs.getInt("posizioneFinale"));
                partecipazioniList.add(partecipazione);
            }
            return partecipazioniList;
        }
    }

    public void addPartecipa(Partecipa partecipazione) throws SQLException {
        String sql = "INSERT INTO Partecipa (nomeCompetizione, annoSvolgimento, nomeSquadra, posizioneFinale) VALUES (?, ?, ?, ?)";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, partecipazione.getNomeCompetizione());
            ps.setString(2, partecipazione.getAnnoSvolgimento());
            ps.setString(3, partecipazione.getNomeSquadra());
            ps.setInt(4, partecipazione.getPosizioneFinale());
            ps.executeUpdate();
        }
    }

    public void updatePartecipa(Partecipa partecipazione) throws SQLException {
        String sql = "UPDATE Partecipa SET posizioneFinale = ? WHERE nomeCompetizione = ? AND annoSvolgimento = ? AND nomeSquadra = ?";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setInt(1, partecipazione.getPosizioneFinale());
            ps.setString(2, partecipazione.getNomeCompetizione());
            ps.setString(3, partecipazione.getAnnoSvolgimento());
            ps.setString(4, partecipazione.getNomeSquadra());
            ps.executeUpdate();
        }
    }

    public void deletePartecipa(String nomeCompetizione, String annoSvolgimento, String nomeSquadra) throws SQLException {
        String sql = "DELETE FROM Partecipa WHERE nomeCompetizione = ? AND annoSvolgimento = ? AND nomeSquadra = ?";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, nomeCompetizione);
            ps.setString(2, annoSvolgimento);
            ps.setString(3, nomeSquadra);
            ps.executeUpdate();
        }
    }
}
