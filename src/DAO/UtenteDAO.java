package DAO;

import Model.Utente;
import java.sql.*;
import Database.DatabaseConnection;

public class UtenteDAO {

    public Utente autenticaUtente(String username, String password) {
        String sql = "SELECT * FROM Utenti WHERE username = ? AND password = ?";
        try (Connection conn = DatabaseConnection.getConnection();  // Usa DatabaseConnection qui
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
                return false; // Vecchia password errata
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
}