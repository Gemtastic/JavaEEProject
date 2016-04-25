package com.gemtastic.attendancesystem.services.CRUDservices;

import com.gemtastic.attendancesystem.services.CRUDservices.interfaces.LocalEmployeeEJBService;
import com.gemtastic.attendencesystem.enteties.Employees;
import com.gemtastic.attendencesystem.enteties.Position;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

/**
 * Local EJB bean for handling the Employee entity and its CRUD.
 * 
 * @author Aizic Moisen
 */
@Stateless
public class EmployeeEJBService implements LocalEmployeeEJBService {
    
    @PersistenceContext
    private EntityManager em;

    /**
     * Finds the given employee by id and returns it or null.
     * 
     * @param id
     * @return 
     */
    @Override
    public Employees readOne(int id) {
        Employees result = em.find(Employees.class, id);
        return result;
    }

    /**
     * Finds and returns all employees as a list.
     * 
     * @return List{@literal <}Employees{@literal >}
     */
    @Override
    public List<Employees> findAll() {
        TypedQuery<Employees> query = em.createNamedQuery("Employees.findAll", Employees.class);
        return query.getResultList();
    }

    /**
     * Deletes the given employee or throws an exception.
     * 
     * @param employee 
     */
    @Override
    public void delete(Employees employee) {
        em.remove(employee);
    }

    /**
     * Updates or inserts the given employee depending on its existence 
     * in the database.
     * 
     * @param employee
     * @return Employees
     */
    @Override
    public Employees upsert(Employees employee) {
        Employees result = em.merge(employee);
        return result;
    }
    
    /**
     * Finds all Employees by position and returns them as list.
     * 
     * @param position
     * @return List{@literal <}Employees{@literal >}
     */
    public List<Employees> findAllByPosition(Position position) {
        Position query = em.createNamedQuery("Position.findByName", Position.class).setParameter("name", position.getName()).getSingleResult();
        return query.getEmployeesList();
    }
}
