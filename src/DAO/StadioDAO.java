package DAO;

import Database.DatabaseConnection;
import Model.Stadio;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class StadioDAO {

    private Connection getConnection() throws SQLException, ClassNotFoundException {
        return DatabaseConnection.getConnection();
    }

    public List<String> SelectAll() throws SQLException, ClassNotFoundException {
        String sql = "SELECT nomeStadio FROM STADIO";
        List<String> stadioList = new ArrayList<>();
        try (Connection conn = getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql);
             ResultSet rs = pstmt.executeQuery()) {
            while (rs.next()) {
                stadioList.add(rs.getString("nomeStadio"));
            }
        }
        return stadioList;
    }

    private Stadio mapResultSetToStadio(ResultSet rs) throws SQLException {
        Stadio stadio = new Stadio();
        stadio.setNomeStadio(rs.getString("nomeStadio"));
        stadio.setCapacita(rs.getInt("capacita"));
        return stadio;
    }

    public Stadio findByNomeStadio(String nomeStadio) throws SQLException, ClassNotFoundException {
        String sql = "SELECT nomeStadio, capacita FROM STADIO WHERE nomeStadio = ?";
        try (Connection conn = getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, nomeStadio);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                return mapResultSetToStadio(rs);
            } else {
                return null;
            }
        }
    }

    public List<Stadio> findAll() throws SQLException, ClassNotFoundException {
        String sql = "SELECT nomeStadio, capacita FROM STADIO";
        List<Stadio> stadioList = new ArrayList<>();
        try (Connection conn = getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql);
             ResultSet rs = pstmt.executeQuery()) {
            while (rs.next()) {
                stadioList.add(mapResultSetToStadio(rs));
            }
            return stadioList;
        }
    }

    public void create(Stadio stadio) throws SQLException, ClassNotFoundException {
        String sql = "INSERT INTO STADIO (nomeStadio, capacita) VALUES (?, ?)";
        try (Connection conn = getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, stadio.getNomeStadio());
            pstmt.setInt(2, stadio.getCapacita());
            pstmt.executeUpdate();
        }
    }

    public void update(Stadio stadio) throws SQLException, ClassNotFoundException {
        String sql = "UPDATE STADIO SET capacita = ? WHERE nomeStadio = ?";
        try (Connection conn = getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, stadio.getCapacita());
            pstmt.setString(2, stadio.getNomeStadio());
            pstmt.executeUpdate();
        }
    }

    public void delete(String nomeStadio) throws SQLException, ClassNotFoundException {
        String sql = "DELETE FROM STADIO WHERE nomeStadio = ?";
        try (Connection conn = getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, nomeStadio);
            pstmt.executeUpdate();
        }
    }
}
