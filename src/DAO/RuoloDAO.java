package DAO;

import java.sql.*;
import Model.Ruolo;
import java.util.ArrayList;
import java.util.List;

public class RuoloDAO {
    private Connection connection;

    public RuoloDAO(Connection connection) {
        this.connection = connection;
    }

    public Ruolo getRuoloByAbbr(String abbrRuolo) throws SQLException {
        String sql = "SELECT * FROM Ruolo WHERE abbrRuolo = ?";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, abbrRuolo);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                Ruolo ruolo = new Ruolo();
                ruolo.setAbbrRuolo(rs.getString("abbrRuolo"));
                ruolo.setNomeRuolo(rs.getString("nomeRuolo"));
                ruolo.setDescrizione(rs.getString("descrizione"));
                return ruolo;
            }
            return null;
        }
    }

    public List<Ruolo> getAllRuoli() throws SQLException {
        String sql = "SELECT * FROM Ruolo";
        try (PreparedStatement ps = connection.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            List<Ruolo> ruoliList = new ArrayList<>();
            while (rs.next()) {
                Ruolo ruolo = new Ruolo();
                ruolo.setAbbrRuolo(rs.getString("abbrRuolo"));
                ruolo.setNomeRuolo(rs.getString("nomeRuolo"));
                ruolo.setDescrizione(rs.getString("descrizione"));
                ruoliList.add(ruolo);
            }
            return ruoliList;
        }
    }

    public void addRuolo(Ruolo ruolo) throws SQLException {
        String sql = "INSERT INTO Ruolo (abbrRuolo, nomeRuolo, descrizione) VALUES (?, ?, ?)";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, ruolo.getAbbrRuolo());
            ps.setString(2, ruolo.getNomeRuolo());
            ps.setString(3, ruolo.getDescrizione());
            ps.executeUpdate();
        }
    }

    public void updateRuolo(Ruolo ruolo) throws SQLException {
        String sql = "UPDATE Ruolo SET nomeRuolo = ?, descrizione = ? WHERE abbrRuolo = ?";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, ruolo.getNomeRuolo());
            ps.setString(2, ruolo.getDescrizione());
            ps.setString(3, ruolo.getAbbrRuolo());
            ps.executeUpdate();
        }
    }

    public void deleteRuolo(String abbrRuolo) throws SQLException {
        String sql = "DELETE FROM Ruolo WHERE abbrRuolo = ?";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, abbrRuolo);
            ps.executeUpdate();
        }
    }
}
