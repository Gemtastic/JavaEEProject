/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.gemtastic.attendancesystem.converters;

import com.gemtastic.attendancesystem.services.CRUDservices.interfaces.LocalStudentEJBService;
import com.gemtastic.attendencesystem.enteties.Students;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;

/**
 *
 * @author Gemtastic
 */
@ManagedBean(name="studentConverter")
public class StudentConverter implements Converter{

    @EJB
    LocalStudentEJBService ejb;
    
    @Override
    public Object getAsObject(FacesContext fc, UIComponent uic, String value) {
        Students s = new Students();
        s.setId(Integer.valueOf(value));
        Students student = ejb.readOne(s);
        return student;
    }

    @Override
    public String getAsString(FacesContext fc, UIComponent uic, Object o) {
        Students student = (Students) o;
        return String.valueOf(student.getId());
    }  
}
