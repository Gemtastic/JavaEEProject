package com.gemtastic.attendancesystem.services;

import com.gemtastic.attendancesystem.services.CRUDservices.interfaces.LocalUserEJBService;
import com.gemtastic.attendancesystem.services.interfaces.LoginServices;
import com.gemtastic.attendencesystem.enteties.Login;
import com.gemtastic.attendencesystem.enteties.UserTypes;
import com.gemtastic.attendencesystem.enteties.Users;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import org.mindrot.jbcrypt.BCrypt;

/**
 * A login service for handling login, logout, verification and other user
 * related queries.
 * 
 * @author Aizic Moisen
 */
@Stateless
public class LoginService implements LoginServices{

    @EJB
    LocalUserEJBService uEJB;
    
    @PersistenceContext
    EntityManager em;
    
    /**
     * Verifies the given password with the username's stored password.
     * 
     * @param username
     * @param password
     * @return 
     */
    @Override
    public boolean verify(String username, String password) {
        boolean success = false;
        Login login = null;
        try {
            login = uEJB.findLoginUser(username);
        } catch (Exception e) {
            System.out.println("Could not find user: " + e);
        }
        
        
        if(login != null && login.getPassword() != null){
            success = BCrypt.checkpw(password, login.getPassword());
        }
        return success;
    }

    /**
     * Hashing function for passwords.
     * 
     * @param password
     * @return 
     */
    @Override
    public String hash(String password) {
        String salt = BCrypt.gensalt();
        String hashed = BCrypt.hashpw(password, salt);
        
        return hashed;
    }

    /**
     * Registering function for a new user.
     * 
     * @param user
     * @param login
     * @return 
     */
    @Override
    public boolean register(Users user, Login login) {
        Users userResult = em.merge(user);
        Login loginResult = em.merge(login);
        
        return userResult.getId() != null && loginResult != null;
    }

    /**
     * Method for unregistering a user. Not implemented in current version. Will
     * throw an exception.
     * @param user
     * @return 
     */
    @Override
    public boolean unregister(Users user) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    /**
     * Finds the user type of a user with the given user name.
     * 
     * @param username
     * @return 
     */
    @Override
    public UserTypes getUserType(String username) {
        Users user = uEJB.findByUser(username);
        return user.getType();
    }
    
}
