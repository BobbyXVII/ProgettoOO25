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
            stmt.setString(1, Role);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return rs.getString("abbrRuolo");
            }
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }

    public List<String> getRuoliByID(int id) throws SQLException {
        String sql = "SELECT nomeRuolo FROM ruolo JOIN gioca ON ruolo.abbrRuolo = gioca.abbrRuolo WHERE gioca.ID = ?";
        List<String> ruoli = new ArrayList<>();

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                ruoli.add(rs.getString("nomeRuolo"));
            }
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }

        return ruoli;
    }

    public List<String> getRolesNotAssignedToID(int playerID) throws SQLException {
        String sql = "SELECT DISTINCT nomeRuolo FROM ruolo WHERE abbrRuolo NOT IN (SELECT abbrRuolo FROM gioca WHERE gioca.ID = ?)";
        List<String> roles = new ArrayList<>();

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, playerID);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    roles.add(rs.getString("nomeRuolo"));
                }
            }
        } catch (SQLException e) {
            throw new SQLException("Errore durante l'esecuzione della query: " + e.getMessage(), e);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }

        return roles;
    }
}
