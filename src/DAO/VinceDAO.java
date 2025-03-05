package DAO;

import java.sql.*;
import Model.Vince;
import java.util.ArrayList;
import java.util.List;

public class VinceDAO {
    private Connection connection;

    public VinceDAO(Connection connection) {
        this.connection = connection;
    }

    public Vince getVinceById(String idTrofeoIN, String idTrofeoDS, int idPersona) throws SQLException {
        String sql = "SELECT * FROM Vince WHERE ID_TROFEO_IN = ? AND ID_TROFEO_DS = ? AND ID = ?";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, idTrofeoIN);
            ps.setString(2, idTrofeoDS);
            ps.setInt(3, idPersona);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                Vince vince = new Vince();
                vince.setIdTrofeoIN(rs.getString("ID_TROFEO_IN"));
                vince.setIdTrofeoDS(rs.getString("ID_TROFEO_DS"));
                vince.setIdPersona(rs.getInt("ID"));
                vince.setDataVincita(rs.getDate("dataVincita"));
                return vince;
            }
            return null;
        }
    }

    public List<Vince> getAllVince() throws SQLException {
        String sql = "SELECT * FROM Vince";
        try (PreparedStatement ps = connection.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            List<Vince> vinceList = new ArrayList<>();
            while (rs.next()) {
                Vince vince = new Vince();
                vince.setIdTrofeoIN(rs.getString("ID_TROFEO_IN"));
                vince.setIdTrofeoDS(rs.getString("ID_TROFEO_DS"));
                vince.setIdPersona(rs.getInt("ID"));
                vince.setDataVincita(rs.getDate("dataVincita"));
                vinceList.add(vince);
            }
            return vinceList;
        }
    }

    public void addVince(Vince vince) throws SQLException {
        String sql = "INSERT INTO Vince (ID_TROFEO_IN, ID_TROFEO_DS, ID, dataVincita) VALUES (?, ?, ?, ?)";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, vince.getIdTrofeoIN());
            ps.setString(2, vince.getIdTrofeoDS());
            ps.setInt(3, vince.getIdPersona());
            ps.setDate(4, new java.sql.Date(vince.getDataVincita().getTime()));
            ps.executeUpdate();
        }
    }

    public void updateVince(Vince vince) throws SQLException {
        String sql = "UPDATE Vince SET dataVincita = ? WHERE ID_TROFEO_IN = ? AND ID_TROFEO_DS = ? AND ID = ?";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setDate(1, new java.sql.Date(vince.getDataVincita().getTime()));
            ps.setString(2, vince.getIdTrofeoIN());
            ps.setString(3, vince.getIdTrofeoDS());
            ps.setInt(4, vince.getIdPersona());
            ps.executeUpdate();
        }
    }

    public void deleteVince(String idTrofeoIN, String idTrofeoDS, int idPersona) throws SQLException {
        String sql = "DELETE FROM Vince WHERE ID_TROFEO_IN = ? AND ID_TROFEO_DS = ? AND ID = ?";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, idTrofeoIN);
            ps.setString(2, idTrofeoDS);
            ps.setInt(3, idPersona);
            ps.executeUpdate();
        }
    }
}
