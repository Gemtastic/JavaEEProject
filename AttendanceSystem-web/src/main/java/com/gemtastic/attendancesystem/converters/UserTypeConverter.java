/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.gemtastic.attendancesystem.converters;

import com.gemtastic.attendancesystem.services.CRUDservices.interfaces.LocalUserEJBService;
import com.gemtastic.attendencesystem.enteties.UserTypes;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;

/**
 *
 * @author Gemtastic
 */
@ManagedBean(name="userTypeConverter")
public class UserTypeConverter implements Converter {
    
    @EJB
    LocalUserEJBService uEJB;

    @Override
    public UserTypes getAsObject(FacesContext fc, UIComponent uic, String id) {
        return uEJB.findUserType(Integer.valueOf(id));
    }

    @Override
    public String getAsString(FacesContext fc, UIComponent uic, Object o) {
        UserTypes type = (UserTypes) o;
        return String.valueOf(type.getId());
    }
    
}
