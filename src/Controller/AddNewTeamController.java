package Controller;

import DAO.SquadraDAO;
import DAO.StadioDAO;
import Model.Squadra;
import Model.Stadio;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

public class AddNewTeamController {

    @FXML private TextField Add_nomeTeam, Add_annoFondazione, Add_CittaApp, Add_Stadio;
    @FXML private ComboBox nationalityComboBox;
    @FXML private Button Proceed_btn, Back_btn;

    String stadioNew;

    StadioDAO stadioDAO = new StadioDAO();

    SquadraDAO squadraDAO = new SquadraDAO();

    public void initialize() throws SQLException {
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
        nationalityComboBox.setItems(nazionalita);
    }


    @FXML
    private void handleProceed() throws SQLException, ClassNotFoundException {
        if (Add_nomeTeam.getText() == null || Add_annoFondazione.getText() == null || Add_CittaApp.getText() == null || Add_Stadio.getText() == null || nationalityComboBox.getValue() == null) {
            showError("Errore Aggiunta Squadra", "Compila tutti i campi prima di procedere.");
        } else {
            List<String> stadi = stadioDAO.SelectAll();
            stadioNew = String.valueOf(Add_Stadio.getText().trim());
            int anno = Integer.parseInt(Add_annoFondazione.getText().trim());
            if (stadi.contains(stadioNew)) {
                insertMethod(String.valueOf(Add_nomeTeam.getText().trim()),anno,String.valueOf(Add_CittaApp.getText().trim()),String.valueOf(nationalityComboBox.getValue()),stadioNew);
            }else{
                aggiungiStadio(String.valueOf(Add_nomeTeam.getText().trim()),anno,String.valueOf(Add_CittaApp.getText().trim()),String.valueOf(nationalityComboBox.getValue()));
            }

        }
    }

    public void aggiungiStadio(String nomeClub,int annoFondazione,String citta,String nazionalita) {
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
                        salvaDimensioneStadio(nomeClub, annoFondazione, citta,nazionalita, dimensione);

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

    private void salvaDimensioneStadio(String nomeClub,int annoFondazione,String citta, String nazionalita, String dimensione) throws SQLException, ClassNotFoundException {
        int capacityNewStadio = Integer.parseInt(dimensione);
        System.out.println("Dimensione stadio salvata: " + dimensione);
        Stadio stadio = new Stadio(String.valueOf(stadioNew), capacityNewStadio);
        stadioDAO.create(stadio);
        insertMethod(nomeClub, annoFondazione, citta,nazionalita, stadioNew);
    }

    private void insertMethod(String nomeClub,int annoFondazione,String citta, String nazionalita,String stadioNew) throws SQLException {
        List<String> squad = squadraDAO.getAllSquadre();
        if (squad.contains(nomeClub)){
            showError("Errore inserimento","Nome squadra scelto già esistente");
        }else{
            Squadra squadra = new Squadra(nomeClub,annoFondazione,citta,nazionalita,stadioNew);
            squadraDAO.addSquadra(squadra);
            showSuccessAlert();
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("../Interfacce/LoggedIn.fxml"));
                Stage stage = (Stage) Proceed_btn.getScene().getWindow();
                Scene scene = new Scene(loader.load());
                stage.setScene(scene);
            } catch (IOException e) {
            }
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
                try {
                    FXMLLoader loader = new FXMLLoader(getClass().getResource("../Interfacce/LoggedIn.fxml"));
                    Stage stage = (Stage) Back_btn.getScene().getWindow();
                    Scene scene = new Scene(loader.load());
                    stage.setScene(scene);
                } catch (IOException e) {
                }
            }
        }



    private void showSuccessAlert() {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Successo");
        alert.setHeaderText(null);
        alert.setContentText("Dati aggiunti con successo!");

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


    private void showError(String titolo, String messaggio) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(titolo);
        alert.setHeaderText(null);
        alert.setContentText(messaggio);
        alert.showAndWait();
    }
}
