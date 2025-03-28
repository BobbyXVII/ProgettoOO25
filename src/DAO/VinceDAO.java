package DAO;

import java.sql.*;

import Database.DatabaseConnection;
import Model.Vince;
import java.util.ArrayList;
import java.util.List;

public class VinceDAO {

    public void addVince(Vince vince) throws SQLException {
        String sql = "INSERT INTO Vince (ID_TROFEO_IN, ID_TROFEO_DS, ID, dataVincita) VALUES (?, ?, ?, ?)";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, vince.getIdTrofeoIN());
            ps.setString(2, vince.getIdTrofeoDS());
            ps.setInt(3, vince.getIdPersona());
            ps.setDate(4, new java.sql.Date(vince.getDataVincita().getTime()));
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    public int checkTrophyInd(Date data, String trophyAn) throws SQLException {
        String sql = "SELECT 1 FROM VINCE JOIN trofeo_individuale ON VINCE.id_trofeo_in = trofeo_individuale.id_trofeo_in WHERE ID_Trofeo_DS is NULL AND datavincita = ? AND nomeAssegnazione = ?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            // Imposta il valore del primo parametro `?` (data)
            stmt.setDate(1, data);

            // Imposta il valore del secondo parametro `?` (nomeAssegnazione)
            stmt.setString(2, trophyAn);

            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return 1;  // Trova il risultato
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
        return -1;  // Se non ci sono risultati, restituisce -1
    }

    public int checkTrophyInd4ID(int id, Date data, String trophyAn) throws SQLException {
        String sql = "SELECT 1 FROM VINCE " +
                "JOIN trofeo_individuale ON VINCE.id_trofeo_in = trofeo_individuale.id_trofeo_in " +
                "WHERE ID_Trofeo_DS IS NULL AND datavincita = ? AND nomeAssegnazione = ? AND ID = ?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            // Imposta i parametri della query
            stmt.setDate(1, data);
            stmt.setString(2, trophyAn);
            stmt.setInt(3, id);  // Filtro per ID giocatore

            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return 1;  // Trovato un risultato
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
        return -1;  // Nessun risultato trovato
    }


    public List<Vince> getTrofeiVintiByPlayer(int playerId) throws SQLException {
        String sql = "SELECT * FROM Vince WHERE ID = ? AND id_trofeo_ds IS NULL";
        List<Vince> vinceList = new ArrayList<>();
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, playerId);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    Vince vince = new Vince();
                    vince.setIdTrofeoIN(rs.getString("ID_TROFEO_IN"));
                    vince.setIdTrofeoDS(rs.getString("ID_TROFEO_DS"));
                    vince.setIdPersona(rs.getInt("ID"));
                    vince.setDataVincita(rs.getDate("dataVincita"));
                    vinceList.add(vince);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
        return vinceList;
    }

    public List<String> getTrofeiVintiByPlayerONLY_ID(int playerId) throws SQLException {
        String sql = "SELECT ID_TROFEO_IN FROM Vince WHERE ID = ? AND id_trofeo_ds IS NULL";
        List<String> trophyIds = new ArrayList<>();

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, playerId);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    String trophyId = rs.getString("ID_TROFEO_IN");
                    trophyIds.add(trophyId);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }

        return trophyIds;
    }

    public void deleteWin(int playerId, String selectedTrophy) throws SQLException {
        String sql = "DELETE FROM Vince WHERE ID = ? AND ID_TROFEO_IN = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, playerId);  // ID del giocatore
            ps.setString(2, selectedTrophy);  // ID del trofeo selezionato
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }


}
/*
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
 */
