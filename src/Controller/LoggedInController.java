package Controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import java.io.IOException;

public class LoggedInController {

    @FXML
    private Button GoToProfile;

    @FXML
    private Button Q_Start;

    @FXML
    private TextField Q_Search;

    public static String StringaQuery;

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

    private void showError(String title, String message) {
        Alert alert = new Alert(AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
