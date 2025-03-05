package Database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class DatabaseConnection {

    private static final Logger LOGGER = Logger.getLogger(DatabaseConnection.class.getName());

    // NIENTE PIÙ CREDENZIALI HARDCODED QUI!
    // Le leggeremo dalle variabili d'ambiente

    public static Connection getConnection() throws SQLException, ClassNotFoundException {
        String dbUrl = System.getenv("DB_URL");        // Leggi la variabile d'ambiente DB_URL
        String dbUsername = System.getenv("DB_USERNAME"); // Leggi la variabile d'ambiente DB_USERNAME
        String dbPassword = System.getenv("DB_PASSWORD"); // Leggi la variabile d'ambiente DB_PASSWORD
        String jdbcDriver = "org.postgresql.Driver"; // Il driver JDBC rimane costante

        if (dbUrl == null || dbUsername == null || dbPassword == null) {
            LOGGER.severe("Variabili d'ambiente del database non configurate (DB_URL, DB_USERNAME, DB_PASSWORD)");
            throw new SQLException("Variabili d'ambiente del database non configurate.");
        }

        Class.forName(jdbcDriver);
        return DriverManager.getConnection(dbUrl, dbUsername, dbPassword);
    }

    public static void main(String[] args) {
        try {
            Connection connection = DatabaseConnection.getConnection();
            if (connection != null) {
                System.out.println("Connessione al database stabilita con successo!");
                connection.close();
            } else {
                System.out.println("Connessione al database fallita.");
            }
        } catch (SQLException | ClassNotFoundException e) {
            LOGGER.log(Level.SEVERE, "Errore durante la connessione al database:", e);
        }
    }
}