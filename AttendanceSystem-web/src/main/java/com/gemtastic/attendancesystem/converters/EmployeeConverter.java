package com.gemtastic.attendancesystem.converters;

import com.gemtastic.attendancesystem.services.CRUDservices.interfaces.LocalEmployeeEJBService;
import com.gemtastic.attendencesystem.enteties.Employees;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;

/**
 * Employee converting managed bean.
 * 
 * @author Aizic Moisen
 */
@ManagedBean(name="employeeConverter")
public class EmployeeConverter implements Converter{

    @EJB
    LocalEmployeeEJBService ejb;
    
    /**
     * Takes a string of an id and returns the employee of the corresponding id.
     * 
     * @param fc
     * @param uic
     * @param value
     * @return 
     */
    @Override
    public Object getAsObject(FacesContext fc, UIComponent uic, String value) {
        Employees employee = ejb.readOne(Integer.valueOf(value));
        return employee;
    }

    /**
     * Takes an employee and converts it to a string of its id.
     * 
     * @param fc
     * @param uic
     * @param o
     * @return 
     */
    @Override
    public String getAsString(FacesContext fc, UIComponent uic, Object o) {
        Employees employee = (Employees) o;
        return String.valueOf(employee.getId());
    }   
}
