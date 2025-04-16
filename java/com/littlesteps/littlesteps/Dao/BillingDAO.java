package com.littlesteps.littlesteps.Dao;

import com.littlesteps.littlesteps.Records.BillingRecord;
import com.littlesteps.littlesteps.Records.StaffRecord;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.*;

public class BillingDAO{

    private Connection connection;

    public BillingDAO(Connection connection) {
        this.connection = connection;
    }

    public ObservableList<BillingRecord> getBillingRecordsByChildId(int childId) throws SQLException {
        ObservableList<BillingRecord> invoiceList = FXCollections.observableArrayList();
        String query = "SELECT service_description, issue_date, discount, amount_due, late_fee, total_amount, due_date, payment_status, payment_method, payment_date, tenant_id FROM invoice WHERE child_id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setInt(1, childId);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                BillingRecord bill = new BillingRecord();
                bill.setService(rs.getString("service_description"));
                bill.setIssueDate(rs.getDate("issue_date"));
                bill.setDiscount(rs.getDouble("discount"));
                bill.setAmountDue(rs.getDouble("amount_due"));
                bill.setLateFee(rs.getDouble("late_fee"));
                bill.setTotalAmount(rs.getDouble("total_amount"));
                bill.setDueDate(rs.getDate("due_date"));
                bill.setStatus(rs.getString("payment_status"));
                bill.setPaymentMethod(rs.getString("payment_method"));
                bill.setPaymentDate(rs.getDate("payment_date"));
                bill.setTenantId(rs.getInt("tenant_id"));
                invoiceList.add(bill);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return invoiceList;
    }
}