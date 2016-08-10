/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ua.in.socket;

import java.awt.AWTException;
import java.awt.Image;
import java.awt.MenuItem;
import java.awt.PopupMenu;
import java.awt.SystemTray;
import java.awt.TrayIcon;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.Socket;
import javafx.scene.control.TextField;
import javax.swing.ImageIcon;

/**
 *
 * @author Alexander
 */
public class Client {

    private static String username;
    private static String pass;
    private static String ip;
    private static int port;
    private static Socket s;
    private static DataInputStream dis;
    private static DataOutputStream dos;

    private TextField phoneTextField;
    private TextField fnameTextField;
    private TextField lnameTextField;

    private TrayIcon trayIcon;
    private Image icon;
    private Image icon2;
    public static final String APPLICATION_NAME = "Who is calling";

    public Client(TextField phoneTextField, TextField fnameTextField, TextField lnameTextField) {
        this.phoneTextField = phoneTextField;
        this.fnameTextField = fnameTextField;
        this.lnameTextField = lnameTextField;
        setTrayIcon();
        startSocket();
    }

    public Client(String username, String password) {
        Client.username = username;
        Client.pass = password;
    }

    public Client(String ip, int port) {
        Client.ip = ip;
        Client.port = port;
    }

    public boolean socketINI() {
        try {
            dis = new DataInputStream(s.getInputStream());
            dos = new DataOutputStream(s.getOutputStream());
            dos.writeUTF("connClient");
            dos.writeUTF(pass);
            trayIcon.displayMessage(APPLICATION_NAME, "Connected.",
                    TrayIcon.MessageType.INFO);
            trayIcon.setImage(icon);
        } catch (IOException e) {
            trayIcon.setImage(icon2);
            return false;
        }
        return true;
    }

    public boolean clientConn() {
        try {
            String phone = dis.readUTF();
            String fname = dis.readUTF();
            String lname = dis.readUTF();
            trayIcon.displayMessage(APPLICATION_NAME, "телефон:  " + phone + "\n" + "имя:            " + fname + "\n" + "фамилия:    " + lname,
                    TrayIcon.MessageType.INFO);
            phoneTextField.setText(phone);
            fnameTextField.setText(fname);
            lnameTextField.setText(lname);
        } catch (IOException e) {
            trayIcon.displayMessage(APPLICATION_NAME, "Disconnected!",
                    TrayIcon.MessageType.INFO);
            trayIcon.setImage(icon2);
            return false;
        }
        return true;
    }

    private void startSocket() {
        while (true) {
            if (getSignup() && socketINI()) {
                while (clientConn()) {
                }
            }
        }
    }

    public boolean getSignup() {
        try {
            s = new Socket();
            s.connect(new InetSocketAddress(ip, port), 5000);
            dis = new DataInputStream(s.getInputStream());
            dos = new DataOutputStream(s.getOutputStream());
            dos.writeUTF("newLogin");
            dos.writeUTF(username);
            dos.writeUTF(pass);
            return dis.readUTF().equals("true");
        } catch (IOException e) {
            return false;
        }
    }

    private void setTrayIcon() {
        if (!SystemTray.isSupported()) {
            return;
        }

        PopupMenu trayMenu = new PopupMenu();
        MenuItem item = new MenuItem("Exit");
        item.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        });
        MenuItem item2 = new MenuItem("Open");
        item2.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

            }
        });
        trayMenu.add(item2);
        trayMenu.add(item);

        icon = new ImageIcon(getClass().getResource("/online.png")).getImage();
        icon2 = new ImageIcon(getClass().getResource("/ofline.png")).getImage();
        trayIcon = new TrayIcon(icon, APPLICATION_NAME, trayMenu);
        trayIcon.setImageAutoSize(true);

        SystemTray tray = SystemTray.getSystemTray();
        try {
            tray.add(trayIcon);
        } catch (AWTException e) {
            e.printStackTrace();
        }
    }
}
