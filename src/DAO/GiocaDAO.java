package DAO;

import Database.DatabaseConnection;
import Model.Gioca;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class GiocaDAO {

    // Metodo per ottenere una connessione al database
    private Connection getConnection() throws SQLException, ClassNotFoundException {
        return DatabaseConnection.getConnection();
    }

    // Metodo per mappare una riga del ResultSet a un oggetto Gioca
    private Gioca mapResultSetToGioca(ResultSet rs) throws SQLException {
        Gioca gioca = new Gioca();
        gioca.setID(rs.getInt("id_persona"));
        gioca.setAbbrRuolo(rs.getString("abbr_ruolo"));
        return gioca;
    }

    public List<Gioca> findByPersonaId(int personaId) throws SQLException, ClassNotFoundException {
        String sql = "SELECT id_persona, abbr_ruolo FROM Gioca WHERE id_persona = ?";
        List<Gioca> giocate = new ArrayList<>();
        try (Connection conn = getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, personaId);
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                giocate.add(mapResultSetToGioca(rs));
            }
            return giocate;
        }
    }

    public List<Gioca> findByAbbrRuolo(String abbrRuolo) throws SQLException, ClassNotFoundException {
        String sql = "SELECT id_persona, abbr_ruolo FROM Gioca WHERE abbr_ruolo = ?";
        List<Gioca> giocate = new ArrayList<>();
        try (Connection conn = getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, abbrRuolo);
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                giocate.add(mapResultSetToGioca(rs));
            }
            return giocate;
        }
    }


    public Gioca findByIdEAbbrRuolo(int personaId, String abbrRuolo) throws SQLException, ClassNotFoundException {
        String sql = "SELECT id_persona, abbr_ruolo FROM Gioca WHERE id_persona = ? AND abbr_ruolo = ?";
        try (Connection conn = getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, personaId);
            pstmt.setString(2, abbrRuolo);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                return mapResultSetToGioca(rs);
            } else {
                return null; // Relazione Gioca non trovata
            }
        }
    }


    public void create(Gioca gioca) throws SQLException, ClassNotFoundException {
        String sql = "INSERT INTO Gioca (id_persona, abbr_ruolo) VALUES (?, ?, ?, ?)";
        try (Connection conn = getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, gioca.getID());
            pstmt.setString(2, gioca.getAbbrRuolo());
            pstmt.executeUpdate();
        }
    }

    public void delete(int personaId, String abbrRuolo) throws SQLException, ClassNotFoundException {
        String sql = "DELETE FROM Gioca WHERE id_persona = ? AND abbr_ruolo = ?";
        try (Connection conn = getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, personaId);
            pstmt.setString(2, abbrRuolo);
            pstmt.executeUpdate();
        }
    }

    // ... (Altri metodi specifici per Gioca) ...
}