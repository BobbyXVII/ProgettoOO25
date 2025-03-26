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
            stmt.setInt(1, ID);  // Imposta il parametro ID
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return rs.getString("nome") + " " + rs.getString("cognome"); // Ritorna "Nome Cognome"
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw e;
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
        return null; // Se non trova la persona, ritorna null
    }

    public int getIdByNome(String result) throws SQLException {
        String sql = "SELECT id FROM Persona WHERE LOWER(nome || ' ' || cognome) LIKE LOWER(?)";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            // Imposta il parametro per il nome completo (nome e cognome)
            stmt.setString(1, "%" + result.toLowerCase() + "%");

            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return rs.getInt("id");
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw e;
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
        return -1; // Se non trova la persona, ritorna -1
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
        return -1; // Se non trova alcun ID, ritorna -1
    }
/*
    public List<Persona> getAllPersonas() throws SQLException {
        String sql = "SELECT * FROM Persona";
        try (PreparedStatement ps = connection.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            List<Persona> personas = new ArrayList<>();
            while (rs.next()) {
                Persona persona = new Persona();
                persona.setID(rs.getInt("ID"));
                persona.setNome(rs.getString("nome"));
                persona.setCognome(rs.getString("cognome"));
                persona.setData_Nascita(rs.getDate("data_Nascita"));
                persona.setNazionalita(rs.getString("nazionalita"));
                persona.setAltezza(rs.getFloat("altezza"));
                persona.setPiede(rs.getString("piede"));
                personas.add(persona);
            }
            return personas;
        }
    }

    public void addPersona(Persona persona) throws SQLException {
        String sql = "INSERT INTO Persona (nome, cognome, data_Nascita, nazionalita, altezza, piede) VALUES (?, ?, ?, ?, ?, ?)";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, persona.getNome());
            ps.setString(2, persona.getCognome());
            ps.setDate(3, persona.getData_Nascita());
            ps.setString(4, persona.getNazionalita());
            ps.setFloat(5, persona.getAltezza());
            ps.setString(6, persona.getPiede());
            ps.executeUpdate();
        }
    }

    public void updatePersona(Persona persona) throws SQLException {
        String sql = "UPDATE Persona SET nome = ?, cognome = ?, data_Nascita = ?, nazionalita = ?, altezza = ?, piede = ? WHERE ID = ?";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, persona.getNome());
            ps.setString(2, persona.getCognome());
            ps.setDate(3, persona.getData_Nascita());
            ps.setString(4, persona.getNazionalita());
            ps.setFloat(5, persona.getAltezza());
            ps.setString(6, persona.getPiede());
            ps.setInt(7, persona.getID());
            ps.executeUpdate();
        }
    }

    public void deletePersona(int ID) throws SQLException {
        String sql = "DELETE FROM Persona WHERE ID = ?";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setInt(1, ID);
            ps.executeUpdate();
        }
    }
 */
}
