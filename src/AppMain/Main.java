package AppMain;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("../Interfacce/Login.fxml"));
        Parent root = loader.load();

        Scene scene = new Scene(root);
        primaryStage.setScene(scene);
        primaryStage.setTitle("Unina Soccer");

        String imagePath = "/Immagini/Icon.png";
        Image icon = new Image(getClass().getResource(imagePath).toExternalForm());
        primaryStage.getIcons().add(icon);

        primaryStage.show();
        primaryStage.setResizable(false);
    }

    public static void main(String[] args) {
        launch(args);
    }
}