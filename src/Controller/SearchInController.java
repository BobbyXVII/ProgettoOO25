package Controller;

import DAO.*;
import Model.Carriera;
import Model.Persona;
import Model.Squadra;
import Model.Utente;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

public class SearchInController {

    @FXML
    private ListView<String> myListView;

    @FXML
    private Button Rm_btn;

    @FXML
    private Button BackLogged;

    private int isComp = 0;

    private final SquadraDAO squadraDAO = new SquadraDAO();
    private final CarrieraDAO carrieraDAO = new CarrieraDAO();
    private final PersonaDAO personaDAO = new PersonaDAO();
    private final UtenteDAO utenteDAO = new UtenteDAO();

    private final CompetizioneDAO competizioneDAO = new CompetizioneDAO();

    private final String QueryDone = LoggedInController.StringaQuery;

    private final String UtenteConnesso = ControllerLogin.nomeUtenteConnesso;

    public static String selectedItem;

    public static int Flag;

    public void initialize() throws SQLException {

        ObservableList<String> Totalquery = FXCollections.observableArrayList();
        Squadra squadra = squadraDAO.getSquadraByNome(QueryDone);

        int resultSearchComp = 0;
        if (squadra != null) {
            Flag = 1;
            Totalquery.add(squadra.getNomeSquadra());
            Totalquery.add(" ");
            Totalquery.add(" ");
            List<Integer> carriereIds = carrieraDAO.getCarrieraByClubName(QueryDone);
            for (Integer id : carriereIds) {
                String nomeCognome = personaDAO.getNomeCognomeById(id);
                Totalquery.add(nomeCognome);
            }
        } else {
            List<Integer> idResults = personaDAO.getIdsByNome(QueryDone);
            if(!idResults.isEmpty()){
                Flag = 2;
                for (Integer id : idResults) {
                    String nomeCognome = personaDAO.getNomeCognomeById(id);
                    if (nomeCognome != null) {
                        Totalquery.add(nomeCognome);
                    }
                }
            } else {
                resultSearchComp = competizioneDAO.checkCompetizioneEsiste(QueryDone);
                if (resultSearchComp == 1){
                    Flag = 3;
                    List<String> competizioni = competizioneDAO.getCompetizioniAndDate(QueryDone);
                    for (String competizione : competizioni) {
                        System.out.println(competizione);
                        Totalquery.add(competizione);
                    }
                }else {
                    showAlert("Errore di ricerca", "La query non ha riportato alcun risultato.");
                    handleHomepage();
                }
            }
        }

        myListView.setItems(Totalquery);

        myListView.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null && !newValue.trim().isEmpty()) {
                selectedItem = newValue;
            }
            if (Flag == 3){
                String[] parti = selectedItem.split(" - ");
                String competizione = parti[0].trim();
                String anno = parti[1].trim();
                selectedItem = competizione;
            }
        });
    }

    public void handleHomepage() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/Interfacce/LoggedIn.fxml"));
            Stage stage = (Stage) BackLogged.getScene().getWindow();
            Scene scene = new Scene(loader.load());
            stage.setScene(scene);
        } catch (IOException e) {
            showAlert("ERRORE NEL SISTEMA", "Il sistema non è riuscito ad elaborare correttamente la richiesta.");
        }
    }

    @FXML
    public void GoVisit() throws SQLException {
        String Interface;
        Squadra squadra = squadraDAO.getSquadraByNome(selectedItem);
        if (squadra != null) {
            Interface = "VisitTeam";
        }else{
            List<Integer> idResults = personaDAO.getIdsByNome(selectedItem);
            if (!idResults.isEmpty()) {
                Interface = "Visit";
            }else{
                Interface = "VisitCompetition";
            }
        }
        CorrectView(Interface);
    }

    public void CorrectView(String Interface){
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/Interfacce/" + Interface + "Query.fxml"));
            Stage stage = (Stage) BackLogged.getScene().getWindow();
            Scene scene = new Scene(loader.load());
            stage.setScene(scene);
        } catch (IOException e) {
            e.printStackTrace();
            showAlert("ERRORE NEL SISTEMA", "Il sistema non è riuscito ad elaborare correttamente la richiesta.");
        }
    }

    private void showSuccessAlert() {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Successo");
        alert.setHeaderText(null);
        alert.setContentText("Elemento rimosso con successo!");

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

    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}