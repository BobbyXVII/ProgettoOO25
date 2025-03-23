package Controller;

import DAO.CarrieraDAO;
import DAO.PersonaDAO;
import DAO.SquadraDAO;
import Model.Carriera;
import Model.Persona;
import Model.Squadra;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.SQLException;
import java.util.List;

public class SearchInController {

    @FXML
    private ListView<String> myListView; // La ListView per visualizzare i risultati

    @FXML
    private Label myLabel;


    private SquadraDAO squadraDAO = new SquadraDAO();

    private CarrieraDAO carrieraDAO = new CarrieraDAO();

    private PersonaDAO personaDAO = new PersonaDAO();

    private final String QueryDone = LoggedInController.StringaQuery;

    public void initialize() throws SQLException {
        // Recupera una lista di squadre dal DAO
        ObservableList<String> Totalquery = FXCollections.observableArrayList();

        // Recupera la squadra con il nome "SSC Napoli" (puoi modificarlo per ottenere altre squadre)
        Squadra squadra = squadraDAO.getSquadraByNome(QueryDone);
        if (squadra != null) {
            // Aggiungi il nome della squadra alla ListView
            Totalquery.add(squadra.getNomeSquadra()); // Usa il nome della squadra
            Totalquery.add(" ");
            Totalquery.add(" ");
            List<Carriera> carriere = carrieraDAO.getCarrieraByClubName(QueryDone);
            for (Carriera carriera : carriere) {
                String nomeCognome = personaDAO.getNomeCognomeById(carriera.getId()); // Ottieni Nome Cognome
                Totalquery.add(nomeCognome);
            }
        }else{
            int IdResult = personaDAO.getIdByNome(QueryDone);
            if(IdResult > 0){
                String nomeCognome = personaDAO.getNomeCognomeById(IdResult); // Ottieni Nome Cognome
                Totalquery.add(nomeCognome);
                Totalquery.add(" ");
                Totalquery.add(" ");
            }
        }



        // Popola la ListView con i nomi delle squadre
        myListView.setItems(Totalquery);

        // Aggiungi un listener per quando un elemento della ListView è selezionato
        myListView.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observableValue, String s, String t1) {
                // Quando un elemento è selezionato, mostra il nome nella label
                String selectedSquadra = myListView.getSelectionModel().getSelectedItem();

            }
        });
    }
}
