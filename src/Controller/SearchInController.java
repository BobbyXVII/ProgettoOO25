package Controller;

import DAO.SquadraDAO;
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

public class SearchInController {

    @FXML
    private ListView<String> myListView; // La ListView per visualizzare i risultati

    @FXML
    private Label myLabel;


    private SquadraDAO squadraDAO = new SquadraDAO();

    private final String QueryDone = LoggedInController.StringaQuery;

    public void initialize() throws SQLException {
        // Recupera una lista di squadre dal DAO
        ObservableList<String> squadraNames = FXCollections.observableArrayList();

        // Recupera la squadra con il nome "SSC Napoli" (puoi modificarlo per ottenere altre squadre)
        Squadra squadra = squadraDAO.getSquadraByNome(QueryDone);
        if (squadra != null) {
            // Aggiungi il nome della squadra alla ListView
            squadraNames.add(squadra.getNomeSquadra()); // Usa il nome della squadra
        }

        // Popola la ListView con i nomi delle squadre
        myListView.setItems(squadraNames);

        // Aggiungi un listener per quando un elemento della ListView è selezionato
        myListView.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observableValue, String s, String t1) {
                // Quando un elemento è selezionato, mostra il nome nella label
                String selectedSquadra = myListView.getSelectionModel().getSelectedItem();
                myLabel.setText("Squadra selezionata: " + selectedSquadra);
            }
        });
    }
}
