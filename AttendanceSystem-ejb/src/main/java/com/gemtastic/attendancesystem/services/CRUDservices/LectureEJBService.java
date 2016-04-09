/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.gemtastic.attendancesystem.services.CRUDservices;

import com.gemtastic.attendancesystem.services.CRUDservices.interfaces.LocalLectureEJBService;
import com.gemtastic.attendencesystem.enteties.Courses;
import com.gemtastic.attendencesystem.enteties.Employees;
import com.gemtastic.attendencesystem.enteties.Lectures;
import java.sql.Date;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.PreDestroy;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

/**
 *
 * @author Gemtastic
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
    
    @Override
    public List<Lectures> findByDate(LocalDate date){
        List<Lectures> lectures =  em.createNamedQuery("Lectures.findByDate", Lectures.class)
                                .setParameter("date", Date.valueOf(date))
                                .getResultList();
        return lectures;
    }
    
    @Override
    public List<Lectures> findByCourseName(Courses course) {
        Courses result = em.createNamedQuery("Courses.findByName", Courses.class)
                            .setParameter("name", course.getName())
                            .getSingleResult();
        return result.getLecturesList();
    }
    
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
            System.out.println("Contained the lecture");
            course.getLecturesList().remove(lecture);
        } 
        course.getLecturesList().add(result);
        System.out.println("course lectures:" + course.getLecturesList().size());
        em.merge(course);
        return result;
    }
    
    @PreDestroy
    public void destruct() {
        System.out.println("I'm about to be destroyed!");
        em.close();
    }
}
