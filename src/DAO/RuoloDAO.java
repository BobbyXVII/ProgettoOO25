package DAO;

import Database.DatabaseConnection;
import Model.Ruolo;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class RuoloDAO {

    // Metodo per ottenere una connessione al database
    private Connection getConnection() throws SQLException, ClassNotFoundException {
        return DatabaseConnection.getConnection();
    }

    // Metodo per mappare una riga del ResultSet a un oggetto Ruolo
    private Ruolo mapResultSetToRuolo(ResultSet rs) throws SQLException {
        Ruolo ruolo = new Ruolo();
        ruolo.setAbbrRuolo(rs.getString("abbr_ruolo"));
        ruolo.setNomeRuolo(rs.getString("nome_ruolo"));
        ruolo.setDescrizione(rs.getString("descrizione_ruolo"));
        return ruolo;
    }

    public Ruolo findByAbbrRuolo(String abbrRuolo) throws SQLException, ClassNotFoundException {
        String sql = "SELECT abbr_ruolo, nome_ruolo, descrizione_ruolo FROM Ruolo WHERE abbr_ruolo = ?";
        try (Connection conn = getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, abbrRuolo);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                return mapResultSetToRuolo(rs);
            } else {
                return null; // Ruolo non trovato
            }
        }
    }

    public List<Ruolo> findAll() throws SQLException, ClassNotFoundException {
        String sql = "SELECT abbr_ruolo, nome_ruolo, descrizione_ruolo FROM Ruolo";
        List<Ruolo> ruoli = new ArrayList<>();
        try (Connection conn = getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql);
             ResultSet rs = pstmt.executeQuery()) {
            while (rs.next()) {
                ruoli.add(mapResultSetToRuolo(rs));
            }
            return ruoli;
        }
    }

    public void create(Ruolo ruolo) throws SQLException, ClassNotFoundException {
        String sql = "INSERT INTO Ruolo (abbr_ruolo, nome_ruolo, descrizione_ruolo) VALUES (?, ?, ?)";
        try (Connection conn = getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, ruolo.getAbbrRuolo());
            pstmt.setString(2, ruolo.getNomeRuolo());
            pstmt.setString(3, ruolo.getDescrizione());
            pstmt.executeUpdate();
        }
    }

    public void update(Ruolo ruolo) throws SQLException, ClassNotFoundException {
        String sql = "UPDATE Ruolo SET nome_ruolo = ?, descrizione_ruolo = ? WHERE abbr_ruolo = ?";
        try (Connection conn = getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, ruolo.getNomeRuolo());
            pstmt.setString(2, ruolo.getDescrizione());
            pstmt.setString(3, ruolo.getAbbrRuolo());
            pstmt.executeUpdate();
        }
    }

    public void delete(String abbrRuolo) throws SQLException, ClassNotFoundException {
        String sql = "DELETE FROM Ruolo WHERE abbr_ruolo = ?";
        try (Connection conn = getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, abbrRuolo);
            pstmt.executeUpdate();
        }
    }

    // ... (Altri metodi specifici per Ruolo) ...
}