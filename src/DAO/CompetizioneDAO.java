package DAO;

import Database.DatabaseConnection;
import Model.Competizione;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class CompetizioneDAO {

    // Metodo per ottenere una connessione al database
    private Connection getConnection() throws SQLException, ClassNotFoundException {
        return DatabaseConnection.getConnection();
    }

    // Metodo per mappare una riga del ResultSet a un oggetto Competizione
    private Competizione mapResultSetToCompetizione(ResultSet rs) throws SQLException {
        Competizione competizione = new Competizione();
        competizione.setNomeCompetizione(rs.getString("nome_competizione"));
        competizione.setAnnoSvolgimento(rs.getString("anno_svolgimento"));
        competizione.setTipCompetizione(rs.getString("tip_competizione"));
        competizione.setDescrizioneCompetizione(rs.getString("descrizione_competizione"));
        return competizione;
    }

    public Competizione findByNomeAnno(String nomeCompetizione, String annoSvolgimento) throws SQLException, ClassNotFoundException {
        String sql = "SELECT nome_competizione, anno_svolgimento, tip_competizione, descrizione_competizione FROM Competizione WHERE nome_competizione = ? AND anno_svolgimento = ?";
        try (Connection conn = getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, nomeCompetizione);
            pstmt.setString(2, annoSvolgimento);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                return mapResultSetToCompetizione(rs);
            } else {
                return null; // Competizione non trovata
            }
        }
    }

    public List<Competizione> findAll() throws SQLException, ClassNotFoundException {
        String sql = "SELECT nome_competizione, anno_svolgimento, tip_competizione, descrizione_competizione FROM Competizione";
        List<Competizione> competizioni = new ArrayList<>();
        try (Connection conn = getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql);
             ResultSet rs = pstmt.executeQuery()) {
            while (rs.next()) {
                competizioni.add(mapResultSetToCompetizione(rs));
            }
            return competizioni;
        }
    }

    public void create(Competizione competizione) throws SQLException, ClassNotFoundException {
        String sql = "INSERT INTO Competizione (nome_competizione, anno_svolgimento, tip_competizione, descrizione_competizione) VALUES (?, ?, ?, ?)";
        try (Connection conn = getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, competizione.getNomeCompetizione());
            pstmt.setString(2, competizione.getAn