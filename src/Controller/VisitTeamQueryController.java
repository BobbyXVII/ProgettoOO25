package Controller;

import DAO.*;
import Model.*;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ButtonType;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.VBox;
import javafx.scene.Scene;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Optional;
import javafx.geometry.Insets;


import java.awt.*;
import java.sql.Date;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;

public class VisitTeamQueryController {

    @FXML private Label NomeBar;
    @FXML private Label nomeClubL;
    @FXML private Label stadioL;
    @FXML private Label annoFondazioneL;
    @FXML private Label NazionalitaL;

    @FXML private Button btnModifica, btnAnnulla, btnConferma, BackToSearch, BackToLogged,
            btn_inserisciComp;

    @FXML private TableView<TrofeoDiSquadra> TrofeiVinti;
    @FXML private TableColumn<TrofeoDiSquadra, String> nomeTrofeo;
    @FXML private TableColumn<TrofeoDiSquadra, String> dataVincita;

    @FXML private ChoiceBox nationalityChoiceBox,ListComp;

    @FXML private TextField Edit_nomeClub, Edit_stadio, Edit_annoFondazione;
    @FXML private DatePicker DateComp;
    @FXML private Spinner<Integer> posFinale;
    @FXML private Label TextComp;

    @FXML private TableView CompGiocate;
    @FXML private TableColumn<Partecipa, String> nomeComp;
    @FXML private TableColumn<Partecipa, Integer> PosizioneFinale;
    @FXML private TableColumn<Partecipa, String> AnnoSvolgimento;


    private final String CurrentTeam = SearchInController.selectedItem;

    private String CurrentUserPEX;

    private int CapacityNewStadio;

    private String ResultComp;

    private String stadioNew;

    private int Flag = 0;

    private SquadraDAO squadraDAO = new SquadraDAO();

    private UtenteDAO utenteDAO = new UtenteDAO();

    private StadioDAO stadioDAO = new StadioDAO();

    private PartecipaDAO partecipaDAO = new PartecipaDAO();

    private CompetizioneDAO competizioneDAO = new CompetizioneDAO();

    private final TrofeoDiSquadraDAO trofeoDiSquadraDAO = new TrofeoDiSquadraDAO();

    public void initialize() throws SQLException {
        posFinale = new Spinner<>(0, 100, 0);
        CurrentUserPEX = utenteDAO.ControllaPex(ControllerLogin.nomeUtenteConnesso);
        System.out.println(CurrentUserPEX);
        Squadra squadra = squadraDAO.getSquadraByNome(CurrentTeam);

        NomeBar.setText(CurrentTeam);
        nomeClubL.setText(squadra.getNomeSquadra());
        stadioL.setText(squadra.getNomeStadio());
        annoFondazioneL.setText(String.valueOf(squadra.getAnnoFondazione()));
        NazionalitaL.setText(squadra.getNazionalita());

        System.out.println(CurrentTeam);
        nomeComp.setCellValueFactory(new PropertyValueFactory<>("nomeCompetizione"));
        AnnoSvolgimento.setCellValueFactory(new PropertyValueFactory<>("annoSvolgimento"));
        PosizioneFinale.setCellValueFactory(new PropertyValueFactory<>("posizioneFinale"));

        try {
            loadCompetizioni();
        } catch (SQLException e) {
            e.printStackTrace();
        }



        nomeTrofeo.setCellValueFactory(new PropertyValueFactory<>("nomeCompetizione"));
        dataVincita.setCellValueFactory(new PropertyValueFactory<>("annoSvolgimento"));

        try {
            loadTrofeiVinti();
        } catch (SQLException e) {
            e.printStackTrace();
        }


        ObservableList<String> nazionalita = FXCollections.observableArrayList(
                "Italia", "Germania", "Francia", "Spagna", "Inghilterra", "Portogallo", "Brasile", "Argentina", "Stati Uniti",
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
                "Indonesia", "Singapore", "Malaysia", "Brunei"
        );
        nationalityChoiceBox.setItems(nazionalita);


        List<String> TotalComp = competizioneDAO.getCompetizioniAndDate();
        ObservableList<String> competizioniList = FXCollections.observableArrayList(TotalComp);
        ListComp.setItems(competizioniList);

        // Gestione della selezione della skill
        ListComp.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            ResultComp = String.valueOf(newValue);
        });

        if ("ADMIN".equals(CurrentUserPEX)) {
            btnModifica.setOpacity(1);
            ListComp.setOpacity(1);
            posFinale.setOpacity(1);
            btn_inserisciComp.setOpacity(1);
            if(ResultComp != null){
                btn_inserisciComp.setOpacity(1);
            }
        }

    }

    @FXML
    private void ActiveEdit() {
        btnAnnulla.setOpacity(1);
        btnConferma.setOpacity(1);
        Edit_nomeClub.setOpacity(1);
        Edit_stadio.setOpacity(1);
        Edit_annoFondazione.setOpacity(1);
        nationalityChoiceBox.setOpacity(1);

    }

    @FXML
    public void backToVisit(){
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("../Interfacce/VisitTeamQuery.fxml"));
            Stage stage = (Stage) btnAnnulla.getScene().getWindow();
            Scene scene = new Scene(loader.load());
            stage.setScene(scene);
        } catch (IOException e) {
        }
    }

    @FXML
    private void ConfirmInsertComp() throws SQLException, ClassNotFoundException {
            // Dividi la stringa in due parti usando il " - "
        String[] parti = ResultComp.split(" - ");
        String competizione = parti[0].trim();
        String anno = parti[1].trim();
        if((posFinale.getValue().equals(null))){
            showError("Errore di selezione","Nessun valore di posizione finale è stato selezionato. Selezionare 0 se non si vuole inserire posizione");
        }else{
            if((Integer) posFinale.getValue() < 1){
                Partecipa partecipa  = new Partecipa(competizione,anno,CurrentTeam);
                partecipaDAO.insertPartecipa(partecipa);
            }else{
                Partecipa partecipa  = new Partecipa(competizione,anno,CurrentTeam,((Integer)posFinale.getValue()));
                partecipaDAO.insertPartecipa(partecipa);
            }
        }
    }

    private void UpdateMethod(String nomeClub,int annoFondazione,String nazionalita,String stadioNew) throws SQLException {
        Squadra UpdateSquadra = new Squadra(nomeClub, annoFondazione, nazionalita, stadioNew);

        squadraDAO.updateSquadra(UpdateSquadra);
        showSuccessAlert();
        backToVisit();
    }


    @FXML
    private void backToSearch() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("../Interfacce/SearchIn.fxml"));
            Stage stage = (Stage) BackToSearch.getScene().getWindow();
            Scene scene = new Scene(loader.load());
            stage.setScene(scene);
        } catch (IOException e) {
        }
    }

    @FXML
    private void backToLogged() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("../Interfacce/LoggedIn.fxml"));
            Stage stage = (Stage) BackToLogged.getScene().getWindow();
            Scene scene = new Scene(loader.load());
            stage.setScene(scene);
        } catch (IOException e) {
        }
    }

    @FXML
    private void confirmRemove() {
        // Implementa la logica per rimuovere una skill
    }

    @FXML
    private void ConfirmEdit() throws SQLException, ClassNotFoundException {

        Squadra squadraEsistente = squadraDAO.getSquadraByNome(CurrentTeam);

        // Se la persona esiste, aggiorna solo i campi modificati
        String nomeClub = Edit_nomeClub.getText().isEmpty() ? squadraEsistente.getNomeSquadra() : Edit_nomeClub.getText().trim();
        stadioNew = Edit_stadio.getText().isEmpty() ? squadraEsistente.getNomeStadio() : Edit_stadio.getText().trim();
        String nazionalita = (nationalityChoiceBox.getValue() == null) ? squadraEsistente.getNazionalita() : String.valueOf(nationalityChoiceBox.getValue());
        int annoFondazione = Edit_annoFondazione.getText().isEmpty() ? squadraEsistente.getAnnoFondazione() : Integer.parseInt(Edit_annoFondazione.getText().trim());


        List<String> stadi = stadioDAO.SelectAll();
        for (String s : stadi) {
            if (s.equalsIgnoreCase(stadioNew)) {
                Flag = 1;
            }
        }

        if (Flag == 1) {
            UpdateMethod(nomeClub,annoFondazione,nazionalita,stadioNew);
            backToVisit();
        }else{
            aggiungiStadio(nomeClub, annoFondazione, nazionalita);
        }
    }

    @FXML
    private void confirmRemoveTrophy() {
        // Implementa la logica per rimuovere un trofeo
    }

    @FXML
    private void ConfirmInsertTrophy() {
        // Implementa la logica per inserire un trofeo
    }
    private void showAlert(String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("ERRORE");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    private void loadTrofeiVinti() throws SQLException {
        List<TrofeoDiSquadra> trofei = trofeoDiSquadraDAO.getTrofeiBySquadra(CurrentTeam);
        ObservableList<TrofeoDiSquadra> data = FXCollections.observableArrayList(trofei);
        TrofeiVinti.setItems(data);
    }

    private void loadCompetizioni() throws SQLException {
        List<Partecipa> competizioni = partecipaDAO.getCompetizioniBySquadra(CurrentTeam);
        ObservableList<Partecipa> data = FXCollections.observableArrayList(competizioni);
        CompGiocate.setItems(data);
    }

    private void showSuccessAlert() {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Successo");
        alert.setHeaderText(null);
        alert.setContentText("Dati aggiornati con successo!");

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

    private void showSuccessRemoveS() {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Successo");
        alert.setHeaderText(null);
        alert.setContentText("Skill Rimossa con successo!");

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

    public void aggiungiStadio(String nomeClub,int annoFondazione,String nazionalita) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Aggiunta Stadio");
        alert.setHeaderText("Vuoi aggiungere un nuovo stadio?");
        alert.setContentText("Scegli un'opzione:");

        ButtonType yesButton = new ButtonType("Sì");
        ButtonType noButton = new ButtonType("No", ButtonBar.ButtonData.CANCEL_CLOSE);

        alert.getButtonTypes().setAll(yesButton, noButton);
        Optional<ButtonType> result = alert.showAndWait();

        if (result.isPresent() && result.get() == yesButton) {
            // Creazione della nuova finestra per inserire la dimensione dello stadio
            Stage stage = new Stage();
            stage.setTitle("Inserisci Dimensione Stadio");

            VBox vbox = new VBox(10);
            vbox.setPadding(new Insets(10));

            Label label = new Label("Inserisci la dimensione dello stadio:");
            TextField dimensioneField = new TextField();
            Button salvaButton = new Button("Salva");

            salvaButton.setOnAction(e -> {
                String dimensione = dimensioneField.getText();
                if (!dimensione.isEmpty()) {
                    try {
                        salvaDimensioneStadio(nomeClub, annoFondazione, nazionalita, dimensione);

                    } catch (SQLException ex) {
                        throw new RuntimeException(ex);
                    } catch (ClassNotFoundException ex) {
                        throw new RuntimeException(ex);
                    }
                    stage.close();
                } else {
                    showError("Errore", "Il campo non può essere vuoto.");
                }
            });

            vbox.getChildren().addAll(label, dimensioneField, salvaButton);

            Scene scene = new Scene(vbox, 300, 150);
            stage.setScene(scene);
            stage.show();
        }
    }

    private void salvaDimensioneStadio(String nomeClub,int annoFondazione,String nazionalita, String dimensione) throws SQLException, ClassNotFoundException {
        CapacityNewStadio = Integer.parseInt(dimensione);
        System.out.println("Dimensione stadio salvata: " + dimensione);
        Stadio stadio = new Stadio(String.valueOf(stadioNew),CapacityNewStadio);
        stadioDAO.create(stadio);
        UpdateMethod(nomeClub, annoFondazione, nazionalita, stadioNew);
    }

    private void showError(String titolo, String messaggio) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(titolo);
        alert.setHeaderText(null);
        alert.setContentText(messaggio);
        alert.showAndWait();
    }


    private void showSuccessRemoveT() {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Successo");
        alert.setHeaderText(null);
        alert.setContentText("Trofeo rimosso con successo!");

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
