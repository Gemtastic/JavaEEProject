package com.gemtastic.attendancesystem.managedbeans;

import com.gemtastic.attendancesystem.services.CRUDservices.interfaces.LocalEmployeeEJBService;
import com.gemtastic.attendancesystem.services.CRUDservices.interfaces.LocalUserTypeEJBService;
import com.gemtastic.attendancesystem.services.interfaces.LoginServices;
import com.gemtastic.attendencesystem.enteties.Employees;
import com.gemtastic.attendencesystem.enteties.Login;
import com.gemtastic.attendencesystem.enteties.UserTypes;
import com.gemtastic.attendencesystem.enteties.Users;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

/**
 *
 * @author Gemtastic
 */
@ManagedBean(name="userMB")
@ViewScoped
public class UserMB {
    
    @EJB
    LoginServices loginEJB;
    @EJB
    LocalUserTypeEJBService uEJB;
    @EJB
    LocalEmployeeEJBService eEJB;
    
    private String username;
    private String password;
    private String email;
    private Employees employee;
    private UserTypes userType;
    
    private List<Employees> employees;
    private List<UserTypes> types;

    @PostConstruct
    public void init() {
        employees = eEJB.findAll();
        types = uEJB.getUserTypes();
    }
    
    public UserMB() {
    }

    public String registerUser() {
        System.out.println("About to register this user: ");
        Users user = new Users();
        user.setEmail(email);
        user.setEmployee(employee);
        user.setType(userType);
        user.setUsername(username);
        user.setFailedlogins(0);
        Login login = new Login();
        login.setUsername(username);
        login.setPassword(loginEJB.hash(password));
        
        loginEJB.register(user, login);
        
        return "/index.xhtml?faces-redirect=true";
    }
    
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Employees getEmployee() {
        return employee;
    }

    public void setEmployee(Employees employee) {
        this.employee = employee;
    }

    public UserTypes getUserType() {
        return userType;
    }

    public void setUserType(UserTypes userType) {
        this.userType = userType;
    }

    public List<Employees> getEmployees() {
        return employees;
    }

    public void setEmployees(List<Employees> employees) {
        this.employees = employees;
    }

    public List<UserTypes> getTypes() {
        return types;
    }

    public void setTypes(List<UserTypes> types) {
        this.types = types;
    }
}
