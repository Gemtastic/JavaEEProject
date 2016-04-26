package com.gemtastic.attendancesystem.managedbeans;

import com.gemtastic.attendancesystem.services.CRUDservices.EmployeeEJBService;
import com.gemtastic.attendencesystem.enteties.Employees;
import com.gemtastic.attendencesystem.enteties.Position;
import java.util.List;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;

/**
 * Managed bean for the employees.
 * @author Aizic Moisen
 */
@ManagedBean(name="teacher")
@RequestScoped
public class EmployeeMB {
    Position position = new Position(0, "teacher");
    
    @EJB
    private EmployeeEJBService eEJB;
    
    private List<Employees> teachers = eEJB.findAllByPosition(position);
    
    public List<Employees> findAllTeachers (){
        return teachers;
    }
    
    public Employees findTeacher(Employees teacher){
        return eEJB.readOne(teacher.getId());
    }
    
}
