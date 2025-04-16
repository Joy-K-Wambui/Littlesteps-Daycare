package com.littlesteps.littlesteps.Records;

import javafx.beans.property.*;

public class PCRecord {
    private StringProperty rlship;

    public PCRecord() {
        this.rlship = new SimpleStringProperty();
    }


    public String getRlship() {
        return rlship.get();
    }

    public void setName(String rlship) {
        this.rlship.set(rlship);
    }

    public StringProperty getRlshipProperty() {
        return rlship;
    }
}