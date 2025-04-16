package com.littlesteps.littlesteps.Records;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class DaycareBranch {
    private IntegerProperty tenantId;
    private StringProperty name;
    private StringProperty contactInfo;

    // Constructor with int and String parameters
    public DaycareBranch(int tenantId, String name, String contactInfo) {
        this.tenantId = new SimpleIntegerProperty(tenantId);
        this.name = new SimpleStringProperty(name);
        this.contactInfo = new SimpleStringProperty(contactInfo);
    }

    // Default constructor
    public DaycareBranch() {
        this.tenantId = new SimpleIntegerProperty();
        this.name = new SimpleStringProperty();
        this.contactInfo = new SimpleStringProperty();
    }

    // Getters and setters
    public int getTenantId() {
        return tenantId.get();
    }

    public void setTenantId(int tenantId) {
        this.tenantId.set(tenantId);
    }

    public IntegerProperty getTenantIdProperty() {
        return tenantId;
    }

    public String getName() {
        return name.get();
    }

    public void setName(String name) {
        this.name.set(name);
    }

    public StringProperty getNameProperty() {
        return name;
    }

    public String getContactInfo() {
        return contactInfo.get();
    }

    public void setContactInfo(String contactInfo) {
        this.contactInfo.set(contactInfo);
    }

    public StringProperty getContactInfoProperty() {
        return contactInfo;
    }
}
