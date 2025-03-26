package DAO;

import java.sql.*;

import Database.DatabaseConnection;
import Model.Competizione;
import Model.Squadra;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class CompetizioneDAO {

    public int checkCompetizioneEsiste(String compAnalizzata) throws SQLException {
        String sql = "SELECT 1 FROM competizione WHERE LOWER(nomecompetizione) LIKE LOWER(?)";  // Usa LIKE per una ricerca parziale

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            // Imposta il parametro della query (nome competizione)
            stmt.setString(1, "%" + compAnalizzata.toLowerCase() + "%");  // Usa il "%" per cercare competizioni che contengano il testo

            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return 1;  // Se c'è una competizione che corrisponde al nome (anche parzialmente), restituisce 1
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }

        return 0;  // Se la competizione non esiste, restituisce 0
    }




    public List<String> getCompetizioniAndDate(String compAnalizzata) throws SQLException {
        String sql = "SELECT nomecompetizione, annoSvolgimento FROM competizione WHERE LOWER(nomecompetizione) LIKE LOWER(?)";
        List<String> competizioniAndDates = new ArrayList<>();

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            // Imposta il parametro, convertendolo in minuscolo
            stmt.setString(1, "%" + compAnalizzata.toLowerCase() + "%");

            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                // Crea una stringa che contiene nome e data della competizione
                String competizione = rs.getString("nomecompetizione");
                String annoSvolgimento = rs.getString("annoSvolgimento");

                // Aggiungi il risultato formattato alla lista
                competizioniAndDates.add(competizione + " - " + annoSvolgimento);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }

        return competizioniAndDates;  // Restituisce la lista di competizioni con le date
    }

}


    /*

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
     */
