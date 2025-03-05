package DAO;

import Database.DatabaseConnection;
import Model.Partita;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.sql.Date;
import java.sql.Time;

public class PartitaDAO {

    private Connection getConnection() throws SQLException, ClassNotFoundException {
        return DatabaseConnection.getConnection();
    }

    private Partita mapResultSetToPartita(ResultSet rs) throws SQLException {
        Partita partita = new Partita();
        partita.setID_Partita(rs.getInt("ID_Partita"));
        partita.setNomeSquadra(rs.getString("nomeSquadra"));
        partita.setNomeCompetizione(rs.getString("nomeCompetizione"));
        partita.setAnnoSvolgimentoCompetizione(rs.getString("annoSvolgimento"));
        partita.setID_Rosa(rs.getInt("ID_Rosa"));
        partita.setInCasa(rs.getBoolean("inCasa"));
        partita.setData(rs.getDate("data"));
        partita.setOra(rs.getTime("ora"));
        partita.setRisultato(rs.getString("risultato"));
        partita.setNomeStadio(rs.getString("nomeStadio"));
        return partita;
    }

    public Partita findByIdPartitaNomeSquadra(int idPartita, String nomeSquadra) throws SQLException, ClassNotFoundException {
        String sql = "SELECT ID_Partita, nomeSquadra, nomeCompetizione, annoSvolgimento, ID_Rosa, inCasa, data, ora, risultato, nomeStadio FROM PARTITA WHERE ID_Partita = ? AND nomeSquadra = ?";
        try (Connection conn = getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, idPartita);
            pstmt.setString(2, nomeSquadra);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                return mapResultSetToPartita(rs);
            } else {
                return null;
            }
        }
    }

    public List<Partita> findAll() throws SQLException, ClassNotFoundException {
        String sql = "SELECT ID_Partita, nomeSquadra, nomeCompetizione, annoSvolgimento, ID_Rosa, inCasa, data, ora, risultato, nomeStadio FROM PARTITA";
        List<Partita> partitaList = new ArrayList<>();
        try (Connection conn = getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql);
             ResultSet rs = pstmt.executeQuery()) {
            while (rs.next()) {
                partitaList.add(mapResultSetToPartita(rs));
            }
            return partitaList;
        }
    }

    public void create(Partita partita) throws SQLException, ClassNotFoundException {
        String sql = "INSERT INTO PARTITA (ID_Partita, nomeSquadra, nomeCompetizione, annoSvolgimento, ID_Rosa, inCasa, data, ora, risultato, nomeStadio) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        try (Connection conn = getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, partita.getID_Partita());
            pstmt.setString(2, partita.getNomeSquadra());
            pstmt.setString(3, partita.getNomeCompetizione());
            pstmt.setString(4, partita.getAnnoSvolgimentoCompetizione());
            pstmt.setInt(5, partita.getID_Rosa());
            pstmt.setBoolean(6, partita.isInCasa());
            pstmt.setDate(7, new java.sql.Date(partita.getData().getTime()));
            pstmt.setTime(8, new java.sql.Time(partita.getOra().getTime()));
            pstmt.setString(9, partita.getRisultato());
            pstmt.setString(10, partita.getNomeStadio());
            pstmt.executeUpdate();
        }
    }

    public void update(Partita partita) throws SQLException, ClassNotFoundException {
        String sql = "UPDATE PARTITA SET nomeCompetizione = ?, annoSvolgimento = ?, ID_Rosa = ?, inCasa = ?, data = ?, ora = ?, risultato = ?, nomeStadio = ? WHERE ID_Partita = ? AND nomeSquadra = ?";
        try (Connection conn = getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, partita.getNomeCompetizione());
            pstmt.setString(2, partita.getAnnoSvolgimentoCompetizione());
            pstmt.setInt(3, partita.getID_Rosa());
            pstmt.setBoolean(4, partita.isInCasa());
            pstmt.setDate(5, new java.sql.Date(partita.getData().getTime()));
            pstmt.setTime(6, new java.sql.Time(partita.getOra().getTime()));
            pstmt.setString(7, partita.getRisultato());
            pstmt.setString(8, partita.getNomeStadio());
            pstmt.setInt(9, partita.getID_Partita());
            pstmt.setString(10, partita.getNomeSquadra());
            pstmt.executeUpdate();
        }
    }

    public void delete(int idPartita, String nomeSquadra) throws SQLException, ClassNotFoundException {
        String sql = "DELETE FROM PARTITA WHERE ID_Partita = ? AND nomeSquadra = ?";
        try (Connection conn = getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, idPartita);
            pstmt.setString(2, nomeSquadra);
            pstmt.executeUpdate();
        }
    }

    public List<Partita> findByCompetizioneAnno(String nomeCompetizione, String annoSvolgimento) throws SQLException, ClassNotFoundException {
        String sql = "SELECT ID_Partita, nomeSquadra, nomeCompetizione, annoSvolgimento, ID_Rosa, inCasa, data, ora, risultato, nomeStadio FROM PARTITA WHERE nomeCompetizione = ? AND annoSvolgimento = ?";
        List<Partita> partitaList = new ArrayList<>();
        try (Connection conn = getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, nomeCompetizione);
            pstmt.setString(2, annoSvolgimento);
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                partitaList.add(mapResultSetToPartita(rs));
            }
            return partitaList;
        }
    }

    public List<Partita> findByNomeSquadra(String nomeSquadra) throws SQLException, ClassNotFoundException {
        String sql = "SELECT ID_Partita, nomeSquadra, nomeCompetizione, annoSvolgimento, ID_Rosa, inCasa, data, ora, risultato, nomeStadio FROM PARTITA WHERE nomeSquadra = ?";
        List<Partita> partitaList = new ArrayList<>();
        try (Connection conn = getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, nomeSquadra);
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                partitaList.add(mapResultSetToPartita(rs));
            }
            return partitaList;
        }
    }


    // Metodi aggiuntivi (se necessario)
    // ...
}