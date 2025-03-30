package Controller;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.ProgressBar;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.IOException;
import java.util.Random;

public class ElaborazioneQueryController {

    @FXML
    private ProgressBar ProgressB;

    private Timeline timeline;

    public void initialize() {
        Random random = new Random();
        int duration = random.nextInt(3) + 8;
        double increment = 1.0 / (duration * 10.0);

        timeline = new Timeline(
                new KeyFrame(Duration.millis(100), event -> {
                    Platform.runLater(() -> {
                        double currentProgress = ProgressB.getProgress();
                        if (currentProgress < 1.0) {
                            ProgressB.setProgress(currentProgress + increment);
                        } else {
                            timeline.stop();
                            loadNextScene();
                        }
                    });
                })
        );
        timeline.setCycleCount(Timeline.INDEFINITE);
        timeline.play();
    }

    private void loadNextScene() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/Interfacce/SearchIn.fxml"));
            Stage stage = (Stage) ProgressB.getScene().getWindow();
            Scene scene = new Scene(loader.load());
            stage.setScene(scene);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
