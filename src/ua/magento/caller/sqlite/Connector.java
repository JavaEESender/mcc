/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ua.magento.caller.sqlite;

import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import ua.magento.caller.interfaces.Call;

/**
 *
 * @author Alexander
 */
public class Connector {
    Connection conn = null;
    Statement stmt = null;
    
    
    public void ConnectTo(String url) throws SQLException{
        conn = DriverManager.getConnection(url);
        stmt = conn.createStatement();
        String sql = "SELECT * FROM calls";
        ResultSet rs = stmt.executeQuery(sql);
        while (rs.next()){
            System.out.println(rs.getString("number"));
        }
    }
      public boolean InsertData(Call call) throws SQLException{
        conn = DriverManager.getConnection("jdbc:sqlite:/ua/obolon/ponovoy/res/missed.db");
        stmt = conn.createStatement();
        Date date = new Date(call.getDate());
        String sql = "INSERT INTO calls (number,date) " +
            "VALUES ('"+ call.getNumber() +"', " + date + " );"; 
        stmt.executeUpdate(sql);
        
        return true;
    }      
}