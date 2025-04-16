package com.littlesteps.littlesteps.Dao;

import com.littlesteps.littlesteps.Records.ChildRecord;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.*;

public class ChildRecordDAO {
    private Connection connection;

    public ChildRecordDAO(Connection connection) {
        this.connection = connection;
    }

    public ObservableList<ChildRecord> getAllChildRecords() throws SQLException {
        ObservableList<ChildRecord> childList = FXCollections.observableArrayList();
        String query = "SELECT first_name, last_name, tenant_id FROM children"; // Adjust based on your database schema

        try (PreparedStatement pstmt = connection.prepareStatement(query);
             ResultSet rs = pstmt.executeQuery()) {

            while (rs.next()) {
                ChildRecord child = new ChildRecord();
                child.setName(rs.getString("first_name"));
                child.setSurName(rs.getString("last_name"));
                child.setBranchId(rs.getInt("tenant_id")); // Assuming branch_id is an Integer
                childList.add(child);
            }
        }
        return childList;
    }
    public void addChildRecord(ChildRecord child) throws SQLException {
        String sql = "INSERT INTO children (first_name, last_name, tenant_id) VALUES (?, ?, ?)";

        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setString(1, child.getName());
            pstmt.setString(2, child.getSurName());
            pstmt.setInt(3, child.getBranchId());

            pstmt.executeUpdate();
        }
    }
}