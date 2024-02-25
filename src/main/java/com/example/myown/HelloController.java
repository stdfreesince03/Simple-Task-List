package com.example.myown;

import com.example.myown.datamodel.Contact;
import com.example.myown.datamodel.ContactData;
import javafx.beans.binding.Bindings;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.Dialog;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.input.KeyCode;

import javafx.scene.input.KeyEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.util.Callback;

//import java.awt.*;
import java.io.IOException;
import java.util.Optional;

public class HelloController {
   @FXML private TableView<Contact> tableView;
   @FXML private TableColumn<Contact,String> firstNameCol = new TableColumn<>();
    @FXML private TableColumn<Contact,String>  phoneNumCol = new TableColumn<>();
    @FXML private TableColumn<Contact,String>  lastNameCol = new TableColumn<>();
    @FXML private TableColumn<Contact,String>  notesCol = new TableColumn<>();

    @FXML private BorderPane borderPane;

    @FXML private ScrollPane scrollPane;


    @FXML StackPane stackPane;

    @FXML HBox editHbox;
    @FXML TextField firstNameTF;
    @FXML TextField lastNameTF;
    @FXML TextField phoneNumTF;
    @FXML TextField notesTF;

    @FXML Button commitButton;


//    private TableRow<Contact> tableRow = new TableRow<>();
    private ContextMenu listContextMenu;



    public void initialize(){

//        tableView.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
        scrollPane.setMaxHeight(Double.MAX_VALUE);
        listContextMenu = new ContextMenu();


//        tableView.setContextMenu(listContextMenu);


        tableView.getColumns().clear();

        firstNameCol.setCellValueFactory(new PropertyValueFactory<Contact,String>("firstName"));
        firstNameCol.setCellFactory(TextFieldTableCell.forTableColumn());
        firstNameCol.setOnEditCommit(new EventHandler<TableColumn.CellEditEvent<Contact, String>>() {
            @Override
            public void handle(TableColumn.CellEditEvent<Contact, String> event) {
                Contact selected = event.getRowValue();
                selected.firstNameProperty().set(event.getNewValue());
            }
        });


        phoneNumCol.setCellValueFactory(new PropertyValueFactory<Contact,String>("phoneNumber"));
        phoneNumCol.setCellFactory(TextFieldTableCell.forTableColumn());
        phoneNumCol.setOnEditCommit(new EventHandler<TableColumn.CellEditEvent<Contact, String>>() {
            @Override
            public void handle(TableColumn.CellEditEvent<Contact, String> event) {
                Contact selected = event.getRowValue();
                selected.phoneNumberProperty().set(event.getNewValue());
            }
        });

        lastNameCol.setCellValueFactory(new PropertyValueFactory<Contact,String>("lastName"));
        lastNameCol.setCellFactory(TextFieldTableCell.forTableColumn());
        lastNameCol.setOnEditCommit(new EventHandler<TableColumn.CellEditEvent<Contact, String>>() {
            @Override
            public void handle(TableColumn.CellEditEvent<Contact, String> event) {
                Contact selected = event.getRowValue();
                selected.lastNameProperty().set(event.getNewValue());
            }
        });

        notesCol.setCellValueFactory(new PropertyValueFactory<Contact,String>("notes"));
        notesCol.setCellFactory(TextFieldTableCell.forTableColumn());
        notesCol.setOnEditCommit(new EventHandler<TableColumn.CellEditEvent<Contact, String>>() {
            @Override
            public void handle(TableColumn.CellEditEvent<Contact, String> event) {
                Contact selected = event.getRowValue();
                selected.notesProperty().set(event.getNewValue());
            }
        });




        MenuItem deleteItem = new MenuItem("delete");
        deleteItem.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                deleteContact();
            }
        });



        tableView.getColumns().addAll(firstNameCol,lastNameCol,notesCol,phoneNumCol);

        ObjectProperty<TableRow<Contact>> lastSelectedRow = new SimpleObjectProperty<>();
        tableView.setRowFactory(new Callback<TableView<Contact>, TableRow<Contact>>() {
            @Override
            public TableRow<Contact> call(TableView<Contact> contactTableView) {
                TableRow<Contact> row = new TableRow<>();
                row.contextMenuProperty().bind(
                        Bindings.when(row.emptyProperty())
                                .then((ContextMenu)null)
                                .otherwise(listContextMenu)
                );
                row.selectedProperty().addListener((obs,wasSelected,isSelected)->{
                    if(isSelected){
                        lastSelectedRow.set(row);
                    }
                });
                return row;
            }
        });
        commitButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                Contact selected = tableView.getSelectionModel().getSelectedItem();
                selected.firstNameProperty().set(firstNameTF.getText());
                selected.lastNameProperty().set(lastNameTF.getText());
                selected.phoneNumberProperty().set(phoneNumTF.getText());
                selected.notesProperty().set(notesTF.getText());
                editHbox.toBack();
                tableView.setDisable(false);
            }
        });

        MenuItem editItem = new MenuItem("edit");
        editItem.setId("editID");
        editItem.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                TableRow<Contact> last = lastSelectedRow.get();
                Contact selected = tableView.getSelectionModel().getSelectedItem();
                StackPane.setMargin(editHbox,new Insets(last.getLayoutY() + last.getHeight(),0,0,0));

                firstNameTF.setText(selected.firstNameProperty().get().trim());
                lastNameTF.setText(selected.lastNameProperty().get().trim());
                phoneNumTF.setText(selected.phoneNumberProperty().get().trim());
                notesTF.setText(selected.notesProperty().get().trim());
                editHbox.toFront();
                tableView.setDisable(true);
            }
        });



        listContextMenu.getItems().setAll(editItem,deleteItem);

        tableView.setItems(ContactData.getContactIntance().getContacts());



    }

    public void showNewItemDialog(){
        Dialog<ButtonType> dialog = new Dialog<>();
        dialog.initOwner(borderPane.getScene().getWindow());
        dialog.setTitle("Add New Item");
        FXMLLoader fxmlLoader = new FXMLLoader(HelloController.class.getResource("dialog.fxml"));

        try{
            dialog.getDialogPane().setContent(fxmlLoader.load());
        }catch(IOException e){
            System.out.println("Couldn't load the dialogue");
            e.printStackTrace();
            return;
        }

        dialog.getDialogPane().getButtonTypes().addAll(ButtonType.OK,ButtonType.CANCEL);

        Optional<ButtonType> result = dialog.showAndWait();
        if(result.isPresent() && result.get() == (ButtonType.OK)){
            DialogController controller = fxmlLoader.getController();
            Contact justAdded = controller.processResults();
            tableView.refresh();
            tableView.getSelectionModel().select(justAdded);
        }
    }

    public void handleKeyDelete(final KeyEvent event){
        final Contact selectedItem = tableView.getSelectionModel().getSelectedItem();
        if(event.getCode() == (KeyCode.DELETE) || event.getCode() == (KeyCode.BACK_SPACE)){
            ContactData.getContactIntance().removeContacts(selectedItem);
        }

    }

    public void deleteContact( ){
        String FN = tableView.getSelectionModel().getSelectedItem().firstNameProperty().get();
        String LN = tableView.getSelectionModel().getSelectedItem().lastNameProperty().get();

        Alert deleteAlert = new Alert(Alert.AlertType.CONFIRMATION);
        deleteAlert.setTitle("Delete Confirmation");
        deleteAlert.setHeaderText("Item '" + FN + " " + LN + "' to be deleted" );
        deleteAlert.setContentText("Press OK to delete,cancel to back out");

        Optional<ButtonType> res = deleteAlert.showAndWait();
        if(res.get() == ButtonType.OK ){
            ContactData.getContactIntance().removeContacts(tableView.getSelectionModel().getSelectedItem());
        }

    }

//    public void trialHandle(ActionEvent evt){
//        StackPane
//    }










}