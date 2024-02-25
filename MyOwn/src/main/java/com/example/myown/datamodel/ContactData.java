package com.example.myown.datamodel;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class ContactData {
    private String fileName = "ContactFile.txt";
    private static ContactData contactInstance = new ContactData();
    private ObservableList<Contact> contacts;
    private ContactData(){
        contacts = FXCollections.observableArrayList();
    }

    public ObservableList<Contact> getContacts() {
        return contacts;
    }

    public static  ContactData getContactIntance(){
        return contactInstance;
    }

    public void loadContact() throws IOException{
        Path path = Paths.get(fileName);
        BufferedReader rd = Files.newBufferedReader(path);
        try{

            String input;
            while((input = rd.readLine()) != null){
                String[] stringArray = input.split(",");
                String firstName = stringArray[0];
                String lastName = stringArray[1];
                String phoneNum = stringArray[2];
                String notes = stringArray[3];

                Contact added = new Contact(firstName,lastName,phoneNum,notes);
                addContacts(added);


            }

        }catch(IOException e ){
            e.getMessage();
        }finally{
            if(rd != null){
                rd.close();
            }
        }

    }
    public void writeContact() throws IOException{
        Path path = Paths.get(fileName);
        BufferedWriter bw = Files.newBufferedWriter(path);
        try{
            for(int i = 0;i<contacts.size();i++){
                Contact tbw = contacts.get(i);
                String fName = tbw.firstNameProperty().get();
                String lName = tbw.lastNameProperty().get();
                String pNumber = tbw.phoneNumberProperty().get();
                String nt = tbw.notesProperty().get();
                bw.write(String.format("%s,%s,%s,%s",fName,lName,pNumber,nt));
                bw.newLine();
            }
        }finally{
            if(bw!= null){
                bw.close();
            }
        }
    }
    public void addContacts(Contact contact){
        if(contact != null){
            contacts.add(contact);
        }
    }

    public void removeContacts(Contact contact){
        if(contacts.contains(contact) ){
            contacts.remove(contact);
        }
    }












}
