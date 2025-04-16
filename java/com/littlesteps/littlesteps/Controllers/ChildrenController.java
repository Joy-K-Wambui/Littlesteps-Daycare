package com.littlesteps.littlesteps.Controllers;

import java.io.*;
import java.net.URLDecoder;
import java.sql.*;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Arrays;

import com.littlesteps.littlesteps.Records.AttendanceRecord;
import com.littlesteps.littlesteps.Records.Child;
import com.littlesteps.littlesteps.Dao.ChildDAO;
import com.littlesteps.littlesteps.Records.Parents;
import com.littlesteps.littlesteps.Dao.ParentDAO;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.event.ActionEvent;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import static javafx.scene.image.Image.*;

public class ChildrenController {
    private Connection connection;


    // ObservableList to store attendance data
    private ObservableList<AttendanceRecord> attendanceData = FXCollections.observableArrayList();
    private final ObservableList<String> foodPreferences = FXCollections.observableArrayList();
    private final ObservableList<String> emergencyContacts = FXCollections.observableArrayList();

    @FXML private TableView attendanceTable;
    @FXML private TableColumn<AttendanceRecord, String> AIdClmn;
    @FXML private TableColumn<AttendanceRecord, String> cNameClmn;
    @FXML private TableColumn<AttendanceRecord, String> statusClmn;
    @FXML private TableColumn<AttendanceRecord, String> dateClmn;
    @FXML private TableColumn<AttendanceRecord, String> branchClmn;
    @FXML private Label attendanceMessage; // Add this to your FXML file for displaying messages

    @FXML private Button handleLogAttendance;
    @FXML private TextField nameField;
    @FXML private DatePicker dobPicker;
    @FXML private TextField emergencyContactField;
    @FXML private TextField branchField;
    @FXML private TextField allergiesField;
    @FXML private TextField genderField;
    @FXML private TextField fPrefField;
    @FXML private DatePicker enrolPicker;
    @FXML private TextArea healthConditionsArea;
    @FXML private TextField healthConditionsAreaUpdate;
    @FXML private ImageView childImageView;
    @FXML private TextField newFoodPreference;
    @FXML private ListView foodPreferencesList;
    @FXML private ListView emergencyContactsList;
    @FXML private ListView allergyNoteslist;
    @FXML private TextField emergencyContactRelation;
    @FXML private TextField emergencyContactPhone;
    @FXML private TextField emergencyContactName;
    @FXML private TextField newAllergy;
    @FXML private TextField allergyreaction;
    @FXML private TextField notes;
    @FXML private Button backToDashboard;
    @FXML private TextField parentNames;
    @FXML private TextField parentNumber;
    @FXML private TextField parentAddress;
    @FXML private TextField parentEmail;

    @FXML private ComboBox loadChildDetails;
    @FXML
    private ComboBox<String> bloodTypeComboBox; // ComboBox for blood types

    // ObservableList for Blood Types
    private final ObservableList<String> bloodTypes = FXCollections.observableArrayList(
            "A+", "A-", "B+", "B-", "AB+", "AB-", "O+", "O-"
    );
    @FXML private void backToDashboard() {
        try {
            // Assuming you have a SceneSwitcher utility or a way to switch scenes
            FXMLLoader loader = new FXMLLoader(getClass().getResource("dashboard.fxml"));
            Parent root = loader.load();

            Scene scene = new Scene(root);
            Stage stage = (Stage) backToDashboard.getScene().getWindow(); // Get the current window (stage)
            stage.setScene(scene); // Set the new scene (dashboard)
            stage.show(); // Show the new scene
        } catch (IOException e) {
            e.printStackTrace(); // Handle exceptions if the dashboard fails to load
        }
    }


    // Initialize connection to the database
    private void connectToDatabase() {
        try {
            connection = DriverManager.getConnection(
                    "jdbc:mysql://127.0.0.1:3306/littlestepsdaycare", "root", "password");
            System.out.println("Database connection successful.");
        } catch (SQLException e) {
            System.out.println("Database connection failed: " + e.getMessage());
        }
    }

    // Initialize method to set up TableView
    @FXML
    public void initialize() {
        // Initialize table columns
        AIdClmn.setCellValueFactory(cellData -> cellData.getValue().getAttIdProperty().asObject().asString());
        cNameClmn.setCellValueFactory(cellData -> cellData.getValue().getCNameProperty().asObject().asString());
        statusClmn.setCellValueFactory(cellData -> cellData.getValue().getStateProperty());
        dateClmn.setCellValueFactory(cellData -> cellData.getValue().getDateProperty());
        branchClmn.setCellValueFactory(cellData -> cellData.getValue().getTntIdProperty().asObject().asString());

        attendanceTable.getSelectionModel().setSelectionMode(SelectionMode.SINGLE); // Allow single selection


        // Load data into ComboBoxes
        loadChildComboBox();
        populateChildDetails();
        bloodTypeComboBox.setItems(bloodTypes);

        // Load data from the database and populate the table
        try {
            connection = DriverManager.getConnection(
                    "jdbc:mysql://127.0.0.1:3306/littlestepsdaycare", "root", "password");
            System.out.println("Database connection successful.");

            attendanceData = getAllRecords();
            populateTable(attendanceData);
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
        loadChildComboBox();
        loadchildNameDropdown();
    }
    // Method to handle search functionality
    @FXML
    private ComboBox<String> childComboBox;
    @FXML
    private ComboBox<String> childNameDropdown;


    // Method to load child names into the combo box
    public void loadChildComboBox() {
        String query = "SELECT child_id, first_name, last_name FROM children";  // SQL query to fetch data

        try {
            // Check if the connection is open, if not, try connecting
            if (connection == null || connection.isClosed()) {
                connectToDatabase();  // Ensure connectToDatabase() is working properly
            }

            PreparedStatement preparedStatement = connection.prepareStatement(query);
            ResultSet resultSet = preparedStatement.executeQuery();

            // ObservableList to hold combo box items
            ObservableList<String> childrenList = FXCollections.observableArrayList();

            // Add results from database to the list
            while (resultSet.next()) {
                String fullName = resultSet.getString("first_name") + " " + resultSet.getString("last_name");
                childrenList.add(fullName);  // Add the full name to the list
            }

            // Set items in the ComboBox
            childComboBox.setItems(childrenList);


            // Optional: You can set a default value
            if (!childrenList.isEmpty()) {
                childComboBox.getSelectionModel().selectFirst();  // Select the first item by default
            }

            // Set up listener for child selection changes
            childComboBox.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
                if (newValue != null) {
                    int childId = getChildIdByName(newValue); // Retrieves the ID of the selected child
                    LoadChildDetails(childId); // Updates details pane for the selected child
                }
            });

            // Close the result set and prepared statement
            resultSet.close();
            preparedStatement.close();

        } catch (SQLException e) {
            // Handle any SQL exceptions
            System.out.println("Error loading combo box: " + e.getMessage());
        }
    }


    // Method to retrieve child ID from a selected full name
    private int getChildIdByName(String childName) {
        int childId = -1; // Default value if not found

        String query = "SELECT child_id FROM children WHERE CONCAT(first_name, ' ', last_name) = ?";

        try {
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, childName); // Assuming childName is in the format "first_name last_name"
            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                childId = resultSet.getInt("child_id"); // Get the child_id from the result set
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return childId; // Return the childId, or -1 if not found
    }


    // Load child details into the respective text fields
    private void LoadChildDetails(int childId) {
        String childQuery = "SELECT first_name, last_name, date_of_birth, emergency_contact, gender, allergies, " +
                "enrollment_date, food_pref, photo FROM children WHERE child_id = ?";

        try {
            PreparedStatement preparedStatement = connection.prepareStatement(childQuery);
            preparedStatement.setInt(1, childId); // Set child_id parameter
            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                // Populate the UI with data from the result set
                String firstName = resultSet.getString("first_name");
                String lastName = resultSet.getString("last_name");
                Date dob = resultSet.getDate("date_of_birth");
                String emergencyContact = resultSet.getString("emergency_contact");
                String gender = resultSet.getString("gender");
                String allergies = resultSet.getString("allergies");
                Date enrollmentDate = resultSet.getDate("enrollment_date");
                String foodPref = resultSet.getString("food_pref");
                Blob photoBlob = resultSet.getBlob("photo");

                // Set the data to the respective fields
                nameField.setText(firstName + " " + lastName);
                dobPicker.setValue(dob.toLocalDate());
                emergencyContactField.setText(emergencyContact);
                genderField.setText(gender);
                allergiesField.setText(allergies);
                enrolPicker.setValue(enrollmentDate.toLocalDate());
                fPrefField.setText(foodPref);

                loadParentDetails(childId);

                // Convert the Blob to an Image and set it to the ImageView
                if (photoBlob != null) {
                    byte[] imageBytes = photoBlob.getBytes(1, (int) photoBlob.length());
                    Image image = new Image(new ByteArrayInputStream(imageBytes));
                    childImageView.setImage(image);
                } else {
                    // Optionally handle the case where there is no image
                    childImageView.setImage(null); // Clear the image if none is found
                }
            } else {
                System.out.println("No child found with ID: " + childId);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    ObservableList<String> childName = FXCollections.observableArrayList();
    private void populateChildDetails() {
        String query = "SELECT CONCAT(first_name, ' ', last_name) AS full_name, gender, date_of_birth, emergency_contact, allergies, enrollment_date, food_pref, tenant_id, photo FROM children";


        try {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(query);

            String gender = null;
            Date dob = null;
            String emergencyContact = null;
            String allergies = null;
            Date enrolDate = null;
            String foodPref = null;
            String branch = null;
            while (resultSet.next()) {
                String fullName = resultSet.getString("full_name");
                if (fullName.equals(childComboBox.getSelectionModel().getSelectedItem())) {
                    nameField.setText(fullName);
                }
                childName.add(fullName);
                gender = resultSet.getString("gender");
                dob = resultSet.getDate("date_of_birth");
                emergencyContact = resultSet.getString("emergency_contact");
                allergies = resultSet.getString("allergies");
                foodPref = resultSet.getString("food_Pref");
                enrolDate = resultSet.getDate("enrollment_date");
                int branchField = resultSet.getInt("tenant_id");
                branch = getBranchName(branchField);
            }

            nameField.setText(String.valueOf(childName)); // Set the ComboBox items to the list
            genderField.setText(gender);
            dobPicker.setValue(dob.toLocalDate());
            emergencyContactField.setText(emergencyContact);
            allergiesField.setText(allergies);
            enrolPicker.setValue(enrolDate.toLocalDate());
            fPrefField.setText(foodPref);
            branchField.setText(branch);


        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private String getBranchName(int tenant_Id) {
        String branch = "";
        String query = "SELECT name FROM tenants WHERE tenant_id = ?";
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, String.valueOf(tenant_Id));
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                branch = resultSet.getString("tenant_id");
            }
            resultSet.close();
            preparedStatement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return "LittleSteps Valley";
    }


    // Load parent details into the respective text fields
    private void loadParentDetails(int childId) {
        String parentQuery = "SELECT p.first_name, p.last_name, p.phone_number, p.email, p.address " +
                "FROM parents p INNER JOIN child_parent cp ON p.parent_id = cp.parent_id WHERE cp.child_id = ?";

        try {
            PreparedStatement preparedStatement = connection.prepareStatement(parentQuery);
            preparedStatement.setInt(1, childId); // Set child_id parameter
            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                // Populate parent fields with data from the result set
                String parentFirstName = resultSet.getString("first_name");
                String parentLastName = resultSet.getString("last_name");
                String parentPhone = resultSet.getString("phone_number");
                String parentEmailText = resultSet.getString("email");
                String parentsAddress = resultSet.getString("address");

                // Set values into the text fields
                parentNames.setText(parentFirstName + " " + parentLastName);
                parentNumber.setText(parentPhone);
                parentAddress.setText(parentsAddress);
                parentEmail.setText(parentEmailText);
            } else {
                // Clear parent fields if no parent found
                parentNames.clear();
                parentNumber.clear();
                parentAddress.clear();
                parentEmail.clear();
                System.out.println("No parent details found for child ID: " + childId);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Method to update child detail pane based on selected child
    private void updateChildDetailsPane(int childId) {
        // Code to fetch child details and set them to your text fields or labels for child info.
        // E.g., fullNameLabel.setText(fullName);
    }

    //Attendance table
    public ObservableList<AttendanceRecord> getAllRecords() throws ClassNotFoundException, SQLException {
        String sql = "SELECT attendance_id, child_id, status, date, tenant_id FROM attendance";
        ObservableList<AttendanceRecord> attendanceData = FXCollections.observableArrayList();

        try {
            if (connection == null || connection.isClosed()) {
                connectToDatabase(); // Ensure the database connection is established
            }
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            ResultSet resultSet = preparedStatement.executeQuery();

            // Populate the ObservableList with AttendanceRecord objects
            attendanceData = getAttendanceObjects(resultSet);

            resultSet.close();
            preparedStatement.close();
        } catch (SQLException e) {
            System.out.println("Error: " + e.getMessage());
            e.printStackTrace();
            throw e;
        }
        return attendanceData;
    }

    private ObservableList<AttendanceRecord> getAttendanceObjects(ResultSet resultSet) throws SQLException {
        ObservableList<AttendanceRecord> attendanceData = FXCollections.observableArrayList();

        while (resultSet.next()) {
            AttendanceRecord att = new AttendanceRecord();
            att.setAttId(resultSet.getInt("attendance_id"));
            att.setCName(resultSet.getInt("child_id")); // Ensure child name is being fetched correctly
            att.setState(resultSet.getString("status")); // No need to parse Integer
            att.setAttDate(resultSet.getDate("date").toString());
            att.setTntId(resultSet.getInt("tenant_id"));

            attendanceData.add(att);
        }
        return attendanceData;
    }

    private void populateTable(ObservableList<AttendanceRecord> attendanceData) {
        attendanceTable.setItems(attendanceData);
    }

    // Method to log attendance
    @FXML
    private void handleLogAttendance(ActionEvent event) {
        // Get the selected child's name from the childComboBox
        String selectedChildName = childComboBox.getSelectionModel().getSelectedItem();

        // Ensure a child is selected
        if (selectedChildName == null) {
            System.out.println("No child selected for attendance logging.");
            attendanceMessage.setText("No child selected for attendance logging.");
            return;
        }

        // Get the child ID associated with the selected child's name
        int childId = getChildIdByName(selectedChildName);
        if (childId == -1) {
            System.out.println("Child ID not found for the selected child.");
            attendanceMessage.setText("Child ID not found for the selected child.");
            return;
        }

        // Determine attendance status (e.g., check-in or check-out)
        String status = "present"; // Default to "present" for check-in

        // Get the current date for logging
        LocalDate currentDate = LocalDate.now();

        // Update attendance record in the database
        try (Connection conn = DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/littlestepsdaycare", "root", "password");
             PreparedStatement stmt = conn.prepareStatement(
                     "INSERT INTO attendance (child_id, status, date, tenant_id) VALUES (?, ?, ?, ?)")) {

            // Assuming tenant_id would be fetched from the child details pane or another relevant source
            int tenantId = getTenantIdByChildId(childId); // Implement this method to get the tenant ID

            // Set parameters for the prepared statement
            stmt.setInt(1, childId);
            stmt.setString(2, status);
            stmt.setDate(3, Date.valueOf(currentDate));
            stmt.setInt(4, tenantId); // Set the tenant ID

            // Execute the insert statement
            stmt.executeUpdate();
            System.out.println("Attendance logged successfully for child ID: " + childId);
            attendanceMessage.setText("Attendance logged successfully for " + selectedChildName + ".");

            // Refresh the attendance table
            ObservableList<AttendanceRecord> attendanceData = getAllRecords();
            populateTable(attendanceData);

        } catch (SQLException | ClassNotFoundException e) {
            System.out.println("Error logging attendance: " + e.getMessage());
            attendanceMessage.setText("Error logging attendance: " + e.getMessage());
            e.printStackTrace();
        }
    }

    // Helper method to get tenant ID associated with the child
    private int getTenantIdByChildId(int childId) {
        int tenantId = -1;
        String query = "SELECT tenant_id FROM children WHERE child_id = ?";

        try {
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setInt(1, childId);
            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                tenantId = resultSet.getInt("tenant_id");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return tenantId;
    }


    // Method to add a new child along with the parent details
    @FXML
    private void handleAddChild() throws UnsupportedEncodingException {
        String[] childNames = nameField.getText().split(" ");
        String firstName = childNames[0]; // Assuming first name is the first part
        String lastName = childNames.length > 1 ? childNames[1] : ""; // Default to empty if not provided

        String branch = branchField.getText();
        String gender = genderField.getText();
        String dob = dobPicker.getValue().toString();
        String enrollmentDate = enrolPicker.getValue().toString();
        String emergencyContact = emergencyContactField.getText();
        String allergies = allergiesField.getText();
        String healthConditions = healthConditionsArea.getText();

        // Convert the image from ImageView to byte array to store in the database
        byte[] photoBytes = null;
        Image image = childImageView.getImage();
        if (image != null) {
            // Convert Image to byte array
            String filePath = URLDecoder.decode(image.getUrl(), "UTF-8").replace("file:/", "");
            File file = new File(filePath);
            try (FileInputStream fis = new FileInputStream(file)) {
                photoBytes = new byte[(int) file.length()];
                fis.read(photoBytes);
            } catch (IOException e) {
                System.out.println("Error reading image file: " + e.getMessage());
            }
        }

        String childQuery = "INSERT INTO children (first_name, last_name, gender, date_of_birth, enrollment_date, emergency_contact, allergies, health_condition, photo) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";

        try {
            if (connection == null || connection.isClosed()) {
                connectToDatabase();
            }

            PreparedStatement preparedStatement = connection.prepareStatement(childQuery, Statement.RETURN_GENERATED_KEYS);
            preparedStatement.setString(1, firstName);
            preparedStatement.setString(2, lastName);
            preparedStatement.setString(3, gender);
            preparedStatement.setString(4, dob);
            preparedStatement.setString(5, enrollmentDate);
            preparedStatement.setString(6, emergencyContact);
            preparedStatement.setString(7, allergies);
            preparedStatement.setString(8, healthConditions);
            preparedStatement.setBytes(9, photoBytes); // Set the photo bytes as a BLOB

            preparedStatement.executeUpdate();

            // Retrieve the generated child_id
            ResultSet generatedKeys = preparedStatement.getGeneratedKeys();
            if (generatedKeys.next()) {
                int childId = generatedKeys.getInt(1);
                // Now add the parent details
                addParentDetails(childId); // Pass the generated child ID to the method
            }
            System.out.println("Child added successfully.");
            preparedStatement.close();
        } catch (SQLException e) {
            System.out.println("Error adding child: " + e.getMessage());
        }
    }

    // Method to add parent details and link to the child
    private void addParentDetails(int childId) {
        String parentFirstName = parentNames.getText().split(" ")[0]; // Assuming first name is the first part
        String parentLastName = parentNames.getText().split(" ").length > 1 ? parentNames.getText().split(" ")[1] : ""; // Default to empty if not provided
        String parentPhone = parentNumber.getText();
        String parentEmailText = parentEmail.getText();
        String parentAddressText = parentAddress.getText();

        // Insert into parent table
        String parentQuery = "INSERT INTO parents (first_name, last_name, email, phone_number, address) VALUES (?, ?, ?, ?, ?)";

        try {
            PreparedStatement preparedStatement = connection.prepareStatement(parentQuery, Statement.RETURN_GENERATED_KEYS);
            preparedStatement.setString(1, parentFirstName);
            preparedStatement.setString(2, parentLastName);
            preparedStatement.setString(3, parentEmailText);
            preparedStatement.setString(4, parentPhone);
            preparedStatement.setString(5, parentAddressText);

            preparedStatement.executeUpdate();

            // Retrieve the generated parent_id
            ResultSet generatedKeys = preparedStatement.getGeneratedKeys();
            if (generatedKeys.next()) {
                int parentId = generatedKeys.getInt(1);
                // Now link the child and parent in the child_parent table
                linkChildAndParent(childId, parentId, "mother"); // Adjust relationship as needed
            }
            preparedStatement.close();
        } catch (SQLException e) {
            System.out.println("Error adding parent: " + e.getMessage());
        }
    }

    // Method to link child and parent
    private void linkChildAndParent(int childId, int parentId, String relationship) {
        String linkQuery = "INSERT INTO child_parent (child_id, parent_id, relationship) VALUES (?, ?, ?)";
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(linkQuery);
            preparedStatement.setInt(1, childId);
            preparedStatement.setInt(2, parentId);
            preparedStatement.setString(3, relationship);

            preparedStatement.executeUpdate();
            System.out.println("Linked child ID " + childId + " with parent ID " + parentId);
            preparedStatement.close();
        } catch (SQLException e) {
            System.out.println("Error linking child and parent: " + e.getMessage());
        }
    }

    @FXML
    private void handleEditSelectedChild() {
        String selectedChildName = childComboBox.getSelectionModel().getSelectedItem();
        if (selectedChildName != null) {
            int childId = getChildIdByName(selectedChildName);
            String query = "SELECT * FROM children WHERE child_id = ?";

            try {
                if (connection == null || connection.isClosed()) {
                    connectToDatabase();
                }

                PreparedStatement preparedStatement = connection.prepareStatement(query);
                preparedStatement.setInt(1, childId);
                ResultSet resultSet = preparedStatement.executeQuery();

                if (resultSet.next()) {
                    // Populate child fields
                    nameField.setText(resultSet.getString("first_name") + " " + resultSet.getString("last_name"));
                    branchField.setText(String.valueOf(resultSet.getInt("tenant_id")));
                    genderField.setText(resultSet.getString("gender"));
                    dobPicker.setValue(resultSet.getDate("date_of_birth").toLocalDate());
                    enrolPicker.setValue(resultSet.getDate("enrollment_date").toLocalDate());
                    emergencyContactField.setText(resultSet.getString("emergency_contact"));
                    allergiesField.setText(resultSet.getString("allergies"));
                    healthConditionsArea.setText(resultSet.getString("health_condition"));

                    // Load the child's image
                    Blob photoBlob = resultSet.getBlob("photo");
                    if (photoBlob != null) {
                        byte[] imageBytes = photoBlob.getBytes(1, (int) photoBlob.length());
                        Image image = new Image(new ByteArrayInputStream(imageBytes));
                        childImageView.setImage(image);
                    } else {
                        childImageView.setImage(null); // Clear the image if none is found
                    }

                    // Load parent details
                    System.out.println("Child details loaded for editing.");
                } else {
                    System.out.println("Child not found.");
                }

                resultSet.close();
                preparedStatement.close();
            } catch (SQLException e) {
                System.out.println("Error loading child details: " + e.getMessage());
            }
        }
    }
    @FXML
    private void handleProfileUpdate() {
        String selectedChildName = childComboBox.getSelectionModel().getSelectedItem();
        int childId = getChildIdByName(selectedChildName); // Retrieve child ID based on selected child name

        // Start building the update query
        StringBuilder childQueryBuilder = new StringBuilder("UPDATE children SET ");
        boolean hasChildChanges = false;

        // Check each field and build the query dynamically for child
        if (!nameField.getText().isEmpty()) {
            String[] names = nameField.getText().split(" ");
            childQueryBuilder.append("first_name = ?, last_name = ?, ");
            hasChildChanges = true;
        }
        if (!branchField.getText().isEmpty()) {
            childQueryBuilder.append("tenant_id = ?, ");
            hasChildChanges = true;
        }
        if (!genderField.getText().isEmpty()) {
            childQueryBuilder.append("gender = ?, ");
            hasChildChanges = true;
        }
        if (dobPicker.getValue() != null) {
            childQueryBuilder.append("date_of_birth = ?, ");
            hasChildChanges = true;
        }
        if (enrolPicker.getValue() != null) {
            childQueryBuilder.append("enrollment_date = ?, ");
            hasChildChanges = true;
        }
        if (!emergencyContactField.getText().isEmpty()) {
            childQueryBuilder.append("emergency_contact = ?, ");
            hasChildChanges = true;
        }
        if (!allergiesField.getText().isEmpty()) {
            childQueryBuilder.append("allergies = ?, ");
            hasChildChanges = true;
        }
        if (!healthConditionsArea.getText().isEmpty()) {
            childQueryBuilder.append("health_condition = ?, ");
            hasChildChanges = true;
        }

        // Remove the last comma and space, and add the WHERE clause
        if (hasChildChanges) {
            childQueryBuilder.setLength(childQueryBuilder.length() - 2); // Remove last comma
            childQueryBuilder.append(" WHERE child_id = ?");
        } else {
            System.out.println("No child fields to update.");
        }

        String childQuery = childQueryBuilder.toString();

        // Execute child update
        try {
            if (connection == null || connection.isClosed()) {
                connectToDatabase();
            }

            PreparedStatement childPreparedStatement = connection.prepareStatement(childQuery);
            int paramIndex = 1;

            // Set the parameters based on which fields were updated for child
            if (!nameField.getText().isEmpty()) {
                String[] names = nameField.getText().split(" ");
                childPreparedStatement.setString(paramIndex++, names[0]); // First Name
                childPreparedStatement.setString(paramIndex++, names.length > 1 ? names[1] : ""); // Last Name
            }
            if (!branchField.getText().isEmpty()) {
                childPreparedStatement.setInt(paramIndex++, Integer.parseInt(branchField.getText())); // Tenant ID
            }
            if (!genderField.getText().isEmpty()) {
                childPreparedStatement.setString(paramIndex++, genderField.getText());
            }
            if (dobPicker.getValue() != null) {
                childPreparedStatement.setDate(paramIndex++, Date.valueOf(dobPicker.getValue()));
            }
            if (enrolPicker.getValue() != null) {
                childPreparedStatement.setDate(paramIndex++, Date.valueOf(enrolPicker.getValue()));
            }
            if (!emergencyContactField.getText().isEmpty()) {
                childPreparedStatement.setString(paramIndex++, emergencyContactField.getText());
            }
            if (!allergiesField.getText().isEmpty()) {
                childPreparedStatement.setString(paramIndex++, allergiesField.getText());
            }
            if (!healthConditionsArea.getText().isEmpty()) {
                childPreparedStatement.setString(paramIndex++, healthConditionsArea.getText());
            }

            // Set the child ID for the WHERE clause
            childPreparedStatement.setInt(paramIndex, childId);

            // Execute the update
            int childRowsAffected = childPreparedStatement.executeUpdate();
            System.out.println(childRowsAffected > 0 ? "Child profile updated." : "Child not found.");
            childPreparedStatement.close();
        } catch (SQLException e) {
            System.out.println("Error updating child profile: " + e.getMessage());
        }

        // Now update parent details if they have changed
        updateParentDetails(childId);
    }

    private void updateParentDetails(int childId) {
        String parentFirstName = parentNames.getText().split(" ")[0]; // Assuming first name is the first part
        String parentLastName = parentNames.getText().split(" ").length > 1 ? parentNames.getText().split(" ")[1] : ""; // Default to empty if not provided
        String parentPhone = parentNumber.getText();
        String parentEmailText = parentEmail.getText();
        String parentAddressText = parentAddress.getText();

        String parentQuery = "UPDATE parents SET first_name = ?, last_name = ?, phone_number = ?, email = ?, address = ? " +
                "WHERE parent_id IN (SELECT parent_id FROM child_parent WHERE child_id = ?)";

        try {
            if (connection == null || connection.isClosed()) {
                connectToDatabase();
            }

            PreparedStatement preparedStatement = connection.prepareStatement(parentQuery);
            preparedStatement.setString(1, parentFirstName);
            preparedStatement.setString(2, parentLastName);
            preparedStatement.setString(3, parentPhone);
            preparedStatement.setString(4, parentEmailText);
            preparedStatement.setString(5, parentAddressText);
            preparedStatement.setInt(6, childId); // Set the child ID

            int parentRowsAffected = preparedStatement.executeUpdate();
            System.out.println(parentRowsAffected > 0 ? "Parent details updated." : "Parent not found.");
            preparedStatement.close();
        } catch (SQLException e) {
            System.out.println("Error updating parent details: " + e.getMessage());
        }
    }

    @FXML
    private void handleClearForm() {
        nameField.clear();
        branchField.clear();
        genderField.clear();
        dobPicker.setValue(null);
        enrolPicker.setValue(null);
        emergencyContactField.clear();
        allergiesField.clear();
        healthConditionsArea.clear();
        System.out.println("Form cleared.");
    }

    @FXML
    private void handleUploadImage() {
        FileChooser fileChooser = new FileChooser();

        // Set filter to show only image files
        FileChooser.ExtensionFilter imageFilter = new FileChooser.ExtensionFilter("Image Files", "*.png", "*.jpg", "*.jpeg");
        fileChooser.getExtensionFilters().add(imageFilter);

        // Show open file dialog
        File file = fileChooser.showOpenDialog(childImageView.getScene().getWindow());

        if (file != null) {
            // Convert the selected file to an Image
            Image image = new Image(file.toURI().toString());

            // Set the image to the ImageView
            childImageView.setImage(image);
            System.out.println("Image uploaded: " + file.getName());

            // Optionally, save the file path or image in the database for retrieval later
            // You could save file.getAbsolutePath() if you're storing the image path in the database
        } else {
            System.out.println("Image upload canceled.");
        }
    }

    public void loadchildNameDropdown() {
        String query = "SELECT first_name, last_name FROM children";  // SQL query to fetch data

        try {
            // Check if the connection is open, if not, try connecting
            if (connection == null || connection.isClosed()) {
                connectToDatabase();  // Ensure connectToDatabase() is working properly
            }

            PreparedStatement preparedStatement = connection.prepareStatement(query);
            ResultSet resultSet = preparedStatement.executeQuery();

            // ObservableList to hold combo box items
            ObservableList<String> childrenList = FXCollections.observableArrayList();

            // Add results from database to the list
            while (resultSet.next()) {
                String fullName = resultSet.getString("first_name") + " " + resultSet.getString("last_name");
                childrenList.add(fullName);  // Add the full name to the list
            }

            // Set items in the ComboBox
            childNameDropdown.setItems(childrenList);

            // Optional: You can set a default value
            if (!childrenList.isEmpty()) {
                childNameDropdown.getSelectionModel().selectFirst();  // Select the first item by default
            }

            // Close the result set and prepared statement
            resultSet.close();
            preparedStatement.close();
        } catch (SQLException e) {
            // Handle any SQL exceptions
            System.out.println("Error loading combo box: " + e.getMessage());
        }
    }

    @FXML
    private void handleUpdateHealthRecords() {
        // Retrieve values from UI components
        String selectedBloodType = bloodTypeComboBox.getSelectionModel().getSelectedItem();
        String healthConditions = healthConditionsAreaUpdate.getText();
        int childId = getChildIdByName(childComboBox.getSelectionModel().getSelectedItem());

        // SQL query to update health records
        String updateQuery = "UPDATE healthrecords SET blood_type = ?, health_issue = ? WHERE child_id = ?";

        try {
            // Ensure database connection is established
            if (connection == null || connection.isClosed()) {
                connectToDatabase();
            }

            // Prepare the statement and set parameters
            PreparedStatement preparedStatement = connection.prepareStatement(updateQuery);
            preparedStatement.setString(1, selectedBloodType);
            preparedStatement.setString(2, healthConditions);
            preparedStatement.setInt(3, childId);

            // Execute update and check results
            int rowsAffected = preparedStatement.executeUpdate();
            if (rowsAffected > 0) {
                System.out.println("Health records updated successfully.");
            } else {
                System.out.println("No records updated. Check if the child ID is correct.");
            }

            // Close the prepared statement
            preparedStatement.close();
        } catch (SQLException e) {
            System.err.println("Error updating health records: " + e.getMessage());
            e.printStackTrace();
        }
    }


    @FXML
    private void handleAddAllergy() {
        String name = newAllergy.getText();
        String relation = allergyreaction.getText();
        String phone = notes.getText();
        if (!name.isEmpty() && !relation.isEmpty() && !phone.isEmpty()) {
            allergyNoteslist.getItems().add(name + " (" + relation + "): " + phone);
            newAllergy.clear();
            allergyreaction.clear();
            notes.clear();
        }
    }
    @FXML
    private void handleAddFoodPreference() {
        String foodPreference = newFoodPreference.getText();
        if (!foodPreference.isEmpty()) {
            foodPreferencesList.getItems().add(foodPreference);
            newFoodPreference.clear();
        }
    }

    @FXML
    private void handleAddEmergencyContact() {
        String name = emergencyContactName.getText();
        String relation = emergencyContactRelation.getText();
        String phone = emergencyContactPhone.getText();
        if (!name.isEmpty() && !relation.isEmpty() && !phone.isEmpty()) {
            emergencyContactsList.getItems().add(name + " (" + relation + "): " + phone);
            emergencyContactName.clear();
            emergencyContactRelation.clear();
            emergencyContactPhone.clear();
        }
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

            Stage stage = (Stage) nameField.getScene().getWindow(); // Get the current stage
            stage.setScene(new Scene(root));
            stage.setTitle(title);
            stage.setFullScreen(true); // Set to full screen
            stage.show();
        } catch (IOException e) {
            e.printStackTrace(); // Handle exceptions
        }
    }
    public void handleAdminBtn(ActionEvent actionEvent) {
    }
}
