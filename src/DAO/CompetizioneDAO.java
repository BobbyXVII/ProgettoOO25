package DAO;

import java.sql.*;

import Database.DatabaseConnection;
import Model.Competizione;
import Model.Squadra;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class CompetizioneDAO {

    public int checkCompetizioneEsiste(String compAnalizzata) throws SQLException {
        String sql = "SELECT 1 FROM competizione WHERE LOWER(nomecompetizione) LIKE LOWER(?)";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            // Imposta il parametro della query (nome competizione)
            stmt.setString(1, "%" + compAnalizzata.toLowerCase() + "%");

            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return 1;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }

        return 0;
    }


    public List<String> getCompetizioniAndDate() throws SQLException {
        String sql = "SELECT nomecompetizione, annoSvolgimento FROM competizione";
        List<String> competizioniAndDates = new ArrayList<>();

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                String competizione = rs.getString("nomecompetizione");
                String annoSvolgimento = rs.getString("annoSvolgimento");
                competizioniAndDates.add(competizione + " - " + annoSvolgimento);
            }
        } catch (SQLException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
        return competizioniAndDates;
    }
    public List<String> getCompetizioniAndDate(String compAnalizzata) throws SQLException {
        String sql = "SELECT nomecompetizione, annoSvolgimento FROM competizione WHERE LOWER(nomecompetizione) LIKE LOWER(?)";
        List<String> competizioniAndDates = new ArrayList<>();

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, "%" + compAnalizzata.toLowerCase() + "%");

            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                String competizione = rs.getString("nomecompetizione");
                String annoSvolgimento = rs.getString("annoSvolgimento");

                competizioniAndDates.add(competizione + " - " + annoSvolgimento);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }

        return competizioniAndDates;
    }

    public void deleteCompetizione(String nomeCompetizione, String annoSvolgimento) throws SQLException {
        String sql = "DELETE FROM Competizione WHERE nomeCompetizione = ? AND annoSvolgimento = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, nomeCompetizione);
            ps.setString(2, annoSvolgimento);
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    public List<String> getTipologieCompetizioni() throws SQLException, ClassNotFoundException {
        String sql = "SELECT DISTINCT tipCompetizione FROM competizione";
        List<String> tipologieCompetizioni = new ArrayList<>();

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                tipologieCompetizioni.add(rs.getString("tipCompetizione"));
            }
        }

        return tipologieCompetizioni;
    }

    public List<String> getAnniSvolgimento(String nomeCompetizione) throws SQLException, ClassNotFoundException {
        String sql = "SELECT annoSvolgimento FROM competizione WHERE nomeCompetizione = ?";
        List<String> anniSvolgimento = new ArrayList<>();

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, nomeCompetizione);
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    anniSvolgimento.add(rs.getString("annoSvolgimento"));
                }
            }
        }
        return anniSvolgimento;
    }


    public List<String> getInfoTeam(String nomeCompetizione) throws SQLException, ClassNotFoundException {
        String sql = "SELECT tipCompetizione, nazionalita FROM competizione WHERE nomeCompetizione = ?";
        List<String> infoCompetizione = new ArrayList<>();

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, nomeCompetizione);

            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    infoCompetizione.add(rs.getString("tipCompetizione"));
                    infoCompetizione.add(rs.getString("nazionalita"));
                }
            }
        }

        return infoCompetizione;
    }



    public void addCompetizione(Competizione competizione) throws SQLException {
        String sql = "INSERT INTO Competizione (nomeCompetizione, annoSvolgimento, tipCompetizione, nazionalita) VALUES (?, ?, ?, ?)";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, competizione.getNomeCompetizione());
            ps.setString(2, competizione.getAnnoSvolgimento());
            ps.setString(3, competizione.getTipCompetizione());
            ps.setString(4, competizione.getNazionalita());
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }



    public void updateCompetizioneTip_Naz(Competizione competizione) throws SQLException {
        String sql = "UPDATE Competizione SET tipCompetizione = ?, nazionalita = ? WHERE nomeCompetizione = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, competizione.getTipCompetizione());
            ps.setString(2, competizione.getNazionalita());
            ps.setString(3, competizione.getNomeCompetizione());
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    public void updateCompetizioneTip(Competizione competizione) throws SQLException {
        String sql = "UPDATE Competizione SET tipCompetizione = ? WHERE nomeCompetizione = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, competizione.getTipCompetizione());
            ps.setString(2, competizione.getNomeCompetizione());
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    public void updateCompetizioneNaz(Competizione competizione) throws SQLException {
        String sql = "UPDATE Competizione SET nazionalita = ? WHERE nomeCompetizione = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, competizione.getNazionalita());
            ps.setString(2, competizione.getNomeCompetizione());
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

}