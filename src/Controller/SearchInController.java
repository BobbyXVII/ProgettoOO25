package Controller;

import DAO.*;
import Model.Carriera;
import Model.Persona;
import Model.Squadra;
import Model.Utente;
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
    private Label myLabel;

    @FXML
    private Button Rm_btn;

    @FXML
    private Button BackLogged;

    private final SquadraDAO squadraDAO = new SquadraDAO();
    private final CarrieraDAO carrieraDAO = new CarrieraDAO();
    private final PersonaDAO personaDAO = new PersonaDAO();
    private final UtenteDAO utenteDAO = new UtenteDAO();

    private final CompetizioneDAO competizioneDAO = new CompetizioneDAO();

    private final String QueryDone = LoggedInController.StringaQuery;


    //importazione variabile globale
    private final String UtenteConnesso = ControllerLogin.nomeUtenteConnesso;

    public static String selectedItem;

    public static int Flag;

    public String CalciatoreRichiesto;


    public void initialize() throws SQLException {
        String Pex = utenteDAO.ControllaPex(UtenteConnesso);
        ShowButtons(0);
        if ("ADMIN".equals(Pex)) {
            ShowButtons(1);
        }

        ObservableList<String> Totalquery = FXCollections.observableArrayList();
        Squadra squadra = squadraDAO.getSquadraByNome(QueryDone);

        if (squadra != null) {
            Flag = 1;
            Totalquery.add(squadra.getNomeSquadra());
            Totalquery.add(" ");
            Totalquery.add(" ");
            List<Integer> carriereIds = carrieraDAO.getCarrieraByClubName(QueryDone); // Restituisce solo gli ID
            for (Integer id : carriereIds) {
                // Usa l'ID per fare qualcosa, come recuperare informazioni da un altro DAO
                String nomeCognome = personaDAO.getNomeCognomeById(id);
                Totalquery.add(nomeCognome);
            }


        } else {
            List<Integer> idResults = personaDAO.getIdsByNome(QueryDone);  // Restituisce una lista di ID
            if(!idResults.isEmpty()){
                Flag = 2;
                for (Integer id : idResults) {
                    String nomeCognome = personaDAO.getNomeCognomeById(id);  // Ottieni nome e cognome per ogni ID
                    if (nomeCognome != null) {
                        Totalquery.add(nomeCognome);
                    }
                }
        } else {
                int ResultSearchComp = competizioneDAO.checkCompetizioneEsiste(QueryDone);
                if (ResultSearchComp == 1){
                    Flag = 3;
                    List<String> competizioni = competizioneDAO.getCompetizioniAndDate(QueryDone);
                    for (String competizione : competizioni) {
                        System.out.println(competizione);  // Stampa ogni competizione con la sua data
                        Totalquery.add(competizione);
                    }

                }else {
                    showAlert("Errore di ricerca", "La query non ha riportato alcun risultato.");
                }
            }
        }

        myListView.setItems(Totalquery);

        myListView.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null && !newValue.trim().isEmpty()) {
                selectedItem = newValue;
            }
        });
        Rm_btn.setOnAction(event -> handleRemove());
    }

    private void ShowButtons(int i) {
        Rm_btn.setOpacity(i);
        Rm_btn.setDisable(i == 0);
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
    public void GoVisit(){
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/Interfacce/VisitQuery.fxml"));
            Stage stage = (Stage) BackLogged.getScene().getWindow();
            Scene scene = new Scene(loader.load());
            stage.setScene(scene);
        } catch (IOException e) {
            e.printStackTrace();
            showAlert("ERRORE NEL SISTEMA", "Il sistema non è riuscito ad elaborare correttamente la richiesta.");
        }
    }

            /*
    private void handleAdd() {
        if (selectedItem == null || selectedItem.trim().isEmpty() || selectedItem.equals(" ")) {
            showAlert("Errore", "Seleziona un elemento dalla lista.");
            return;
        }
        System.out.println("Aggiungi: " + selectedItem);
        cambiaScena();
    }
     */

    private void handleRemove() {
        if (selectedItem == null || selectedItem.trim().isEmpty() || selectedItem.equals(" ")) {
            showAlert("Errore", "Seleziona un elemento dalla lista.");
            return;
        }
        System.out.println("Rimuovi: " + selectedItem);
    }

    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }


}
