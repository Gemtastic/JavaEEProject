package com.gemtastic.attendancesystem.services.CRUDservices;

import com.gemtastic.attendancesystem.services.CRUDservices.interfaces.LocalMessageEJBService;
import com.gemtastic.attendencesystem.enteties.Message;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 * Local EJB bean for handling the Message entity and its CRUP operations.
 * 
 * @author Aizic Moisen
 */
@Stateless
public class MessageEJBService implements LocalMessageEJBService {

    @PersistenceContext
    EntityManager em;
    
    /**
     * Returns the message of the provided id if there is one.
     * @param id
     * @return 
     */
    @Override
    public Message readOne(int id) {
        Message result = em.find(Message.class, id);
        return result;
    }

    /**
     * Returns a list of all the messages.
     * @return 
     */
    @Override
    public List<Message> findAll() {
        return em.createNamedQuery("Message.findAll", Message.class).getResultList();
    }

    /**
     * Deletes the message provided.
     * @param message 
     */
    @Override
    public void delete(Message message) {
        Message toRemove = em.find(Message.class, message.getId());
        em.merge(toRemove);
        em.remove(toRemove);
    }

    /**
     * Updates the given message or creates it if it doesn't already exist.
     * 
     * @param message
     * @return 
     */
    @Override
    public Message upsert(Message message) {
        return em.merge(message);
    }
}
