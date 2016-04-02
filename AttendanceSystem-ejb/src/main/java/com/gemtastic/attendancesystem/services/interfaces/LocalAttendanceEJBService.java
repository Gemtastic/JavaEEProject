/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.gemtastic.attendancesystem.services.interfaces;

import com.gemtastic.attendencesystem.enteties.Lectures;
import com.gemtastic.attendencesystem.helpenteties.Attendance;
import javax.ejb.Local;

/**
 *
 * @author Gemtastic
 */
@Local
public interface LocalAttendanceEJBService {
    
    public Attendance[] getAttendance(Lectures l);
    
    public boolean saveAttendance(Attendance[] attendance);
}
