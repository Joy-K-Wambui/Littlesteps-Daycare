package com.littlesteps.littlesteps.Dao;

import com.littlesteps.littlesteps.Records.ChildRecord;
import com.littlesteps.littlesteps.Records.DaycareBranch;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class DaycareBranchDAO {

    private Connection connection;

    // Constructor with a connection parameter, falls back to default connection if null
    public DaycareBranchDAO(Connection connection) {
        if (connection == null) {
            connectToDatabase();
        } else {
            this.connection = connection;
        }
    }

    // Default constructor that automatically connects to the database
    public DaycareBranchDAO() {
        connectToDatabase();
    }

    // Method to connect to the database
    private void connectToDatabase() {
        try {
            connection = DriverManager.getConnection(
                    "jdbc:mysql://127.0.0.1:3306/littlestepsdaycare", "root", "password");
            System.out.println("Database connection successful.");
        } catch (SQLException e) {
            System.out.println("Database connection failed: " + e.getMessage());
        }
    }

    // Method to retrieve all daycare branches
    public ObservableList<DaycareBranch> getAllBranches() throws SQLException {
        String query = "SELECT tenant_id, name, contact_info FROM tenants";
        ObservableList<DaycareBranch> branches = FXCollections.observableArrayList();

        try (PreparedStatement statement = connection.prepareStatement(query);
             ResultSet resultSet = statement.executeQuery()) {
            while (resultSet.next()) {
                DaycareBranch branch = new DaycareBranch();

                // Set properties directly
                branch.setTenantId(resultSet.getInt("tenant_id"));
                branch.setName(resultSet.getString("name"));
                branch.setContactInfo(resultSet.getString("contact_info"));

                branches.add(branch);
            }
        } catch (SQLException e) {
            System.out.println("Error retrieving branches: " + e.getMessage());
            throw e;
        }
        return branches;
    }

    public int getBranchIdByName(String branchName) throws SQLException {
        String query = "SELECT id FROM branches WHERE name = ?";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, branchName); // Set the branch name parameter

            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    return resultSet.getInt("id"); // Return the branch ID
                } else {
                    return -1; // If no branch with the given name is found
                }
            }
        }
    }
}
