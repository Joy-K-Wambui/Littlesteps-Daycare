package com.littlesteps.littlesteps.Records;

import java.sql.Date;

public class Parents {
    private int id;
    private String name;
    private String email;
    private String address;
    private String phoneNumber;
    private String firstName;
    private String lastName;

    // Constructor
    public Parents(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public Parents(int parentId, String firstName, String lastName, String email, String phoneNumber, String address) {
    }

    // Getters and Setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

}
