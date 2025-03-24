package Controller;

import DAO.CarrieraDAO;
import DAO.PersonaDAO;
import DAO.SquadraDAO;
import DAO.UtenteDAO;
import Model.Carriera;
import Model.Persona;
import Model.Squadra;
import Model.Utente;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;

import java.sql.SQLException;
import java.util.List;

public class SearchInController {

    @FXML
    private ListView<String> myListView;

    @FXML
    private Label myLabel;

    @FXML
    private Button Add_btn;

    @FXML
    private Button Rm_btn;

    private final SquadraDAO squadraDAO = new SquadraDAO();
    private final CarrieraDAO carrieraDAO = new CarrieraDAO();
    private final PersonaDAO personaDAO = new PersonaDAO();
    private final UtenteDAO utenteDAO = new UtenteDAO();

    private final String QueryDone = LoggedInController.StringaQuery;


    //importazione variabile globale
    private final String UtenteConnesso = ControllerLogin.nomeUtenteConnesso;

    private String selectedItem;

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
            Totalquery.add(squadra.getNomeSquadra());
            Totalquery.add(" ");
            Totalquery.add(" ");
            List<Carriera> carriere = carrieraDAO.getCarrieraByClubName(QueryDone);
            for (Carriera carriera : carriere) {
                String nomeCognome = personaDAO.getNomeCognomeById(carriera.getId());
                Totalquery.add(nomeCognome);
            }
        } else {
            int IdResult = personaDAO.getIdByNome(QueryDone);
            if (IdResult > 0) {
                String nomeCognome = personaDAO.getNomeCognomeById(IdResult);
                Totalquery.add(nomeCognome);
                Totalquery.add(" ");
                Totalquery.add(" ");
            } else {
                showAlert("Errore di ricerca", "La query non ha riportato alcun risultato.");
            }
        }

        myListView.setItems(Totalquery);

        myListView.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null && !newValue.trim().isEmpty()) {
                selectedItem = newValue;
                CalciatoreRichiesto = selectedItem;
                System.out.println(CalciatoreRichiesto);
            }
        });
        Add_btn.setOnAction(event -> handleAdd());
        Rm_btn.setOnAction(event -> handleRemove());
    }

    private void ShowButtons(int i) {
        Add_btn.setOpacity(i);
        Rm_btn.setOpacity(i);
        Add_btn.setDisable(i == 0);
        Rm_btn.setDisable(i == 0);
    }

    private void handleAdd() {
        if (selectedItem == null || selectedItem.trim().isEmpty() || selectedItem.equals(" ")) {
            showAlert("Errore", "Seleziona un elemento dalla lista.");
            return;
        }
        System.out.println("Aggiungi: " + selectedItem);
    }

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
