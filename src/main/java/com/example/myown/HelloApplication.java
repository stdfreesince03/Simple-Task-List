package com.example.myown;

import com.example.myown.datamodel.ContactData;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class HelloApplication extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("hello-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 1075, 550);
        stage.setResizable(false);
        stage.setTitle("My Contacts");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }

    @Override
    public void init() throws Exception {
        try{
            ContactData.getContactIntance().loadContact();
        }catch(IOException e ){
            e.getMessage();
        }
    }

    @Override
    public void stop() throws Exception {
        try{
            ContactData.getContactIntance().writeContact();
        }catch(IOException e ){
            e.getMessage();
        }
    }


}