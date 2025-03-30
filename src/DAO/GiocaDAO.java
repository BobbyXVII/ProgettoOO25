package DAO;

import Database.DatabaseConnection;
import Model.Gioca;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

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
