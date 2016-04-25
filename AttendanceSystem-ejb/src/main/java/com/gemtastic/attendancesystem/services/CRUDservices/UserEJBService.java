/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.gemtastic.attendancesystem.services.CRUDservices;

import com.gemtastic.attendancesystem.services.CRUDservices.interfaces.LocalUserEJBService;
import com.gemtastic.attendencesystem.enteties.Login;
import com.gemtastic.attendencesystem.enteties.Users;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

/**
 * Local EJB bean for managing the user entity and it's CRUD along with some
 * login operations.
 * 
 * @author Aizic Moisen
 */
@Stateless
public class UserEJBService implements LocalUserEJBService {

    @PersistenceContext
    private EntityManager em;
    
    /**
     * Finds the user of the given object by id and returns it or null.
     * 
     * @param id
     * @return Users
     */
    @Override
    public Users readOne(int id) {
        Users result = em.find(Users.class, id);
        return result;
    }

    /**
     * Finds and returns a list of all users.
     * 
     * @return List{@literal <}Courses{@literal >}
     */
    @Override
    public List<Users> findAll() {
        TypedQuery<Users> query = em.createNamedQuery("Users.findAll", Users.class);
        return query.getResultList();
    }

    /**
     * Deletes the given user or throws an exception.
     * 
     * @param user
     */
    @Override
    public void delete(Users user) {
        em.remove(user);
    }

    /**
     * Updates or inserts the given user depending on the existence 
     * in the database.
     * 
     * @param user
     * @return Users
     */
    @Override
    public Users upsert(Users user) {
        Users result = em.merge(user);
        return result;
    }
    
    /**
     * Find user by the given username.
     * 
     * @param uName
     * @return 
     */
    @Override
    public Users findByUser(String uName) {
        Users result = em.createNamedQuery("Users.findByUsername", Users.class).setParameter("username", uName).getSingleResult();
        return result;
    }

    /**
     * Finds a login entity with the given username.
     * 
     * @param uName
     * @return 
     */
    @Override
    public Login findLoginUser(String uName) {
        return em.createNamedQuery("Login.findByUsername", Login.class).setParameter("username", uName).getSingleResult();
    }
}
