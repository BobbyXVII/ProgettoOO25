package Controller;

        import DAO.*;
        import Model.Persona;
        import Model.Squadra;
        import javafx.application.Platform;
        import javafx.collections.FXCollections;
        import javafx.collections.ObservableList;
        import javafx.fxml.FXML;
        import javafx.fxml.FXMLLoader;
        import javafx.scene.Scene;
        import javafx.scene.control.*;
        import javafx.scene.image.ImageView;
        import javafx.scene.layout.AnchorPane;
        import javafx.scene.layout.Pane;
        import javafx.stage.Stage;

        import java.io.IOException;
        import java.math.BigDecimal;
        import java.sql.Date;
        import java.sql.SQLException;
        import java.time.LocalDate;
        import java.util.List;
        import java.text.DecimalFormat;
        import java.text.DecimalFormatSymbols;
        import java.util.Locale;

public class VisitQueryController {

    private final String QueryRichiesta = SearchInController.selectedItem;

    @FXML private AnchorPane VisitCalciatori;
    @FXML private Pane Pannello, Pannello1, Valore, Panel, Bar, Pannello3;
    @FXML private Label NomeBar, CognomeBar, ValoreDiMercL, ValoreDiMercL1;
    @FXML private Label NomeL, cognomeL, DateNascitaL, NazionalitaL, PiedeL, CurrTeamL, AltezzaL;
    @FXML private TextField Edit_name, Edit_cognome, Edit_Nazio, Edit_piede, Edit_altezza;
    @FXML private DatePicker Edit_Date;
    @FXML private ImageView logoVisitaPlayer, iconImage;
    @FXML private ChoiceBox nationalityChoiceBox, PiedeChoiceBox;
    @FXML private Button btnTornaRicerca, btnTornaHome, btnAnnulla, BackToSearch, BackToLogged, btnConferma;

    private int resultID;

    private final SquadraDAO squadraDAO = new SquadraDAO();
    private final CarrieraDAO carrieraDAO = new CarrieraDAO();
    private final PersonaDAO personaDAO = new PersonaDAO();
    private final UtenteDAO utenteDAO = new UtenteDAO();
    private final CompetizioneDAO competizioneDAO = new CompetizioneDAO();
    @FXML
    public void initialize() throws SQLException {
        NomeBar.setText(QueryRichiesta);

        Squadra squadra = squadraDAO.getSquadraByNome(QueryRichiesta);
        if (squadra != null) {
            //
        } else {
            List<Integer> idResults = personaDAO.getIdsByNome(QueryRichiesta);  // Restituisce una lista di ID
            if (!idResults.isEmpty()) {
                VisitCalciatori.setOpacity(1);
                resultID = personaDAO.getIdByNomeQ(QueryRichiesta);
                Persona persona = personaDAO.getPersonaById(resultID);
                NomeL.setText(persona.getNome());
                cognomeL.setText(persona.getCognome());
                DateNascitaL.setText(String.valueOf(persona.getData_Nascita()));
                NazionalitaL.setText(persona.getNazionalita());
                PiedeL.setText(persona.getPiede());
                AltezzaL.setText(String.valueOf(persona.getAltezza()));
                CurrTeamL.setText(carrieraDAO.getNomeSquadraByCalciatoreId(resultID));
                BigDecimal valore = carrieraDAO.getValoreDiMercatoByCalciatoreId(resultID);
                if (valore != null) {
                    DecimalFormatSymbols symbols = new DecimalFormatSymbols(Locale.ITALY);
                    symbols.setGroupingSeparator('.'); // Imposta il punto come separatore delle migliaia
                    DecimalFormat df = new DecimalFormat("#,###", symbols);

                    ValoreDiMercL.setText(df.format(valore));
                } else {
                    ValoreDiMercL.setText("N/A");
                }



            } else {
                int ResultSearchComp = competizioneDAO.checkCompetizioneEsiste(QueryRichiesta);
                if (ResultSearchComp == 1) {
                    //
                } else {
                    showAlert("La query non ha riportato alcun risultato.");
                }
            }
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

        ObservableList<String> tipologiePiedi = FXCollections.observableArrayList("Destro", "Sinistro", "Ambidestro");
        PiedeChoiceBox.setItems(tipologiePiedi);
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
        // Recupera la persona attuale dal database
        Persona personaEsistente = personaDAO.getPersonaById(resultID);

        // Se la persona esiste, aggiorna solo i campi modificati
        String nome = Edit_name.getText().isEmpty() ? personaEsistente.getNome() : Edit_name.getText().trim();
        String cognome = Edit_cognome.getText().isEmpty() ? personaEsistente.getCognome() : Edit_cognome.getText().trim();
        LocalDate localDate = Edit_Date.getValue() == null ? personaEsistente.getData_Nascita().toLocalDate() : Edit_Date.getValue();
        Date data = Date.valueOf(localDate);
        String nazionalita = (nationalityChoiceBox.getValue() == null) ? personaEsistente.getNazionalita() : String.valueOf(nationalityChoiceBox.getValue());
        float altezza = Edit_altezza.getText().isEmpty() ? personaEsistente.getAltezza() : Float.parseFloat(Edit_altezza.getText());
        String piede = (PiedeChoiceBox.getValue() == null) ? personaEsistente.getPiede() : String.valueOf(PiedeChoiceBox.getValue());


        // Crea il nuovo oggetto aggiornato
        Persona personaAggiornata = new Persona(resultID, nome, cognome, data, nazionalita, altezza, piede);

        // Esegui l'update
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

}
