/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ua.magento.caller.interfaces;

/**
 *
 * @author Alexander
 */
public interface Call {

    Call setNumber(String number);
    Call setDate(long date);
    long getDate();
    String getNumber();

}
