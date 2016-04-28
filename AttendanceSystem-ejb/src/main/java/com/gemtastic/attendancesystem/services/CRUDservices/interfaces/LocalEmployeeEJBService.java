package com.gemtastic.attendancesystem.services.CRUDservices.interfaces;

import com.gemtastic.attendancesystem.services.interfaces.CRUDService;
import com.gemtastic.attendencesystem.enteties.Employees;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author Gemtastic
 */
@Local
public interface LocalEmployeeEJBService extends CRUDService<Employees> {

    public List<Employees> findAllByPosition(String position);
}
