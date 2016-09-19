/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ua.obolon.ponovoy.interfaces;

/**
 *
 * @author Alexander
 */
public interface User {

    void setId(long id);

    long getId();

    void setFirstName(String firstName);

    void setLastName(String lastName);

    void setIsActive(boolean isActive);

    void setTelephone(String telephone);

    String getFirstName();

    String getLastName();

    boolean isActive();

    String getTelephone();

}