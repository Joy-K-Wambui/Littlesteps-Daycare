package com.littlesteps.littlesteps.Dao;

import com.littlesteps.littlesteps.Records.StaffRecord;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.*;

public class StaffDAO {
    private Connection connection;

    public StaffDAO(Connection connection) {
        this.connection = connection;
    }

    public ObservableList<StaffRecord> getAllStaffRecords() throws SQLException {
        ObservableList<StaffRecord> staffList = FXCollections.observableArrayList();
        String query = "SELECT staff_id, first_name, last_name, role, hire_date, duty, tenant_id FROM staff"; // Adjust based on your database schema

        try (PreparedStatement pstmt = connection.prepareStatement(query);
             ResultSet rs = pstmt.executeQuery()) {

            while (rs.next()) {
                StaffRecord staff = new StaffRecord();
                staff.setStaffId(rs.getInt("staff_id"));
                staff.setName(rs.getString("first_name"));
                staff.setSurName(rs.getString("last_name"));
                staff.setRole(rs.getString("role"));
                staff.setHireDate(Date.valueOf(String.valueOf(rs.getDate("hire_date")))); // Assuming hire_date is of type Date
                staff.setBranchId(rs.getInt("tenant_id")); // Assuming branch_id is an Integer
                staff.setDuty(rs.getString("duty"));
                staffList.add(staff);
            }
        }
        return staffList;
    }
    public void addStaffRecord(StaffRecord staff) throws SQLException {
        String sql = "INSERT INTO staff (first_name, last_name, role, hire_date, tenant_id) VALUES (?, ?, ?, ?, ?, ?, ?)";

        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setString(1, staff.getName());
            pstmt.setString(2, staff.getSurName());
            pstmt.setString(3, staff.getRole());
            pstmt.setDate(4, staff.getHireDate());
            pstmt.setInt(5, staff.getBranchId());

            pstmt.executeUpdate();
        }
    }
}