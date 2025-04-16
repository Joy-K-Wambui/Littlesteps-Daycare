package com.littlesteps.littlesteps.Dao;

import com.littlesteps.littlesteps.Records.ParentRecord;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.*;

public class ParentDAO {
    private Connection connection;

    public ParentDAO(Connection connection) {
        this.connection = connection;
    }

    public ObservableList<ParentRecord> getAllParentRecords() throws SQLException {
        ObservableList<ParentRecord> parentList = FXCollections.observableArrayList();
        String query = "SELECT first_name, last_name, phone_number FROM parents"; // Adjust based on your database schema

        try (PreparedStatement pstmt = connection.prepareStatement(query);
             ResultSet rs = pstmt.executeQuery()) {

            while (rs.next()) {
                ParentRecord parent = new ParentRecord();
                parent.setName(rs.getString("first_name"));
                parent.setSurName(rs.getString("last_name"));
                parent.setContact(rs.getString("phone_number"));
                parentList.add(parent);
            }
        }
        return parentList;
    }
    public void addParentRecord(ParentRecord parent) throws SQLException {
        String sql = "INSERT INTO parents (first_name, last_name, contact_info, address) VALUES (?, ?, ?, ?)";

        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setString(1, parent.getName());
            pstmt.setString(2, parent.getSurName());
            pstmt.setString(3, parent.getContact());
            pstmt.setString(4, parent.getAddress());

            pstmt.executeUpdate();
        }
    }
}