package com.littlesteps.littlesteps.Records;

import javafx.beans.property.*;

import java.sql.Date;

public class StaffRecord {
    private IntegerProperty staffId;
    private StringProperty name;
    private StringProperty surname;
    private StringProperty role;
    private StringProperty contactInfo;
    private ObjectProperty<Date> hireDate;
    private IntegerProperty branchId;
    private StringProperty duty;

    public <date> StaffRecord() {
        this.staffId = new SimpleIntegerProperty();
        this.name = new SimpleStringProperty();
        this.surname = new SimpleStringProperty();
        this.role = new SimpleStringProperty();
        this.contactInfo = new SimpleStringProperty();
        this.hireDate = new SimpleObjectProperty();
        this.branchId = new SimpleIntegerProperty();
        this.duty = new SimpleStringProperty();
    }

    public void setStaffId(int id) {
        this.staffId.set(id);
    }

    public IntegerProperty getStaffIdProperty() {
        return staffId;
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
    public String getSurName() {
        return surname.get();
    }

    public void setSurName(String surname) {
        this.surname.set(surname);
    }

    public StringProperty getSurNameProperty() {
        return surname;
    }

    public String getRole() {
        return role.get();
    }

    public void setRole(String role) {
        this.role.set(role);
    }

    public StringProperty getRoleProperty() {
        return role;
    }

    public Date getHireDate() {
        return hireDate.get();
    }

    public void setHireDate(Date date) {
        this.hireDate.set(date);
    }

    public ObjectProperty<Date> getHireDateProperty() {
        return hireDate;
    }

    public int getBranchId() {
        return branchId.get();
    }

    public void setBranchId(int id) {
        this.branchId.set(id);
    }

    public IntegerProperty getBranchIdProperty() {
        return branchId;
    }

    public String getDuty() {
        return duty.get();
    }

    public void setDuty(String duty) {
        this.duty.set(duty);
    }

    public StringProperty getDutyProperty() {
        return duty;
    }

    public String getContactInfo() {
        return contactInfo.get();
    }
    public void setContactInfo(String contactInfo){
        this.contactInfo.set(contactInfo);
    }
}