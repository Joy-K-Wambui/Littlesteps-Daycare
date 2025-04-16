package com.littlesteps.littlesteps.Records;

import javafx.beans.property.*;

import java.sql.Date;

public class HealthRecord {
    private IntegerProperty recordId;
    private IntegerProperty childId;
    private StringProperty incident;
    private StringProperty issue;
    private StringProperty bloodType;
    private ObjectProperty<Date> reportDate;
    private IntegerProperty branchId;

    public HealthRecord() {
        this.recordId = new SimpleIntegerProperty();
        this.childId = new SimpleIntegerProperty();
        this.incident = new SimpleStringProperty();
        this.issue = new SimpleStringProperty();
        this.bloodType = new SimpleStringProperty();
        this.reportDate = new SimpleObjectProperty();
        this.branchId = new SimpleIntegerProperty();
    }

    public void setRecordId(int id) {
        this.recordId.set(id);
    }

    public IntegerProperty getRecordIdProperty() {
        return recordId;
    }

    public void setChildId(int id) {
        this.childId.set(id);
    }

    public IntegerProperty getChildIdProperty() {
        return childId;
    }

    public String getIncident() {
        return incident.get();
    }

    public void setIncident(String incident) {
        this.incident.set(incident);
    }

    public StringProperty getIncidentProperty() {
        return incident;
    }
    public String getIssue() {
        return issue.get();
    }

    public void setIssue(String issue) {
        this.issue.set(issue);
    }

    public StringProperty getIssueProperty() {
        return issue;
    }

    public String getBloodType() {
        return bloodType.get();
    }

    public void setBloodType(String bloodType) {
        this.bloodType.set(bloodType);
    }

    public StringProperty getBloodTypeProperty() {
        return bloodType;
    }

    public Date getReportDate() {
        return reportDate.get();
    }

    public void setReportDate(Date date) {
        this.reportDate.set(date);
    }

    public ObjectProperty<Date> getReportDateProperty() {
        return reportDate;
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
}