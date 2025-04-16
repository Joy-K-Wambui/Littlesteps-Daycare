package com.littlesteps.littlesteps;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class SceneNavigator {

    private static Stage primaryStage;
    private static final Map<String, String> scenes = new HashMap<>();

    public static void setStage(Stage stage) {
        primaryStage = stage;
    }

    public static void loadScene(String sceneName) {
        try {
            String fxmlFile = scenes.get(sceneName);
            if (fxmlFile == null) {
                throw new IllegalArgumentException("Scene not found: " + sceneName);
            }

            Parent root = FXMLLoader.load(SceneNavigator.class.getResource(fxmlFile));
            primaryStage.setScene(new Scene(root));
            primaryStage.show();
        } catch (IOException e) {
            System.err.println("Failed to load scene: " + sceneName);
            e.printStackTrace();
        }
    }

    public static void addScene(String sceneName, String fxmlFile) {
        scenes.put(sceneName, fxmlFile);
    }
}
