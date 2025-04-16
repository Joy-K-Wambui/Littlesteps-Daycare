package com.littlesteps.littlesteps.Controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.regex.Pattern;

public class EventNotificationController {

    @FXML
    private TextField eventTitleField, notificationTitleField;
    @FXML
    private DatePicker eventDatePicker;
    @FXML
    private ComboBox<String> eventAudienceDropdown, notificationAudienceDropdown;
    @FXML
    private TextArea eventDescriptionArea, notificationMessageArea;
    @FXML
    private Label feedbackLabel;

    @FXML
    private TableView<Event> upcomingEventsTable;
    @FXML
    private TableColumn<Event, String> eventDateColumn, eventTitleColumn, eventAudienceColumn;

    @FXML
    private TableView<Notification> recentNotificationsTable;
    @FXML
    private TableColumn<Notification, String> notificationDateColumn, notificationTitleColumn;

    private final ObservableList<Event> eventList = FXCollections.observableArrayList();
    private final ObservableList<Notification> notificationList = FXCollections.observableArrayList();

    @FXML
    private ListView<String> listView;

    @FXML
    public void initialize() {
        // Set up columns
        eventDateColumn.setCellValueFactory(new PropertyValueFactory<>("date"));
        eventTitleColumn.setCellValueFactory(new PropertyValueFactory<>("title"));
        eventAudienceColumn.setCellValueFactory(new PropertyValueFactory<>("audience"));

        notificationDateColumn.setCellValueFactory(new PropertyValueFactory<>("dateSent"));
        notificationTitleColumn.setCellValueFactory(new PropertyValueFactory<>("title"));

        // Populate combo boxes
        eventAudienceDropdown.getItems().addAll("Parents", "Children", "Staff", "Both");
        notificationAudienceDropdown.getItems().addAll("Parents", "Children", "Staff", "Both");

        // Load initial data into tables
        loadInitialData();
    }

    private void loadInitialData() {
        // Sample initial data for events and notifications
        eventList.add(new Event("2024-11-15", "Parent-Teacher Meeting", "Parents"));
        eventList.add(new Event("2024-12-01", "Holiday Party", "Both"));
        upcomingEventsTable.setItems(eventList);

        notificationList.add(new Notification("2024-10-15", "Welcome Parents"));
        notificationList.add(new Notification("2024-10-18", "Winter Preparations"));
        recentNotificationsTable.setItems(notificationList);
    }

    @FXML
    private void handleScheduleEvent() {
        String title = eventTitleField.getText();
        String date = eventDatePicker.getValue() != null ? eventDatePicker.getValue().format(DateTimeFormatter.ofPattern("yyyy-MM-dd")) : null;
        String audience = eventAudienceDropdown.getValue();

        if (title.isEmpty() || date == null || audience == null) {
            feedbackLabel.setText("Please complete all fields for scheduling an event.");
            feedbackLabel.setStyle("-fx-text-fill: red;");
            return;
        }

        Event newEvent = new Event(date, title, audience);
        eventList.add(newEvent);
        feedbackLabel.setText("Event scheduled successfully.");
    }

    @FXML
    private void handleSendNotification() {
        String title = notificationTitleField.getText();
        String message = notificationMessageArea.getText();
        String audience = notificationAudienceDropdown.getValue();

        if (title.isEmpty() || message.isEmpty() || audience == null) {
            feedbackLabel.setText("Please complete all fields for sending a notification.");
            feedbackLabel.setStyle("-fx-text-fill: red;");
            return;
        }

        Notification newNotification = new Notification(LocalDate.now().toString(), title);
        notificationList.add(newNotification);
        feedbackLabel.setText("Notification sent successfully.");
    }

    public void backToDasboard(ActionEvent actionEvent) {
    }

    // Event and Notification inner classes for TableView data
    public static class Event {
        private final String date;
        private final String title;
        private final String audience;

        public Event(String date, String title, String audience) {
            this.date = date;
            this.title = title;
            this.audience = audience;
        }

        public String getDate() { return date; }
        public String getTitle() { return title; }
        public String getAudience() { return audience; }
    }

    public static class Notification {
        private final String dateSent;
        private final String title;

        public Notification(String dateSent, String title) {
            this.dateSent = dateSent;
            this.title = title;
        }

        public String getDateSent() { return dateSent; }
        public String getTitle() { return title; }
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

            Stage stage = (Stage) eventTitleField.getScene().getWindow(); // Get the current stage
            stage.setScene(new Scene(root));
            stage.setTitle(title);
            stage.setFullScreen(true); // Set to full screen
            stage.show();
        } catch (IOException e) {
            e.printStackTrace(); // Handle exceptions
        }
    }
}
