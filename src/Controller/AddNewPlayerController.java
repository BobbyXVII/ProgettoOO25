package Controller;

import Database.DatabaseConnection;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Date;

public class AddNewPlayerController {

    @FXML
    private ChoiceBox<String> nationalityChoiceBox;

    @FXML
    private ChoiceBox<String> PiedeChoiceBox;

    @FXML
    private TextField nomeField;

    @FXML
    private TextField cognomeField;

    @FXML
    private DatePicker dataNascitaPicker;

    @FXML
    private TextField altezzaField;

    @FXML
    private Button Back_btn;

    @FXML
    private Button Proceed_btn;

    @FXML
    public void initialize() {
        // Imposta le opzioni per la ChoiceBox della Nazionalità
        ObservableList<String> nazionalita = FXCollections.observableArrayList(
                "Italia", "Germania", "Francia", "Spagna", "Inghilterra", "Portogallo", "Brasile", "Argentina", "Stati Uniti",
                "Belgio", "Olanda", "Polonia", "Grecia", "Svezia", "Russia", "Giappone", "Messico", "Uruguay", "Colombia",
                "Cile", "Croazia", "Danimarca", "Serbia", "Svizzera", "Australia", "Ecuador", "Tunisia", "Algeria", "Canada",
                "Corea del Sud", "Perù", "Egitto", "Nigeria", "Camerun", "Sud Africa", "Turchia", "Israele", "Romania",
                "Bulgaria", "Finlandia", "Norvegia", "Austria", "Slovacchia", "Slovenia", "Ceco", "Costa Rica", "Giamaica",
                "Honduras", "Panama", "Kenia", "Zambia", "Mozambico", "Ghana", "Marocco", "Galles", "Scozia", "Paraguay",
                "Bolivia", "Venezuela", "Perù", "Ecuador", "Libia", "Seychelles", "Malta", "Figi", "Samoa", "Tonga",
                "Isole Faroe", "Isole Cook", "Saint Kitts e Nevis", "Saint Lucia", "Barbados", "Trinidad e Tobago",
                "Grenada", "Antigua e Barbuda", "Saint Vincent e Grenadine", "Bahamas", "Bermuda", "Isole Vergini",
                "Armenia", "Georgia", "Azerbaigian", "Kazakhstan", "Uzbekistan", "Turkmenistan", "Kyrgyzstan", "Tajikistan",
                "Mongolia", "Nepal", "Bhutan", "Maldivas", "Sri Lanka", "Myanmar", "Cambogia", "Laos", "Filippine",
                "Indonesia", "Singapore", "Malaysia", "Brunei"
        );
        nationalityChoiceBox.setItems(nazionalita);

        // Imposta le opzioni per la ChoiceBox del Piede
        ObservableList<String> tipologiePiedi = FXCollections.observableArrayList("Destro", "Sinistro", "Ambidestro");
        PiedeChoiceBox.setItems(tipologiePiedi);
    }

    @FXML
    private void handleBack() {
        cambiaScena("SearchIn.fxml");
    }

    @FXML
    private void handleProceed() {
        if (campiValidi()) {
            // Proviamo a salvare il giocatore nel database
            if (salvaGiocatore()) {
                cambiaScena2("AddNewPlayer2.fxml");
            }
        } else {
            mostraErrore("Compila tutti i campi prima di procedere.");
        }
    }

    private boolean campiValidi() {
        return !nomeField.getText().isEmpty() &&
                !cognomeField.getText().isEmpty() &&
                dataNascitaPicker.getValue() != null &&
                !altezzaField.getText().isEmpty() &&
                nationalityChoiceBox.getValue() != null &&
                PiedeChoiceBox.getValue() != null;
    }

    private void mostraErrore(String messaggio) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Errore");
        alert.setHeaderText(null);
        alert.setContentText(messaggio);
        alert.showAndWait();
    }

    private void cambiaScena(String fxmlFile) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("../Interfacce/SearchIn.fxml"));
            Parent root = loader.load();

            Stage stage = (Stage) Back_btn.getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.show();
        } catch (IOException e) {
            System.out.println("Errore nel caricamento del file FXML: " + fxmlFile);
            e.printStackTrace();
        }
    }

    private void cambiaScena2(String fxmlFile) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("../Interfacce/ANP/AddNewPlayer2.fxml"));
            Parent root = loader.load();

            Stage stage = (Stage) Proceed_btn.getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.show();
        } catch (IOException e) {
            System.out.println("Errore nel caricamento del file FXML: " + fxmlFile);
            e.printStackTrace();
        }
    }

    /**
     * Metodo che salva i dati del nuovo giocatore nel database.
     *
     * @return true se l'operazione è andata a buon fine, false altrimenti
     */
    private boolean salvaGiocatore() {
        String sql = "INSERT INTO persona (nome, cognome, data_nascita, altezza, nazionalita, piede) VALUES (?, ?, ?, ?, ?, ?)";
        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement ps = connection.prepareStatement(sql)) {

            ps.setString(1, nomeField.getText());
            ps.setString(2, cognomeField.getText());
            // Convertiamo la data del DatePicker nel formato java.sql.Date
            ps.setDate(3, Date.valueOf(dataNascitaPicker.getValue()));
            // Converte il campo altezza in double
            ps.setDouble(4, Double.parseDouble(altezzaField.getText()));
            ps.setString(5, nationalityChoiceBox.getValue());
            ps.setString(6, PiedeChoiceBox.getValue());

            ps.executeUpdate();
            return true;
        } catch (SQLException ex) {
            ex.printStackTrace();
            mostraErrore("Errore nell'inserimento del giocatore nel database: " + ex.getMessage());
            return false;
        } catch (NumberFormatException ex) {
            ex.printStackTrace();
            mostraErrore("Il campo altezza deve essere un numero valido.");
            return false;
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }
}
