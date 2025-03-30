package Controller;

import DAO.CompetizioneDAO;
import DAO.TrofeoDiSquadraDAO;
import DAO.UtenteDAO;
import Model.Competizione;
import Model.TrofeoDiSquadra;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

public class VisitCompetitionQueryController {
    @FXML private ChoiceBox<String> Edit_TipComp, nationalityChoiceBox, Ed_Comp;

    @FXML private Button btnModifica, btnAnnulla, btnConferma;

    @FXML private Label NomeBar, nomeCompL, tipCompL, NazionalitaL;

    @FXML
    private TableView<TrofeoDiSquadra> SquadreVincitrici;
    @FXML
    private TableColumn<TrofeoDiSquadra, String> AnnoSvolgimento;
    @FXML
    private TableColumn<TrofeoDiSquadra, String> nomeSquadra;

    private final UtenteDAO utenteDAO = new UtenteDAO();

    private final CompetizioneDAO competizioneDAO = new CompetizioneDAO();

    TrofeoDiSquadraDAO trofeoDAO = new TrofeoDiSquadraDAO();

    String currentComp = SearchInController.selectedItem;
    String TipSelected;

    public void initialize() throws SQLException, ClassNotFoundException {
        NomeBar.setText(currentComp);
        nomeCompL.setText(currentComp);

        try {
            List<String> anni = competizioneDAO.getAnniSvolgimento(currentComp);

            Ed_Comp.getItems().clear();
            Ed_Comp.getItems().addAll(anni);
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }

        try {
            List<String> info = competizioneDAO.getInfoTeam(currentComp);
            if (!info.isEmpty()) {
                tipCompL.setText(info.get(0));
                NazionalitaL.setText(info.get(1));
            } else {
                tipCompL.setText("N/D");
                NazionalitaL.setText("N/D");
            }
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
            tipCompL.setText("Errore");
            NazionalitaL.setText("Errore");
        }

        try {
            List<TrofeoDiSquadra> squadre = trofeoDAO.getTrofeiByCom(currentComp);

            nomeSquadra.setCellValueFactory(new PropertyValueFactory<>("nomeSquadra"));
            AnnoSvolgimento.setCellValueFactory(new PropertyValueFactory<>("annoSvolgimento"));

            SquadreVincitrici.setItems(FXCollections.observableArrayList(squadre));
        } catch (SQLException e) {
            e.printStackTrace();
        }

        ObservableList<String> nazionalita = FXCollections.observableArrayList(
                "Italia", "Internazionale","Germania", "Francia", "Spagna", "Inghilterra", "Portogallo", "Brasile", "Argentina", "Stati Uniti",
                "Belgio", "Olanda", "Polonia", "Grecia", "Svezia", "Russia", "Giappone", "Messico", "Uruguay", "Colombia",
                "Cile", "Croazia", "Danimarca", "Serbia", "Svizzera", "Australia", "Ecuador", "Tunisia", "Algeria", "Canada",
                "Corea del Sud", "Perù", "Egitto", "Nigeria", "Camerun", "Sud Africa", "Turchia", "Israele", "Romania",
                "Bulgaria", "Finlandia", "Norvegia", "Austria", "Slovacchia", "Slovenia", "Ceco", "Costa Rica", "Giamaica",
                "Honduras", "Panama", "Kenia", "Zambia", "Mozambico", "Ghana", "Marocco", "Galles", "Scozia", "Paraguay",
                "Bolivia", "Venezuela", "Perù", "Ecuador", "Libia", "Seychelles", "Malta", "Figi", "Samoa", "Tonga",
                "Isole Faroe", "Isole Cook", "Saint Kitts e Nevis", "Saint Lucia", "Barbados", "Trinidad e Tobago",
                "Grenada", "Antigua e Barbuda", "Saint Vincent e Grenadine", "Bahamas", "Bermuda", "Isole Vergini",
                "Armenia", "Georgia", "Azerbaigian", "Kazakhstan", "Uzbekistan", "Turkmenistan", "Kyrgyzstan", "Tajikistan",
                "Mongolia", "Nepal", "Bhutan", "Maldivas", "Sri Lanka", "Myanmar", "Cambogia", "Laos", "Filippine",
                "Indonesia", "Singapore", "Malaysia", "Brunei");
        nationalityChoiceBox.setItems(nazionalita);

        String currentUserPEX = utenteDAO.ControllaPex(ControllerLogin.nomeUtenteConnesso);
        if ("ADMIN".equals(currentUserPEX)) {
            btnModifica.setOpacity(1);
        }

        try {
            List<String> TotalTip = competizioneDAO.getTipologieCompetizioni();
            if (TotalTip != null) {
                ObservableList<String> TipList = FXCollections.observableArrayList(TotalTip);
                Edit_TipComp.setItems(TipList);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        Edit_TipComp.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {
                TipSelected = String.valueOf(newValue);
            }
        });
    }

    @FXML
    private void ActiveEdit() {
        btnAnnulla.setOpacity(1);
        btnConferma.setOpacity(1);
        Edit_TipComp.setOpacity(1);
        nationalityChoiceBox.setOpacity(1);
    }

    @FXML
    public void backToVisit(){
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("../Interfacce/VisitCompetitionQuery.fxml"));
            Stage stage = (Stage) btnAnnulla.getScene().getWindow();
            Scene scene = new Scene(loader.load());
            stage.setScene(scene);
        } catch (IOException ignored) {
        }
    }

    @FXML
    private void BackToSearch(){
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("../Interfacce/SearchIn.fxml"));
            Stage stage = (Stage) btnAnnulla.getScene().getWindow();
            Scene scene = new Scene(loader.load());
            stage.setScene(scene);
        } catch (IOException ignored) {
        }
    }

    @FXML
    private void BackToLogged(){
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("../Interfacce/LoggedIn.fxml"));
            Stage stage = (Stage) btnAnnulla.getScene().getWindow();
            Scene scene = new Scene(loader.load());
            stage.setScene(scene);
        } catch (IOException ignored) {
        }
    }

    @FXML
    private void ConfirmUpdate() throws SQLException {
        if(TipSelected != null){
            if (nationalityChoiceBox.getValue() == null){
                Competizione competizione = new Competizione(currentComp,null,TipSelected.trim(),null);
                competizioneDAO.updateCompetizioneTip(competizione);
                showSuccessAlert();
            }else{
                Competizione competizione = new Competizione(currentComp,null,TipSelected.trim(),String.valueOf(nationalityChoiceBox.getValue().trim()));
                competizioneDAO.updateCompetizioneTip_Naz(competizione);
                showSuccessAlert();
            }
        }else{
            if(nationalityChoiceBox.getValue() != null){
                Competizione competizione = new Competizione(currentComp,null,null,String.valueOf(nationalityChoiceBox.getValue().trim()));
                competizioneDAO.updateCompetizioneNaz(competizione);
                showSuccessAlert();
            }else{
                showError("Errore di Aggiornamento","L'update ha riscontrato dei problemi");
            }
        }
        backToVisit();
    }

    private void showError(String titolo, String messaggio) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(titolo);
        alert.setHeaderText(null);
        alert.setContentText(messaggio);
        alert.showAndWait();
    }

    private void showSuccessAlert() {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Successo");
        alert.setHeaderText(null);
        alert.setContentText("Dati aggiornati con successo!");
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
