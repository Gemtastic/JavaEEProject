/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.gemtastic.attendancesystem.sessionbeans;

import com.gemtastic.attendancesystem.services.interfaces.LoginServices;
import java.io.Serializable;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.RequestScoped;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpSession;

/**
 *
 * @author Gemtastic
 */
@RequestScoped
@ManagedBean(name = "login")
public class LoginBean implements Serializable {

    @EJB
    LoginServices loginService;
    
    @ManagedProperty(value="#{sessionBean}")
    public SessionBean sessionBean;

    private String username;
    private String password;
    private final String loggedIn = "isLoggedIn";
    private final String loggedInUser = "loggedInUser";
    private HttpSession session;

    @PostConstruct
    public void init() {
        session = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(false);
    }

    public String logIn() {
        boolean verified = loginService.verify(username, password);
        if (verified) {
            System.out.println("User credentials are valid!");
            doLogin();
            return "index.xhtml?faces-redirect=true";
        }
        return "login.xhtml?faces-redirect=true";
    }

    public String logOut() {
        if (session != null) {
            return doLogout();
        }
        return "login.xhtml?faces-redirect=true";
    }

    public boolean isLoggedIn() {
        boolean online = false;
        if (session != null) {
            Boolean attribute = (Boolean) session.getAttribute(loggedIn);
            if (attribute != null) {
                online = attribute;
            }
        }
        return online;
    }
    
    public String getUserType() {
        System.out.println("Getting the user type");
        username = (String) session.getAttribute(loggedInUser);
        return loginService.getUserType(username).getName();
    }

    private void doLogin() {
        if (session != null) {
            String userType = loginService.getUserType(username).getName();
            session.setAttribute(loggedIn, true);
            session.setAttribute(loggedInUser, username);
            sessionBean.setLoggedIn(true);
            sessionBean.setUsertype(userType);
            sessionBean.setUsername(username);
        }
    }

    private String doLogout() {
        session.invalidate();
        return "login.xhtml?faces-redirect=true";
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public SessionBean getSessionBean() {
        return sessionBean;
    }

    public void setSessionBean(SessionBean sessionBean) {
        this.sessionBean = sessionBean;
    }
}
