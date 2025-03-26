package DAO;

import java.sql.*;

import Database.DatabaseConnection;
import Model.Ruolo;
import java.util.ArrayList;
import java.util.List;

public class RuoloDAO {

    public Ruolo getRuoloByAbbr(String abbrRuolo) throws SQLException {
        String sql = "SELECT * FROM Ruolo WHERE abbrRuolo = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, abbrRuolo);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                Ruolo ruolo = new Ruolo();
                ruolo.setAbbrRuolo(rs.getString("abbrRuolo"));
                ruolo.setNomeRuolo(rs.getString("nomeRuolo"));
                ruolo.setDescrizione(rs.getString("descrizione"));
                return ruolo;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
        return null;
    }

    public List<String> getAllRoles() throws SQLException {
        List<String> RoleList = new ArrayList<>();
        String sql = "SELECT * FROM Ruolo";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                String Role = rs.getString("nomeRuolo");
                RoleList.add(Role);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
        return RoleList;
    }

    public String getAbbrFromRole(String Role) throws SQLException {
        String sql = "SELECT abbrRuolo FROM Ruolo WHERE nomeRuolo = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, Role);  // AGGIUNTO: impostare il parametro correttamente
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {  // Usa if invece di while
                return rs.getString("abbrRuolo");
            }
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }

}

/*
    public void addRuolo(Ruolo ruolo) throws SQLException {
        String sql = "INSERT INTO Ruolo (abbrRuolo, nomeRuolo, descrizione) VALUES (?, ?, ?)";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, ruolo.getAbbrRuolo());
            ps.setString(2, ruolo.getNomeRuolo());
            ps.setString(3, ruolo.getDescrizione());
            ps.executeUpdate();
        }
    }

    public void updateRuolo(Ruolo ruolo) throws SQLException {
        String sql = "UPDATE Ruolo SET nomeRuolo = ?, descrizione = ? WHERE abbrRuolo = ?";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, ruolo.getNomeRuolo());
            ps.setString(2, ruolo.getDescrizione());
            ps.setString(3, ruolo.getAbbrRuolo());
            ps.executeUpdate();
        }
    }

    public void deleteRuolo(String abbrRuolo) throws SQLException {
        String sql = "DELETE FROM Ruolo WHERE abbrRuolo = ?";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, abbrRuolo);
            ps.executeUpdate();
        }
    }
}
*/