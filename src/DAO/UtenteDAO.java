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
                        rs.getString("pex"),
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
}