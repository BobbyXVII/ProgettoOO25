package DAO;

import java.sql.*;

import Database.DatabaseConnection;
import Model.Partecipa;
import java.util.ArrayList;
import java.util.List;

public class PartecipaDAO {

    public List<Partecipa> getCompetizioniBySquadra(String nomeSquadra) throws SQLException {
        String sql = "SELECT nomeCompetizione, annoSvolgimento, posizioneFinale FROM Partecipa WHERE nomeSquadra LIKE ?";
        List<Partecipa> partecipazioni = new ArrayList<>();

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, nomeSquadra);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                Partecipa partecipazione = new Partecipa();
                partecipazione.setNomeCompetizione(rs.getString("nomeCompetizione"));
                partecipazione.setAnnoSvolgimento(rs.getString("annoSvolgimento"));
                partecipazione.setPosizioneFinale(rs.getInt("posizioneFinale"));
                partecipazioni.add(partecipazione);
            }
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
        return partecipazioni;
    }

    public void insertPartecipa(Partecipa partecipazione) throws SQLException {
        String sql = "INSERT INTO Partecipa (nomeCompetizione, annoSvolgimento, nomeSquadra, posizioneFinale) VALUES (?, ?, ?, ?)";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            // Imposta i parametri della query con i valori del modello Partecipa
            ps.setString(1, partecipazione.getNomeCompetizione());
            ps.setString(2, partecipazione.getAnnoSvolgimento());
            ps.setString(3, partecipazione.getNomeSquadra());
            ps.setInt(4, partecipazione.getPosizioneFinale());

            // Esegui la query
            ps.executeUpdate();
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
            throw new SQLException("Errore durante l'inserimento della partecipazione", e);
        }
    }

/*
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
 */
}
