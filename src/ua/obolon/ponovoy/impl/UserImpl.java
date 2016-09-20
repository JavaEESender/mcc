/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ua.obolon.ponovoy.impl;

import java.io.Serializable;
import ua.obolon.ponovoy.interfaces.User;

/**
 *
 * @author Alexander
 */
public class UserImpl implements User, Serializable {

    private String firstName;
    private String lastName;
    private boolean isActive;
    private String telephone = "NONE";
    private long id;
    private long callDate;

    public UserImpl(long id, String firstname, String lastName, boolean isActive, String telephone) {
        this.firstName = firstname;
        this.id = id;
        this.lastName = lastName;
        this.isActive = isActive;
        if (telephone != null) {
            this.telephone = telephone;
        }
    }

    public UserImpl() {

    }

    @Override
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    @Override
    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    @Override
    public void setIsActive(boolean isActive) {
        this.isActive = isActive;
    }

    @Override
    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    @Override
    public String getFirstName() {
        return firstName;
    }

    @Override
    public String getLastName() {
        return lastName;
    }

    @Override
    public boolean isActive() {
        return isActive;
    }

    @Override
    public String getTelephone() {
        return telephone;
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 19 * hash + (int) (this.id ^ (this.id >>> 32));
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final UserImpl other = (UserImpl) obj;
        if (this.id != other.id) {
            return false;
        }
        return true;
    }

   

    @Override
    public void setId(long id) {
        this.id = id;
    }

    @Override
    public long getId() {
        return id;
    }

    @Override
    public void setCallDate(long callDate) {
        this.callDate = callDate;
    }

    @Override
    public long getCallDate() {
        return callDate;
    }

}