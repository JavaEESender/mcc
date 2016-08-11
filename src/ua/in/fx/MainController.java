/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ua.in.fx;

import in.ua.call.Call;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import ua.in.socket.Client;

/**
 * FXML Controller class
 *
 * @author Alexander
 */
public class MainController implements Initializable {

    @FXML
    private TableView<Call> historyTableView;
    @FXML
    private TableColumn<Call, String> numberColumn;
    @FXML
    private TableColumn<Call, String> fnameColumn;
    @FXML
    private TableColumn<Call, String> lnameColumn;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        numberColumn.setCellValueFactory(new PropertyValueFactory<Call, String>("number"));
        fnameColumn.setCellValueFactory(new PropertyValueFactory<Call, String>("fname"));
        lnameColumn.setCellValueFactory(new PropertyValueFactory<Call, String>("lname"));
        new Thread(new Runnable() {
            @Override
            public void run() {
                Client client = new Client(historyTableView);
            }
        }).start();
    }

}
