/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.gemtastic.attendancesystem.services.CRUDservices.interfaces;

import com.gemtastic.attendancesystem.services.interfaces.CRUDService;
import com.gemtastic.attendencesystem.enteties.CourseLevel;
import com.gemtastic.attendencesystem.enteties.Courses;
import com.gemtastic.attendencesystem.enteties.Languages;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author Gemtastic
 */
@Local
public interface LocalCourseEJBService extends CRUDService<Courses> {

    public List<CourseLevel> getLevels();
    
    public CourseLevel findLevel(int id);
    
    public Languages findLanguageByName(String name);
}
