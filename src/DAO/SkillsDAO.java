package DAO;

import Database.DatabaseConnection;
import Model.Skills;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class SkillsDAO {

    public List<String> getAllSkills() throws SQLException {
        List<String> skillsList = new ArrayList<>();
        String sql = "SELECT nomeSkill FROM Skills"; // Recupera solo i nomi delle skill
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                String skill = rs.getString("nomeSkill");
                skillsList.add(skill);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
        return skillsList;
    }
}
/*
    public List<Skills> getAllSkills() throws SQLException {
        String sql = "SELECT * FROM Skills";
        try (PreparedStatement ps = connection.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            List<Skills> skillsList = new ArrayList<>();
            while (rs.next()) {
                Skills skill = new Skills();
                skill.setNomeSkill(rs.getString("nomeSkill"));
                skill.setDescrizione(rs.getString("descrizione"));
                skillsList.add(skill);
            }
            return skillsList;
        }
    }

    public void addSkill(Skills skill) throws SQLException {
        String sql = "INSERT INTO Skills (nomeSkill, descrizione) VALUES (?, ?)";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, skill.getNomeSkill());
            ps.setString(2, skill.getDescrizione());
            ps.executeUpdate();
        }
    }

    public void updateSkill(Skills skill) throws SQLException {
        String sql = "UPDATE Skills SET descrizione = ? WHERE nomeSkill = ?";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, skill.getDescrizione());
            ps.setString(2, skill.getNomeSkill());
            ps.executeUpdate();
        }
    }

    public void deleteSkill(String nomeSkill) throws SQLException {
        String sql = "DELETE FROM Skills WHERE nomeSkill = ?";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, nomeSkill);
            ps.executeUpdate();
        }
    }

 */

