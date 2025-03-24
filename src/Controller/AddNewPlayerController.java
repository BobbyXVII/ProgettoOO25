package Controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ChoiceBox;
import javafx.scene.layout.AnchorPane;

public class AddNewPlayerController {



    @FXML
    private ChoiceBox<String> nationalityChoiceBox;

    @FXML
    private ChoiceBox<String> PiedeChoiceBox;

    @FXML
    public void initialize() {
        // Lista predefinita di nazionalità
        ObservableList<String> nazionalità = FXCollections.observableArrayList(
                "Italia", "Germania", "Francia", "Spagna", "Inghilterra", "Portogallo", "Brasile", "Argentina", "Stati Uniti", "Belgio", "Olanda", "Polonia", "Grecia",
                "Svezia", "Russia", "Giappone", "Messico", "Uruguay", "Colombia", "Cile", "Croazia", "Danimarca", "Serbia", "Svizzera", "Australia", "Ecuador", "Tunisia", "Algeria",
                "Canada", "Corea del Sud", "Perù", "Egitto", "Nigeria", "Camerun", "Sud Africa", "Turchia", "Israele", "Romania", "Bulgaria", "Finlandia", "Norvegia", "Austria",
                "Slovacchia", "Slovenia", "Ceco", "Costa Rica", "Giamaica", "Honduras", "Panama", "Kenia", "Zambia", "Mozambico", "Ghana", "Marocco", "Galles", "Scozia", "Paraguay",
                "Bolivia", "Venezuela", "Perù", "Ecuador", "Libia", "Seychelles", "Malta", "Figi", "Samoa", "Tonga", "Isole Faroe", "Isole Cook", "Saint Kitts e Nevis", "Saint Lucia",
                "Barbados", "Trinidad e Tobago", "Grenada", "Antigua e Barbuda", "Saint Vincent e Grenadine", "Bahamas", "Bermuda", "Isole Vergini", "Armenia", "Georgia", "Azerbaigian",
                "Kazakhstan", "Uzbekistan", "Turkmenistan", "Kyrgyzstan", "Tajikistan", "Mongolia", "Nepal", "Bhutan", "Maldivas", "Sri Lanka", "Myanmar", "Cambogia", "Laos", "Filippine",
                "Indonesia", "Singapore", "Malaysia", "Brunei");

        ObservableList<String> TipologiePiedi = FXCollections.observableArrayList("Destro","Sinistro","Ambidestro");
        // Carica le nazionalità nel ChoiceBox
        nationalityChoiceBox.setItems(nazionalità);
        PiedeChoiceBox.setItems(TipologiePiedi);
    }
}
