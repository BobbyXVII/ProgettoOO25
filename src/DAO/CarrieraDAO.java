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
                int id = rs.getInt("ID");
                idList.add(id);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw e;
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }

        return idList;
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
                "WHERE id = ? AND dataFineCarriera IS NULL " +
                "ORDER BY dataInizioCarriera DESC LIMIT 1";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, calciatoreId);

            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return rs.getString("nomeSquadra");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw e;
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }

        return null;
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

        return null;
    }
}
