package Controller;

import DAO.CompetizioneDAO;
import Model.Competizione;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class AddNewCompetitionController {

    private String TipSelected;

    @FXML private TextField Add_nomeComp, Add_annoSvolgimento;
    @FXML private ComboBox nationalityComboBox,Add_TipComp;
    @FXML private Button Back_btn, Proceed_btn;
    private String ResultAnno;

    private final CompetizioneDAO competizioneDAO = new CompetizioneDAO();

    public void initialize(){

        ObservableList<String> nazionalita = FXCollections.observableArrayList(
                "Italia", "Internazionale","Germania", "Francia", "Spagna", "Inghilterra", "Portogallo", "Brasile", "Argentina", "Stati Uniti",
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
                "Indonesia", "Singapore", "Malaysia", "Brunei");
        nationalityComboBox.setItems(nazionalita);

        try {
            List<String> TotalTip = competizioneDAO.getTipologieCompetizioni();
            if (TotalTip != null) {
                ObservableList<String> TipList = FXCollections.observableArrayList(TotalTip);
                Add_TipComp.setItems(TipList);
            }
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }

        Add_TipComp.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {
                TipSelected = String.valueOf(newValue);
            }
        });

        Add_annoSvolgimento.textProperty().addListener((observable, oldValue, newValue) -> {
            ResultAnno = newValue;
        });
    }

    @FXML
    private void handleProceed() throws SQLException {
        if (Add_nomeComp.getText() != null && Add_annoSvolgimento.getText() != null && nationalityComboBox.getValue() != null && TipSelected !=  null){
            if (!isValidAnnoSvolgimento(ResultAnno)) {
                showError("Formato Anno Svolgimento", "Il formato deve essere 'YYYY/YYYY'.");
            }else{
                List<String> comp = competizioneDAO.getCompetizioniAndDate();
                String nomeCompetizione = Add_nomeComp.getText().trim();
                String annoSvolgimento = Add_annoSvolgimento.getText().trim();

                String competizioneAnno = nomeCompetizione + " - " + annoSvolgimento;

                if (comp.contains(competizioneAnno)) {
                    showError("ERRORE CREAZIONE", "Competizione con anno svolgimento e nome già esistente");
                }else{
                    Competizione competizione = new Competizione(String.valueOf(Add_nomeComp.getText().trim()),String.valueOf(Add_annoSvolgimento.getText().trim()),String.valueOf(TipSelected),String.valueOf(nationalityComboBox.getValue()));
                    try {
                        competizioneDAO.addCompetizione(competizione);
                        showSuccessAlert();
                    } catch (SQLException e) {
                        showError("ERRORE CREAZIONE","Competizione non aggiunta correttamente.");
                        throw new RuntimeException(e);
                    }
                    try {
                        FXMLLoader loader = new FXMLLoader(getClass().getResource("../Interfacce/LoggedIn.fxml"));
                        Stage stage = (Stage) Proceed_btn.getScene().getWindow();
                        Scene scene = new Scene(loader.load());
                        stage.setScene(scene);
                    } catch (IOException ignored) {
                    }
                }
            }
        }else{
            showError("ERRORE INSERIMENTO", "Compila tutti i campi prima di procedere.");
        }
    }

    @FXML
    private void handleBack() {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Conferma");
        alert.setHeaderText("Vuoi annullare la compilazione?");
        alert.setContentText("Questa azione cancellerà i dati inseriti.");

        Optional<ButtonType> result = alert.showAndWait();
        if (result.isPresent() && result.get() == ButtonType.OK) {
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("../Interfacce/LoggedIn.fxml"));
                Stage stage = (Stage) Back_btn.getScene().getWindow();
                Scene scene = new Scene(loader.load());
                stage.setScene(scene);
            } catch (IOException e) {
            }
        }
    }

    private boolean isValidAnnoSvolgimento(String input) {
        String regex = "\\d{4}/\\d{4}";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(input);
        return matcher.matches();
    }

    private void showError(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    private void showSuccessAlert() {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Successo");
        alert.setHeaderText(null);
        alert.setContentText("Competizione aggiunta con successo!");
        alert.show();

        Platform.runLater(() -> {
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            alert.close();
        });
    }
}
