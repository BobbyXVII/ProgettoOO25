package DAO;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import Model.Partita;

public class PartitaDAO {
    private Connection connection;

    public PartitaDAO(Connection connection) {
        this.connection = connection;
    }

    public Partita getPartitaById(int idPartita, String nomeSquadra) throws SQLException {
        String sql = "SELECT * FROM Partita WHERE ID_Partita = ? AND nomeSquadra = ?";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setInt(1, idPartita);
            ps.setString(2, nomeSquadra);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return new Partita(
                        rs.getInt("ID_Partita"),
                        rs.getString("nomeSquadra"),
                        rs.getString("nomeCompetizione"),
                        rs.getString("annoSvolgimento"),
                        rs.getInt("ID_Rosa"),
                        rs.getBoolean("inCasa"),
                        rs.getDate("data"),
                        rs.getTime("ora"),
                        rs.getString("risultato"),
                        rs.getString("nomeStadio")
                );
            }
            return null;
        }
    }

    public List<Partita> getAllPartite() throws SQLException {
        String sql = "SELECT * FROM Partita";
        try (PreparedStatement ps = connection.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            List<Partita> partite = new ArrayList<>();
            while (rs.next()) {
                partite.add(new Partita(
                        rs.getInt("ID_Partita"),
                        rs.getString("nomeSquadra"),
                        rs.getString("nomeCompetizione"),
                        rs.getString("annoSvolgimento"),
                        rs.getInt("ID_Rosa"),
                        rs.getBoolean("inCasa"),
                        rs.getDate("data"),
                        rs.getTime("ora"),
                        rs.getString("risultato"),
                        rs.getString("nomeStadio")
                ));
            }
            return partite;
        }
    }

    public void addPartita(Partita partita) throws SQLException {
        String sql = "INSERT INTO Partita (ID_Partita, nomeSquadra, nomeCompetizione, annoSvolgimento, ID_Rosa, inCasa, data, ora, risultato, nomeStadio) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setInt(1, partita.getIdPartita());
            ps.setString(2, partita.getNomeSquadra());
            ps.setString(3, partita.getNomeCompetizione());
            ps.setString(4, partita.getAnnoSvolgimento());
            ps.setInt(5, partita.getIdRosa());
            ps.setBoolean(6, partita.isInCasa());
            ps.setDate(7, partita.getData());
            ps.setTime(8, partita.getOra());
            ps.setString(9, partita.getRisultato());
            ps.setString(10, partita.getNomeStadio());
            ps.executeUpdate();
        }
    }

    public void updatePartita(Partita partita) throws SQLException {
        String sql = "UPDATE Partita SET nomeCompetizione = ?, annoSvolgimento = ?, ID_Rosa = ?, inCasa = ?, data = ?, ora = ?, risultato = ?, nomeStadio = ? WHERE ID_Partita = ? AND nomeSquadra = ?";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, partita.getNomeCompetizione());
            ps.setString(2, partita.getAnnoSvolgimento());
            ps.setInt(3, partita.getIdRosa());
            ps.setBoolean(4, partita.isInCasa());
            ps.setDate(5, partita.getData());
            ps.setTime(6, partita.getOra());
            ps.setString(7, partita.getRisultato());
            ps.setString(8, partita.getNomeStadio());
            ps.setInt(9, partita.getIdPartita());
            ps.setString(10, partita.getNomeSquadra());
            ps.executeUpdate();
        }
    }

    public void deletePartita(int idPartita, String nomeSquadra) throws SQLException {
        String sql = "DELETE FROM Partita WHERE ID_Partita = ? AND nomeSquadra = ?";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setInt(1, idPartita);
            ps.setString(2, nomeSquadra);
            ps.executeUpdate();
        }
    }
}
