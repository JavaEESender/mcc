/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ua.obolon.ponovoy.socket;

import java.awt.AWTException;
import java.awt.Image;
import java.awt.MenuItem;
import java.awt.PopupMenu;
import java.awt.SystemTray;
import java.awt.TrayIcon;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetSocketAddress;
import java.nio.channels.SocketChannel;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.TableView;
import javax.swing.ImageIcon;
import ua.obolon.ponovoy.view.ViewOrder;
import ua.obolon.ponovoy.impl.UserImpl;
import ua.obolon.ponovoy.interfaces.Order;
import ua.obolon.ponovoy.interfaces.User;
import ua.obolon.ponovoy.res.RequestKey;
import ua.obolon.ponovoy.view.ViewCall;

/**
 *
 * @author Alexander
 */
public class Client {

    private static String username;
    private static String pass;
    private static String ip;
    private static int port;
    private static SocketChannel s;
    private static ObjectInputStream ois;
    private static ObjectOutputStream oos;

    private static TableView<ViewCall> callsTableView;
    private static TableView<ViewOrder> ordersTableView;
    private ObservableList<ViewCall> callData = FXCollections.observableArrayList();
    private ObservableList<ViewOrder> orderData = FXCollections.observableArrayList();

    private static TrayIcon trayIcon;
    private Image icon;
    private Image icon2;
    private static final String APPLICATION_NAME = "Who is calling";

    public Client() {

    }

    public Client(TableView<ViewCall> historyTableView, TableView<ViewOrder> orderTableView) {
        this.callsTableView = historyTableView;
        this.ordersTableView = orderTableView;
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

//    public boolean socketINI() {
//        try {
//            oos = new ObjectOutputStream(s.socket().getOutputStream());
//            oos.writeObject("connClient");
//            oos.writeObject(pass);
//            trayIcon.displayMessage(APPLICATION_NAME, "Connected.",
//                    TrayIcon.MessageType.INFO);
//            trayIcon.setImage(icon);
//        } catch (IOException e) {
//            trayIcon.setImage(icon2);
//            return false;
//        }
//        return true;
//    }
    public boolean clientConn() {
        try {
            ois = new ObjectInputStream(s.socket().getInputStream());
            User user = new UserImpl();
            user = (User) ois.readObject();
            String phone = user.getTelephone();
            String fname = user.getFirstName();
            String lname = user.getLastName();

            trayIcon.displayMessage(APPLICATION_NAME, "телефон:  " + phone + "\n" + "имя:            " + fname + "\n" + "фамилия:    " + lname,
                    TrayIcon.MessageType.INFO);
            ViewCall cl = new ViewCall(phone, fname, lname);
            callData.add(cl);
            callsTableView.setItems(callData);
            return true;
        } catch (IOException e) {
            trayIcon.displayMessage(APPLICATION_NAME, "Disconnected!",
                    TrayIcon.MessageType.INFO);
            trayIcon.setImage(icon2);
            return false;
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(Client.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
    }

    public boolean getUserDetail(String telephone) {
        try {
            SocketChannel reqChanel = SocketChannel.open();
            reqChanel.connect(new InetSocketAddress(ip, port));
            ObjectOutputStream reqOos = new ObjectOutputStream(reqChanel.socket().getOutputStream());
            reqOos.writeObject(RequestKey.GET_ORDERS);
            reqOos.writeObject(username);
            reqOos.writeObject(pass);
            reqOos.writeObject(telephone);
            ObjectInputStream reqOis = new ObjectInputStream(reqChanel.socket().getInputStream());
            List<Order> orders = (List<Order>) reqOis.readObject();
            reqChanel.close();

            if (!orders.isEmpty()) {
                orders.forEach((Order o) -> {
                    ViewOrder tmp = new ViewOrder(o.getOrderID(), o.getOrderDate().toString(), String.valueOf(o.getTotal()), o.getStatus());
                    this.orderData.add(tmp);
                    ordersTableView.setItems(orderData);
                });
            } else {
                orderData.clear();
                ordersTableView.setItems(orderData);
            }
            return true;
        } catch (IOException | ClassNotFoundException ex) {
            Logger.getLogger(Client.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
    }

    private void startSocket() {
        while (true) {
            if (getSignup()) {
                trayIcon.displayMessage(APPLICATION_NAME, "Connected!",
                        TrayIcon.MessageType.INFO);
                trayIcon.setImage(icon);
                while (clientConn()) {
                }
            }
        }
    }

    public boolean getSignup() {
        try {
            s = SocketChannel.open();
            s.connect(new InetSocketAddress(ip, port));
            oos = new ObjectOutputStream(s.socket().getOutputStream());

            oos.writeObject(RequestKey.NEW_LOGIN);
            oos.writeObject(username);
            oos.writeObject(pass);
            ois = new ObjectInputStream(s.socket().getInputStream());
            String ok = (String) ois.readObject();
            return ok.equals("true");
        } catch (IOException e) {
            return false;
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(Client.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
    }

    private void setTrayIcon() {
        if (!SystemTray.isSupported()) {
            return;
        }

        PopupMenu trayMenu = new PopupMenu();
        MenuItem item_open = new MenuItem("Open");
        item_open.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

            }
        });
        MenuItem item_exit = new MenuItem("Exit");
        item_exit.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        });
        trayMenu.add(item_open);
        trayMenu.add(item_exit);

        icon = new ImageIcon(getClass().getResource("/ua/obolon/ponovoy/res/online.png")).getImage();
        icon2 = new ImageIcon(getClass().getResource("/ua/obolon/ponovoy/res/ofline.png")).getImage();
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
