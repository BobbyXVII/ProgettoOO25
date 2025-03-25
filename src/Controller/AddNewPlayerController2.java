package Controller;

import DAO.PossiedeDAO;
import DAO.SkillsDAO;
import Model.Possiede;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import java.sql.SQLException;
import java.util.List;

public class AddNewPlayerController2 {

    @FXML
    private ListView<String> myListView_Skills;
    @FXML
    private Button SkillsButton;
    @FXML
    private ChoiceBox<String> squadraChoiceBox;
    @FXML
    private ChoiceBox<String> tipologiaChoiceBox;
    @FXML
    private TextField valoreMercatoField;

    private String selectedSkill;
    private int ID=1;

    public AddNewPlayerController2() {
        // Questo è il costruttore per eventuale inizializzazione
    }

    // Metodo per caricare tutte le skill nella ListView

    SkillsDAO skillsDAO = new SkillsDAO();
    PossiedeDAO possiedeDAO = new PossiedeDAO();
    @FXML
    public void initialize() {
        try {
            List<String> skillsList = skillsDAO.getAllSkills();
            ObservableList<String> observableSkillsList = FXCollections.observableArrayList(skillsList);
            myListView_Skills.setItems(observableSkillsList);
        } catch (SQLException e) {
            e.printStackTrace();
        }

        // Gestione dell'evento per il bottone 'aggiungi'
        SkillsButton.setOnAction(event -> {
            try {
                handleAddSkill();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        });
    }

    // Metodo per gestire l'aggiunta della skill selezionata
    private void handleAddSkill() throws SQLException {
        selectedSkill = myListView_Skills.getSelectionModel().getSelectedItem();
        if (selectedSkill != null) {
            Possiede possiede = new Possiede(ID,selectedSkill.trim());
            try {
                possiedeDAO.insertNewSKills(possiede);
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
            showSuccessAlert();
        } else {
            showAlert("ERRORE NEL SISTEMA", "Nessuna skill selezionata");
        }
    }

    private void showAlert(String title, String message) {
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
        alert.setContentText("Skill aggiunta con successo!");

        // Mostra l'alert
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