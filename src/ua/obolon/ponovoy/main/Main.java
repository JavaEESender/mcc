/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ua.obolon.ponovoy.main;

import java.io.IOException;
import javafx.application.Application;
import static javafx.application.Application.launch;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import ua.obolon.ponovoy.fx.LoginController;
import ua.obolon.ponovoy.socket.Client;

/**
 *
 * @author Alexander
 */
public class Main extends Application {

    private static final String ip = "10.0.74.100";
    private static final int port = 7878;

    public static void main(String[] args) {
        Client client = new Client(ip, port);
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("/ua/obolon/ponovoy/fx/Login.fxml"));
            Scene scene_login = new Scene(root);
            scene_login.getStylesheets().add(LoginController.class.getResource("/ua/obolon/ponovoy/res/Login.css").toExternalForm());
            primaryStage.setScene(scene_login);
            primaryStage.setResizable(false);
        } catch (IOException e) {
            System.exit(0);
        }
        primaryStage.setTitle("Login");
        primaryStage.getIcons().add(new Image("/ua/obolon/ponovoy/res/ofline.png"));
        primaryStage.show();

    }

}
