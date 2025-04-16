package com.littlesteps.littlesteps.Records;

import javafx.beans.property.*;

public class ParentRecord {
    private StringProperty name;
    private StringProperty surname;
    private StringProperty contact;
    private StringProperty address;

    public ParentRecord() {
        this.name = new SimpleStringProperty();
        this.surname = new SimpleStringProperty();
        this.contact = new SimpleStringProperty();
        this.address = new SimpleStringProperty();
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

    public String getContact() {
        return contact.get();
    }

    public void setContact(String contact) {
        this.contact.set(contact);
    }

    public StringProperty getContactProperty() {
        return contact;
    }

    public String getAddress() {
        return address.get();
    }
    public void setAddress(String address){
        this.address.set(address);
    }
}