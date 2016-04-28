package com.gemtastic.attendancesystem.converters;

import com.gemtastic.attendancesystem.services.CRUDservices.interfaces.LocalCourseEJBService;
import com.gemtastic.attendencesystem.enteties.CourseLevel;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;

/**
 * Converter for the CourseLevel object.
 * @author Aizic Moisen
 */
@ManagedBean(name="levelConverter")
public class LevelConverter implements Converter {

    @EJB
    LocalCourseEJBService cEJB;
    
    /**
     * Converts a string representation of a CourseLevel's id to a Course Level.
     * @param fc
     * @param uic
     * @param levelId
     * @return 
     */
    @Override
    public Object getAsObject(FacesContext fc, UIComponent uic, String levelId) {
        return cEJB.findLevel(Integer.parseInt(levelId));
    }

    /**
     * Returns a stringification of the CourseLevel's id.
     * @param fc
     * @param uic
     * @param o
     * @return 
     */
    @Override
    public String getAsString(FacesContext fc, UIComponent uic, Object o) {
        CourseLevel level = (CourseLevel) o;
        return level.getId().toString();
    }
    
}
