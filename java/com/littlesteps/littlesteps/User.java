package com.littlesteps.littlesteps;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class User {
    private final SimpleIntegerProperty id;
    private final SimpleStringProperty username;
    private final SimpleStringProperty passwordHash;
    private final SimpleStringProperty role;

    // Constructor
    public User(int id, String username, String passwordHash, String role) {
        this.id = new SimpleIntegerProperty(id);
        this.username = new SimpleStringProperty(username);
        this.passwordHash = new SimpleStringProperty(passwordHash);
        this.role = new SimpleStringProperty(role);
    }

    // Getters
    public int getId() {
        return id.get();
    }

    public String getUsername() {
        return username.get();
    }

    public String getPasswordHash() {
        return passwordHash.get();
    }

    public String getRole() {
        return role.get();
    }

    // Property methods for JavaFX binding
    public StringProperty usernameProperty() {
        return username;
    }

    public StringProperty passwordHashProperty() {
        return passwordHash;
    }

    public StringProperty roleProperty() {
        return role;
    }

}
