/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.gemtastic.attendancesystem.services.interfaces;

import com.gemtastic.attendencesystem.enteties.Login;
import com.gemtastic.attendencesystem.enteties.UserTypes;
import com.gemtastic.attendencesystem.enteties.Users;
import javax.ejb.Local;


/**
 *
 * @author Gemtastic
 */
@Local
public interface LoginServices {
    
    public boolean verify(String username, String password);
    
    public String hash(String password);
    
    public boolean register(Users user, Login login);
    
    public boolean unregister(Users user);
    
    public UserTypes getUserType(String username);
}
