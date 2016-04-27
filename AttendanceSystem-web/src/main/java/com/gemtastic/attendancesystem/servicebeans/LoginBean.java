package com.gemtastic.attendancesystem.servicebeans;

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
 * Managed bean for handling the login logic.
 * 
 * @author Aizic Moisen
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

    /**
     * Attempts to log the user in. If successful it redirects to index, else
     * it redirects to the login page.
     * @return 
     */
    public String logIn() {
        boolean verified = loginService.verify(username, password);
        if (verified) {
            doLogin();
            return "index.xhtml?faces-redirect=true";
        }
        return "login.xhtml?faces-redirect=true";
    }

    /**
     * Logs a user out and redirects to the login page.
     * @return 
     */
    public String logOut() {
        if (session != null) {
            return doLogout();
        }
        return "login.xhtml?faces-redirect=true";
    }

    /**
     * Checks if the user is logged in.
     * 
     * @return 
     */
    public boolean isLoggedIn() {
        boolean online = false;
        if (sessionBean != null && session != null) {
            online = sessionBean.isLoggedIn();
        }
        return online;
    }
    
    /**
     * Gets the active users user type.
     * @return 
     */
    public String getUserType() {
        username = (String) sessionBean.getUsertype();
        return loginService.getUserType(username).getName();
    }

    /**
     * Sets up the user as logged in in the http session and the sessionBean.
     */
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

    /**
     * invalidates the session and resets the sessionBean.
     * @return 
     */
    private String doLogout() {
        session.invalidate();
        sessionBean.setLoggedIn(false);
        sessionBean.setUsername("");
        sessionBean.setUsertype("");
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
