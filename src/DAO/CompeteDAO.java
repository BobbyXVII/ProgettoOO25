package DAO;

import Database.DatabaseConnection;
import Model.Compete;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class CompeteDAO {

    private Connection getConnection() throws SQLException, ClassNotFoundException {
        return DatabaseConnection.getConnection();
    }

    private Compete mapResultSetToCompete(ResultSet rs) throws SQLException {
        Compete compete = new Compete();
        compete.setID(rs.getInt("ID"));
        compete.setID_Partita(rs.getInt("ID_Partita"));
        compete.setNomeSquadra(rs.getString("nomeSquadra"));
        compete.setTipoAzione(rs.getString("tipoAzione"));
        compete.setMinutaggio(rs.getInt("minutaggio"));
        return compete;
    }

    public Compete findByIdPartitaNomeSquadraIdPersona(int idPartita, String nomeSquadra, int id) throws SQLException, ClassNotFoundException {
        String sql = "SELECT ID, ID_Partita, nomeSquadra, tipoAzione, minutaggio FROM COMPETE WHERE ID_Partita = ? AND nomeSquadra = ? AND ID = ?";
        try (Connection conn = getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, idPartita);
            pstmt.setString(2, nomeSquadra);
            pstmt.setInt(3, id);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                return mapResultSetToCompete(rs);
            } else {
                return null;
            }
        }
    }

    public List<Compete> findAll() throws SQLException, ClassNotFoundException {
        String sql = "SELECT ID, ID_Partita, nomeSquadra, tipoAzione, minutaggio FROM COMPETE";
        List<Compete> competeList = new ArrayList<>();
        try (Connection conn = getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql);
             ResultSet rs = pstmt.executeQuery()) {
            while (rs.next()) {
                competeList.add(mapResultSetToCompete(rs));
            }
            return competeList;
        }
    }

    public void create(Compete compete) throws SQLException, ClassNotFoundException {
        String sql = "INSERT INTO COMPETE (ID, ID_Partita, nomeSquadra, tipoAzione, minutaggio) VALUES (?, ?, ?, ?, ?)";
        try (Connection conn = getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, compete.getID());
            pstmt.setInt(2, compete.getID_Partita());
            pstmt.setString(3, compete.getNomeSquadra());
            pstmt.setString(4, compete.getTipoAzione());
            pstmt.setInt(5, compete.getMinutaggio());
            pstmt.executeUpdate();
        }
    }


    public void delete(int idPartita, String nomeSquadra, int id) throws SQLException, ClassNotFoundException {
        String sql = "DELETE FROM COMPETE WHERE ID_Partita = ? AND nomeSquadra = ? AND ID = ?";
        try (Connection conn = getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, idPartita);
            pstmt.setString(2, nomeSquadra);
            pstmt.setInt(3, id);
            pstmt.executeUpdate();
        }
    }

    public List<Compete> findByIdPartitaNomeSquadra(int idPartita, String nomeSquadra) throws SQLException, ClassNotFoundException {
        String sql = "SELECT ID, ID_Partita, nomeSquadra, tipoAzione, minutaggio FROM COMPETE WHERE ID_Partita = ? AND nomeSquadra = ?";
        List<Compete> competeList = new ArrayList<>();
        try (Connection conn = getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, idPartita);
            pstmt.setString(2, nomeSquadra);
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                competeList.add(mapResultSetToCompete(rs));
            }
            return competeList;
        }
    }

    public List<Compete> findByIdPersona(int id) throws SQLException, ClassNotFoundException {
        String sql = "SELECT ID, ID_Partita, nomeSquadra, tipoAzione, minutaggio FROM COMPETE WHERE ID = ?";
        List<Compete> competeList = new ArrayList<>();
        try (Connection conn = getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, id);
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                competeList.add(mapResultSetToCompete(rs));
            }
            return competeList;
        }
    }

    // Metodi aggiuntivi (se necessario)
    // ...
}