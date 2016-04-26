package com.gemtastic.attendancesystem.converters;

import com.gemtastic.attendancesystem.services.CRUDservices.interfaces.LocalCourseEJBService;
import com.gemtastic.attendencesystem.enteties.Courses;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;

/**
 * Converter for courses.
 * 
 * @author Aizic Moisen
 */
@ManagedBean(name="courseConverter")
public class CourseConverter implements Converter {
    @EJB
    LocalCourseEJBService ejb;
    
    /**
     * Takes a string integer and fetches the course with the corresponding id.
     * 
     * @param fc
     * @param uic
     * @param value
     * @return 
     */
    @Override
    public Object getAsObject(FacesContext fc, UIComponent uic, String value) {
        Courses course = ejb.readOne(Integer.valueOf(value));
        return course;
    }
    
    /**
     * Gets a Course object and transforms it into a string containing its id.
     * @param fc
     * @param uic
     * @param o
     * @return 
     */
    @Override
    public String getAsString(FacesContext fc, UIComponent uic, Object o) {
        Courses course = (Courses) o;
        return String.valueOf(course.getId());
    }
}
