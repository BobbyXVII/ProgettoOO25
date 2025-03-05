package DAO;

import Model.Skills;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class SkillsDAO {
    private Connection connection;

    public SkillsDAO(Connection connection) {
        this.connection = connection;
    }

    public void insert(Skills skill) throws SQLException {
        String query = "INSERT INTO Skills (nomeSkill, descrizione) VALUES (?, ?)";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setString(1, skill.getNomeSkill());
            stmt.setString(2, skill.getDescrizione());
            stmt.executeUpdate();
        }
    }

    public Skills getByNomeSkill(String nomeSkill) throws SQLException {
        String query = "SELECT * FROM Skills WHERE nomeSkill = ?";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setString(1, nomeSkill);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return new Skills(
                        rs.getString("nomeSkill"),
                        rs.getString("descrizione")
                );
            }
        }
        return null;
    }

    public List<Skills> getAll() throws SQLException {
        List<Skills> skillsList = new ArrayList<>();
        String query = "SELECT * FROM Skills";
        try (Statement stmt = connection.createStatement(); ResultSet rs = stmt.executeQuery(query)) {
            while (rs.next()) {
                skillsList.add(new Skills(
                        rs.getString("nomeSkill"),
                        rs.getString("descrizione")
                ));
            }
        }
        return skillsList;
    }

    public void update(Skills skill) throws SQLException {
        String query = "UPDATE Skills SET descrizione = ? WHERE nomeSkill = ?";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setString(1, skill.getDescrizione());
            stmt.setString(2, skill.getNomeSkill());
            stmt.executeUpdate();
        }
    }

    public void delete(String nomeSkill) throws SQLException {
        String query = "DELETE FROM Skills WHERE nomeSkill = ?";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setString(1, nomeSkill);
            stmt.executeUpdate();
        }
    }
}
