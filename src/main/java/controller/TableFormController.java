package controller;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import model.CustomerTM;

import java.util.Optional;

public class TableFormController {
    public TableView<CustomerTM> tblCustomers;
    public TextField txtId;
    public TextField txtName;
    public TextField txtAddress;
    public Button btnSave;
    public Button btnNew;
    public Button btnDelete;

    public void initialize(){

        btnDelete.setDisable(true);
        //mapping column names
        tblCustomers.getColumns().get(0).setCellValueFactory(new PropertyValueFactory<>("customerId"));
        tblCustomers.getColumns().get(1).setCellValueFactory(new PropertyValueFactory<>("customerName"));
        tblCustomers.getColumns().get(2).setCellValueFactory(new PropertyValueFactory<>("customerAddress"));



        ObservableList<CustomerTM> items = tblCustomers.getItems();
        items.add(new CustomerTM("C001","Naween","Alawwa"));
        items.add(new CustomerTM("C002","Nuwan","Kandy"));
        items.add(new CustomerTM("C003","Visal","Mathara"));

        btnSave.setOnAction(new EventHandler<ActionEvent>() {
            @Override

            // isBlack  -- spaces are also detected
            // isEmpty  -- spaces are not deteected
            public void handle(ActionEvent actionEvent) {
                if(txtId.getText().isBlank()){
                    new Alert(Alert.AlertType.ERROR, "Customer ID cannot be Empty").showAndWait();
                    txtId.requestFocus();
                    return;
                } else if (txtName.getText().isBlank()) {
                    new Alert(Alert.AlertType.ERROR, "Customer Name cannot be Empty").showAndWait();
                    txtName.requestFocus();
                    return;
                } else if (txtAddress.getText().isBlank()) {
                    new Alert(Alert.AlertType.ERROR, "Customer Address cannot be Empty").showAndWait();
                    txtAddress.requestFocus();
                    return;
                }
                for (CustomerTM item : items) {
                    if(item.getCustomerId().equals(txtId.getText())){
                        new Alert(Alert.AlertType.ERROR, "Same ID cannot be used").showAndWait();
                        return;
                    }
                }

                items.add(new CustomerTM(txtId.getText(),txtName.getText(),txtAddress.getText()));
                txtId.clear();
                txtName.clear();
                txtAddress.clear();
                txtId.requestFocus();
            }
        });
        btnNew.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                tblCustomers.getSelectionModel().clearSelection();
                btnSave.setText("Save Customer");
            }
        });
        btnDelete.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {

                if(tblCustomers.getSelectionModel().getSelectedItem()==null){
                    return;

                }

                Optional<ButtonType> buttonType = new Alert(Alert.AlertType.CONFIRMATION,
                        "Are you Sure to delete?",ButtonType.YES,ButtonType.NO).showAndWait();
                if(buttonType.get()==ButtonType.YES){
                    tblCustomers.getItems().remove(tblCustomers.getSelectionModel().getSelectedIndex());
                }

            }
        });
        tblCustomers.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<CustomerTM>() {
            @Override
            public void changed(ObservableValue<? extends CustomerTM> observableValue,
                                CustomerTM previous,
                                CustomerTM current) {
                if(current==null){
                    btnDelete.setDisable(true);
                    txtId.clear();
                    txtName.clear();
                    txtAddress.clear();
                    txtId.setEditable(true);
                    btnSave.setText("Save Customer");
                    return;
                }
                btnDelete.setDisable(false);
                txtId.setText(current.getCustomerId());
                //txtId.setDisable(true);   /// disable the field, all controls have disable
                txtId.setEditable(false);   /// cannot edit, only txt control has this
                txtName.setText(current.getCustomerName());
                txtAddress.setText(current.getCustomerAddress());
                btnSave.setText("Update Customer");
            }
        });
    }
}
