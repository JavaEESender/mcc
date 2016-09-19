/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ua.obolon.ponovoy.fx;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import ua.obolon.ponovoy.socket.Client;
import ua.obolon.ponovoy.view.ViewOrder;
import ua.obolon.ponovoy.view.ViewCall;

/**
 * FXML Controller class
 *
 * @author Alexander
 */
public class MainController implements Initializable {

    @FXML
    private TableView<ViewCall> historyTableView;
    @FXML
    private TableColumn<ViewCall, String> numberColumn;
    @FXML
    private TableColumn<ViewCall, String> fnameColumn;
    @FXML
    private TableColumn<ViewCall, String> lnameColumn;
    @FXML
    private TableView<ViewOrder> orderTableView;
    @FXML
    private TableColumn<ViewOrder, String> orderID;
    @FXML
    private TableColumn<ViewOrder, String> orderDate;
    @FXML
    private TableColumn<ViewOrder, String> orderTotal;
    @FXML
    private TableColumn<ViewOrder, String> orderStatus;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        numberColumn.setCellValueFactory(new PropertyValueFactory<ViewCall, String>("number"));
        fnameColumn.setCellValueFactory(new PropertyValueFactory<ViewCall, String>("fname"));
        lnameColumn.setCellValueFactory(new PropertyValueFactory<ViewCall, String>("lname"));
        orderID.setCellValueFactory(new PropertyValueFactory<ViewOrder, String>("orderId"));
        orderDate.setCellValueFactory(new PropertyValueFactory<ViewOrder, String>("orderDate"));
        orderTotal.setCellValueFactory(new PropertyValueFactory<ViewOrder, String>("orderTotal"));
        orderStatus.setCellValueFactory(new PropertyValueFactory<ViewOrder, String>("orderStatus"));

        historyTableView.setRowFactory(tv -> {
            TableRow<ViewCall> row = new TableRow<>();
            row.setOnMouseClicked(event -> {
                ViewCall rowData = row.getItem();
                Client client = new Client();
                client.getUserDetail(rowData.getNumber());
            });
            return row;
        });

        new Thread(new Runnable() {
            @Override
            public void run() {
                Client client = new Client(historyTableView, orderTableView);
            }
        }).start();
    }

}
