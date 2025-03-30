package Controller;

import DAO.*;
import Model.Carriera;
import Model.Gioca;
import Model.Persona;
import Model.Possiede;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.Date;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public class AddNewPlayerController2 {

    @FXML
    private ListView<String> myListView_Skills;

    @FXML
    private Button Back_btn;

    @FXML
    private ListView<String> myListView_Ruoli;
    @FXML
    private Button SkillsButton;

    @FXML
    private Button RuoliButton;

    @FXML
    private Button procediButton;
    @FXML
    private ChoiceBox<String> squadraChoiceBox;

    @FXML
    private ChoiceBox<String> tipologiaChoiceBox;
    @FXML
    private TextField valoreMercatoField;

    @FXML
    private DatePicker dataInizioCarriera;

    String selectedSkill;

    String selectedRole;
    String ResultRole;


    String SelectedClub;

    private final int idNewPlayerAdded = AddNewPlayerController.idNewPlayerAdded;

    SkillsDAO skillsDAO = new SkillsDAO();
    PossiedeDAO possiedeDAO = new PossiedeDAO();
    GiocaDAO giocaDAO = new GiocaDAO();
    RuoloDAO ruoloDAO = new RuoloDAO();
    SquadraDAO squadraDAO = new SquadraDAO();

    CarrieraDAO carrieraDAO = new CarrieraDAO();


    @FXML
    public void initialize() {
        System.out.println(idNewPlayerAdded);
        ObservableList<String> squadraScelta = FXCollections.observableArrayList();

        try {
            List<String> squadre = squadraDAO.getAllSquadre();
            squadraScelta.addAll(squadre);
        } catch (SQLException e) {
            e.printStackTrace();
        }

        squadraChoiceBox.setItems(squadraScelta);

        squadraChoiceBox.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {
                SelectedClub = newValue;
            }
        });

        ObservableList<String> careerTypology = FXCollections.observableArrayList("Calciatore", "Allenatore", "Dirigente");
        tipologiaChoiceBox.setItems(careerTypology);

        try {
            List<String> skillsList = skillsDAO.getAllSkills();
            ObservableList<String> observableSkillsList = FXCollections.observableArrayList(skillsList);
            myListView_Skills.setItems(observableSkillsList);
        } catch (SQLException e) {
            e.printStackTrace();
        }

        try {
            List<String> RoleList = ruoloDAO.getAllRoles();
            ObservableList<String> observableRoleList = FXCollections.observableArrayList(RoleList);
            myListView_Ruoli.setItems(observableRoleList);
        } catch (SQLException e) {
            e.printStackTrace();
        }

        RuoliButton.setOnAction(event -> {
            try {
                handleAddRole();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        });

        SkillsButton.setOnAction(event -> {
            try {
                handleAddSkill();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        });
    }

    private void handleAddSkill() throws SQLException {
        selectedSkill = myListView_Skills.getSelectionModel().getSelectedItem();
        if (selectedSkill != null) {
            if (possiedeDAO.skillExists(idNewPlayerAdded, selectedSkill.trim())) {
                showAlert("Questa skill è già stata assegnata.");
                return;
            }

            Possiede possiede = new Possiede(idNewPlayerAdded, selectedSkill);
            try {
                possiedeDAO.insertNewSKills(possiede);
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
            showSuccessAlert();
        } else {
            showAlert("Nessuna Skill Selezionata");
        }
    }


    private void handleAddRole() throws SQLException {
        selectedRole = myListView_Ruoli.getSelectionModel().getSelectedItem();
        if (selectedRole != null) {
            ResultRole = ruoloDAO.getAbbrFromRole(selectedRole.trim());
            if (ResultRole == null) {
                showAlert("Ruolo non trovato.");
                return;
            }

            if (giocaDAO.roleExists(ResultRole, idNewPlayerAdded)) {
                showAlert("Questo ruolo è già stato assegnato.");
                return;
            }

            Gioca gioca = new Gioca(ResultRole, idNewPlayerAdded);
            try {
                giocaDAO.insertNewRole(gioca);
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
            showSuccessAlert();
        } else {
            showAlert("Nessun Ruolo Selezionato");
        }
    }

    @FXML
    private void handleProceed() {
        if (campiValidi()) {
            LocalDate localDate = dataInizioCarriera.getValue();
            Date data = Date.valueOf(localDate);

            Carriera carriera = new Carriera(idNewPlayerAdded,SelectedClub,data,null,0,0,
                    String.valueOf(tipologiaChoiceBox.getValue().trim()),0,0, 0,Integer.parseInt(String.valueOf(valoreMercatoField.getText().trim()))
                    ,null);
            try {
                carrieraDAO.addCarriera(carriera);
                System.out.println("Persona aggiunta con successo!");
                ProceedAdd();
            } catch (SQLException e) {
                System.out.println("Errore durante l'inserimento: " + e.getMessage());
            }
        }else{
            showAlert("Compila tutti i campi prima di procedere.");
        }
    }

    private void ProceedAdd() throws SQLException {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/Interfacce/ANP/AddNewPlayer3.fxml"));
            Stage stage = (Stage) procediButton.getScene().getWindow();
            Scene scene = new Scene(loader.load());
            stage.setScene(scene);
        } catch (IOException e) {
            e.printStackTrace();
            showAlert("Il sistema non è riuscito ad elaborare correttamente la richiesta.");
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
            BackToSearch();
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

    private boolean campiValidi() {
        return idNewPlayerAdded > 0 &&
                !SelectedClub.isEmpty() &&
                dataInizioCarriera.getValue() != null &&
                !valoreMercatoField.getText().isEmpty();
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

}
