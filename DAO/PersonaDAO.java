package DAO;

import Database.DatabaseConnection;
import Model.Persona;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class PersonaDAO {

    public static void inserisciPersona(Persona persona) throws SQLException, ClassNotFoundException {
        String query = "INSERT INTO Persona (nome, cognome, data_nascita, nazionalita, altezza, piede) VALUES (?, ?, ?, ?, ?, ?)";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {
            stmt.setString(1, persona.getNome());
            stmt.setString(2, persona.getCognome());
            stmt.setDate(3, Date.valueOf(persona.getDataNascita()));
            stmt.setString(4, persona.getNazionalita());
            stmt.setFloat(5, persona.getAltezza());
            stmt.setString(6, persona.getPiede());
            stmt.executeUpdate();
        }
    }

    public static Persona getPersonaById(int id) throws SQLException, ClassNotFoundException {
        String query = "SELECT * FROM Persona WHERE id = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return new Persona(rs.getInt("id"), rs.getString("nome"), rs.getString("cognome"),
                        rs.getString("data_nascita"), rs.getString("nazionalita"),
                        rs.getFloat("altezza"), rs.getString("piede"));
            }
        }
        return null;
    }

    public static List<Persona> getAllPersone() throws SQLException, ClassNotFoundException {
        List<Persona> persone = new ArrayList<>();
        String query = "SELECT * FROM Persona";
        try (Connection conn = DatabaseConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {
            while (rs.next()) {
                persone.add(new Persona(rs.getInt("id"), rs.getString("nome"), rs.getString("cognome"),
                        rs.getString("data_nascita"), rs.getString("nazionalita"),
                        rs.getFloat("altezza"), rs.getString("piede")));
            }
        }
        return persone;
    }

    public static void aggiornaPersona(Persona persona) throws SQLException, ClassNotFoundException {
        String query = "UPDATE Persona SET nome=?, cognome=?, data_nascita=?, nazionalita=?, altezza=?, piede=? WHERE id=?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, persona.getNome());
            stmt.setString(2, persona.getCognome());
            stmt.setDate(3, Date.valueOf(persona.getDataNascita()));
            stmt.setString(4, persona.getNazionalita());
            stmt.setFloat(5, persona.getAltezza());
            stmt.setString(6, persona.getPiede());
            stmt.setInt(7, persona.getId());
            stmt.executeUpdate();
        }
    }

    public static void eliminaPersona(int id) throws SQLException, ClassNotFoundException {
        String query = "DELETE FROM Persona WHERE id = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setInt(1, id);
            stmt.executeUpdate();
        }
    }
}

