package com.gemtastic.attendancesystem.converters;

import com.gemtastic.attendancesystem.services.CRUDservices.interfaces.LocalUserTypeEJBService;
import com.gemtastic.attendencesystem.enteties.UserTypes;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;

/**
 * A user type converter managed bean.
 * @author Aizic Moisen
 */
@ManagedBean(name="userTypeConverter")
public class UserTypeConverter implements Converter {
    
    @EJB
    LocalUserTypeEJBService uEJB;

    /**
     * Takes a string representing an id and returns the UserType with the 
     * corresponding id. 
     * 
     * @param fc
     * @param uic
     * @param id
     * @return 
     */
    @Override
    public UserTypes getAsObject(FacesContext fc, UIComponent uic, String id) {
        return uEJB.readOne(Integer.valueOf(id));
    }

    /**
     * Takes a user type and returns a string of its id.
     * @param fc
     * @param uic
     * @param o
     * @return 
     */
    @Override
    public String getAsString(FacesContext fc, UIComponent uic, Object o) {
        UserTypes type = (UserTypes) o;
        return String.valueOf(type.getId());
    }
}
