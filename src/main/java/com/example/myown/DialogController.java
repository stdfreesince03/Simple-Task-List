package com.example.myown;

import com.example.myown.datamodel.Contact;
import com.example.myown.datamodel.ContactData;
import javafx.fxml.FXML;

import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;


public class DialogController {
   @FXML
   private TextField firstNameField;
    @FXML
    private TextField lastNameField;
    @FXML
    private TextField phoneNumField;
    @FXML
    private TextArea notesField;

    public Contact processResults(){
        String firstName = firstNameField.getText().trim();
        String lastName = lastNameField.getText().trim();
        String phoneNum = phoneNumField.getText().trim();
        String notes = notesField.getText().trim();

        Contact added = new Contact(firstName,lastName,phoneNum,notes);
        ContactData.getContactIntance().addContacts(added);
        return added;
    }
}
