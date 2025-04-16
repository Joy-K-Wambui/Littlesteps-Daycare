package com.littlesteps.littlesteps.Dao;

import com.littlesteps.littlesteps.Records.PCRecord;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.*;

public class PCDAO {
    private Connection connection;

    public PCDAO(Connection connection) {
        this.connection = connection;
    }

    public ObservableList<PCRecord> getAllPCRecords() throws SQLException {
        ObservableList<PCRecord> PCList = FXCollections.observableArrayList();
        String query = "SELECT relationship FROM child_parent"; // Adjust based on your database schema

        try (PreparedStatement pstmt = connection.prepareStatement(query);
             ResultSet rs = pstmt.executeQuery()) {

            while (rs.next()) {
                PCRecord PC = new PCRecord();
                PC.setName(rs.getString("relationship"));
                PCList.add(PC);
            }
        }
        return PCList;
    }
}