package DAO;

import java.sql.*;

import Database.DatabaseConnection;
import Model.Persona;
import java.util.ArrayList;
import java.util.List;

public class PersonaDAO {

    public String getNomeCognomeById(int ID) throws SQLException {
        String sql = "SELECT nome, cognome FROM Persona WHERE ID = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, ID);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return rs.getString("nome") + " " + rs.getString("cognome");
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw e;
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
        return null;
    }

    public Persona getPersonaById(int ID) throws SQLException {
        String sql = "SELECT nome, cognome, data_Nascita, nazionalita, altezza, piede FROM Persona WHERE ID = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, ID);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                String nome = rs.getString("nome");
                String cognome = rs.getString("cognome");
                Date dataNascita = rs.getDate("data_Nascita");
                String nazionalita = rs.getString("nazionalita");
                float altezza = rs.getFloat("altezza");
                String piede = rs.getString("piede");

                return new Persona(nome, cognome, dataNascita, nazionalita, altezza, piede);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw e;
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
        return null;
    }

    public List<Integer> getIdsByNome(String result) throws SQLException {
        List<Integer> ids = new ArrayList<>();
        String sql = "SELECT id FROM Persona WHERE LOWER(nome || ' ' || cognome) LIKE LOWER(?)";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, "%" + result.toLowerCase() + "%");

            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    ids.add(rs.getInt("id"));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw e;
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
        return ids;
    }

    public Integer getIdByNomeQ(String result) throws SQLException {
        String sql = "SELECT id FROM Persona WHERE LOWER(nome || ' ' || cognome) LIKE LOWER(?) LIMIT 1";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, "%" + result.toLowerCase() + "%");

            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt("id");
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

    public void addPersona(Persona persona) throws SQLException {
        String sql = "INSERT INTO Persona (nome, cognome, data_Nascita, nazionalita, altezza, piede) VALUES (?, ?, ?, ?, ?, ?)";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)){
            ps.setString(1, persona.getNome());
            ps.setString(2, persona.getCognome());
            ps.setDate(3, persona.getData_Nascita());
            ps.setString(4, persona.getNazionalita());
            ps.setFloat(5, persona.getAltezza());
            ps.setString(6, persona.getPiede());
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            throw e;
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    public void updatePersona(Persona persona) throws SQLException {
        String sql = "UPDATE persona SET nome = ?, cognome = ?, data_nascita = ?, nazionalita = ?, altezza = ?, piede = ? WHERE id = ?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, persona.getNome());
            ps.setString(2, persona.getCognome());
            ps.setDate(3, persona.getData_Nascita());
            ps.setString(4, persona.getNazionalita());
            ps.setFloat(5, persona.getAltezza());
            ps.setString(6, persona.getPiede());
            ps.setInt(7, persona.getId());

            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            throw e;
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    public void deletePersona(int ID) throws SQLException {
        String sql = "DELETE FROM Persona WHERE ID = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, ID);
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            throw e;
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    public int getLastInsertedId() throws SQLException {
        String sql = "SELECT MAX(ID) AS lastId FROM Persona";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            if (rs.next()) {
                return rs.getInt("lastId");
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw e;
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
        return -1;
    }
}
