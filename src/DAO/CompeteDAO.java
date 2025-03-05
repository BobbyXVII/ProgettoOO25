package DAO;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import Model.Compete;

public class CompeteDAO {
    private Connection connection;

    public CompeteDAO(Connection connection) {
        this.connection = connection;
    }

    public Compete getCompeteById(int id, int idPartita, String nomeSquadra) throws SQLException {
        String sql = "SELECT * FROM COMPETE WHERE ID = ? AND ID_Partita = ? AND nomeSquadra = ?";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setInt(1, id);
            ps.setInt(2, idPartita);
            ps.setString(3, nomeSquadra);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return new Compete(
                        rs.getInt("ID"),
                        rs.getInt("ID_Partita"),
                        rs.getString("nomeSquadra"),
                        rs.getString("tipoAzione"),
                        rs.getInt("minutaggio")
                );
            }
            return null;
        }
    }

    public List<Compete> getAllCompete() throws SQLException {
        String sql = "SELECT * FROM COMPETE";
        try (PreparedStatement ps = connection.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            List<Compete> competeList = new ArrayList<>();
            while (rs.next()) {
                competeList.add(new Compete(
                        rs.getInt("ID"),
                        rs.getInt("ID_Partita"),
                        rs.getString("nomeSquadra"),
                        rs.getString("tipoAzione"),
                        rs.getInt("minutaggio")
                ));
            }
            return competeList;
        }
    }

    public void addCompete(Compete compete) throws SQLException {
        String sql = "INSERT INTO COMPETE (ID, ID_Partita, nomeSquadra, tipoAzione, minutaggio) VALUES (?, ?, ?, ?, ?)";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setInt(1, compete.getId());
            ps.setInt(2, compete.getIdPartita());
            ps.setString(3, compete.getNomeSquadra());
            ps.setString(4, compete.getTipoAzione());
            ps.setInt(5, compete.getMinutaggio());
            ps.executeUpdate();
        }
    }

    public void updateCompete(Compete compete) throws SQLException {
        String sql = "UPDATE COMPETE SET tipoAzione = ?, minutaggio = ? WHERE ID = ? AND ID_Partita = ? AND nomeSquadra = ?";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, compete.getTipoAzione());
            ps.setInt(2, compete.getMinutaggio());
            ps.setInt(3, compete.getId());
            ps.setInt(4, compete.getIdPartita());
            ps.setString(5, compete.getNomeSquadra());
            ps.executeUpdate();
        }
    }

    public void deleteCompete(int id, int idPartita, String nomeSquadra) throws SQLException {
        String sql = "DELETE FROM COMPETE WHERE ID = ? AND ID_Partita = ? AND nomeSquadra = ?";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setInt(1, id);
            ps.setInt(2, idPartita);
            ps.setString(3, nomeSquadra);
            ps.executeUpdate();
        }
    }
}