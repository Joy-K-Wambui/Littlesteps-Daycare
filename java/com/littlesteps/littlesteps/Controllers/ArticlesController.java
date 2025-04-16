package com.littlesteps.littlesteps.Controllers;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.event.ActionEvent;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Stack;
import java.util.regex.Pattern;

public class ArticlesController {

    @FXML
    private TextField NameFieldExample;

    @FXML
    private TextField EmailFieldExample;

    @FXML
    private TextField CommentField;

    @FXML
    private ListView<String> listView;

    @FXML
    public void initialize() throws SQLException {
        Connection connection = DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/littlestepsdaycare", "root", "password");
    }
        public void EmailFieldExample(ActionEvent actionEvent) {
        String email = EmailFieldExample.getText();
        String emailPattern = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,6}$";

        if (!Pattern.matches(emailPattern, email)) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Invalid Email");
            alert.setHeaderText(null);
            alert.setContentText("Please enter a valid email address.");
            alert.showAndWait();
        } else {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Valid Email");
            alert.setHeaderText(null);
            alert.setContentText("Email is valid!");
            alert.showAndWait();
        }
    }

    private Stack<String> sceneStack = new Stack<>();

    @FXML
    private ImageView articleImage;


    // Updates the ListView with the entered name and comment
    public void articleSubmit(ActionEvent actionEvent) {
        String name = NameFieldExample.getText();
        String comment = CommentField.getText();

        if (!name.isEmpty() && !comment.isEmpty()) {
            listView.getItems().add(name + ": " + comment);
            NameFieldExample.clear();
            CommentField.clear();
        } else {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Missing Information");
            alert.setHeaderText(null);
            alert.setContentText("Please enter both a name and a comment.");
            alert.showAndWait();
        }
    }

    // Clears the input fields for name, email, and comment
    public void articleClear(ActionEvent actionEvent) {
        NameFieldExample.clear();
        EmailFieldExample.clear();
        CommentField.clear();
    }

    // Utility method to clear text fields
    private void clearFields() {
        NameFieldExample.clear();
        EmailFieldExample.clear();
        CommentField.clear();
    }

    // Utility method to show alerts
    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    public void article2(ActionEvent actionEvent) {
    }

    public void article1(ActionEvent actionEvent) {
    }

    // Method to navigate back to the dashboard
    @FXML
    public void backToDashboard(ActionEvent actionEvent) {
        switchScene("dashboard.fxml", "Dashboard Screen");
    }

    @FXML
    public void backToAdminDashboard(ActionEvent actionEvent) {
        switchScene("adminDashboad.fxml", "Admin Dashboard");
    }

    private void switchScene(String fxmlFileName, String title) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/littlesteps/littlesteps/" + fxmlFileName));
            Parent root = loader.load(); // Load the new scene

            Stage stage = (Stage) NameFieldExample.getScene().getWindow(); // Get the current stage
            stage.setScene(new Scene(root));
            stage.setTitle(title);
            stage.setFullScreen(true); // Set to full screen
            stage.show();
        } catch (IOException e) {
            e.printStackTrace(); // Handle exceptions
        }
    }
}