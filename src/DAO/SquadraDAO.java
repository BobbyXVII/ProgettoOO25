package DAO;

import Database.DatabaseConnection;
import Model.Squadra;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class SquadraDAO {

    private Connection getConnection() throws SQLException, ClassNotFoundException {
        return DatabaseConnection.getConnection();
    }

    private Squadra mapResultSetToSquadra(ResultSet rs) throws SQLException {
        Squadra squadra = new Squadra();
        squadra.setNomeSquadra(rs.getString("nomeSquadra"));
        squadra.setAnnoFondazione(rs.getInt("annoFondazione"));
        squadra.setCampAppartenenza(rs.getString("campAppartenenza"));
        squadra.setNazionalita(rs.getString("nazionalita"));
        squadra.setNomeStadio(rs.getString("nomeStadio"));
        return squadra;
    }

    public Squadra findByNomeSquadra(String nomeSquadra) throws SQLException, ClassNotFoundException {
        String sql = "SELECT nomeSquadra, annoFondazione, campAppartenenza, nazionalita, nomeStadio FROM SQUADRA WHERE nomeSquadra = ?";
        try (Connection conn = getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, nomeSquadra);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                return mapResultSetToSquadra(rs);
            } else {
                return null;
            }
        }
    }

    public List<Squadra> findAll() throws SQLException, ClassNotFoundException {
        String sql = "SELECT nomeSquadra, annoFondazione, campAppartenenza, nazionalita, nomeStadio FROM SQUADRA";
        List<Squadra> squadraList = new ArrayList<>();
        try (Connection conn = getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql);
             ResultSet rs = pstmt.executeQuery()) {
            while (rs.next()) {
                squadraList.add(mapResultSetToSquadra(rs));
            }
            return squadraList;
        }
    }

    public void create(Squadra squadra) throws SQLException, ClassNotFoundException {
        String sql = "INSERT INTO SQUADRA (nomeSquadra, annoFondazione, campAppartenenza, nazionalita, nomeStadio) VALUES (?, ?, ?, ?, ?)";
        try (Connection conn = getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, squadra.getNomeSquadra());
            pstmt.setInt(2, squadra.getAnnoFondazione());
            pstmt.setString(3, squadra.getCampAppartenenza());
            pstmt.setString(4, squadra.getNazionalita());
            pstmt.setString(5, squadra.getNomeStadio());
            pstmt.executeUpdate();
        }
    }

    public void update(Squadra squadra) throws SQLException, ClassNotFoundException {
        String sql = "UPDATE SQUADRA SET annoFondazione = ?, campAppartenenza = ?, nazionalita = ?, nomeStadio = ? WHERE nomeSquadra = ?";
        try (Connection conn = getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, squadra.getAnnoFondazione());
            pstmt.setString(2, squadra.getCampAppartenenza());
            pstmt.setString(3, squadra.getNazionalita());
            pstmt.setString(4, squadra.getNomeStadio());
            pstmt.setString(5, squadra.getNomeSquadra());
            pstmt.executeUpdate();
        }
    }

    public void delete(String nomeSquadra) throws SQLException, ClassNotFoundException {
        String sql = "DELETE FROM SQUADRA WHERE nomeSquadra = ?";
        try (Connection conn = getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, nomeSquadra);
            pstmt.executeUpdate();
        }
    }

    public List<Squadra> findByCampAppartenenza(String campAppartenenza) throws SQLException, ClassNotFoundException {
        String sql = "SELECT nomeSquadra, annoFondazione, campAppartenenza, nazionalita, nomeStadio FROM SQUADRA WHERE campAppartenenza = ?";
        List<Squadra> squadraList = new ArrayList<>();
        try (Connection conn = getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, campAppartenenza);
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                squadraList.add(mapResultSetToSquadra(rs));
            }
            return squadraList;
        }
    }

    // Metodi aggiuntivi (se necessario)
    // ...
}