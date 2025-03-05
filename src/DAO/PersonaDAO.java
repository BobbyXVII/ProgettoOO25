package DAO;

import java.sql.*;
import Model.Persona;
import java.util.ArrayList;
import java.util.List;

public class PersonaDAO {
    private Connection connection;

    public PersonaDAO(Connection connection) {
        this.connection = connection;
    }

    public Persona getPersonaById(int ID) throws SQLException {
        String sql = "SELECT * FROM Persona WHERE ID = ?";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setInt(1, ID);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                Persona persona = new Persona();
                persona.setID(rs.getInt("ID"));
                persona.setNome(rs.getString("nome"));
                persona.setCognome(rs.getString("cognome"));
                persona.setData_Nascita(rs.getDate("data_Nascita"));
                persona.setNazionalita(rs.getString("nazionalita"));
                persona.setAltezza(rs.getFloat("altezza"));
                persona.setPiede(rs.getString("piede"));
                return persona;
            }
            return null;
        }
    }

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
}
