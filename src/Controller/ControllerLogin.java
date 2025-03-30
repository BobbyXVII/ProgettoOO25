package Controller;

import DAO.UtenteDAO;
import Model.Utente;
import java.io.IOException;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class ControllerLogin {

    @FXML
    private TextField UsernameLogIn;

    @FXML
    private PasswordField PasswordLogIn;

    @FXML
    private Button PulsanteLogIn;

    @FXML
    private Button RegistrazioneLogIn;

    private UtenteDAO utenteDAO = new UtenteDAO();

    public static String nomeUtenteConnesso;

    public static String roleUserConnected;

    @FXML
    public void initialize() {
        PulsanteLogIn.setOnAction((ActionEvent event) -> {
            try {
                handleLogin(event);
            } catch (IOException e) {
                e.printStackTrace();
            }
        });

        RegistrazioneLogIn.setOnAction((ActionEvent event) -> {
            try {
                handleRegistrazione(event);
            } catch (IOException e) {
                e.printStackTrace();
            }
        });

        roleUserConnected = utenteDAO.ControllaPex(nomeUtenteConnesso);

    }

    private void handleLogin(ActionEvent event) throws IOException {
        String username = UsernameLogIn.getText().trim();
        String password = PasswordLogIn.getText().trim();

        if (username.isEmpty() || password.isEmpty()) {
            showAlert("Errore di Login", "Inserisci sia l'username che la password.");
            return;
        }

        Utente utente = utenteDAO.autenticaUtente(username, password);

        if (utente != null) {
            nomeUtenteConnesso = username;

            FXMLLoader loader = new FXMLLoader(getClass().getResource("../Interfacce/LoggedIn.fxml"));
            Stage stage = (Stage) PulsanteLogIn.getScene().getWindow();
            Scene scene = new Scene(loader.load());
            stage.setScene(scene);
        } else {
            showAlert("Errore di Login", "Username o password non corretti.");
        }
    }

    private void handleRegistrazione(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/Interfacce/SignUp.fxml"));
        Stage stage = (Stage) RegistrazioneLogIn.getScene().getWindow();
        Scene scene = new Scene(loader.load());
        stage.setScene(scene);
    }

    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
