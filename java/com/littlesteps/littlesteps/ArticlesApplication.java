package com.littlesteps.littlesteps;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.util.Objects;

public class ArticlesApplication extends Application {

    @Override
    public void start(Stage primaryStage) {
        try {
            // Load the articles FXML file
            FXMLLoader loader = new FXMLLoader(getClass().getResource("articles.fxml"));
            Parent root = loader.load();

            // Set the scene and stage properties
            Scene scene = new Scene(root, 800, 600);
            scene.getStylesheets().add(Objects.requireNonNull(getClass().getResource("styles.css")).toExternalForm());

            primaryStage.setTitle("LittleSteps Daycare - Articles");
            primaryStage.setScene(scene);
            primaryStage.show();
        } catch (Exception e) {
            System.err.println("Failed to load the Articles scene.");
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}
