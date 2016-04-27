package com.gemtastic.attendancesystem.servicebeans;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

/**
 * Session bean that keeps track of the logged in user and if it's logged in.
 * 
 * @author Aizic Moisen
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
