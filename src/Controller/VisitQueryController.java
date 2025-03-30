package Controller;

        import DAO.*;
        import Model.*;
        import javafx.application.Platform;
        import javafx.beans.property.SimpleStringProperty;
        import javafx.collections.FXCollections;
        import javafx.collections.ObservableList;
        import javafx.fxml.FXML;
        import javafx.fxml.FXMLLoader;
        import javafx.scene.Scene;
        import javafx.scene.control.*;
        import javafx.scene.control.cell.PropertyValueFactory;
        import javafx.scene.image.ImageView;
        import javafx.stage.Stage;
        import java.io.IOException;
        import java.sql.Date;
        import java.sql.SQLException;
        import java.time.LocalDate;
        import java.util.List;

public class VisitQueryController {

    private final String QueryRichiesta = SearchInController.selectedItem;
    @FXML
    private TableView<String> roleTable;
    @FXML
    private TableColumn<String, String> roleCol;

    private final String CurrentUser = ControllerLogin.nomeUtenteConnesso;

    private int CurrentUserID;

    private String ResultSelection;

    private final String CurrentUserPEX = ControllerLogin.roleUserConnected;

    @FXML private Label NomeBar,ValoreDiMercL, TextSkill, TextTrophy;
    @FXML private Label NomeL, cognomeL, DateNascitaL, NazionalitaL, PiedeL, CurrTeamL, AltezzaL;
    @FXML private TextField Edit_name, Edit_cognome, Edit_altezza;
    @FXML private DatePicker Edit_Date, DateWinTrophy;
    @FXML private ImageView logoVisitaPlayer, iconImage;
    @FXML private ChoiceBox<String> nationalityChoiceBox, PiedeChoiceBox, ListSkills, ListTrophy;

    @FXML private ComboBox<String> ListOfRoles;
    @FXML private Button btnTornaRicerca, btnTornaHome, btnAnnulla, BackToSearch, BackToLogged, btnConferma, btn_inserisciTrophy, btn_RemoveTrophy, btnModifica, btn_InserisciRuolo;

    @FXML
    private TableView<Vince> trophyTable;

    @FXML
    private TableColumn<Vince, String> trophyName;

    @FXML
    private TableColumn<Vince, String> trophyWinDate;

    private VinceDAO vinceDAO = new VinceDAO();

    @FXML
    private TableView<Possiede> skillsTable;

    @FXML
    private TableColumn<Possiede, String> nomeSkillColumn;

    private int CurrentPlayer;

    private String ValueOfRole;

    private final SquadraDAO squadraDAO = new SquadraDAO();
    private final CarrieraDAO carrieraDAO = new CarrieraDAO();
    private final PersonaDAO personaDAO = new PersonaDAO();
    private final UtenteDAO utenteDAO = new UtenteDAO();

    private final SkillsDAO skillsDAO = new SkillsDAO();

    private final TrofeoIndividualeDAO trofIndDAO = new TrofeoIndividualeDAO();
    private final CompetizioneDAO competizioneDAO = new CompetizioneDAO();

    @FXML private Button btn_RemoveSkill, btn_inserisciSkill;
    private final PossiedeDAO possiedeDAO = new PossiedeDAO();

    private final RuoloDAO ruoloDAO = new RuoloDAO();

    private final GiocaDAO giocaDAO = new GiocaDAO();

    @FXML
    public void initialize() throws SQLException {

        NomeBar.setText(QueryRichiesta);
        updatelist();
                CurrentUserID = utenteDAO.getIdByUsernameIfCalciatore(CurrentUser);
                CurrentPlayer = personaDAO.getIdByNomeQ(QueryRichiesta);
                Persona persona = personaDAO.getPersonaById(CurrentPlayer);

                NomeL.setText(persona.getNome());
                cognomeL.setText(persona.getCognome());
                DateNascitaL.setText(String.valueOf(persona.getData_Nascita()));
                NazionalitaL.setText(persona.getNazionalita());
                PiedeL.setText(persona.getPiede());
                AltezzaL.setText(String.valueOf(persona.getAltezza()));
                CurrTeamL.setText(carrieraDAO.getNomeSquadraByCalciatoreId(CurrentPlayer));
                ValoreDiMercL.setText(String.valueOf(carrieraDAO.getValoreDiMercatoByCalciatoreId(CurrentPlayer)));

                String Pex = utenteDAO.ControllaPex(CurrentUser);
                System.out.println(CurrentUser);
                System.out.println(CurrentUserID);
                System.out.println(CurrentPlayer);
                if ("ADMIN".equals(Pex) || (CurrentUserID == CurrentPlayer)) {
                    TextSkill.setOpacity(1);
                    TextTrophy.setOpacity(1);
                    ListTrophy.setOpacity(1);
                    DateWinTrophy.setOpacity(1);
                    btn_RemoveTrophy.setOpacity(1);
                    btn_inserisciTrophy.setOpacity(1);
                    ListSkills.setOpacity(1);
                    ListTrophy.setOpacity(1);
                    btnModifica.setOpacity(1);
                    ListOfRoles.setOpacity(1);
                }




                trophyName.setCellValueFactory(cellData -> {
                    try {
                        return new SimpleStringProperty(String.valueOf(trofIndDAO.getNomeAssegnazioneById(cellData.getValue().getIdTrofeoIN())));
                    } catch (SQLException e) {
                        throw new RuntimeException(e);
                    }
                });
                trophyWinDate.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getDataVincita().toString()));


                List<Vince> vinceList = vinceDAO.getTrofeiVintiByPlayer(CurrentPlayer);
                ObservableList<Vince> observableList = FXCollections.observableArrayList(vinceList);
                trophyTable.setItems(observableList);


                nomeSkillColumn.setCellValueFactory(new PropertyValueFactory<>("nomeSkill"));
                List<Possiede> skills = possiedeDAO.getSkillsByCalciatoreIdT(CurrentPlayer);
                ObservableList<Possiede> observableSkills = FXCollections.observableArrayList(skills);
                skillsTable.setItems(observableSkills);


                List<String> skillsPossedute = possiedeDAO.getSkillsByCalciatoreId(CurrentPlayer);
                ObservableList<String> skillsList = FXCollections.observableArrayList(skillsDAO.getAllSkills());
                ListSkills.setItems(skillsList);

        ListSkills.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {
                List<String> result = null;
                try {
                    result = possiedeDAO.getSkillsByCalciatoreId(CurrentPlayer);
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }

                // Controlla se la skill è già posseduta
                if (result.contains(newValue)) {
                    btn_inserisciSkill.setOpacity(0);
                    btn_RemoveSkill.setOpacity(1);
                } else {
                    btn_inserisciSkill.setOpacity(1);
                    btn_RemoveSkill.setOpacity(0);
                }
            }
        });

        try {

            List<String> ruoli = ruoloDAO.getRuoliByID(CurrentPlayer);


            roleCol.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue()));


            roleTable.setItems(FXCollections.observableArrayList(ruoli));

        } catch (SQLException e) {
            e.printStackTrace();
        }


        ObservableList<String> TotalTrophy = FXCollections.observableArrayList(trofIndDAO.getAllTrophyNames());
        ListTrophy.setItems(TotalTrophy);


        ListTrophy.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {

            if (newValue != null) {
                ResultSelection = String.valueOf(newValue);
            }
        });


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


        ObservableList<String> tipologiePiedi = FXCollections.observableArrayList("Destro", "Sinistro", "Ambidestro");
        PiedeChoiceBox.setItems(tipologiePiedi);
    }


    @FXML
    private void InsertRole() {
        if(ValueOfRole != null){
            String abbrToAdd;
            try {
                abbrToAdd = ruoloDAO.getAbbrFromRole(String.valueOf(ValueOfRole));
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
            Gioca gioca = new Gioca(abbrToAdd,CurrentPlayer);
            try {
                giocaDAO.insertNewRole(gioca);
                updatelist();
                showSuccessAddR();
            } catch (SQLException e) {
                showAlert("Ruolo già assegnato");
                throw new RuntimeException(e);
            }
            showSuccessAddR();
        }else{
            showAlert("Errore nella selezione del ruolo da assegnare");
        }
        backToVisit();
    }

    public void updatelist(){
        try {
            List<String> ruoli = ruoloDAO.getRolesNotAssignedToID(CurrentUserID);

            ObservableList<String> roleList = FXCollections.observableArrayList(ruoli);

            ListOfRoles.setItems(roleList);

        } catch (SQLException e) {
            e.printStackTrace();
        }

        ListOfRoles.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {
                ValueOfRole = newValue;
                btn_InserisciRuolo.setOpacity(1);
            }
        });
    }



    @FXML
    private void confirmRemoveTrophy() throws SQLException {
        String selectedTrophy = (String) ListTrophy.getSelectionModel().getSelectedItem();
        System.out.println(selectedTrophy);
        String idToRemove = trofIndDAO.getIdTrofeoByNomeAssegnazione(selectedTrophy);
        System.out.println(idToRemove);
        if (selectedTrophy != null) {
            try {
                vinceDAO.deleteWin(CurrentPlayer,idToRemove);
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
            backToVisit();
            showSuccessRemoveT();
        } else {
            showAlert("Nessun Trofeo Selezionato");
        }
    }

    @FXML
    private void ConfirmInsertTrophy() throws SQLException {
        String selectedTrophy = (String) ListTrophy.getSelectionModel().getSelectedItem();
        if (DateWinTrophy.getValue() != null) {
            LocalDate localDate = DateWinTrophy.getValue();
            Date dataWin = Date.valueOf(localDate);
            if (selectedTrophy != null) {
                int ControlloTrofeo = vinceDAO.checkTrophyInd4ID(CurrentPlayer, dataWin, selectedTrophy);
                if (ControlloTrofeo == 1) {
                        showAlert("Trofeo già assegnato");
                    } else {
                        TrofeoIndividuale trofInd = new TrofeoIndividuale(" ", selectedTrophy, dataWin);
                        trofIndDAO.addTrofeoIndividuale(trofInd);
                        String LastTrophyAdded = trofIndDAO.getLastTrofeoIndividualeId();
                        Vince vince = new Vince(LastTrophyAdded, null, CurrentPlayer, dataWin);
                        try {
                            vinceDAO.addVince(vince);
                        } catch (SQLException e) {
                            throw new RuntimeException(e);
                        }
                        showSuccessAlert();
                        backToVisit();
                }
            }else{
                showAlert("Nessun Trofeo Selezionato");
            }
        } else {
                showAlert("Nessuna Data Selezionata");
        }
    }
    @FXML
    private void ConfirmInsert() throws SQLException {
        String selectedSkill = (String) ListSkills.getSelectionModel().getSelectedItem();
        if (selectedSkill != null) {
            Possiede possiede = new Possiede(CurrentPlayer, selectedSkill);
            try {
                possiedeDAO.insertNewSKills(possiede);
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
            backToVisit();
            showSuccessAlert();
        } else {
            showAlert("Nessuna Skill Selezionata");
        }
    }

    @FXML
    private void confirmRemove(){
        String selectedSkill = (String) ListSkills.getSelectionModel().getSelectedItem();
        if (selectedSkill != null) {
            try {
                possiedeDAO.deleteSkill(CurrentPlayer,selectedSkill);
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
            backToVisit();
            showSuccessRemoveS();
        } else {
            showAlert("Nessuna Skill Selezionata");
        }
    }


    @FXML
    public void ActiveEdit(){
        Edit_cognome.setOpacity(1);
        Edit_name.setOpacity(1);
        nationalityChoiceBox.setOpacity(1);
        PiedeChoiceBox.setOpacity(1);
        Edit_Date.setOpacity(1);
        Edit_altezza.setOpacity(1);
        btnAnnulla.setOpacity(1);
        btnConferma.setOpacity(1);

    }

    @FXML
    public void backToVisit(){
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("../Interfacce/VisitQuery.fxml"));
            Stage stage = (Stage) btnAnnulla.getScene().getWindow();
            Scene scene = new Scene(loader.load());
            stage.setScene(scene);
        } catch (IOException e) {
        }
    }



    @FXML
    public void backToSearch(){
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("../Interfacce/SearchIn.fxml"));
            Stage stage = (Stage) BackToSearch.getScene().getWindow();
            Scene scene = new Scene(loader.load());
            stage.setScene(scene);
        } catch (IOException e) {
        }
    }

    @FXML
    public void backToLogged(){
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("../Interfacce/LoggedIn.fxml"));
                Stage stage = (Stage) BackToLogged.getScene().getWindow();
                Scene scene = new Scene(loader.load());
                stage.setScene(scene);
            } catch (IOException e) {
            }
    }

    @FXML
    public void ConfirmEdit() throws SQLException {
        Persona personaEsistente = personaDAO.getPersonaById(CurrentPlayer);

        String nome = Edit_name.getText().isEmpty() ? personaEsistente.getNome() : Edit_name.getText().trim();
        String cognome = Edit_cognome.getText().isEmpty() ? personaEsistente.getCognome() : Edit_cognome.getText().trim();
        LocalDate localDate = Edit_Date.getValue() == null ? personaEsistente.getData_Nascita().toLocalDate() : Edit_Date.getValue();
        Date data = Date.valueOf(localDate);
        String nazionalita = (nationalityChoiceBox.getValue() == null) ? personaEsistente.getNazionalita() : String.valueOf(nationalityChoiceBox.getValue());
        float altezza = Edit_altezza.getText().isEmpty() ? personaEsistente.getAltezza() : Float.parseFloat(Edit_altezza.getText());
        String piede = (PiedeChoiceBox.getValue() == null) ? personaEsistente.getPiede() : String.valueOf(PiedeChoiceBox.getValue());


        Persona personaAggiornata = new Persona(CurrentPlayer, nome, cognome, data, nazionalita, altezza, piede);

        personaDAO.updatePersona(personaAggiornata);
        showSuccessAlert();
        backToVisit();
    }

    private boolean campiValidi() {
        return !Edit_name.getText().isEmpty() &&
                !Edit_cognome.getText().isEmpty() &&
                Edit_Date.getValue() != null &&
                !Edit_altezza.getText().isEmpty() &&
                nationalityChoiceBox.getValue() != null &&
                PiedeChoiceBox.getValue() != null;
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

    private void showSuccessAddR() {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Successo");
        alert.setHeaderText(null);
        alert.setContentText("Ruolo Aggiunto con successo!");

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
