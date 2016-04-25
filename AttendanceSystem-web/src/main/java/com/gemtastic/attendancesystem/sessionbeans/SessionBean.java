/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.gemtastic.attendancesystem.sessionbeans;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

/**
 *
 * @author Gemtastic
 */
@SessionScoped
@ManagedBean(name="sessionBean")
public class SessionBean {
    
    private String username;
    private String usertype;
    private boolean loggedIn;

    public SessionBean() {
    }
    
    @PostConstruct
    public void init() {
        System.out.println("You just initiated a session bean!");
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getUsertype() {
        return usertype;
    }

    public void setUsertype(String usertype) {
        this.usertype = usertype;
    }

    public boolean isLoggedIn() {
        return loggedIn;
    }

    public void setLoggedIn(boolean loggedIn) {
        this.loggedIn = loggedIn;
    }
}
