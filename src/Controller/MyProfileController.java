package Controller;

import DAO.UtenteDAO;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.PasswordField;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import java.io.IOException;

public class MyProfileController {

    @FXML
    private Label welcomeLabel;
    @FXML
    private PasswordField Old_Passw;
    @FXML
    private PasswordField New_Passw;
    @FXML
    private PasswordField Confirm_Passw;
    @FXML
    private Button btn_Disconnect;
    @FXML
    private Button btn_ModifyInformation;

    @FXML
    private Button BackLogged;

    private final UtenteDAO utenteDAO = new UtenteDAO();
    private final String nomeUtente = ControllerLogin.nomeUtenteConnesso;

    public void initialize() {
        if (nomeUtente != null && !nomeUtente.isEmpty()) {
            welcomeLabel.setText("Benvenuto, " + nomeUtente + "!");
        } else {
            welcomeLabel.setText("Benvenuto, Utente!");
        }
        setupListeners();
    }

    private void setupListeners() {
        Confirm_Passw.textProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue.equals(New_Passw.getText())) {
                Confirm_Passw.setStyle("-fx-border-color: red;");
            } else {
                Confirm_Passw.setStyle("");
            }
        });
    }

    @FXML
    private void handleModifyPassword() {
        String oldPassword = Old_Passw.getText();
        String newPassword = New_Passw.getText();
        String confirmPassword = Confirm_Passw.getText();

        if (oldPassword.isEmpty() || newPassword.isEmpty() || confirmPassword.isEmpty()) {
            showAlert("Errore", "Tutti i campi devono essere compilati.");
            return;
        }

        if (!newPassword.equals(confirmPassword)) {
            showAlert("Errore", "La nuova password e la conferma devono coincidere.");
            return;
        }

        if (newPassword.length() < 8) {
            showAlert("Errore", "La password deve contenere almeno 8 caratteri.");
            return;
        }

        boolean isUpdated = utenteDAO.aggiornaPassword(nomeUtente, oldPassword, newPassword);
        if (isUpdated) {
            showAlert("Successo", "Password cambiata con successo.");
            navigateTo("/interfacce/MyProfile.fxml");
        } else {
            showAlert("Errore", "La vecchia password non è corretta.");
        }
    }

    @FXML
    private void handleDisconnect() {
        navigateTo("/interfacce/Login.fxml");
    }

    @FXML
    private void handleHomepage() {
        navigateTo("/interfacce/LoggedIn.fxml");
    }

    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    private void navigateTo(String fxmlPath) {
        try {
            Stage stage = (Stage) btn_ModifyInformation.getScene().getWindow();
            stage.getScene().setRoot(javafx.fxml.FXMLLoader.load(getClass().getResource(fxmlPath)));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
