package com.littlesteps.littlesteps.Dao;

import com.littlesteps.littlesteps.Records.AttendanceRecord;
import com.littlesteps.littlesteps.Records.Child;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

import java.sql.*;

public class ChildDAO {
    private ObservableList<Child> childrenList = FXCollections.observableArrayList();
    @FXML
    private TableView attendanceTable;
    @FXML private TextField nameField;
    @FXML private TextField branchField;
    @FXML private TextField genderField;
    @FXML private TextField dobPicker;
    @FXML private TextField childIdField;
    @FXML private TextField enrolPicker;
    @FXML private TextField emergencyContactField;
    @FXML private TextField allergiesField;
    @FXML private TextArea healthConditionsArea;
    @FXML private TextArea fPrefField;

    // Method in ChildDAO to get a child by first and last name
    public ObservableList<Child> getAllChildren() {
        ObservableList<Child> childrenList = FXCollections.observableArrayList();
        String query = "SELECT * FROM children";

        try (Connection conn = DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/littlestepsdaycare", "root", "password");
             PreparedStatement pstmt = conn.prepareStatement(query);
             ResultSet rs = pstmt.executeQuery()) {

            while (rs.next()) {
                Child child = new Child(
                        rs.getString("first_name") + " " + rs.getString("last_name"),
                        rs.getString("tenant_id"),
                        rs.getDate("date_of_birth"),
                        rs.getString("emergency_contact"),
                        rs.getString("gender"),
                        rs.getString("allergies"),
                        rs.getDate("enrollment_date"),
                        rs.getString("food_pref"),
                        rs.getBytes("photo")
                );
                childrenList.add(child);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return childrenList;
    }
    public Child getChildByName(String firstName, String lastName) {
        Child child = null;

        String query = "SELECT * FROM children WHERE first_name = ? AND last_name = ?";
        System.out.println("Running query for child: " + firstName + " " + lastName);

        try (Connection conn = DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/littlestepsdaycare", "root", "password");
             PreparedStatement pstmt = conn.prepareStatement(query)) {

            // Set parameters for each field in the WHERE clause
            pstmt.setString(1, firstName);
            pstmt.setString(2, lastName);

            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                System.out.println("Child data retrieved successfully.");
                child = new Child(
                        rs.getString("first_name") + " " + rs.getString("last_name"),
                        rs.getString("tenant_id"),
                        rs.getDate("date_of_birth"),
                        rs.getString("emergency_contact"),
                        rs.getString("gender"),
                        rs.getString("food_pref"),
                        rs.getDate("enrollment_date"),
                        rs.getString("allergies"),
                        rs.getBytes("photo")
                );
            } else {
                System.out.println("No child found with that name and matching criteria.");
            }
            rs.close();
        } catch (SQLException e) {
            System.out.println("Error fetching child by name: " + e.getMessage());
            e.printStackTrace();
        }
        return child;
    }
}
