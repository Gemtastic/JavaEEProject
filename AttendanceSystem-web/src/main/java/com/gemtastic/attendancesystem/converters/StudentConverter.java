package com.gemtastic.attendancesystem.converters;

import com.gemtastic.attendancesystem.services.CRUDservices.interfaces.LocalStudentEJBService;
import com.gemtastic.attendencesystem.enteties.Students;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;

/**
 * Converter managed bean for the student entity.
 * 
 * @author Aizic Moisen
 */
@ManagedBean(name="studentConverter")
public class StudentConverter implements Converter{

    @EJB
    LocalStudentEJBService ejb;
    
    /**
     * Takes a string of an id and returns the student of the corresponding id.
     * 
     * @param fc
     * @param uic
     * @param value
     * @return 
     */
    @Override
    public Object getAsObject(FacesContext fc, UIComponent uic, String value) {
        Students student = ejb.readOne(Integer.valueOf(value));
        return student;
    }

    /**
     * Takes a student and returns a string conversion of its id.
     * 
     * @param fc
     * @param uic
     * @param o
     * @return 
     */
    @Override
    public String getAsString(FacesContext fc, UIComponent uic, Object o) {
        Students student = (Students) o;
        return String.valueOf(student.getId());
    }  
}
