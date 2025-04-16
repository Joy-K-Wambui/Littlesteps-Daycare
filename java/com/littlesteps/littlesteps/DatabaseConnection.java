package com.littlesteps.littlesteps;

import javafx.collections.ObservableList;

import java.sql.*;

public class DatabaseConnection {
    public static void main(String[] args) {
        try {
            Connection connection = DriverManager.getConnection(
                    "jdbc:mysql://127.0.0.1:3306/littlestepsdaycare",
                    "root",
                    "password"
            );
            Statement statement = connection.createStatement();

            // Query the CHILDREN table
            ResultSet childrenResultSet = statement.executeQuery("SELECT * FROM CHILDREN");
            while (childrenResultSet.next()) {
                System.out.println("Child: " + childrenResultSet.getString("first_name") + " " + childrenResultSet.getString("last_name"));
            }

            // Query the USERS table
            ResultSet usersResultSet = statement.executeQuery("SELECT * FROM USERS");
            while (usersResultSet.next()) {
                System.out.println("User: " + usersResultSet.getString("username"));
            }

            // Query the STAFF table
            ResultSet staffResultSet = statement.executeQuery("SELECT * FROM STAFF");
            while (staffResultSet.next()) {
                System.out.println("Staff: " + staffResultSet.getString("first_name") + " " + staffResultSet.getString("last_name"));
                System.out.println("Position: " + staffResultSet.getString("position"));
            }

            // Query the PARENTS table
            ResultSet parentsResultSet = statement.executeQuery("SELECT * FROM PARENTS");
            while (parentsResultSet.next()) {
                System.out.println("Parent: " + parentsResultSet.getString("first_name") + " " + parentsResultSet.getString("last_name"));
            }

            // Query the ATTENDANCE table
            ResultSet attendanceResultSet = statement.executeQuery("SELECT * FROM ATTENDANCE");
            while (attendanceResultSet.next()) {
                System.out.println("Attendance Record: " + attendanceResultSet.getString("child_id") + " - " + attendanceResultSet.getString("date"));
            }

            // Query the BILLING table
            ResultSet billingResultSet = statement.executeQuery("SELECT * FROM BILLING");
            while (billingResultSet.next()) {
                System.out.println("Billing Record: " + billingResultSet.getString("amount") + " - " + billingResultSet.getString("date"));
            }

            // Query the CHILD_PARENT table
            ResultSet childParentResultSet = statement.executeQuery("SELECT * FROM CHILD_PARENT");
            while (childParentResultSet.next()) {
                System.out.println("Child-Parent Link: " + childParentResultSet.getString("child_id") + " - " + childParentResultSet.getString("parent_id"));
            }

            // Query the EVENTS table
            ResultSet eventsResultSet = statement.executeQuery("SELECT * FROM EVENTS");
            while (eventsResultSet.next()) {
                System.out.println("Event: " + eventsResultSet.getString("event_name") + " - " + eventsResultSet.getString("date"));
            }

            // Query the HEALTHRECORDS table
            ResultSet healthRecordsResultSet = statement.executeQuery("SELECT * FROM HEALTHRECORDS");
            while (healthRecordsResultSet.next()) {
                System.out.println("Health Record: " + healthRecordsResultSet.getString("child_id") + " - " + healthRecordsResultSet.getString("details"));
            }

            // Query the NOTIFICATIONS table
            ResultSet notificationsResultSet = statement.executeQuery("SELECT * FROM NOTIFICATIONS");
            while (notificationsResultSet.next()) {
                System.out.println("Notification: " + notificationsResultSet.getString("message") + " - " + notificationsResultSet.getString("date"));
            }

            // Query the TENANTS table
            ResultSet tenantsResultSet = statement.executeQuery("SELECT * FROM TENANTS");
            while (tenantsResultSet.next()) {
                System.out.println("Tenant: " + tenantsResultSet.getString("name") + " - " + tenantsResultSet.getString("unit"));
            }

            // Close the resources after queries
            childrenResultSet.close();
            usersResultSet.close();
            staffResultSet.close();
            parentsResultSet.close();
            attendanceResultSet.close();
            billingResultSet.close();
            childParentResultSet.close();
            eventsResultSet.close();
            healthRecordsResultSet.close();
            notificationsResultSet.close();
            tenantsResultSet.close();
            statement.close();
            connection.close();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
