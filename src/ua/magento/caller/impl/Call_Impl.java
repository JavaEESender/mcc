/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ua.magento.caller.impl;

import java.io.Serializable;
import ua.magento.caller.interfaces.Call;

/**
 *
 * @author Alexander
 */
public class Call_Impl implements Call, Serializable {

    private String number;
    private long date;

    @Override
    public Call setNumber(String number) {
        this.number = number;
        return this;
    }

    @Override
    public Call setDate(long date) {
        this.date = date;
        return this;
    }

    @Override
    public long getDate() {
        return this.date;
    }

    @Override
    public String getNumber() {
        return this.number;
    }
}