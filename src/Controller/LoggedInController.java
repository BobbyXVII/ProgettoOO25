package Controller;

import DAO.UtenteDAO;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import java.io.IOException;
import java.sql.SQLException;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ButtonType;
import java.util.Optional;


public class LoggedInController {
    @FXML
    private Button GoToProfile;

    @FXML
    private Button Q_Start;

    @FXML
    private TextField Q_Search;

    @FXML
    private Button Add_btn;

    public static String StringaQuery;

    private final UtenteDAO utenteDAO = new UtenteDAO();

    //importazione variabile globale
    private final String UtenteConnesso = ControllerLogin.nomeUtenteConnesso;

    public void handleGoToProfile() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/Interfacce/MyProfile.fxml"));
            Stage stage = (Stage) GoToProfile.getScene().getWindow();
            Scene scene = new Scene(loader.load());
            stage.setScene(scene);
        } catch (IOException e) {
            showError("ERRORE NEL SISTEMA", "Il sistema non è riuscito ad elaborare correttamente la richiesta.");
        }
    }

    public void initialize() throws SQLException {
        String Pex = utenteDAO.ControllaPex(UtenteConnesso);
        ShowButtons(0);
        if ("ADMIN".equals(Pex)) {
            ShowButtons(1);
        }
    }

    private void ShowButtons(int i) {
        Add_btn.setOpacity(i);
        Add_btn.setDisable(i == 0);
    }


    public void handleGoAdd() {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Seleziona un'opzione");
        alert.setHeaderText("Cosa vuoi aggiungere?");
        alert.setContentText("Scegli tra le seguenti opzioni:");

        ButtonType addPlayer = new ButtonType("Calciatore");
        ButtonType addTeam = new ButtonType("Squadra");
        ButtonType addCompetition = new ButtonType("Competizione");
        ButtonType cancel = new ButtonType("Annulla", ButtonBar.ButtonData.CANCEL_CLOSE);

        alert.getButtonTypes().setAll(addPlayer, addTeam, addCompetition, cancel);
        Optional<ButtonType> result = alert.showAndWait();
        if (result.isPresent()) {
            if (result.get() == addPlayer) {
                try {
                    FXMLLoader loader = new FXMLLoader(getClass().getResource("../Interfacce/ANP/AddNewPlayer2.fxml"));
                    Stage stage = (Stage) Add_btn.getScene().getWindow();
                    Scene scene = new Scene(loader.load());
                    stage.setScene(scene);
                } catch (IOException e) {
                    e.printStackTrace();  // Stampa l'eccezione per capire cosa sta succedendo
                    showError("ERRORE NEL SISTEMA", "Il sistema non è riuscito ad elaborare correttamente la richiesta.");
                }
            }
        }
    }

    public void handleSearch() {
        String inputText = Q_Search.getText().trim();
        if (inputText.isEmpty()) {
            showError("Errore", "Il campo di ricerca non può essere vuoto.");
            return;
        }

        StringaQuery = inputText;
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/Interfacce/SearchIn.fxml"));
            Stage stage = (Stage) Q_Start.getScene().getWindow();
            Scene scene = new Scene(loader.load());
            stage.setScene(scene);
        } catch (IOException e) {
            showError("ERRORE NEL SISTEMA", "Il sistema non è riuscito ad elaborare correttamente la richiesta.");
        }
    }

    public void handleSearchSuggest() {
        StringaQuery = "Antonio Conte";
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/Interfacce/SearchIn.fxml"));
            Stage stage = (Stage) Q_Start.getScene().getWindow();
            Scene scene = new Scene(loader.load());
            stage.setScene(scene);
        } catch (IOException e) {
            showError("ERRORE NEL SISTEMA", "Il sistema non è riuscito ad elaborare correttamente la richiesta.");
        }
    }

    public void handleSearchSuggest2() {
        StringaQuery = "Alex Meret";
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/Interfacce/SearchIn.fxml"));
            Stage stage = (Stage) Q_Start.getScene().getWindow();
            Scene scene = new Scene(loader.load());
            stage.setScene(scene);
        } catch (IOException e) {
            showError("ERRORE NEL SISTEMA", "Il sistema non è riuscito ad elaborare correttamente la richiesta.");
        }
    }

    public void handleSearchSuggest3() {
        StringaQuery = "Champions League";
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/Interfacce/SearchIn.fxml"));
            Stage stage = (Stage) Q_Start.getScene().getWindow();
            Scene scene = new Scene(loader.load());
            stage.setScene(scene);
        } catch (IOException e) {
            showError("ERRORE NEL SISTEMA", "Il sistema non è riuscito ad elaborare correttamente la richiesta.");
        }
    }

    private void showError(String title, String message) {
        Alert alert = new Alert(AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
