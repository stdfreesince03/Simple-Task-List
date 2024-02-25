package com.example.myown.datamodel;

import javafx.beans.property.SimpleStringProperty;

public class Contact {
    private SimpleStringProperty firstName = new SimpleStringProperty();
    private SimpleStringProperty lastName = new SimpleStringProperty();

    private SimpleStringProperty phoneNumber = new SimpleStringProperty();
    private SimpleStringProperty notes = new SimpleStringProperty();

    public Contact(String firstName, String lastName, String phoneNumber, String notes) {
        this.firstName.set(firstName);
        this.lastName .set(lastName);
        this.phoneNumber .set(phoneNumber);
        this.notes.set(notes);
    }


    public SimpleStringProperty firstNameProperty() {
        return firstName;
    }
    public SimpleStringProperty lastNameProperty() {
        return lastName;
    }

    public SimpleStringProperty phoneNumberProperty() {
        return phoneNumber;
    }
    public SimpleStringProperty notesProperty() {
        return notes;
    }
}
