package Controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.stage.Stage;
import java.io.IOException;

public class LoggedInController {

    @FXML
    private Button GoToProfile;

    // Questo metodo viene chiamato quando si clicca sul bottone "GoToProfile"
    public void handleGoToProfile() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("../Interfacce/MyProfile.fxml"));
            Stage stage = (Stage) GoToProfile.getScene().getWindow();
            Scene scene = new Scene(loader.load());
            stage.setScene(scene);

        } catch (IOException e) {
            // Se c'è un errore nel caricare il file FXML
            Alert alert = new Alert(AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("Failed to load profile");
            alert.setContentText("There was an error loading the profile view.");
            alert.showAndWait();
        }
    }
}
