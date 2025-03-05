package DAO;

import java.sql.*;
import Model.Skills;
import java.util.ArrayList;
import java.util.List;

public class SkillsDAO {
    private Connection connection;

    public SkillsDAO(Connection connection) {
        this.connection = connection;
    }

    public Skills getSkillByName(String nomeSkill) throws SQLException {
        String sql = "SELECT * FROM Skills WHERE nomeSkill = ?";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, nomeSkill);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                Skills skill = new Skills();
                skill.setNomeSkill(rs.getString("nomeSkill"));
                skill.setDescrizione(rs.getString("descrizione"));
                return skill;
            }
            return null;
        }
    }

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
}
