package DAO;

import Model.Utente;
import java.sql.*;
import Database.DatabaseConnection;

public class UtenteDAO {

    public Utente autenticaUtente(String username, String password) {
        String sql = "SELECT * FROM Utenti WHERE username = ? AND password = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, username);
            stmt.setString(2, password);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                return new Utente(
                        rs.getString("username"),
                        rs.getString("password"),
                        rs.getString("ruolo"),
                        rs.getInt("id_calciatore")
                );
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
        return null;
    }

    public boolean aggiornaPassword(String username, String oldPassword, String newPassword) {
        String verificaSql = "SELECT * FROM Utenti WHERE username = ? AND password = ?";
        String updateSql = "UPDATE Utenti SET password = ? WHERE username = ?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement verificaStmt = conn.prepareStatement(verificaSql);
             PreparedStatement updateStmt = conn.prepareStatement(updateSql)) {

            verificaStmt.setString(1, username);
            verificaStmt.setString(2, oldPassword);
            ResultSet rs = verificaStmt.executeQuery();

            if (!rs.next()) {
                return false;
            }

            updateStmt.setString(1, newPassword);
            updateStmt.setString(2, username);
            int rowsUpdated = updateStmt.executeUpdate();

            return rowsUpdated > 0;

        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return false;
    }

    public String ControllaPex(String username) {
        String sql = "SELECT ruolo FROM Utenti WHERE username = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, username);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return (rs.getString("ruolo"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
        return null;
    }

    public Integer getIdByUsernameIfCalciatore(String username) throws SQLException {
        String sql = "SELECT id_calciatore FROM Utenti WHERE username = ? AND ruolo LIKE 'CALCIATORE'";
        Integer userId = null;

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, username);

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    userId = rs.getInt("id_calciatore");
                    return userId;
                }
            }
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }

        return -1;
    }
}
