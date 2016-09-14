/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ua.in.socket;

import ua.in.view.ViewCall;
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
import ua.in.view.ViewOrder;
import ua.obolon.ponovoy.impl.UserImpl;
import ua.obolon.ponovoy.inerfaces.Order;
import ua.obolon.ponovoy.inerfaces.User;
import ua.obolon.ponovoy.res.RequestKey;

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

    private TrayIcon trayIcon;
    private Image icon;
    private Image icon2;
    public static final String APPLICATION_NAME = "Who is calling";

    public Client() {

    }

    public Client(TableView<ViewCall> historyTableView, TableView<ViewOrder> orderTableView) throws IOException, ClassNotFoundException {
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
    public boolean clientConn() throws ClassNotFoundException {
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
        } catch (IOException e) {
            trayIcon.displayMessage(APPLICATION_NAME, "Disconnected!",
                    TrayIcon.MessageType.INFO);
            trayIcon.setImage(icon2);
            return false;
        }
        return true;
    }

    public boolean getUserDetail(String telephone) {
        try {
            SocketChannel reqChanel = SocketChannel.open();
            reqChanel.connect(new InetSocketAddress("10.0.74.100", 7878));
            ObjectOutputStream reqOos = new ObjectOutputStream(reqChanel.socket().getOutputStream());
            reqOos.writeObject(RequestKey.GET_ORDERS);
            reqOos.writeObject(username);
            reqOos.writeObject(pass);
            reqOos.writeObject(telephone);
            ObjectInputStream reqOis = new ObjectInputStream(reqChanel.socket().getInputStream());
            List<Order> orders = (List<Order>) reqOis.readObject();
            reqChanel.close();

            if(!orders.isEmpty()){
            orders.forEach((Order o) -> {
                ViewOrder tmp = new ViewOrder(String.valueOf(o.getId()), o.getOrderDate().toString(), String.valueOf(o.getTotal()), o.getStatus());
                this.orderData.add(tmp);
                ordersTableView.setItems(orderData);
            });}else{
                orderData.clear();
                ordersTableView.setItems(orderData);
            }
            return true;
        } catch (IOException ex) {
            Logger.getLogger(Client.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(Client.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
    }

    private void startSocket() throws IOException, ClassNotFoundException {
        while (true) {
            if (getSignup()) {
                while (clientConn()) {
                }
            }
        }
    }

    public boolean getSignup() throws IOException, ClassNotFoundException {
        try {
            s = SocketChannel.open();
            s.connect(new InetSocketAddress("10.0.74.100", 7878));
            oos = new ObjectOutputStream(s.socket().getOutputStream());

            oos.writeObject(RequestKey.NEW_LOGIN);
            oos.writeObject(username);
            oos.writeObject(pass);
            ois = new ObjectInputStream(s.socket().getInputStream());
            String ok = (String) ois.readObject();
            return ok.equals("true");
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
