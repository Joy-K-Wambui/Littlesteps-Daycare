package com.littlesteps.littlesteps.Controllers;

import com.littlesteps.littlesteps.Dao.*;
import com.littlesteps.littlesteps.Records.EventsNotificationRecord;
import com.littlesteps.littlesteps.Records.StaffRecord;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.chart.AreaChart;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Stack;

public class DashboardController {

    //staff table
    @FXML
    private TableView<StaffRecord> staffTable;
    @FXML
    private TableColumn<StaffRecord, String> staffIdClmn;
    @FXML
    private TableColumn<StaffRecord, String> staffNameClmn;
    @FXML
    private TableColumn<StaffRecord, String> staffSurnameClmn;
    @FXML
    private TableColumn<StaffRecord, String> staffRoleClmn;
    @FXML
    private TableColumn<StaffRecord, String> staffDutyClmn;
    @FXML
    private TableColumn<StaffRecord, Date> StaffHireDateClmn;
    @FXML
    private TableColumn<StaffRecord, String> staffBranchClmn;

    @FXML
    private TextArea notification_textview;

    //Activity table
    @FXML
    private TableView<EventsNotificationRecord> activityTable;
    @FXML
    private TableColumn<EventsNotificationRecord, String> activityColumn;
    @FXML
    private TableColumn<EventsNotificationRecord, String> daycareColumn;

    // Attendance statistics panel
    @FXML
    private ProgressBar totalPresentProgressBar;

    @FXML
    private Label totalPresentLabel;

    @FXML
    private ProgressBar totalAbsentProgressBar;

    @FXML
    private Label totalAbsentLabel;

    @FXML
    private Label averageAttendanceLabel;

    // Line Chart
    @FXML
    private LineChart<String, Number> attendanceLineChart;


    private EventsNotificationDAO eventsNotificationDAO;
    private StaffDAO staffDAO;
    @FXML
    public void initialize() throws SQLException {
        Connection connection = DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/littlestepsdaycare", "root", "password");
        eventsNotificationDAO = new EventsNotificationDAO(connection);
        staffDAO = new StaffDAO(connection);


// Configure staff columns
        staffIdClmn.setCellValueFactory(cellData -> cellData.getValue().getStaffIdProperty().asObject().asString());
        staffNameClmn.setCellValueFactory(cellData -> cellData.getValue().getNameProperty());
        staffSurnameClmn.setCellValueFactory(cellData -> cellData.getValue().getSurNameProperty());
        staffRoleClmn.setCellValueFactory(cellData -> cellData.getValue().getRoleProperty());
        StaffHireDateClmn.setCellValueFactory(cellData -> cellData.getValue().getHireDateProperty());
        staffBranchClmn.setCellValueFactory(cellData -> cellData.getValue().getBranchIdProperty().asObject().asString());
        staffDutyClmn.setCellValueFactory(cellData -> cellData.getValue().getDutyProperty());

// Configure activity columns
        activityColumn.setCellValueFactory(cellData -> cellData.getValue().getActivityProperty());
        daycareColumn.setCellValueFactory(cellData -> cellData.getValue().getBranchIdProperty().asObject().asString());

        // Load data into the table
        loadENData(eventsNotificationDAO);
        loadStaffData(staffDAO);
        loadNotifications();
        loadAttendanceData();
        loadAttendanceStatistics();
    }

    private void loadStaffData(StaffDAO staffDAO) {
        try {
            ObservableList<StaffRecord> staffData = staffDAO.getAllStaffRecords();
            staffTable.setItems(staffData); // Populate the table with data from DAO
        } catch (SQLException e) {
            e.printStackTrace(); // Handle exception appropriately
        }
    }

    // Method to load notifications from the database and display them in the TextArea
    public void loadNotifications() {
        String query = "SELECT title, message FROM notifications";

        try (Connection connection = DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/littlestepsdaycare", "root", "password");
                PreparedStatement pstmt = connection.prepareStatement(query);
                ResultSet rs = pstmt.executeQuery(query)) {

            StringBuilder notifications = new StringBuilder();

            while (rs.next()) {
                String title = rs.getString("title");
                String message = rs.getString("message");

                // Format the data and append it to the StringBuilder
                notifications.append("Title: ").append(title).append("\n");
                notifications.append("Message: ").append(message).append("\n\n");
            }

            // Set the text content of the TextArea to display the notifications
            notification_textview.setText(notifications.toString());

        } catch (SQLException e) {
            e.printStackTrace(); // Handle exceptions (e.g., connection issues)
        }
    }
    private void loadENData(EventsNotificationDAO eventsNotificationDAO) {
        try {
            ObservableList<EventsNotificationRecord> ENData = eventsNotificationDAO.getAllEventsNotificationRecords();
            activityTable.setItems(ENData); // Populate the table with data from DAO
        } catch (SQLException e) {
            e.printStackTrace(); // Handle exception appropriately
        }
    }

    private void loadAttendanceStatistics() {
        String query = "SELECT " +
                "SUM(CASE WHEN status = 'Present' THEN 1 ELSE 0 END) AS present_count, " +
                "SUM(CASE WHEN status = 'Absent' THEN 1 ELSE 0 END) AS absent_count " +
                "FROM attendance";

        try (Connection connection = DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/littlestepsdaycare", "root", "password");
             PreparedStatement pstmt = connection.prepareStatement(query);
             ResultSet rs = pstmt.executeQuery()) {

            if (rs.next()) {
                int presentCount = rs.getInt("present_count");
                int absentCount = rs.getInt("absent_count");
                int totalAttendance = presentCount + absentCount;

                // Calculate percentages
                double presentPercentage = (totalAttendance > 0) ? (double) presentCount / totalAttendance : 0;
                double absentPercentage = (totalAttendance > 0) ? (double) absentCount / totalAttendance : 0;

                // Update progress bars and labels
                totalPresentProgressBar.setProgress(presentPercentage);
                totalAbsentProgressBar.setProgress(absentPercentage);
                totalPresentLabel.setText(String.format("Total Present: %.2f%%", presentPercentage * 100));
                totalAbsentLabel.setText(String.format("Total Absent: %.2f%%", absentPercentage * 100));

                // Calculate average monthly attendance
                double averageMonthlyAttendance = calculateAverageMonthlyAttendance();
                averageAttendanceLabel.setText(String.format("Average Monthly Attendance: %.2f%%", averageMonthlyAttendance));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private double calculateAverageMonthlyAttendance() {
        String query = "SELECT AVG(present_percentage) AS average_attendance FROM ( " +
                "SELECT MONTH(date) AS month, " +
                "SUM(CASE WHEN status = 'Present' THEN 1 ELSE 0 END) AS present_count, " +
                "SUM(CASE WHEN status = 'Absent' THEN 1 ELSE 0 END) AS absent_count, " +
                "SUM(CASE WHEN status = 'Present' THEN 1 ELSE 0 END) * 1.0 / " +
                "(SUM(CASE WHEN status = 'Present' THEN 1 ELSE 0 END) + SUM(CASE WHEN status = 'Absent' THEN 1 ELSE 0 END)) AS present_percentage " +
                "FROM attendance GROUP BY MONTH(date)) AS monthly_stats";

        try (Connection connection = DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/littlestepsdaycare", "root", "password");
             PreparedStatement pstmt = connection.prepareStatement(query);
             ResultSet rs = pstmt.executeQuery()) {

            if (rs.next()) {
                return rs.getDouble("average_attendance") * 100; // Convert to percentage
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return 0.0;
    }

    private void loadAttendanceData() {
        String query = "SELECT DATE(date) AS date, " +
                "SUM(CASE WHEN status = 'Present' THEN 1 ELSE 0 END) AS present_count, " +
                "SUM(CASE WHEN status = 'Absent' THEN 1 ELSE 0 END) AS absent_count " +
                "FROM attendance GROUP BY DATE(date)";

        try (Connection connection = DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/littlestepsdaycare", "root", "password");
             PreparedStatement pstmt = connection.prepareStatement(query);
             ResultSet rs = pstmt.executeQuery()) {

            attendanceLineChart.getData().clear(); // Clear existing data

            XYChart.Series<String, Number> presentSeries = new XYChart.Series<>();
            XYChart.Series<String, Number> absentSeries = new XYChart.Series<>();
            presentSeries.setName("Present");
            absentSeries.setName("Absent");

            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd MMM yyyy");

            while (rs.next()) {
                String date = rs.getString("date");
                String formattedDate = LocalDate.parse(date).format(formatter);
                int presentCount = rs.getInt("present_count");
                int absentCount = rs.getInt("absent_count");

                // Add data points
                presentSeries.getData().add(new XYChart.Data<>(formattedDate, presentCount));
                absentSeries.getData().add(new XYChart.Data<>(formattedDate, absentCount));
            }

            attendanceLineChart.getData().add(presentSeries);
            attendanceLineChart.getData().add(absentSeries);

            // Add tooltips
            for (XYChart.Data<String, Number> data : presentSeries.getData()) {
                Tooltip.install(data.getNode(), new Tooltip("Present: " + data.getYValue()));
            }
            for (XYChart.Data<String, Number> data : absentSeries.getData()) {
                Tooltip.install(data.getNode(), new Tooltip("Absent: " + data.getYValue()));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @FXML
    public void handleChildrenbtn(ActionEvent actionEvent) {
        switchScene("children.fxml", "Children Management");
    }

    @FXML
    public void handleBillingbtn(ActionEvent actionEvent) {
        switchScene("BillingInvoice.fxml", "Billing and Invoice Management");
    }

    @FXML
    public void handleHomebtn(ActionEvent actionEvent) {
        refreshCurrentScene();
    }

    @FXML
    public void handleArticlebtn(ActionEvent actionEvent) {
        switchScene("articles.fxml", "Articles Management");
    }

    @FXML
    public void handleLogoutbtn(ActionEvent actionEvent) {
        switchScene("login-screen.fxml", "Login");
    }

    private void switchScene(String fxmlFileName, String title) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/littlesteps/littlesteps/" + fxmlFileName));
            Parent root = loader.load(); // Load the new scene

            Stage stage = (Stage) staffTable.getScene().getWindow(); // Get the current stage
            stage.setScene(new Scene(root));
            stage.setTitle(title);
            stage.setFullScreen(true); // Set to full screen
            stage.show();
        } catch (IOException e) {
            e.printStackTrace(); // Handle exceptions
        }
    }

    private void refreshCurrentScene() {
        try {
            Stage stage = (Stage) staffTable.getScene().getWindow();
            Parent root = FXMLLoader.load(getClass().getResource("/com/littlesteps/littlesteps/dashboard.fxml")); // Load the current dashboard scene
            stage.setScene(new Scene(root));
            stage.setFullScreen(true); // Set to full screen
            stage.show();
        } catch (IOException e) {
            e.printStackTrace(); // Handle exceptions
        }
    }


}
