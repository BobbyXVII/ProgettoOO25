package DAO;

import Database.DatabaseConnection;
import Model.FaParte;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class FaParteDAO {

    private Connection getConnection() throws SQLException, ClassNotFoundException {
        return DatabaseConnection.getConnection();
    }

    private FaParte mapResultSetToFaParte(ResultSet rs) throws SQLException {
        FaParte faParte = new FaParte();
        faParte.setID_Rosa(rs.getInt("ID_Rosa"));
        faParte.setID_Calciatore(rs.getInt("ID_Calciatore"));
        faParte.setTitolare(rs.getBoolean("titolare"));
        faParte.setPosizione(rs.getString("Posizione"));
        return faParte;
    }

    public FaParte findByIdRosaIdCalciatore(int idRosa, int idCalciatore) throws SQLException, ClassNotFoundException {
        String sql = "SELECT ID_Rosa, ID_Calciatore, titolare, Posizione FROM FA_PARTE WHERE ID_Rosa = ? AND ID_Calciatore = ?";
        try (Connection conn = getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, idRosa);
            pstmt.setInt(2, idCalciatore);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                return mapResultSetToFaParte(rs);
            } else {
                return null;
            }
        }
    }

    public List<FaParte> findAll() throws SQLException, ClassNotFoundException {
        String sql = "SELECT ID_Rosa, ID_Calciatore, titolare, Posizione FROM FA_PARTE";
        List<FaParte> faParteList = new ArrayList<>();
        try (Connection conn = getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql);
             ResultSet rs = pstmt.executeQuery()) {
            while (rs.next()) {
                faParteList.add(mapResultSetToFaParte(rs));
            }
            return faParteList;
        }
    }

    public void create(FaParte faParte) throws SQLException, ClassNotFoundException {
        String sql = "INSERT INTO FA_PARTE (ID_Rosa, ID_Calciatore, titolare, Posizione) VALUES (?, ?, ?, ?)";
        try (Connection conn = getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, faParte.getID_Rosa());
            pstmt.setInt(2, faParte.getID_Calciatore());
            pstmt.setBoolean(3, faParte.isTitolare());
            pstmt.setString(4, faParte.getPosizione());
            pstmt.executeUpdate();
        }
    }

    public void update(FaParte faParte) throws SQLException, ClassNotFoundException {
        String sql = "UPDATE FA_PARTE SET titolare = ?, Posizione = ? WHERE ID_Rosa = ? AND ID_Calciatore = ?";
        try (Connection conn = getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setBoolean(1, faParte.isTitolare());
            pstmt.setString(2, faParte.getPosizione());
            pstmt.setInt(3, faParte.getID_Rosa());
            pstmt.setInt(4, faParte.getID_Calciatore());
            pstmt.executeUpdate();
        }
    }


    public void delete(int idRosa, int idCalciatore) throws SQLException, ClassNotFoundException {
        String sql = "DELETE FROM FA_PARTE WHERE ID_Rosa = ? AND ID_Calciatore = ?";
        try (Connection conn = getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, idRosa);
            pstmt.setInt(2, idCalciatore);
            pstmt.executeUpdate();
        }
    }

    public List<FaParte> findByIdRosa(int idRosa) throws SQLException, ClassNotFoundException {
        String sql = "SELECT ID_Rosa, ID_Calciatore, titolare, Posizione FROM FA_PARTE WHERE ID_Rosa = ?";
        List<FaParte> faParteList = new ArrayList<>();
        try (Connection conn = getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, idRosa);
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                faParteList.add(mapResultSetToFaParte(rs));
            }
            return faParteList;
        }
    }

    public List<FaParte> findByIdCalciatore(int idCalciatore) throws SQLException, ClassNotFoundException {
        String sql = "SELECT ID_Rosa, ID_Calciatore, titolare, Posizione FROM FA_PARTE WHERE ID_Calciatore = ?";
        List<FaParte> faParteList = new ArrayList<>();
        try (Connection conn = getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, idCalciatore);
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                faParteList.add(mapResultSetToFaParte(rs));
            }
            return faParteList;
        }
    }

    // Metodi aggiuntivi (se necessario)
    // ...
}