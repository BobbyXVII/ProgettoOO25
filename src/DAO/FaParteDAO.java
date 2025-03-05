package DAO;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import Model.FaParte;

public class FaParteDAO {
    private Connection connection;

    public FaParteDAO(Connection connection) {
        this.connection = connection;
    }

    public FaParte getFaParteById(int idRosa, int idCalciatore) throws SQLException {
        String sql = "SELECT * FROM FA_PARTE WHERE ID_Rosa = ? AND ID_Calciatore = ?";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setInt(1, idRosa);
            ps.setInt(2, idCalciatore);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return new FaParte(
                        rs.getInt("ID_Rosa"),
                        rs.getInt("ID_Calciatore"),
                        rs.getBoolean("titolare"),
                        rs.getString("Posizione")
                );
            }
            return null;
        }
    }

    public List<FaParte> getAllFaParte() throws SQLException {
        String sql = "SELECT * FROM FA_PARTE";
        try (PreparedStatement ps = connection.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            List<FaParte> faParteList = new ArrayList<>();
            while (rs.next()) {
                faParteList.add(new FaParte(
                        rs.getInt("ID_Rosa"),
                        rs.getInt("ID_Calciatore"),
                        rs.getBoolean("titolare"),
                        rs.getString("Posizione")
                ));
            }
            return faParteList;
        }
    }

    public void addFaParte(FaParte faParte) throws SQLException {
        String sql = "INSERT INTO FA_PARTE (ID_Rosa, ID_Calciatore, titolare, Posizione) VALUES (?, ?, ?, ?)";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setInt(1, faParte.getIdRosa());
            ps.setInt(2, faParte.getIdCalciatore());
            ps.setBoolean(3, faParte.isTitolare());
            ps.setString(4, faParte.getPosizione());
            ps.executeUpdate();
        }
    }

    public void updateFaParte(FaParte faParte) throws SQLException {
        String sql = "UPDATE FA_PARTE SET titolare = ?, Posizione = ? WHERE ID_Rosa = ? AND ID_Calciatore = ?";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setBoolean(1, faParte.isTitolare());
            ps.setString(2, faParte.getPosizione());
            ps.setInt(3, faParte.getIdRosa());
            ps.setInt(4, faParte.getIdCalciatore());
            ps.executeUpdate();
        }
    }

    public void deleteFaParte(int idRosa, int idCalciatore) throws SQLException {
        String sql = "DELETE FROM FA_PARTE WHERE ID_Rosa = ? AND ID_Calciatore = ?";}}