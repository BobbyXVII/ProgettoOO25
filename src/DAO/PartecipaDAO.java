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

            ps.setString(1, partecipazione.getNomeCompetizione());
            ps.setString(2, partecipazione.getAnnoSvolgimento());
            ps.setString(3, partecipazione.getNomeSquadra());
            ps.setInt(4, partecipazione.getPosizioneFinale());

            ps.executeUpdate();
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
            throw new SQLException("Errore durante l'inserimento della partecipazione", e);
        }
    }

    public void insertPartecipa1(Partecipa partecipazione) throws SQLException {
        String sql = "INSERT INTO Partecipa (nomeCompetizione, annoSvolgimento, nomeSquadra, posizionefinale) VALUES (?, ?, ?, null)";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, partecipazione.getNomeCompetizione());
            ps.setString(2, partecipazione.getAnnoSvolgimento());
            ps.setString(3, partecipazione.getNomeSquadra());

            ps.executeUpdate();
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
            throw new SQLException("Errore durante l'inserimento della partecipazione", e);
        }
    }
}
