package com.littlesteps.littlesteps.Records;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class AttendanceRecord {
    private final IntegerProperty attendance_id;
    private final IntegerProperty first_name;
    private final StringProperty status;
    private final StringProperty date;
    private final IntegerProperty tenant_id;

    public AttendanceRecord() {
        this.attendance_id = new SimpleIntegerProperty();
        this.first_name = new SimpleIntegerProperty();
        this.status = new SimpleStringProperty();
        this.date = new SimpleStringProperty();
        this.tenant_id = new SimpleIntegerProperty();
    }

    // Attendance ID
    public int getAttId() {
        return attendance_id.get();
    }

    public void setAttId(int id) {
        this.attendance_id.set(id);
    }

    public IntegerProperty getAttIdProperty() {
        return attendance_id;
    }

    // Child Name
    public int getCName() {
        return first_name.get();
    }

    public void setCName(int childId) {
        this.first_name.set(childId);
    }

    public IntegerProperty getCNameProperty() {
        return first_name;
    }

    // Status
    public String getState() {
        return status.get();
    }

    public void setState(String state) {
        this.status.set(state);
    }

    public StringProperty getStateProperty() {
        return status;
    }

    // Date
    public String getAttDate() {
        return date.get();
    }

    public void setAttDate(String atDate) {
        this.date.set(atDate);
    }

    public StringProperty getDateProperty() {
        return date;
    }

    // Tenant ID
    public int getTntId() {
        return tenant_id.get();
    }

    public void setTntId(int Tid) {
        this.tenant_id.set(Tid);
    }

    public IntegerProperty getTntIdProperty() {
        return tenant_id;
    }
}
