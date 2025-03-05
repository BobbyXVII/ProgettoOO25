package DAO;

import Model.Persona;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class PersonaDAO {
    private Connection conn;

    public PersonaDAO(Connection conn) {
        this.conn = conn;
    }

    public void addPersona(Persona persona) throws SQLException {
        String sql = "INSERT INTO Persona (nome, cognome, data_Nascita, nazionalita, altezza, piede) VALUES (?, ?, ?, ?, ?, ?)";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, persona.getNome());
            stmt.setString(2, persona.getCognome());
            stmt.setDate(3, new java.sql.Date(persona.getDataNascita().getTime()));
            stmt.setString(4, persona.getNazionalita());
            stmt.setFloat(5, persona.getAltezza());
            stmt.setString(6, persona.getPiede());
            stmt.executeUpdate();
        }
    }

    public Persona getPersonaById(int id) throws SQLException {
        String sql = "SELECT * FROM Persona WHERE ID = ?";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return new Persona(
                        rs.getInt("ID"),
                        rs.getString("nome"),
                        rs.getString("cognome"),
                        rs.getDate("data_Nascita"),
                        rs.getString("nazionalita"),
                        rs.getFloat("altezza"),
                        rs.getString("piede")
                );
            }
        }
        return null;
    }

    public List<Persona> getAllPersonas() throws SQLException {
        List<Persona> personas = new ArrayList<>();
        String sql = "SELECT * FROM Persona";
        try (Statement stmt = conn.createStatement(); ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                personas.add(new Persona(
                        rs.getInt("ID"),
                        rs.getString("nome"),
                        rs.getString("cognome"),
                        rs.getDate("data_Nascita"),
                        rs.getString("nazionalita"),
                        rs.getFloat("altezza"),
                        rs.getString("piede")
                ));
            }
        }
        return personas;
    }

    public void updatePersona(Persona persona) throws SQLException {
        String sql = "UPDATE Persona SET nome = ?, cognome = ?, data_Nascita = ?, nazionalita = ?, altezza = ?, piede = ? WHERE ID = ?";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, persona.getNome());
            stmt.setString(2, persona.getCognome());
            stmt.setDate(3, new java.sql.Date(persona.getDataNascita().getTime()));
            stmt.setString(4, persona.getNazionalita());
            stmt.setFloat(5, persona.getAltezza());
            stmt.setString(6, persona.getPiede());
            stmt.setInt(7, persona.getId());
            stmt.executeUpdate();
        }
    }

    public void deletePersona(int id) throws SQLException {
        String sql = "DELETE FROM Persona WHERE ID = ?";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);
            stmt.executeUpdate();
        }
    }
}
