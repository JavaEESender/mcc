/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ua.in.fx;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TextField;
import ua.in.socket.Client;

/**
 * FXML Controller class
 *
 * @author Alexander
 */
public class MainController implements Initializable {

    @FXML
    private TextField txtTelephone;
    @FXML
    private TextField txtFname;
    @FXML
    private TextField txtLname;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                Client client = new Client(txtTelephone, txtFname, txtLname);
            }
        }).start();
    }

}
