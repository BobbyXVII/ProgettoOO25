package Controller;

        import DAO.*;
        import Model.Squadra;
        import javafx.application.Platform;
        import javafx.collections.FXCollections;
        import javafx.collections.ObservableList;
        import javafx.fxml.FXML;
        import javafx.scene.control.Alert;
        import javafx.scene.control.Button;
        import javafx.scene.control.Label;
        import javafx.scene.control.TextField;
        import javafx.scene.image.ImageView;
        import javafx.scene.layout.AnchorPane;
        import javafx.scene.layout.Pane;

        import java.sql.SQLException;
        import java.util.List;

public class VisitQueryController {

    private final String QueryRichiesta = SearchInController.selectedItem;

    @FXML private AnchorPane VisitCalciatori;
    @FXML private Pane Pannello, Pannello1, Valore, Panel, Bar, Pannello3;
    @FXML private Label NomeBar, CognomeBar, ValoreDiMercL, ValoreDiMercL1;
    @FXML private Label NomeL, cognomeL, DateNascitaL, NazionalitaL, PiedeL, CurrTeamL;
    @FXML private TextField textFieldNome, textFieldCognome, textFieldDataNascita, textFieldNazionalita, textFieldPiede, textFieldSquadra;
    @FXML private ImageView logoVisitaPlayer, iconImage;
    @FXML private Button btnTornaRicerca, btnTornaHome;

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
            //A
        } else {
            List<Integer> idResults = personaDAO.getIdsByNome(QueryRichiesta);  // Restituisce una lista di ID
            if (!idResults.isEmpty()) {
                VisitCalciatori.setOpacity(1);
            } else {
                int ResultSearchComp = competizioneDAO.checkCompetizioneEsiste(QueryRichiesta);
                if (ResultSearchComp == 1) {
                    //
                } else {
                    showAlert("La query non ha riportato alcun risultato.");
                }
            }
        }
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
