import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnection {

    private static final String DB_URL = "jdbc:postgresql://localhost:5432/UninaSoccer"; // Sostituisci con il tuo URL
    private static final String DB_USERNAME = "postgres"; // Sostituisci con il tuo username
    private static final String DB_PASSWORD = "2486"; // Sostituisci con la tua password
    private static final String JDBC_DRIVER = "org.postgresql.Driver";
    public static Connection getConnection() throws SQLException, ClassNotFoundException {
        Class.forName(JDBC_DRIVER); // Carica il driver JDBC
        return DriverManager.getConnection(DB_URL, DB_USERNAME, DB_PASSWORD); // Stabilisci la connessione
    }

    public static void main(String[] args) {
        try {
            Connection connection = DatabaseConnection.getConnection();
            if (connection != null) {
                System.out.println("Connessione al database stabilita con successo!");
                connection.close(); // Chiudi la connessione dopo il test
            } else {
                System.out.println("Connessione al database fallita.");
            }
        } catch (SQLException | ClassNotFoundException e) {
            System.err.println("Errore durante la connessione al database:");
            e.printStackTrace();
        }
    }
}