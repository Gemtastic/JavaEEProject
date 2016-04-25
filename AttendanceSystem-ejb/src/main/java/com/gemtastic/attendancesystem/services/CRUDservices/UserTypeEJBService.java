package com.gemtastic.attendancesystem.services.CRUDservices;

import com.gemtastic.attendancesystem.services.CRUDservices.interfaces.LocalUserTypeEJBService;
import com.gemtastic.attendencesystem.enteties.UserTypes;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

/**
 * Local EJB bean for user type CRUD operations.
 * 
 * @author Aizic Moisen
 */
@Stateless
public class UserTypeEJBService implements LocalUserTypeEJBService {

    @PersistenceContext
    EntityManager em;
    
    /**
     * Finds one type based on id and returns it or null.
     * 
     * @param id
     * @return UserTypes
     */
    @Override
    public UserTypes readOne(int id) {
        UserTypes result = em.find(UserTypes.class, id);
        return result;
    }

    /**
     * Finds and returns all types as a list.
     * 
     * @return List{@literal <}UserTypes{@literal >} 
     */
    @Override
    public List<UserTypes> findAll() {
        TypedQuery<UserTypes> query = em.createNamedQuery("UserTypes.findAll", UserTypes.class);
        return query.getResultList();
    }

    /**
     * Deletes the given type or throws an exception.
     * 
     * @param type 
     */
    @Override
    public void delete(UserTypes type) {
        em.remove(type);
    }

    /**
     * Updates or inserts the given type depending on its existence 
     * in the database.
     * 
     * @param type
     * @return UserTypes
     */
    @Override
    public UserTypes upsert(UserTypes type) {
        UserTypes result = em.merge(type);
        return result;
    }
    
    /**
     * Finds all types of users.
     * 
     * @return 
     */
    @Override
    public List<UserTypes> getUserTypes() {
        return em.createNamedQuery("UserTypes.findAll", UserTypes.class).getResultList();
    }
}
