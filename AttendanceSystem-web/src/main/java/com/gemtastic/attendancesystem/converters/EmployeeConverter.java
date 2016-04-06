/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.gemtastic.attendancesystem.converters;

import com.gemtastic.attendancesystem.services.CRUDservices.interfaces.LocalEmployeeEJBService;
import com.gemtastic.attendencesystem.enteties.Employees;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;

/**
 *
 * @author Gemtastic
 */
@ManagedBean(name="employeeConverter")
//@FacesConverter("employeeConverter")
public class EmployeeConverter implements Converter{

    @EJB
    LocalEmployeeEJBService ejb;
    
    @Override
    public Object getAsObject(FacesContext fc, UIComponent uic, String value) {
        Employees employee = ejb.readOne(Integer.valueOf(value));
        return employee;
    }

    @Override
    public String getAsString(FacesContext fc, UIComponent uic, Object o) {
//        System.out.println("object: " + o);
        Employees employee = (Employees) o;
//        System.out.println("Employee id: " + employee.getId());
        return String.valueOf(employee.getId());
    }   
}
