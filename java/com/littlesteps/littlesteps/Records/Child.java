package com.littlesteps.littlesteps.Records;

import javafx.beans.property.*;
import java.sql.Date;

public class Child {
    private StringProperty healthCondition = new SimpleStringProperty("");
    private StringProperty childName = new SimpleStringProperty("");
    private IntegerProperty branch = new SimpleIntegerProperty();
    private ObjectProperty<Date> dateOfBirth = new SimpleObjectProperty<>();
    private StringProperty emergencyContact = new SimpleStringProperty("");
    private StringProperty gender = new SimpleStringProperty("");
    private StringProperty allergies = new SimpleStringProperty("");
    private ObjectProperty<Date> enrollmentDate = new SimpleObjectProperty<>();
    private ObjectProperty<byte[]> photo = new SimpleObjectProperty<>(new byte[0]);      // Empty byte array as default
    private StringProperty foodPref = new SimpleStringProperty("");
    private IntegerProperty childId = new SimpleIntegerProperty();


    public Child() {
        this.childName = new SimpleStringProperty();
        this.branch = new SimpleIntegerProperty();
        this.dateOfBirth = new SimpleObjectProperty<>();
        this.emergencyContact = new SimpleStringProperty();
        this.gender = new SimpleStringProperty();
        this.allergies = new SimpleStringProperty();
        this.enrollmentDate = new SimpleObjectProperty<>();
        this.photo = new SimpleObjectProperty<>();
        this.foodPref = new SimpleStringProperty();
        this.healthCondition = new SimpleStringProperty();
    }

    public Child(String childName, Integer childId, Integer branch, Date dateOfBirth, String emergencyContact, String gender, String allergies, Date enrollmentDate, byte[] photos, String foodPref, String healthCondition) {
        this();
        setChildName(childName);
        setBranch(branch);
        setDateOfBirth(dateOfBirth != null ? dateOfBirth : new Date(System.currentTimeMillis()));
        setEmergencyContact(emergencyContact);
        setGender(gender);
        setAllergies(allergies);
        setEnrollmentDate(enrollmentDate != null? enrollmentDate : new Date(System.currentTimeMillis()));
        setPhoto(photos);
        setFoodPref(foodPref);
        setHealthCondition(healthCondition);
    }

    public Child(String s, String tenantId, Date dateOfBirth, String emergencyContact, String gender, String foodPref, Date enrollmentDate, String allergies, byte[] photos) {
    }


    // Getters and setters
    public Integer getChildId() { return childId.get();}
    public Integer setChildId(Integer childId) {this.childId.set(childId);
        return childId;
    }
    public String getChildName() { return childName.get(); }
    public void setChildName(String childName) { this.childName.set(childName); }
    public StringProperty childNameProperty() { return childName; }

    public Integer getBranch() { return branch.get(); }
    public void setBranch(Integer branch) { this.branch.set(branch); }
    public IntegerProperty branchProperty() { return branch; }

    public Date getDateOfBirth() { return dateOfBirth.get(); }
    public void setDateOfBirth(Date dateOfBirth) { this.dateOfBirth.set(dateOfBirth); }
    public ObjectProperty<Date> dateOfBirthProperty() { return dateOfBirth; }

    public String getEmergencyContact() { return emergencyContact.get(); }
    public void setEmergencyContact(String emergencyContact) { this.emergencyContact.set(emergencyContact); }
    public StringProperty emergencyContactProperty() { return emergencyContact; }

    public String getGender() { return gender.get(); }
    public void setGender(String gender) { this.gender.set(gender); }
    public StringProperty genderProperty() { return gender; }

    public String getAllergies() { return allergies.get(); }
    public void setAllergies(String allergies) { this.allergies.set(allergies); }
    public StringProperty allergiesProperty() { return allergies; }

    public Date getEnrollmentDate() { return enrollmentDate.get(); }
    public void setEnrollmentDate(Date enrollmentDate) { this.enrollmentDate.set(enrollmentDate); }
    public ObjectProperty<Date> enrollmentDateProperty() { return enrollmentDate; }

    public byte[] getPhoto() { return photo.get(); }
    public void setPhoto(byte[] photo) { this.photo.set(photo); }
    public ObjectProperty<byte[]> photoProperty() { return photo; }

    public String getFoodPref() { return foodPref.get(); }
    public void setFoodPref(String foodPref) { this.foodPref.set(foodPref); }
    public StringProperty foodPrefProperty() { return foodPref; }
    
    public String getHealthCondition() { return healthCondition.get();}
    public void setHealthCondition(String healthCondition) { this.healthCondition.set(healthCondition);}
    public StringProperty healthConditionProperty() { return healthCondition; }
}
