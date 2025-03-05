package DAO;

import java.sql.*;
import Model.TrofeoIndividuale;
import java.util.ArrayList;
import java.util.List;

public class TrofeoIndividualeDAO {
    private Connection connection;

    public TrofeoIndividualeDAO(Connection connection) {
        this.connection = connection;
    }

    public TrofeoIndividuale getTrofeoIndividualeById(String idTrofeoIN) throws SQLException {
        String sql = "SELECT * FROM Trofeo_Individuale WHERE ID_Trofeo_IN = ?";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, idTrofeoIN);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                TrofeoIndividuale trofeo = new TrofeoIndividuale();
                trofeo.setIdTrofeoIN(rs.getString("ID_Trofeo_IN"));
                trofeo.setNomeAssegnazione(rs.getString("nomeAssegnazione"));
                trofeo.setDataSvolgimento(rs.getDate("dataSvolgimento"));
                return trofeo;
            }
            return null;
        }
    }

    public List<TrofeoIndividuale> getAllTrofeiIndividuali() throws SQLException {
        String sql = "SELECT * FROM Trofeo_Individuale";
        try (PreparedStatement ps = connection.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            List<TrofeoIndividuale> trofeiList = new ArrayList<>();
            while (rs.next()) {
                TrofeoIndividuale trofeo = new TrofeoIndividuale();
                trofeo.setIdTrofeoIN(rs.getString("ID_Trofeo_IN"));
                trofeo.setNomeAssegnazione(rs.getString("nomeAssegnazione"));
                trofeo.setDataSvolgimento(rs.getDate("dataSvolgimento"));
                trofeiList.add(trofeo);
            }
            return trofeiList;
        }
    }

    public void addTrofeoIndividuale(TrofeoIndividuale trofeo) throws SQLException {
        String sql = "INSERT INTO Trofeo_Individuale (ID_Trofeo_IN, nomeAssegnazione, dataSvolgimento) VALUES (?, ?, ?)";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, trofeo.getIdTrofeoIN());
            ps.setString(2, trofeo.getNomeAssegnazione());
            ps.setDate(3, new java.sql.Date(trofeo.getDataSvolgimento().getTime()));
            ps.executeUpdate();
        }
    }

    public void updateTrofeoIndividuale(TrofeoIndividuale trofeo) throws SQLException {
        String sql = "UPDATE Trofeo_Individuale SET nomeAssegnazione = ?, dataSvolgimento = ? WHERE ID_Trofeo_IN = ?";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, trofeo.getNomeAssegnazione());
            ps.setDate(2, new java.sql.Date(trofeo.getDataSvolgimento().getTime()));
            ps.setString(3, trofeo.getIdTrofeoIN());
            ps.executeUpdate();
        }
    }

    public void deleteTrofeoIndividuale(String idTrofeoIN) throws SQLException {
        String sql = "DELETE FROM Trofeo_Individuale WHERE ID_Trofeo_IN = ?";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, idTrofeoIN);
            ps.executeUpdate();
        }
    }
}
