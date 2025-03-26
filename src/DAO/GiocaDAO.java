package DAO;

import Database.DatabaseConnection;
import Model.Gioca;
import Model.Possiede;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class GiocaDAO {


    public void insertNewRole(Gioca gioca) throws SQLException {
        String sql = "INSERT INTO Gioca (abbrRuolo, ID) VALUES (?, ?)";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, gioca.getAbbrRuolo());
            stmt.setInt(2, gioca.getID());
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    public boolean roleExists(String abbrRuolo, int ID) throws SQLException {
        String sql = "SELECT COUNT(*) FROM Gioca WHERE abbrRuolo = ? AND ID = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, abbrRuolo);
            stmt.setInt(2, ID);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return rs.getInt(1) > 0;
            }
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return false;
    }


}
/*

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
 */