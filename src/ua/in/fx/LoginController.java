/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ua.in.fx;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import ua.in.socket.Client;

/**
 * FXML Controller class
 *
 * @author Alexander
 */
public class LoginController implements Initializable {

    @FXML
    private Label lblMessage;
    @FXML
    private TextField txtUsername;
    @FXML
    private PasswordField txtPassword;

    @FXML
    private void btnLoginAction(ActionEvent event) throws IOException, ClassNotFoundException {
        Client client = new Client(txtUsername.getText(), txtPassword.getText());
        if (client.getSignup()) {
            ((Node) (event.getSource())).getScene().getWindow().hide();
            Parent parent = FXMLLoader.load(getClass().getResource("/ua/in/fx/Main.fxml"));
            Stage mainStage = new Stage();
            Scene mainScene = new Scene(parent);
            mainScene.getStylesheets().add(LoginController.class.getResource("/Main.css").toExternalForm());
            mainStage.setScene(mainScene);
            mainStage.setTitle("Who Is Calling");
            mainStage.setOnCloseRequest(new EventHandler<WindowEvent>() {
                @Override
                public void handle(WindowEvent event) {
                    System.exit(0);
                }
            });
            mainStage.getIcons().add(new Image("/online.png"));
            mainStage.show();
        } else {
            lblMessage.setText("Неверное имя пользователя или пароль!");
        }
    }

    @FXML
    private void imgMouseClick() {
        Stage settingsStage = new Stage();
        try {
            Parent settingsParent = FXMLLoader.load(getClass().getResource("/ua/in/fx/Settings.fxml"));
            Scene scene_settings = new Scene(settingsParent);
            scene_settings.getStylesheets().add(LoginController.class.getResource("/Main.css").toExternalForm());
            settingsStage.setScene(scene_settings);
            settingsStage.setResizable(false);
        } catch (IOException e) {
            System.exit(0);
        }
        settingsStage.setTitle("Настройки");
        settingsStage.getIcons().add(new Image("/settings.png"));
        settingsStage.show();
    }

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }

}
