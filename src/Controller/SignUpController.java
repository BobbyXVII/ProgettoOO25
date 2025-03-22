package Controller;

import DAO.UtenteDAO;
import Database.DatabaseConnection;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import javafx.scene.Node;
import javafx.event.ActionEvent;
import javafx.scene.input.KeyEvent;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class SignUpController {

    @FXML
    private TextField Username_SignUp;

    @FXML
    private PasswordField Passw_SignUp;

    @FXML
    private PasswordField ConfirmPassw_SignUp;

    @FXML
    private Button Button_SignUp;

    @FXML
    private Button BackToLogIn;

    @FXML
    private void initialize() {
        ConfirmPassw_SignUp.setOnKeyReleased(this::verificaPassword);
        Button_SignUp.setOnAction(this::signUp);
        BackToLogIn.setOnAction(this::caricaLogin);
    }

    private void verificaPassword(KeyEvent event) {
        if (!ConfirmPassw_SignUp.getText().equals(Passw_SignUp.getText())) {
            ConfirmPassw_SignUp.setStyle("-fx-border-color: red;");
        } else {
            ConfirmPassw_SignUp.setStyle("");
        }
    }

    @FXML
    private void signUp(ActionEvent event) {
        String username = Username_SignUp.getText().trim();
        String password = Passw_SignUp.getText().trim();
        String confirmPassword = ConfirmPassw_SignUp.getText().trim();

        if (username.isEmpty()) {
            showAlert("Errore", "Inserisci un username.");
            return;
        }

        if (password.isEmpty() || confirmPassword.isEmpty()) {
            showAlert("Errore", "Inserisci entrambe le password.");
            return;
        }

        if (!password.equals(confirmPassword)) {
            showAlert("Errore", "Le password non corrispondono.");
            return;
        }

        String sql = "INSERT INTO Utenti (username, password, ruolo, id_calciatore) VALUES (?, ?, 'UTENTE', NULL)";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, username);
            stmt.setString(2, password);
            stmt.executeUpdate();

            caricaLogin(event);
        } catch (SQLException e) {
            if (e.getSQLState().equals("23505")) { // Violazione di chiave primaria (username già usato)
                showAlert("Errore", "Username già utilizzato.");
            } else {
                showAlert("Errore", "La password deve essere di almeno 8 caratteri.");
            }
        } catch (ClassNotFoundException e) {
            showAlert("Errore", "Errore di connessione al database.");
        }
    }

    private void caricaLogin(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/Interfacce/Login.fxml"));
            Scene scene = new Scene(loader.load());
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            showAlert("Errore", "Impossibile caricare la schermata di login.");
            e.printStackTrace();
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
