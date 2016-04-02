/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.gemtastic.attendancesystem.services.CRUDservices.interfaces;

import com.gemtastic.attendancesystem.services.interfaces.CRUDService;
import com.gemtastic.attendencesystem.enteties.Courses;
import com.gemtastic.attendencesystem.enteties.Employees;
import com.gemtastic.attendencesystem.enteties.Lectures;
import java.time.LocalDate;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author Gemtastic
 */
@Local
public interface LocalLectureEJBService extends CRUDService<Lectures>{

    public List<Lectures> findByDate(LocalDate date);
    
    public List<Lectures> findByCourseName(Courses course);
    
    public List<Lectures> findByTeacher(Employees employee);
    
}
