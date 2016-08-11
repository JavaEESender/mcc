/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ua.in.call;

/**
 *
 * @author Alexander
 */
public class Call {

    private String number;
    private String fname;
    private String lname;

    public Call(String number, String fname, String lname) {
        this.number = number;
        this.fname = fname;
        this.lname = lname;
    }

    public Call() {
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getFname() {
        return fname;
    }

    public void setFname(String fname) {
        this.fname = fname;
    }

    public String getLname() {
        return lname;
    }

    public void setLname(String lname) {
        this.lname = lname;
    }
}
