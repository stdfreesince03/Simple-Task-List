module com.example.myown {
    requires javafx.controls;
    requires javafx.base;
    requires javafx.fxml;
    requires javafx.graphics;
    requires javafx.web;
    requires javafx.media;
    requires javafx.swing;
    requires javafx.swt;



    opens com.example.myown to javafx.fxml;
    exports com.example.myown;
    exports com.example.myown.datamodel;
    opens com.example.myown.datamodel to javafx.fxml;
}