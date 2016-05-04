package com.gemtastic.attendancesystem.services.CRUDservices;

import com.gemtastic.attendancesystem.services.CRUDservices.interfaces.LocalLectureEJBService;
import com.gemtastic.attendencesystem.enteties.Courses;
import com.gemtastic.attendencesystem.enteties.Employees;
import com.gemtastic.attendencesystem.enteties.Lectures;
import java.time.Instant;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.annotation.PreDestroy;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

/**
 * Local EJB bean for handling the Lecture entity and its CRUD operations.
 * 
 * @author Aizic Moisen
 */
@Stateless
public class LectureEJBService implements LocalLectureEJBService {
    
    @PersistenceContext
    private EntityManager em;

    /**
     * Finds one lecture based on id and returns it or null.
     * 
     * @param id
     * @return Lectures
     */
    @Override
    public Lectures readOne(int id) {
        Lectures result = em.find(Lectures.class, id);
        return result;
    }
    
    /**
     * Finds a lecture by a date and returns it.
     * 
     * @param date
     * @return 
     */
    @Override
    public List<Lectures> findByDate(LocalDate date){
        List<Lectures> lectures =  em.createNamedQuery("Lectures.findByDate", Lectures.class)
                                .setParameter("date", Date.from(Instant.ofEpochMilli(date.toEpochDay())))
                                .getResultList();
        return lectures;
    }
    
    /**
     * Finds a list of lectures based on a given course's name.
     * 
     * @param course
     * @return 
     */
    @Override
    public List<Lectures> findByCourseName(Courses course) {
        Courses result = em.createNamedQuery("Courses.findByName", Courses.class)
                            .setParameter("name", course.getName())
                            .getSingleResult();
        return result.getLecturesList();
    }
    
    /**
     * Find all the lectures of a given teacher if any.
     * 
     * @param employee
     * @return 
     */
    @Override
    public List<Lectures> findByTeacher(Employees employee){
        List<Lectures> lectures = new ArrayList<>();
        List<Courses> result = em.find(Employees.class, employee.getId()).getCoursesList();
        
        for(Courses c : result){
            List<Lectures> l = c.getLecturesList();
            for(Lectures le : l){
                lectures.add(le);
            }
        }
        
        return lectures;
    }

    /**
     * Finds and returns a list of all lectures.
     * 
     * @return List{@literal <}Lectures{@literal >} 
     */
    @Override
    public List<Lectures> findAll() {
        TypedQuery query = em.createNamedQuery("Lectures.findAll", Lectures.class);
        return query.getResultList();
    }

    /**
     * Removes the given lecture or throws an exception.
     * 
     * @param lecture 
     */
    @Override
    public void delete(Lectures lecture) {
        Lectures toRemove = em.find(Lectures.class, lecture.getId());
        Courses course = toRemove.getCourse();
        List<Lectures> lectures =  course.getLecturesList();
        lectures.remove(toRemove);
        course.setLecturesList(lectures);
        em.merge(course);
        em.remove(toRemove);
    }

    /**
     * Updates or inserts the given lecture depending on its existence 
     * in the database.
     * 
     * @param lecture
     * @return Lectures
     */
    @Override
    public Lectures upsert(Lectures lecture) {
        Lectures result = em.merge(lecture);
        Courses course = em.find(Courses.class, lecture.getCourse().getId());
        List<Lectures> lectures = course.getLecturesList();
        if(lectures.contains(lecture)) {
            course.getLecturesList().remove(lecture);
        } 
        course.getLecturesList().add(result);
        em.merge(course);
        return result;
    }
    
    /**
     * Makes sure the entity manager is closed before the 
     * destruction of the bean.
     */
    @PreDestroy
    public void destruct() {
        em.close();
    }

    /**
     * Finds all attended lectures for the given student inside the 
     * given timeframe.
     * 
     * @param studentId
     * @param startDate
     * @param stopDate
     * @return 
     */
    @Override
    public List<Lectures> findByStudentAndDate(int studentId, LocalDate startDate, LocalDate stopDate) {
        System.out.println("Attempting query:");
        List<Lectures> result = null;
        
        try {
            System.out.println("Trying an SQL string: SELECT l.id, l.course, l.\"date\", l.\"start\", l.stop FROM Attendance a INNER JOIN Lectures l on lecture = l.id AND l.date BETWEEN '" + startDate + "' AND '"+ stopDate + "' AND student = " + String.valueOf(studentId) + ";");
            result = em.createNativeQuery("SELECT l.id, l.course, l.\"date\", l.\"start\", l.stop FROM Attendance a INNER JOIN Lectures l on lecture = l.id AND l.date BETWEEN '" + startDate + "' AND '"+ stopDate + "' AND student = " + String.valueOf(studentId) + ";", Lectures.class).getResultList();
            System.out.println("Result from string: " + result);
        } catch (Exception e) {
            System.out.println("Exception while finding by date and student: " + e);
        }
        return result;
    }
}
