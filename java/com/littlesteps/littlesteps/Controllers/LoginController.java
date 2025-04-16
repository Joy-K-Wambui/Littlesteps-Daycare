package com.littlesteps.littlesteps.Controllers;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.control.Button;
import javafx.stage.Stage;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class LoginController {
    @FXML
    private TextField usernameInput;
    @FXML
    private PasswordField passwordInput;
    @FXML
    private ComboBox<String> roleComboBox;
    @FXML
    private Label feedbackLabel;
    @FXML
    private Button handleLoginButton;

    public void initialize() {
        // Populate the ComboBox with roles from the database
        loadRoles();
    }

    private void loadRoles() {
        String query = "SELECT DISTINCT role FROM users"; // SQL query to fetch distinct roles
        try (Connection connection = DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/littlestepsdaycare", "root", "password");
             PreparedStatement preparedStatement = connection.prepareStatement(query);
             ResultSet resultSet = preparedStatement.executeQuery()) {

            while (resultSet.next()) {
                String role = resultSet.getString("role");
                roleComboBox.getItems().add(role); // Add the role to the ComboBox
            }

        } catch (Exception e) {
            e.printStackTrace();
            feedbackLabel.setText("Error loading roles from database."); // Inform user of error
        }
    }

    @FXML
    private void handleLoginButton() {
        // Retrieve input values from UI
        String username = usernameInput.getText().trim();
        String password = passwordInput.getText().trim();
        String role = roleComboBox.getValue();

        // Validate input fields
        if (username.isEmpty() || password.isEmpty() || role == null) {
            feedbackLabel.setText("All fields are required. Please fill in your details.");
            return;
        }

        // Authenticate user
        if (authenticateUser(username, password, role)) {
            feedbackLabel.setText("Login successful!");
            loadDashboard(role);
        } else {
            feedbackLabel.setText("Invalid credentials. Please try again.");
        }
    }


    private boolean authenticateUser(String username, String password, String role) {
        String query = "SELECT * FROM users WHERE username = ? AND password_hash = ? AND role = ?";

        try (Connection connection = DriverManager.getConnection(
                "jdbc:mysql://127.0.0.1:3306/littlestepsdaycare", "root", "password");
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            // Set query parameters
            preparedStatement.setString(1, username.trim());
            preparedStatement.setString(2, password); // Use plain-text password
            preparedStatement.setString(3, role.trim());

            // Execute the query
            ResultSet resultSet = preparedStatement.executeQuery();
            System.out.println("The password: " + password);

            return resultSet.next(); // Returns true if a matching user is found

        } catch (Exception e) {
            e.printStackTrace();
            return false; // Handle exceptions
        }
    }

    private void loadDashboard(String role) {
        try {
            Stage stage = (Stage) handleLoginButton.getScene().getWindow(); // Get the current stage
            Parent root;

            // Load the appropriate dashboard based on the role
            if ("Admin".equals(role)) {
                root = FXMLLoader.load(getClass().getResource("/com/littlesteps/littlesteps/adminDashboad.fxml"));
                stage.setTitle("Admin Dashboard");
                stage.setFullScreen(true);
            } else if ("Staff".equals(role) || "Parent".equals(role)) {
                root = FXMLLoader.load(getClass().getResource("/com/littlesteps/littlesteps/dashboard.fxml"));
                stage.setTitle("User Dashboard");
                stage.setFullScreen(true);
            } else {
                feedbackLabel.setText("Unknown role selected.");
                return;
            }

            stage.setScene(new Scene(root, 1920, 1080)); // Set the new scene
            stage.setFullScreen(true);
            stage.show(); // Show the new scene
        } catch (Exception e) {
            e.printStackTrace();
            feedbackLabel.setText("Failed to load the dashboard."); // Provide feedback to user
        }
    }
}