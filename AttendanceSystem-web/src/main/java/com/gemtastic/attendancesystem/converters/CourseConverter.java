/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.gemtastic.attendancesystem.converters;

import com.gemtastic.attendancesystem.services.CRUDservices.interfaces.LocalCourseEJBService;
import com.gemtastic.attendencesystem.enteties.Courses;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;

/**
 *
 * @author Gemtastic
 */
@ManagedBean(name="courseConverter")
public class CourseConverter implements Converter {
    @EJB
    LocalCourseEJBService ejb;
    
    @Override
    public Object getAsObject(FacesContext fc, UIComponent uic, String value) {
        Courses c = new Courses();
        c.setId(Integer.valueOf(value));
        Courses course = ejb.readOne(c);
        return course;
    }

    @Override
    public String getAsString(FacesContext fc, UIComponent uic, Object o) {
//        System.out.println("Course obj: " + o);
        Courses course = (Courses) o;
        return String.valueOf(course.getId());
    }
}
