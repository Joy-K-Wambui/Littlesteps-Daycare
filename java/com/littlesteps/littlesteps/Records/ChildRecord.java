package com.littlesteps.littlesteps.Records;

import javafx.beans.property.*;

public class ChildRecord {
    private StringProperty name;
    private StringProperty surname;
    private IntegerProperty branchId;

    public ChildRecord() {
        this.name = new SimpleStringProperty();
        this.surname = new SimpleStringProperty();
        this.branchId = new SimpleIntegerProperty();
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

    public int getBranchId() {
        return branchId.get();
    }

    public void setBranchId(int id) {
        this.branchId.set(id);
    }

    public IntegerProperty getBranchIdProperty() {
        return branchId;
    }

    private SimpleBooleanProperty selected = new SimpleBooleanProperty(false);

    public boolean isSelected() {
        return selected.get();
    }

    public void setSelected(boolean value) {
        selected.set(value);
    }

    public SimpleBooleanProperty selectedProperty() {
        return selected;
    }
}