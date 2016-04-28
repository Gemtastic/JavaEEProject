package com.gemtastic.attendancesystem.services.CRUDservices;

import com.gemtastic.attendancesystem.services.CRUDservices.interfaces.LocalCourseEJBService;
import com.gemtastic.attendencesystem.enteties.CourseLevel;
import com.gemtastic.attendencesystem.enteties.Courses;
import com.gemtastic.attendencesystem.enteties.Languages;
import java.util.List;
import javax.annotation.PreDestroy;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

/**
 * Local EJB bean for handling the Course entity with its CRUD.
 * 
 * @author Aizic Moisen
 */
@Stateless
public class CourseEJBService implements LocalCourseEJBService {

    @PersistenceContext
    private EntityManager em;
    
    /**
     * Finds one course by id and returns it or null if the course doesn't exist.
     * 
     * @param id
     * @return course
     */
    @Override
    public Courses readOne(int id) {
        Courses result = em.find(Courses.class, id);
        return result;
    }

    /**
     * Finds all courses in database and returns the results in a list.
     * 
     * @return List{@literal <}Courses{@literal >}
     */
    @Override
    public List<Courses> findAll() {
        TypedQuery<Courses> result = em.createNamedQuery("Courses.findAll", Courses.class);
        return result.getResultList();
    }

    /**
     * Deletes the given course or throws an exception.
     * 
     * @param course 
     */
    @Override
    public void delete(Courses course) {
        Courses result = em.merge(course);
        em.remove(result);
    }
    
    /**
     * Updates or inserts the given course depending on its existence
     * in the database.
     * 
     * @param course
     * @return Courses
     */
    @Override
    public Courses upsert(Courses course) {
        Courses result = em.merge(course);
        return result;
    }
    
    /**
     * Ensures the entity manager gets closed before destruction.
     */
    @PreDestroy
    public void destruct() {
        em.close();
    }

    /**
     * Returns a list all level types.
     * @return 
     */
    @Override
    public List<CourseLevel> getLevels() {
        return em.createNamedQuery("CourseLevel.findAll").getResultList();
    }

    /**
     * Returns a Course level with the given id.
     * @param id
     * @return 
     */
    @Override
    public CourseLevel findLevel(int id) {
        return em.find(CourseLevel.class, id);
    }

    /**
     * Returns a language of the given name if it exists.
     * @param name
     * @return 
     */
    @Override
    public Languages findLanguageByName(String name) {
        try {
            return em.createNamedQuery("Languages.findByLanguage", Languages.class).setParameter("language", name).getSingleResult();
        } catch (NoResultException e) {
            return null;
        }
    }
}
