package DAO;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import Model.Squadra;
import Database.DatabaseConnection;
import Model.Utente;

public class SquadraDAO {

    public Squadra getSquadraByNome(String nomeSquadra) throws SQLException {
        String sql = "SELECT * FROM Squadra WHERE nomeSquadra LIKE ?";
        try (Connection conn = DatabaseConnection.getConnection();  // Usa DatabaseConnection qui
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, "%" + nomeSquadra + "%");
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return new Squadra(
                        rs.getString("nomeSquadra"),
                        rs.getInt("annoFondazione"),
                        rs.getString("nazionalita"),
                        rs.getString("nomeStadio")
                );
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
        return null;
    }
/*
    public List<Squadra> getAllSquadre() throws SQLException {
        String sql = "SELECT * FROM Squadra";
        try (PreparedStatement ps = connection.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            List<Squadra> squadre = new ArrayList<>();
            while (rs.next()) {
                squadre.add(new Squadra(
                        rs.getString("nomeSquadra"),
                        rs.getInt("annoFondazione"),
                        rs.getString("campAppartenenza"),
                        rs.getString("nazionalita"),
                        rs.getString("nomeStadio")
                ));
            }
            return squadre;
        }
    }

    public void addSquadra(Squadra squadra) throws SQLException {
        String sql = "INSERT INTO Squadra (nomeSquadra, annoFondazione, campAppartenenza, nazionalita, nomeStadio) VALUES (?, ?, ?, ?, ?)";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, squadra.getNomeSquadra());
            ps.setInt(2, squadra.getAnnoFondazione());
            ps.setString(3, squadra.getCampAppartenenza());
            ps.setString(4, squadra.getNazionalita());
            ps.setString(5, squadra.getNomeStadio());
            ps.executeUpdate();
        }
    }

    public void updateSquadra(Squadra squadra) throws SQLException {
        String sql = "UPDATE Squadra SET annoFondazione = ?, campAppartenenza = ?, nazionalita = ?, nomeStadio = ? WHERE nomeSquadra = ?";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setInt(1, squadra.getAnnoFondazione());
            ps.setString(2, squadra.getCampAppartenenza());
            ps.setString(3, squadra.getNazionalita());
            ps.setString(4, squadra.getNomeStadio());
            ps.setString(5, squadra.getNomeSquadra());
            ps.executeUpdate();
        }
    }

    public void deleteSquadra(String nomeSquadra) throws SQLException {
        String sql = "DELETE FROM Squadra WHERE nomeSquadra = ?";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, nomeSquadra);
            ps.executeUpdate();
        }
    }
*/
}