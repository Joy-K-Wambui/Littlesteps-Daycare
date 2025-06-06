package com.littlesteps.littlesteps.Controllers;

import com.littlesteps.littlesteps.Dao.*;
import com.littlesteps.littlesteps.Records.*;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.chart.PieChart;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.XYChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.List;

import static java.sql.DriverManager.getConnection;

public class AdminDashboardController {
    @FXML
    private TabPane adminTabPane;
    @FXML
    private Tab daycareTab;
    @FXML
    private Tab staffTab;
    @FXML
    private Tab parentsTab;
    @FXML
    private Tab childrenTab;
    @FXML
    private Tab healthTab;
    // Line Chart
    @FXML
    private LineChart<String, Number> attendanceLineChart;


    //Daycare table
    @FXML
    private TableView<DaycareBranch> daycareTable;
    @FXML
    private TableColumn<DaycareBranch, Integer> branchIdColumn;
    @FXML
    private TableColumn<DaycareBranch, String> branchNameColumn;
    @FXML
    private TableColumn<DaycareBranch, String> contactColumn;


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
    private TableColumn<StaffRecord, Date> StaffHireDateClmn;
    @FXML
    private TableColumn<StaffRecord, String> staffBranchClmn;

    //parent table
    @FXML
    private TableView<ParentRecord> parentTable;
    @FXML
    private TableColumn<ParentRecord, String> parentFirstNameClmn;
    @FXML
    private TableColumn<ParentRecord, String> parentLastNameClmn;
    @FXML
    private TableColumn<ParentRecord, String> parentContactClmn;

    //Relationship table
    @FXML
    private TableView<PCRecord> rlshipTable;
    @FXML
    private TableColumn<PCRecord, String> rlship;

    //children table
    @FXML
    private TableView<ChildRecord> childrenTable;
    @FXML
    private TableColumn<ChildRecord, String> childFirstNameClmn;
    @FXML
    private TableColumn<ChildRecord, String> childLastNameClmn;
    @FXML
    private TableColumn<ChildRecord, String> childBranchClmn;

    //Health record table
    @FXML
    private TableView<HealthRecord> HRecordTable;
    @FXML
    private TableColumn<HealthRecord, String> hReportIdClmn;
    @FXML
    private TableColumn<HealthRecord, String> hChildIdClmn;
    @FXML
    private TableColumn<HealthRecord, String> hIncidentClmn;
    @FXML
    private TableColumn<HealthRecord, String> hIssueClmn;
    @FXML
    private TableColumn<HealthRecord, String> hBloodClmn;
    @FXML
    private TableColumn<HealthRecord, Date> hReportClmn;
    @FXML
    private TableColumn<HealthRecord, String> hTenantIdClmn;

    @FXML
    private TextField staffNameField; // Staff name field
    @FXML
    private TextField staffSurnameField; // Staff surname field
    @FXML
    private TextField roleField; // Staff role field
    @FXML
    private TextField staffContactField; // Staff contact field
    @FXML
    private DatePicker hireDateField; // Staff hire date picker
    @FXML
    private ComboBox<String> branchComboBox; // ComboBox for selecting branch (populated from branches)

    @FXML
    private TextField parentFirstNameField; // Parent first name field
    @FXML
    private TextField parentSurnameField; // Parent surname field
    @FXML
    private TextField parentContactField; // Parent contact field
    @FXML
    private TextField parentAddressField; // Parent address field

    @FXML
    private TextField childFirstNameField; // Child first name field
    @FXML
    private TextField childSurnameField; // Child surname field
    @FXML
    private ComboBox<String> childBranchComboBox; // ComboBox for selecting branch (populated from branches)
    @FXML
    private CheckBox billingCheckbox;
    @FXML
    private CheckBox staffCheckbox;
    @FXML
    private CheckBox attCheckbox;
    @FXML
    private CheckBox healthCheckbox;

    @FXML
    private Button downloadTxt;
    @FXML
    private Button downloadCsv;

    private DaycareBranchDAO branchDAO;
    private StaffDAO staffDAO;
    private ParentDAO parentDAO;
    private PCDAO pCDAO;
    private ChildRecordDAO childRecordDAO;
    private HealthRecordDAO healthRecordDAO;


    @FXML
    public void initialize() throws SQLException {
        Connection connection = getConnection("jdbc:mysql://127.0.0.1:3306/littlestepsdaycare", "root", "password");
        branchDAO = new DaycareBranchDAO(connection);
        staffDAO = new StaffDAO(connection);
        parentDAO = new ParentDAO(connection);
        pCDAO = new PCDAO(connection);
        childRecordDAO = new ChildRecordDAO(connection);
        healthRecordDAO = new HealthRecordDAO(connection);

        CheckBox billingCheckbox = new CheckBox("Billing");
        CheckBox staffCheckbox = new CheckBox("Staff");
        CheckBox attCheckbox = new CheckBox("Attendance");
        CheckBox healthCheckbox = new CheckBox("Health");

        childRecordDAO = new ChildRecordDAO(connection);


// Set up columns for daycareTable
        branchIdColumn.setCellValueFactory(cellData -> cellData.getValue().getTenantIdProperty().asObject());
        branchNameColumn.setCellValueFactory(cellData -> cellData.getValue().getNameProperty());
        contactColumn.setCellValueFactory(cellData -> cellData.getValue().getContactInfoProperty());

// Configure staff columns
        staffIdClmn.setCellValueFactory(cellData -> cellData.getValue().getStaffIdProperty().asObject().asString());
        staffNameClmn.setCellValueFactory(cellData -> cellData.getValue().getNameProperty());
        staffSurnameClmn.setCellValueFactory(cellData -> cellData.getValue().getSurNameProperty());
        staffRoleClmn.setCellValueFactory(cellData -> cellData.getValue().getRoleProperty());
        StaffHireDateClmn.setCellValueFactory(cellData -> cellData.getValue().getHireDateProperty());
        staffBranchClmn.setCellValueFactory(cellData -> cellData.getValue().getBranchIdProperty().asObject().asString());

// Configure parent columns
        parentFirstNameClmn.setCellValueFactory(cellData -> cellData.getValue().getNameProperty());
        parentLastNameClmn.setCellValueFactory(cellData -> cellData.getValue().getSurNameProperty());
        parentContactClmn.setCellValueFactory(cellData -> cellData.getValue().getContactProperty());

// Configure rlship column
        rlship.setCellValueFactory(cellData -> cellData.getValue().getRlshipProperty());

// Configure children columns
        childFirstNameClmn.setCellValueFactory(cellData -> cellData.getValue().getNameProperty());
        childLastNameClmn.setCellValueFactory(cellData -> cellData.getValue().getSurNameProperty());
        childBranchClmn.setCellValueFactory(cellData -> cellData.getValue().getBranchIdProperty().asObject().asString());

// Configure health report columns
        hReportIdClmn.setCellValueFactory(cellData -> cellData.getValue().getRecordIdProperty().asObject().asString());
        hChildIdClmn.setCellValueFactory(cellData -> cellData.getValue().getChildIdProperty().asObject().asString());
        hIncidentClmn.setCellValueFactory(cellData -> cellData.getValue().getIncidentProperty());
        hIssueClmn.setCellValueFactory(cellData -> cellData.getValue().getIssueProperty());
        hBloodClmn.setCellValueFactory(cellData -> cellData.getValue().getBloodTypeProperty());
        hReportClmn.setCellValueFactory(cellData -> cellData.getValue().getReportDateProperty());
        hTenantIdClmn.setCellValueFactory(cellData -> cellData.getValue().getBranchIdProperty().asObject().asString());

        // Load data into the table
        loadDaycareBranches();
        loadStaffData(staffDAO);
        loadParentData(parentDAO);
        loadPCData(pCDAO);
        loadChildData(childRecordDAO);
        loadHealthData(healthRecordDAO);
        loadEnrollmentData();
        loadAttendanceData();
        loadBillingData();
    }


    private void loadDaycareBranches() {
        try {
            ObservableList<DaycareBranch> branches = branchDAO.getAllBranches();
            daycareTable.setItems(branches);
        } catch (SQLException e) {
            e.printStackTrace();
            // Handle exception
        }
    }
    private void loadStaffData(StaffDAO staffDAO) {
        try {
            ObservableList<StaffRecord> staffData = staffDAO.getAllStaffRecords();
            staffTable.setItems(staffData); // Populate the table with data from DAO
        } catch (SQLException e) {
            e.printStackTrace(); // Handle exception appropriately
        }
    }
    private void loadParentData(ParentDAO parentDAO) {
        try {
            ObservableList<ParentRecord> parentData = parentDAO.getAllParentRecords();
            parentTable.setItems(parentData); // Populate the table with data from DAO
        } catch (SQLException e) {
            e.printStackTrace(); // Handle exception appropriately
        }
    }
    private void loadPCData(PCDAO pCDAO) {
        try {
            ObservableList<PCRecord> pCData = pCDAO.getAllPCRecords();
            rlshipTable.setItems(pCData); // Populate the table with data from DAO
        } catch (SQLException e) {
            e.printStackTrace(); // Handle exception appropriately
        }
    }
    private void loadChildData(ChildRecordDAO childRecordDAO) {
        try {
            ObservableList<ChildRecord> childData = childRecordDAO.getAllChildRecords();
            childrenTable.setItems(childData); // Populate the table with data from DAO
        } catch (SQLException e) {
            e.printStackTrace(); // Handle exception appropriately
        }
    }
    private void loadHealthData(HealthRecordDAO healthRecordDAO) {
        try {
            ObservableList<HealthRecord> healthData = healthRecordDAO.getAllhealthRecords();
            HRecordTable.setItems(healthData); // Populate the table with data from DAO
        } catch (SQLException e) {
            e.printStackTrace(); // Handle exception appropriately
        }
    }

    @FXML
    public void addStaffButton(ActionEvent actionEvent) throws SQLException {
        String firstName = staffNameField.getText();
        String surname = staffSurnameField.getText(); // Get surname
        String role = roleField.getText();
        String contactInfo = staffContactField.getText(); // Get contact info
        LocalDate hireDate = hireDateField.getValue();
        String selectedBranch = branchComboBox.getValue();

        if (!firstName.isEmpty() && !surname.isEmpty() && !role.isEmpty() && !contactInfo.isEmpty() && hireDate != null && selectedBranch != null) {
            int tenantId = getBranchId(selectedBranch); // Method to get branch ID based on name

            StaffRecord newStaff = new StaffRecord();
            newStaff.setName(firstName);
            newStaff.setSurName(surname);
            newStaff.setRole(role);
            newStaff.setContactInfo(contactInfo); // Ensure this property exists
            newStaff.setHireDate(Date.valueOf(hireDate)); // Convert to SQL Date
            newStaff.setBranchId(tenantId); // Set branch ID

            try {
                staffDAO.addStaffRecord(newStaff); // Add staff to database
                loadStaffData(staffDAO); // Reload staff data to refresh the table
            } catch (SQLException e) {
                e.printStackTrace(); // Handle database exceptions
            }
        } else {
            // Show an alert if fields are empty
            showAlert("Please fill all fields.");
        }
    }

    @FXML
    public void addParentButton(ActionEvent actionEvent) {
        String firstName = parentFirstNameField.getText();
        String surname = parentSurnameField.getText();
        String contactInfo = parentContactField.getText();
        String address = parentAddressField.getText();

        if (!firstName.isEmpty() && !surname.isEmpty() && !contactInfo.isEmpty() && !address.isEmpty()) {
            ParentRecord newParent = new ParentRecord();
            newParent.setName(firstName);
            newParent.setSurName(surname);
            newParent.setContact(contactInfo);
            newParent.setAddress(address);

            try {
                parentDAO.addParentRecord(newParent); // Add parent to database
                loadParentData(parentDAO); // Reload parent data to refresh the table
            } catch (SQLException e) {
                e.printStackTrace(); // Handle database exceptions
            }
        } else {
            // Show an alert if fields are empty
            showAlert("Please fill all fields.");
        }
    }

    @FXML
    public void addChildButton(ActionEvent actionEvent) throws SQLException {
        String firstName = childFirstNameField.getText();
        String surname = childSurnameField.getText();
        String selectedBranch = childBranchComboBox.getValue();

        if (!firstName.isEmpty() && !surname.isEmpty() && selectedBranch != null) {
            int tenantId = getBranchId(selectedBranch); // Method to get branch ID based on name

            ChildRecord newChild = new ChildRecord();
            newChild.setName(firstName);
            newChild.setSurName(surname);
            newChild.setBranchId(tenantId); // Set branch ID

            try {
                childRecordDAO.addChildRecord(newChild); // Add child to database
                loadChildData(childRecordDAO); // Reload child data to refresh the table
            } catch (SQLException e) {
                e.printStackTrace(); // Handle database exceptions
            }
        } else {
            // Show an alert if fields are empty
            showAlert("Please fill all fields.");
        }
    }

    private int getBranchId(String branchName) throws SQLException {
        return branchDAO.getBranchIdByName(branchName); // Method to get branch ID based on name
    }

    // Utility method to show alert
    private void showAlert(String message) {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle("Input Validation");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    @FXML
    private PieChart enrollmentPieChart;

    private void loadEnrollmentData() {
        String query = "SELECT tenant_id, COUNT(child_id) AS children_count FROM children GROUP BY tenant_id";

        try (Connection connection = getConnection("jdbc:mysql://127.0.0.1:3306/littlestepsdaycare", "root", "password");
             PreparedStatement pstmt = connection.prepareStatement(query);
             ResultSet rs = pstmt.executeQuery()) {

            enrollmentPieChart.getData().clear(); // Clear existing data

            while (rs.next()) {
                int branchId = rs.getInt("tenant_id");
                int childrenCount = rs.getInt("children_count");
                String branchName = getBranchNameById(branchId); // Method to get branch name by ID

                System.out.println("Branch ID: " + branchId + ", Children Count: " + childrenCount + ", Branch Name: " + branchName);

                // Create a PieChart.Data object and add it to the pie chart
                PieChart.Data slice = new PieChart.Data(branchName, childrenCount);
                enrollmentPieChart.getData().add(slice);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Method to get branch name by tenant ID (you need to implement this)
    private String getBranchNameById(int tenantId) {
        String query = "SELECT name FROM tenants WHERE tenant_id = ?";

        try (Connection connection = getConnection("jdbc:mysql://127.0.0.1:3306/littlestepsdaycare", "root", "password");
             PreparedStatement pstmt = connection.prepareStatement(query)) {

            pstmt.setInt(1, tenantId);
            try (ResultSet rs = pstmt.executeQuery()) {

                System.out.println("Getting name for Branch ID: " + tenantId);
                if (rs.next()) {
                    return rs.getString("name");

                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return "Unknown Branch"; // Default value if not found
    }


    private void loadAttendanceData() {
        String query = "SELECT DATE(date) AS date, " +
                "SUM(CASE WHEN status = 'Present' THEN 1 ELSE 0 END) AS present_count, " +
                "SUM(CASE WHEN status = 'Absent' THEN 1 ELSE 0 END) AS absent_count " +
                "FROM attendance GROUP BY DATE(date)";

        try (Connection connection = getConnection("jdbc:mysql://127.0.0.1:3306/littlestepsdaycare", "root", "password");
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
    private BarChart<String, Number> billingBarChart;

    private void loadBillingData() {
        String query = "SELECT payment_status, SUM(total_amount) AS total_billed FROM invoice GROUP BY payment_status";

        try (Connection connection = getConnection("jdbc:mysql://127.0.0.1:3306/littlestepsdaycare", "root", "password");
             PreparedStatement pstmt = connection.prepareStatement(query);
             ResultSet rs = pstmt.executeQuery()) {

            billingBarChart.getData().clear(); // Clear existing data

            XYChart.Series<String, Number> series = new XYChart.Series<>();
            while (rs.next()) {
                String paymentStatus = rs.getString("payment_status");
                double totalBilled = rs.getDouble("total_billed");

                // Add the data to the series
                series.getData().add(new XYChart.Data<>(paymentStatus, totalBilled));
            }

            billingBarChart.getData().add(series); // Add the series to the bar chart

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    @FXML
    public void handleDaycareClick(ActionEvent actionEvent) {
        adminTabPane.getSelectionModel().select(daycareTab);
    }
    @FXML
    public void handleStaffClick(ActionEvent actionEvent) {
        adminTabPane.getSelectionModel().select(staffTab);
    }

    @FXML
    public void handleParentsClick(ActionEvent actionEvent) {
        adminTabPane.getSelectionModel().select(parentsTab);
    }

    @FXML
    public void handleChildrenClick(ActionEvent actionEvent) {
    }

    @FXML
    public void handleRecordsClick(ActionEvent actionEvent) {
        adminTabPane.getSelectionModel().select(healthTab);
    }

    // Scene switching methods
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
    public void handleEventsbtn(ActionEvent actionEvent) {
        switchScene("EventNotification.fxml", "Event and Notification Management");
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


    public void shareEmail(ActionEvent actionEvent) {
    }

    public void billingCheckbox(ActionEvent actionEvent) {
    }

    public void staffCheckbox(ActionEvent actionEvent) {
    }

    public void attCheckbox(ActionEvent actionEvent) {
    }

    public void healthCheckbox(ActionEvent actionEvent) {
    }

    public void deleteRecordsButton(ActionEvent actionEvent) {
    }

    public class DownloadHandler {

        // Buttons for downloading
        Button downloadTxt = new Button("Download Text");
        Button downloadCsv = new Button("Download CSV");

        public DownloadHandler() {
            // Set action listeners for buttons
            downloadTxt.setOnAction(event -> handleDownload("txt"));
            downloadCsv.setOnAction(event -> handleDownload("csv"));
        }

        private void handleDownload(String format) {
            FileChooser fileChooser = new FileChooser();
            fileChooser.setTitle("Save File");
            fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter(format.toUpperCase() + " Files", "*." + format));
            File file = fileChooser.showSaveDialog(null);

            if (file != null) {
                try (FileWriter writer = new FileWriter(file)) {
                    if (billingCheckbox.isSelected()) {
                        if (format.equals("txt")) {
                            downloadBillingData(writer, "txt");
                        } else {
                            downloadBillingData(writer, "csv");
                        }
                    }
                    if (staffCheckbox.isSelected()) {
                        if (format.equals("txt")) {
                            downloadStaffData(writer, "txt");
                        } else {
                            downloadStaffData(writer, "csv");
                        }
                    }
                    if (attCheckbox.isSelected()) {
                        if (format.equals("txt")) {
                            downloadAttendanceData(writer, "txt");
                        } else {
                            downloadAttendanceData(writer, "csv");
                        }
                    }
                    if (healthCheckbox.isSelected()) {
                        if (format.equals("txt")) {
                            downloadHealthData(writer, "txt");
                        } else {
                            downloadHealthData(writer, "csv");
                        }
                    }
                } catch (IOException e) {
                    e.printStackTrace(); // Handle exceptions
                }
            }
        }

        private void downloadBillingData(FileWriter writer, String format) throws IOException {
            if (format.equals("txt")) {
                writer.append("Billing Data\nService, Amount, Due Date\n");
            } else {
                writer.append("Service, Amount Due, Discount, Late Fee, Total Amount, Issue Date, Due Date, Payment Status, Payment Method, Payment Date\n");
            }

            String query = "SELECT service, amount, due_date FROM billing"; // SQL query to fetch billing data
            try (Connection connection = getConnection("jdbc:mysql://127.0.0.1:3306/littlestepsdaycare", "root", "password");
                 PreparedStatement pstmt = connection.prepareStatement(query);
                 ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    if (format.equals("txt")) {
                        writer.append(rs.getString("service")).append(", ")
                                .append(rs.getString("amount")).append(", ")
                                .append(rs.getString("due_date")).append("\n");
                    } else {
                        // Additional fields for CSV
                        writer.append(rs.getString("service")).append(", ")
                                .append(String.valueOf(rs.getDouble("amount"))).append(", ")
                                .append("0, 0, 0, 0, 0, 0, 0, 0\n"); // Placeholder for extra fields
                    }
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        private void downloadStaffData(FileWriter writer, String format) throws IOException {
            if (format.equals("txt")) {
                writer.append("Staff Data\nID, Name, Role\n");
            } else {
                writer.append("Staff ID, Name, Role, Contact, Branch, Date of Hire\n");
            }

            String query = "SELECT staff_id, first_name, last_name, role, contact_info, tenant_id, hire_date FROM staff";
            try (Connection connection = getConnection("jdbc:mysql://127.0.0.1:3306/littlestepsdaycare", "root", "password");
                 PreparedStatement pstmt = connection.prepareStatement(query);
                 ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    if (format.equals("txt")) {
                        writer.append(rs.getString("staff_id")).append(", ")
                                .append(rs.getString("first_name")).append(" ")
                                .append(rs.getString("last_name")).append(", ")
                                .append(rs.getString("role")).append("\n");
                    } else {
                        writer.append(String.valueOf(rs.getInt("staff_id"))).append(", ")
                                .append(rs.getString("first_name")).append(" ")
                                .append(rs.getString("last_name")).append(", ")
                                .append(rs.getString("role")).append(", ")
                                .append(rs.getString("contact_info")).append(", ")
                                .append(getBranchNameById(rs.getInt("tenant_id"))).append(", ")
                                .append(rs.getDate("hire_date").toString()).append("\n");
                    }
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        private void downloadAttendanceData(FileWriter writer, String format) throws IOException {
            if (format.equals("txt")) {
                writer.append("Attendance Data\nChild Name, Date, Status\n");
            } else {
                writer.append("Child Name, Date, Status\n");
            }

            String query = "SELECT child_id, date, status FROM attendance";
            try (Connection connection = getConnection("jdbc:mysql://127.0.0.1:3306/littlestepsdaycare", "root", "password");
                 PreparedStatement pstmt = connection.prepareStatement(query);
                 ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    if (format.equals("txt")) {
                        writer.append(getChildName(rs.getInt("child_id"))).append(", ")
                                .append(rs.getDate("date").toString()).append(", ")
                                .append(rs.getString("status")).append("\n");
                    } else {
                        writer.append(getChildName(rs.getInt("child_id"))).append(", ")
                                .append(rs.getDate("date").toString()).append(", ")
                                .append(rs.getString("status")).append("\n");
                    }
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        private void downloadHealthData(FileWriter writer, String format) throws IOException {
            if (format.equals("txt")) {
                writer.append("Health Records Data\nChild Name, Health Issue, Date\n");
            } else {
                writer.append("Child Name, Health Issue, Incident, Date\n");
            }

            String query = "SELECT child_id, health_issue, report_date FROM health_records";
            try (Connection connection = getConnection("jdbc:mysql://127.0.0.1:3306/littlestepsdaycare", "root", "password");
                 PreparedStatement pstmt = connection.prepareStatement(query);
                 ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    if (format.equals("txt")) {
                        writer.append(getChildName(rs.getInt("child_id"))).append(", ")
                                .append(rs.getString("health_issue")).append(", ")
                                .append(rs.getDate("report_date").toString()).append("\n");
                    } else {
                        writer.append(getChildName(rs.getInt("child_id"))).append(", ")
                                .append(rs.getString("health_issue")).append(", ")
                                .append("Incident Placeholder, ").append(rs.getDate("report_date").toString()).append("\n");
                    }
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        // Helper methods
        private String getBranchNameById(int tenantId) {
            String query = "SELECT name FROM tenants WHERE tenant_id = ?";
            try (Connection connection = getConnection("jdbc:mysql://127.0.0.1:3306/littlestepsdaycare", "root", "password");
                 PreparedStatement pstmt = connection.prepareStatement(query)) {
                pstmt.setInt(1, tenantId);
                try (ResultSet rs = pstmt.executeQuery()) {
                    if (rs.next()) {
                        return rs.getString("name");
                    }
                }
            } catch (SQLException e) {
                e.printStackTrace(); // Ideally, use a logging framework here
            }
            return "Unknown Branch";
        }

        private String getChildName(int childId) {
            String query = "SELECT first_name, last_name FROM children WHERE child_id = ?";

            try (Connection connection = getConnection("jdbc:mysql://127.0.0.1:3306/littlestepsdaycare", "root", "password");
                 PreparedStatement pstmt = connection.prepareStatement(query)) {
                pstmt.setInt(1, childId);
                try (ResultSet rs = pstmt.executeQuery()) {
                    if (rs.next()) {
                        return rs.getString("first_name") + " " + rs.getString("last_name");
                    }
                }
            } catch (SQLException e) {
                e.printStackTrace(); // Consider using a logging framework instead
            }

            return "Unknown Child";
        }

        private Connection getConnection(String url, String user, String password) {
            // Implement the logic for establishing a database connection
            return null; // Placeholder
        }
    }

    // Method to download selected data as TXT
    @FXML
    public void downloadTxt(ActionEvent actionEvent) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Save TXT File");
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("TXT Files", "*.txt"));
        File file = fileChooser.showSaveDialog(null);

        if (file != null) {
            try (FileWriter writer = new FileWriter(file)) {
                if (billingCheckbox.isSelected()) {
                    downloadBillingTxt(writer);
                }
                if (staffCheckbox.isSelected()) {
                    downloadStaffTxt(writer);
                }
                if (attCheckbox.isSelected()) {
                    downloadAttendanceTxt(writer);
                }
                if (healthCheckbox.isSelected()) {
                    downloadHealthTxt(writer);
                }
            } catch (IOException e) {
                e.printStackTrace(); // Handle exceptions
            }
        }
    }

    // Method to download selected data as CSV
    @FXML
    public void downloadCsv(ActionEvent actionEvent) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Save CSV File");
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("CSV Files", "*.csv"));
        File file = fileChooser.showSaveDialog(null);

        if (file != null) {
            try (FileWriter writer = new FileWriter(file)) {
                if (billingCheckbox.isSelected()) {
                    downloadBillingDataCsv(writer);
                }
                if (staffCheckbox.isSelected()) {
                    downloadStaffDataCsv(writer);
                }
                if (attCheckbox.isSelected()) {
                    downloadAttendanceDataCsv(writer);
                }
                if (healthCheckbox.isSelected()) {
                    downloadHealthDataCsv(writer);
                }
            } catch (IOException e) {
                e.printStackTrace(); // Handle exceptions
            }
        }
    }



    // Helper method to get child name
    private String getChildName(int childId) {
        String query = "SELECT first_name, last_name FROM children WHERE child_id = ?";
        try (Connection connection = getConnection("jdbc:mysql://127.0.0.1:3306/littlestepsdaycare", "root", "password");
             PreparedStatement pstmt = connection.prepareStatement(query)) {
            pstmt.setInt(1, childId);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                return rs.getString("first_name") + " " + rs.getString("last_name");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return "Unknown Child";
    }

    private void downloadBillingDataCsv(FileWriter writer) throws IOException {
        writer.append("Service, Amount Due, Discount, Late Fee, Total Amount, Issue Date, Due Date, Payment Status, Payment Method, Payment Date\n");
        String query = "SELECT service_description, amount_due, discount, late_fee, total_amount, issue_date, due_date, payment_status, payment_method, payment_date FROM billing"; // Adjust the query as needed

        try (Connection connection = getConnection("jdbc:mysql://127.0.0.1:3306/littlestepsdaycare", "root", "password");
             PreparedStatement pstmt = connection.prepareStatement(query);
             ResultSet rs = pstmt.executeQuery()) {
            while (rs.next()) {
                writer.append(rs.getString("service_description")).append(", ")
                        .append(String.valueOf(rs.getDouble("amount_due"))).append(", ")
                        .append(String.valueOf(rs.getDouble("discount"))).append(", ")
                        .append(String.valueOf(rs.getDouble("late_fee"))).append(", ")
                        .append(String.valueOf(rs.getDouble("total_amount"))).append(", ")
                        .append(rs.getString("issue_date")).append(", ")
                        .append(rs.getString("due_date")).append(", ")
                        .append(rs.getString("payment_status")).append(", ")
                        .append(rs.getString("payment_method")).append(", ")
                        .append(rs.getString("payment_date")).append("\n");
            }
        } catch (SQLException e) {
            System.out.println("Error fetching billing data: " + e.getMessage());
        }
    }

    private void downloadStaffDataCsv(FileWriter writer) throws IOException {
        writer.append("Staff ID, Name, Role, Contact, Branch, Date of Hire\n");
        String query = "SELECT staff_id, first_name, last_name, role, contact_info, tenant_id, hire_date FROM staff";

        try (Connection connection = getConnection("jdbc:mysql://127.0.0.1:3306/littlestepsdaycare", "root", "password");
             PreparedStatement pstmt = connection.prepareStatement(query);
             ResultSet rs = pstmt.executeQuery()) {
            while (rs.next()) {
                writer.append(String.valueOf(rs.getInt("staff_id"))).append(", ")
                        .append(rs.getString("first_name")).append(" ")
                        .append(rs.getString("last_name")).append(", ")
                        .append(rs.getString("role")).append(", ")
                        .append(rs.getString("contact_info")).append(", ")
                        .append(getBranchNameById(rs.getInt("tenant_id"))).append(", ")
                        .append(rs.getDate("hire_date").toString()).append("\n");
            }
        } catch (SQLException e) {
            System.out.println("Error fetching staff data: " + e.getMessage());
        }
    }

    private void downloadAttendanceDataCsv(FileWriter writer) throws IOException {
        writer.append("Child Name, Date, Status\n");
        String query = "SELECT child_id, date, status FROM attendance";

        try (Connection connection = getConnection("jdbc:mysql://127.0.0.1:3306/littlestepsdaycare", "root", "password");
             PreparedStatement pstmt = connection.prepareStatement(query);
             ResultSet rs = pstmt.executeQuery()) {
            while (rs.next()) {
                writer.append(getChildName(rs.getInt("child_id"))).append(", ")
                        .append(rs.getDate("date").toString()).append(", ")
                        .append(rs.getString("status")).append("\n");
            }
        } catch (SQLException e) {
            System.out.println("Error fetching attendance data: " + e.getMessage());
        }
    }

    private void downloadHealthDataCsv(FileWriter writer) throws IOException {
        writer.append("Child Name, Allergies, Incident, Incident Date, Health Provider\n");
        String query = "SELECT c.first_name, c.last_name, h.allergy, h.incident, h.report_date, h.health_provider " +
                "FROM health_records h JOIN children c ON c.child_id = h.child_id";

        try (Connection connection = getConnection("jdbc:mysql://127.0.0.1:3306/littlestepsdaycare", "root", "password");
             PreparedStatement pstmt = connection.prepareStatement(query);
             ResultSet rs = pstmt.executeQuery()) {
            while (rs.next()) {
                writer.append(rs.getString("first_name")).append(" ")
                        .append(rs.getString("last_name")).append(", ")
                        .append(rs.getString("allergy")).append(", ")
                        .append(rs.getString("incident")).append(", ")
                        .append(rs.getDate("report_date").toString()).append(", ")
                        .append(rs.getString("health_provider")).append("\n");
            }
        } catch (SQLException e) {
            System.out.println("Error fetching health data: " + e.getMessage());
        }
    }

    public void downloadStaffTxt(FileWriter writer) {
        try {
            FileChooser fileChooser = new FileChooser();
            fileChooser.setTitle("Save Staff TXT");
            fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("TXT Files", "*.txt"));
            File file = fileChooser.showSaveDialog(null);

            if (file != null) {
                writer.append("LittleSteps Daycare System\n");
                writer.append("Branch: [Branch Name]\n");
                writer.append("Date: " + LocalDate.now() + "\n\n");
                writer.append("Staff ID, Name, Role, Contact, Branch, Date of Hire\n");

                String query = "SELECT staff_id, first_name, last_name, role, contact_info, tenant_id, hire_date FROM staff";
                try (Connection connection = getConnection("jdbc:mysql://127.0.0.1:3306/littlestepsdaycare", "root", "password");
                     PreparedStatement pstmt = connection.prepareStatement(query);
                     ResultSet rs = pstmt.executeQuery()) {
                    while (rs.next()) {
                        writer.append(rs.getInt("staff_id") + ", ")
                                .append(rs.getString("first_name") + " " + rs.getString("last_name") + ", ")
                                .append(rs.getString("role") + ", ")
                                .append(rs.getString("contact_info") + ", ")
                                .append(getBranchNameById(rs.getInt("tenant_id")) + ", ")
                                .append(rs.getDate("hire_date").toString() + "\n");
                    }
                }
                writer.flush();
                writer.close();
            }
        } catch (IOException | SQLException e) {
            e.printStackTrace(); // Handle exceptions
        }
    }

    public void downloadAttendanceTxt(FileWriter writer) {
        try {
            FileChooser fileChooser = new FileChooser();
            fileChooser.setTitle("Save Attendance TXT");
            fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("TXT Files", "*.txt"));
            File file = fileChooser.showSaveDialog(null);

            if (file != null) {
                writer.append("LittleSteps Daycare System\n");
                writer.append("Branch: [Branch Name]\n");
                writer.append("Date: " + LocalDate.now() + "\n\n");
                writer.append("Child Name, Date, Status\n");

                String query = "SELECT child_id, date, status FROM attendance";
                try (Connection connection = getConnection("jdbc:mysql://127.0.0.1:3306/littlestepsdaycare", "root", "password");
                     PreparedStatement pstmt = connection.prepareStatement(query);
                     ResultSet rs = pstmt.executeQuery()) {
                    while (rs.next()) {
                        writer.append(getChildName(rs.getInt("child_id")) + ", ")
                                .append(rs.getDate("date").toString() + ", ")
                                .append(rs.getString("status") + "\n");
                    }
                }
                writer.flush();
                writer.close();
            }
        } catch (IOException | SQLException e) {
            e.printStackTrace(); // Handle exceptions
        }
    }

    public void downloadBillingTxt(FileWriter writer) {
        try {
            FileChooser fileChooser = new FileChooser();
            fileChooser.setTitle("Save Parents TXT");
            fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("TXT Files", "*.txt"));
            File file = fileChooser.showSaveDialog(null);

            if (file != null) {
                writer.append("LittleSteps Daycare System\n");
                writer.append("Branch: [Branch Name]\n");
                writer.append("Date: " + LocalDate.now() + "\n\n");
                writer.append("Parent Name, Child Name, Contact, Emergency Contact, Branch\n");

                String query = "SELECT p.first_name AS parent_first_name, p.last_name AS parent_last_name, " +
                        "c.first_name AS child_first_name, c.last_name AS child_last_name, " +
                        "p.contact_info, p.emergency_contact, c.tenant_id " +
                        "FROM parents p JOIN child_parent cp ON p.parent_id = cp.parent_id " +
                        "JOIN children c ON c.child_id = cp.child_id";
                try (Connection connection = getConnection("jdbc:mysql://127.0.0.1:3306/littlestepsdaycare", "root", "password");
                     PreparedStatement pstmt = connection.prepareStatement(query);
                     ResultSet rs = pstmt.executeQuery()) {
                    while (rs.next()) {
                        writer.append(rs.getString("parent_first_name") + " " + rs.getString("parent_last_name") + ", ")
                                .append(rs.getString("child_first_name") + " " + rs.getString("child_last_name") + ", ")
                                .append(rs.getString("contact_info") + ", ")
                                .append(rs.getString("emergency_contact") + ", ")
                                .append(getBranchNameById(rs.getInt("tenant_id")) + "\n");
                    }
                }
                writer.flush();
                writer.close();
            }
        } catch (IOException | SQLException e) {
            e.printStackTrace(); // Handle exceptions
        }
    }

    public void downloadHealthTxt(FileWriter writer) {
        try {
            FileChooser fileChooser = new FileChooser();
            fileChooser.setTitle("Save Health Records TXT");
            fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("TXT Files", "*.txt"));
            File file = fileChooser.showSaveDialog(null);

            if (file != null) {
                writer.append("LittleSteps Daycare System\n");
                writer.append("Branch: [Branch Name]\n");
                writer.append("Date: " + LocalDate.now() + "\n\n");
                writer.append("Child Name, Allergies, Incident, Incident Date, Health Provider\n");

                String query = "SELECT c.first_name, c.last_name, h.allergy, h.incident, h.report_date, h.health_provider " +
                        "FROM health_records h JOIN children c ON c.child_id = h.child_id";
                try (Connection connection = getConnection("jdbc:mysql://127.0.0.1:3306/littlestepsdaycare", "root", "password");
                     PreparedStatement pstmt = connection.prepareStatement(query);
                     ResultSet rs = pstmt.executeQuery()) {
                    while (rs.next()) {
                        writer.append(rs.getString("first_name") + " " + rs.getString("last_name") + ", ")
                                .append(rs.getString("allergy") + ", ")
                                .append(rs.getString("incident") + ", ")
                                .append(rs.getDate("report_date").toString() + ", ")
                                .append(rs.getString("health_provider") + "\n");
                    }
                }
                writer.flush();
                writer.close();
            }
        } catch (IOException | SQLException e) {
            e.printStackTrace(); // Handle exceptions
        }
    }

    public void billingCheckbox(FileWriter writer) throws IOException {
        if (billingCheckbox.isSelected()) {
            downloadBillingDataCsv(writer);
        }
        else {
            downloadBillingTxt(writer);
        }
    }

    public void staffCheckbox(FileWriter writer) throws IOException {
        if (staffCheckbox.isSelected()) {
            downloadStaffDataCsv(writer);
        }
        else {
            downloadStaffTxt(writer);
        }
    }

    public void attCheckbox(FileWriter writer) throws IOException {
        if (attCheckbox.isSelected()) {
            downloadAttendanceDataCsv(writer);
        }
        else {
            downloadAttendanceTxt(writer);
        }
    }

    public void healthCheckbox(FileWriter writer) throws IOException {
        if (healthCheckbox.isSelected()) {
            downloadHealthDataCsv(writer);
        }
        else {
            downloadHealthTxt(writer);
        }
    }
    
}