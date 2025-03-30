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
