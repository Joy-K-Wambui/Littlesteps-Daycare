package com.littlesteps.littlesteps.Dao;

import com.littlesteps.littlesteps.Records.HealthRecord;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.*;

public class HealthRecordDAO {
    private Connection connection;

    public HealthRecordDAO(Connection connection) {
        this.connection = connection;
    }


    public ObservableList<HealthRecord> getAllhealthRecords() throws SQLException {
        ObservableList<HealthRecord> healthRecordsList = FXCollections.observableArrayList();
        String query = "SELECT record_id, child_id, incident_description, health_issue, blood_type, report_date, tenant_id FROM healthrecords"; // Adjust based on your database schema

        try (PreparedStatement pstmt = connection.prepareStatement(query);
             ResultSet rs = pstmt.executeQuery()) {

            while (rs.next()) {
                HealthRecord hRecord = new HealthRecord();
                hRecord.setRecordId(rs.getInt("record_id"));
                hRecord.setRecordId(rs.getInt("child_id"));
                hRecord.setIncident(rs.getString("incident_description"));
                hRecord.setIssue(rs.getString("health_issue"));
                hRecord.setBloodType(rs.getString("blood_type"));
                hRecord.setReportDate(Date.valueOf(String.valueOf(rs.getDate("report_date")))); // Assuming hire_date is of type Date
                hRecord.setBranchId(rs.getInt("tenant_id")); // Assuming branch_id is an Integer
                healthRecordsList.add(hRecord);
            }
        }
        return healthRecordsList;
    }
}