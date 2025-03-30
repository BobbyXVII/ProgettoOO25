package DAO;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import Model.Squadra;
import Database.DatabaseConnection;
import Model.Utente;

public class SquadraDAO {

    public Squadra getSquadraByNome(String nomeSquadra) throws SQLException {
        String sql = "SELECT * FROM Squadra WHERE LOWER(nomeSquadra) LIKE LOWER(?)";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, "%" + nomeSquadra.toLowerCase() + "%");

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

    public List<String> getAllSquadre() throws SQLException {
        List<String> clubList = new ArrayList<>();
        String sql = "SELECT nomeSquadra FROM Squadra";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                String club = rs.getString("nomeSquadra");
                clubList.add(club);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
        return clubList;
    }

    public void updateSquadra(Squadra squadra) throws SQLException {
        String sql = "UPDATE Squadra SET annoFondazione = ?, nazionalita = ?, nomeStadio = ? WHERE nomeSquadra = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, squadra.getAnnoFondazione());
            ps.setString(2, squadra.getNazionalita());
            ps.setString(3, squadra.getNomeStadio());
            ps.setString(4, squadra.getNomeSquadra());
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    public void deleteSquadra(String nomeSquadra) throws SQLException {
        String sql = "DELETE FROM Squadra WHERE nomeSquadra = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, nomeSquadra);
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    public void addSquadra(Squadra squadra) throws SQLException {
        String sql = "INSERT INTO Squadra (nomeSquadra, annoFondazione, citta, nazionalita, nomeStadio) VALUES (?, ?, ?, ?, ?)";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, squadra.getNomeSquadra());
            ps.setInt(2, squadra.getAnnoFondazione());
            ps.setString(3, squadra.getCitta());
            ps.setString(4, squadra.getNazionalita());
            ps.setString(5, squadra.getNomeStadio());
            ps.executeUpdate();
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }
}
