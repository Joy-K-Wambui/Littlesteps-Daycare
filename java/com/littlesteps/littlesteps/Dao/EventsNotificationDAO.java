package com.littlesteps.littlesteps.Dao;

import com.littlesteps.littlesteps.Records.EventsNotificationRecord;
import com.littlesteps.littlesteps.Records.StaffRecord;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.*;

public class EventsNotificationDAO {
    private static Connection connection;

    public EventsNotificationDAO(Connection connection) {
        this.connection = connection;
    }

    public static ObservableList<EventsNotificationRecord> getAllEventsNotificationRecords() throws SQLException {
        ObservableList<EventsNotificationRecord> ENList = FXCollections.observableArrayList();
        String query = "SELECT event_name, description, tenant_id FROM events"; // Adjust based on your database schema

        try (PreparedStatement pstmt = connection.prepareStatement(query);
             ResultSet rs = pstmt.executeQuery()) {

            while (rs.next()) {
                EventsNotificationRecord EN = new EventsNotificationRecord();
                EN.setActivity(rs.getString("event_name") + "" + (rs.getString("description")));
                EN.setBranchId(rs.getInt("tenant_id")); // Assuming branch_id is an Integer
                ENList.add(EN);
            }
        }
        return ENList;
    }
}