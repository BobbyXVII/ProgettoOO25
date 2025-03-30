package Controller;

import DAO.TrofeoDiSquadraDAO;
import DAO.TrofeoIndividualeDAO;
import DAO.VinceDAO;
import Model.TrofeoIndividuale;
import Model.Vince;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.Date;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public class AddNewPlayerController3 {

    @FXML
    private ListView<String> myListView_Trofei_Ind;

    @FXML
    private ListView<String> myListView_Trofei_Squ;

    @FXML
    private Button Back_btn;

    @FXML
    private Button End_Btn;

    @FXML
    private DatePicker dateTrophyInd;

    @FXML
    private Button Ind_Button;

    String selectedTrophy;

    TrofeoIndividualeDAO trofeoInd = new TrofeoIndividualeDAO();

    VinceDAO vinceDAO = new VinceDAO();

    private final int idNewPlayerAdded = AddNewPlayerController.idNewPlayerAdded;

    @FXML
    public void initialize() {
        try {
            List<String> trophyInd = trofeoInd.getAllIndTrophy();
            ObservableList<String> trophyIndlist = FXCollections.observableArrayList(trophyInd);
            myListView_Trofei_Ind.setItems(trophyIndlist);
        } catch (SQLException e) {
            e.printStackTrace();
        }

        Ind_Button.setOnAction(event -> {
            try {
                handleAddTrophyInd();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        });
    }

    public void handleAddTrophyInd() throws SQLException {
        int resultCheckVince;
        String resultLastTrophy;
        LocalDate localDate = dateTrophyInd.getValue();
        Date data = Date.valueOf(localDate);
        selectedTrophy = myListView_Trofei_Ind.getSelectionModel().getSelectedItem();
        try {
            resultCheckVince = vinceDAO.checkTrophyInd(data, selectedTrophy);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        if (resultCheckVince == 1) {
            showAlert("Il Trofeo è già assegnato ad un giocatore");
        } else {
            TrofeoIndividuale trofeoIndividuale = new TrofeoIndividuale(null, selectedTrophy, data);
            trofeoInd.addTrofeoIndividuale(trofeoIndividuale);

            resultLastTrophy = trofeoInd.getLastTrofeoIndividualeId();
            Vince vince = new Vince(resultLastTrophy, null, idNewPlayerAdded, data);
            vinceDAO.addVince(vince);
            showSuccessAlert();
        }
    }

    private void showAlert(String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("ERRORE");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    private void showSuccessAlert() {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Successo");
        alert.setHeaderText(null);
        alert.setContentText("Elemento aggiunto con successo!");
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
    private void goBack() {
        showSuccessAlert();
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("../Interfacce/LoggedIn.fxml"));
            Stage stage = (Stage) End_Btn.getScene().getWindow();
            Scene scene = new Scene(loader.load());
            stage.setScene(scene);
        } catch (IOException e) {
            showAlert("Il sistema non è riuscito ad elaborare correttamente la richiesta.");
        }
    }

    private void BackToSearch() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("../Interfacce/LoggedIn.fxml"));
            Stage stage = (Stage) Back_btn.getScene().getWindow();
            Scene scene = new Scene(loader.load());
            stage.setScene(scene);
        } catch (IOException e) {
            showAlert("Il sistema non è riuscito ad elaborare correttamente la richiesta.");
        }
    }
}
