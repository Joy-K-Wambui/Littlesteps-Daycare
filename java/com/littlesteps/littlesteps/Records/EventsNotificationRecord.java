package com.littlesteps.littlesteps.Records;

import javafx.beans.property.*;

import java.sql.Date;

public class EventsNotificationRecord {
    private StringProperty activity;
    private IntegerProperty branchId;

    public EventsNotificationRecord() {
        this.activity = new SimpleStringProperty();
        this.branchId = new SimpleIntegerProperty();
    }


    public String getActivity() {
        return activity.get();
    }

    public void setActivity(String activity) {
        this.activity.set(activity);
    }

    public StringProperty getActivityProperty() {
        return activity;
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