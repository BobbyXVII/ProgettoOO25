package DAO;

import java.math.BigDecimal;
import java.sql.*;
import Model.Carriera;
import java.util.ArrayList;
import Database.DatabaseConnection;

import java.util.List;

public class CarrieraDAO {

    public List<Integer> getCarrieraByClubName(String nomeSquadra) throws SQLException {
        String sql = "SELECT ID FROM Carriera WHERE LOWER(nomeSquadra) LIKE LOWER(?)";
        List<Integer> idList = new ArrayList<>();

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, "%" + nomeSquadra.toLowerCase() + "%");
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                int id = rs.getInt("ID"); // Ottieni solo l'ID
                idList.add(id); // Aggiungi l'ID alla lista
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw e;
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }

        return idList; // Restituisce la lista degli ID trovati
    }



    public void addCarriera(Carriera carriera) throws SQLException {
        String sql = "INSERT INTO Carriera (ID, nomeSquadra, dataInizioCarriera, dataFineCarriera, " +
                "cartelliniRossiAnnuali, cartelliniGialliAnnuali, tipologia, infortuniAnnuali, goalSubitiAnnuali, " +
                "goalEseguitiAnnuali, valoreDiMercato, dataRitiro) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, carriera.getId());
            ps.setString(2, carriera.getNomeSquadra());
            ps.setDate(3, new java.sql.Date(carriera.getDataInizioCarriera().getTime()));
            ps.setDate(4, carriera.getDataFineCarriera() != null ? new java.sql.Date(carriera.getDataFineCarriera().getTime()) : null);
            ps.setInt(5, carriera.getCartelliniRossiAnnuali());
            ps.setInt(6, carriera.getCartelliniGialliAnnuali());
            ps.setString(7, carriera.getTipologia());
            ps.setInt(8, carriera.getInfortuniAnnuali());
            ps.setInt(9, carriera.getGoalSubitiAnnuali());
            ps.setInt(10, carriera.getGoalEseguitiAnnuali());
            ps.setInt(11, carriera.getValoreDiMercato());
            ps.setDate(12, carriera.getDataRitiro() != null ? new java.sql.Date(carriera.getDataRitiro().getTime()) : null);
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            throw e;
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    public String getNomeSquadraByCalciatoreId(int calciatoreId) throws SQLException {
        String sql = "SELECT nomeSquadra " +
                "FROM Carriera " +
                "WHERE id = ? AND dataFineCarriera IS NULL " +  // Filtra per l'ID del calciatore e carriera attiva
                "ORDER BY dataInizioCarriera DESC LIMIT 1";  // Ordina per la carriera più recente

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            // Imposta l'ID del calciatore come parametro nella query
            stmt.setInt(1, calciatoreId);

            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    // Restituisce il nome della squadra
                    return rs.getString("nomeSquadra");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw e;
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }

        return null;  // Ritorna null se non trova la squadra attuale
    }

    public BigDecimal getValoreDiMercatoByCalciatoreId(int calciatoreId) throws SQLException {
        String sql = "SELECT valoredimercato FROM Carriera WHERE id = ?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, calciatoreId);

            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return rs.getBigDecimal("valoredimercato");
                }
            }
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }

        return null; // Ritorna "0" invece di null per evitare problemi
    }




/*
    public List<Carriera> getAllCarriere() throws SQLException {
        String sql = "SELECT * FROM Carriera";
        try (PreparedStatement ps = connection.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            List<Carriera> carriereList = new ArrayList<>();
            while (rs.next()) {
                Carriera carriera = new Carriera();
                carriera.setId(rs.getInt("ID"));
                carriera.setNomeSquadra(rs.getString("nomeSquadra"));
                carriera.setDataInizioCarriera(rs.getDate("dataInizioCarriera"));
                carriera.setDataFineCarriera(rs.getDate("dataFineCarriera"));
                carriera.setCartelliniRossiAnnuali(rs.getInt("cartelliniRossiAnnuali"));
                carriera.setCartelliniGialliAnnuali(rs.getInt("cartelliniGialliAnnuali"));
                carriera.setTipologia(rs.getString("tipologia"));
                carriera.setInfortuniAnnuali(rs.getInt("infortuniAnnuali"));
                carriera.setGoalSubitiAnnuali(rs.getInt("goalSubitiAnnuali"));
                carriera.setGoalEseguitiAnnuali(rs.getInt("goalEseguitiAnnuali"));
                carriera.setValoreDiMercato(rs.getInt("valoreDiMercato"));
                carriera.setDataRitiro(rs.getDate("dataRitiro"));
                carriereList.add(carriera);
            }
            return carriereList;
        }
    }


    public void updateCarriera(Carriera carriera) throws SQLException {
        String sql = "UPDATE Carriera SET " +
                "dataFineCarriera = ?, cartelliniRossiAnnuali = ?, cartelliniGialliAnnuali = ?, " +
                "tipologia = ?, infortuniAnnuali = ?, goalSubitiAnnuali = ?, goalEseguitiAnnuali = ?, " +
                "valoreDiMercato = ?, dataRitiro = ? " +
                "WHERE ID = ? AND nomeSquadra = ? AND dataInizioCarriera = ?";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setDate(1, carriera.getDataFineCarriera() != null ? new java.sql.Date(carriera.getDataFineCarriera().getTime()) : null);
            ps.setInt(2, carriera.getCartelliniRossiAnnuali());
            ps.setInt(3, carriera.getCartelliniGialliAnnuali());
            ps.setString(4, carriera.getTipologia());
            ps.setInt(5, carriera.getInfortuniAnnuali());
            ps.setInt(6, carriera.getGoalSubitiAnnuali());
            ps.setInt(7, carriera.getGoalEseguitiAnnuali());
            ps.setInt(8, carriera.getValoreDiMercato());
            ps.setDate(9, carriera.getDataRitiro() != null ? new java.sql.Date(carriera.getDataRitiro().getTime()) : null);
            ps.setInt(10, carriera.getId());
            ps.setString(11, carriera.getNomeSquadra());
            ps.setDate(12, new java.sql.Date(carriera.getDataInizioCarriera().getTime()));
            ps.executeUpdate();
        }
    }

    public void deleteCarriera(int id, String nomeSquadra, Date dataInizioCarriera) throws SQLException {
        String sql = "DELETE FROM Carriera WHERE ID = ? AND nomeSquadra = ? AND dataInizioCarriera = ?";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setInt(1, id);
            ps.setString(2, nomeSquadra);
            ps.setDate(3, new java.sql.Date(dataInizioCarriera.getTime()));
            ps.executeUpdate();
        }
    }

 */
}
