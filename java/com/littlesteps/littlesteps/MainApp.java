package com.littlesteps.littlesteps;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Objects;
import java.util.logging.Level;
import java.util.logging.Logger;

public class MainApp extends Application {

    private static final Logger logger = Logger.getLogger(MainApp.class.getName());

    @Override
    public void start(Stage primaryStage) {
        // Set the primary stage for scene navigation
        SceneNavigator.setStage(primaryStage);
        SceneNavigator.addScene("dashboard", "dashboard.fxml");


        // Register scenes with their respective FXML files
        SceneNavigator.addScene("login", "login-screen.fxml");
        SceneNavigator.addScene("dashboard", "dashboard.fxml");

        // Load the initial scene
        SceneNavigator.loadScene("login");

        primaryStage.setTitle("LittleSteps Daycare");
        try {
            // Load the FXML file for the login screen
            FXMLLoader loader = new FXMLLoader(getClass().getResource("login-screen.fxml"));
            Parent root = loader.load();

            // Set up the scene with dimensions and add the stylesheet
            Scene scene = new Scene(root, 800, 600);
            scene.getStylesheets().add(Objects.requireNonNull(getClass().getResource("styles.css")).toExternalForm());

            // Configure the primary stage
            primaryStage.setTitle("LittleSteps Daycare - Login");
            primaryStage.setScene(scene);
            primaryStage.show();
        } catch (Exception e) {
            logger.log(Level.SEVERE, "Error loading FXML: ", e);
        }
    }

    private Stage stage;
    private Scene scene;
    private Parent root;

    public void switchToDashboard(ActionEvent event) throws IOException {
        root = FXMLLoader.load(getClass().getResource("dashboard.fxml"));
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    public void switchToChildren(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("children.fxml"));
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    public void switchToBilling(ActionEvent event) throws IOException {
        root = FXMLLoader.load(getClass().getResource("BillingInvoice.fxml"));
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    public void switchToEventNotification(ActionEvent event) throws IOException {
        root = FXMLLoader.load(getClass().getResource("EventNotification.fxml"));
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }
    public static void main(String[] args) {
        launch(args);
    }
}
