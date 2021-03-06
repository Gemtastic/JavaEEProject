package com.gemtastic.attendancesystem.services.CRUDservices;

import com.gemtastic.attendancesystem.services.CRUDservices.interfaces.LocalPositionEJBService;
import com.gemtastic.attendencesystem.enteties.Position;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

/**
 * Local EJB bean for handling the position entity and its CRUD operations.
 * 
 * @author Aizic Moisen
 */
@Stateless
public class PositionEJBService implements LocalPositionEJBService {

    @PersistenceContext
    private EntityManager em;
    
    /**
     * Finds the given position and returns it or null.
     * 
     * @param id
     * @return Position
     */
    @Override
    public Position readOne(int id) {
        Position result = em.find(Position.class, id);
        return result;
    }

    /**
     * Finds and returns all positions as a list.
     * 
     * @return List{@literal <}Position{@literal >}
     */
    @Override
    public List<Position> findAll() {
        TypedQuery<Position> query = em.createNamedQuery("Position.findAll", Position.class);
        return query.getResultList();
    }

    /**
     * Removes the given position or throws an exception.
     * 
     * @param position 
     */
    @Override
    public void delete(Position position) {
        em.remove(position);
    }

    /**
     * Updates or inserts the given position depending on its existence 
     * in the database.
     * 
     * @param position
     * @return 
     */
    @Override
    public Position upsert(Position position) {
        Position result = em.merge(position);
        return result;
    }
    
}
