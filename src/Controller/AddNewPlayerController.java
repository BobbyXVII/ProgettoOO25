package Controller;

import DAO.PersonaDAO;
import Model.Persona;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.SQLException;
import java.sql.Date;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import java.util.Optional;
import java.time.LocalDate;

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

    private final PersonaDAO personaDAO = new PersonaDAO();

    public static int idNewPlayerAdded;

    @FXML
    public void initialize() {
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

        ObservableList<String> tipologiePiedi = FXCollections.observableArrayList("Destro", "Sinistro", "Ambidestro");
        PiedeChoiceBox.setItems(tipologiePiedi);
    }

    @FXML
    private void handleBack() {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Conferma");
        alert.setHeaderText("Vuoi annullare la compilazione?");
        alert.setContentText("Questa azione cancellerà i dati inseriti.");

        Optional<ButtonType> result = alert.showAndWait();
        if (result.isPresent() && result.get() == ButtonType.OK) {
            BackToSearch();
        }
    }

    @FXML
    private void handleProceed() {
        if (campiValidi()) {
            LocalDate localDate = dataNascitaPicker.getValue();
            Date data = Date.valueOf(localDate);
            float altezzaFinale = Float.parseFloat(altezzaField.getText());
            Persona persona = new Persona(String.valueOf(nomeField.getText().trim()),String.valueOf(cognomeField.getText().trim()),data,String.valueOf(nationalityChoiceBox.getValue().trim()),altezzaFinale,String.valueOf(PiedeChoiceBox.getValue().trim()));
            try {
                personaDAO.addPersona(persona);
                ProceedAdd();
            } catch (SQLException e) {
            }
        }else{
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

    private void BackToSearch() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("../Interfacce/LoggedIn.fxml"));
            Stage stage = (Stage) Back_btn.getScene().getWindow();
            Scene scene = new Scene(loader.load());
            stage.setScene(scene);
        } catch (IOException e) {
            showAlert("ERRORE NEL SISTEMA", "Il sistema non è riuscito ad elaborare correttamente la richiesta.");
        }
    }

    private void ProceedAdd() throws SQLException {
        int resultAdding = personaDAO.getLastInsertedId();
        if (resultAdding == -1){
            showAlert("ERRORE NEL SISTEMA", "Calciatore non aggiunto correttamente.");
        }else{
            idNewPlayerAdded = resultAdding;
        }
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/Interfacce/ANP/AddNewPlayer2.fxml"));
            Stage stage = (Stage) Proceed_btn.getScene().getWindow();
            Scene scene = new Scene(loader.load());
            stage.setScene(scene);
        } catch (IOException e) {
            e.printStackTrace();
            showAlert("ERRORE NEL SISTEMA", "Il sistema non è riuscito ad elaborare correttamente la richiesta.");
        }
    }

    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
