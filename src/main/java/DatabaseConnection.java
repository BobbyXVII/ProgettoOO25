import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.logging.Level; // Importa Level
import java.util.logging.Logger; // Importa Logger

public class DatabaseConnection {

    private static final String DB_URL = "jdbc:postgresql://localhost:5432/UninaSoccer"; // Sostituisci
    private static final String DB_USERNAME = "postgres";       // Sostituisci
    private static final String DB_PASSWORD = "2486";       // Sostituisci
    private static final String JDBC_DRIVER = "org.postgresql.Driver";

    private static final Logger LOGGER = Logger.getLogger(DatabaseConnection.class.getName()); // Logger statico

    public static Connection getConnection() throws SQLException, ClassNotFoundException {
        Class.forName(JDBC_DRIVER);
        return DriverManager.getConnection(DB_URL, DB_USERNAME, DB_PASSWORD);
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
            LOGGER.log(Level.SEVERE, "Errore durante la connessione al database:", e); // Logging robusto
        }
    }
}