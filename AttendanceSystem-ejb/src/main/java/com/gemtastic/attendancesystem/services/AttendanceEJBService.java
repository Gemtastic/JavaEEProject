package com.gemtastic.attendancesystem.services;

import com.gemtastic.attendancesystem.services.CRUDservices.interfaces.LocalCourseEJBService;
import com.gemtastic.attendancesystem.services.CRUDservices.interfaces.LocalLectureEJBService;
import com.gemtastic.attendancesystem.services.interfaces.LocalAttendanceEJBService;
import com.gemtastic.attendencesystem.enteties.Courses;
import com.gemtastic.attendencesystem.enteties.Lectures;
import com.gemtastic.attendencesystem.enteties.Students;
import com.gemtastic.attendencesystem.helpenteties.Attendance;
import java.util.ArrayList;
import java.util.List;
import javax.ejb.EJB;
import javax.ejb.Stateless;

/**
 *
 * @author Gemtastic
 */
@Stateless
public class AttendanceEJBService implements LocalAttendanceEJBService {

    @EJB
    LocalLectureEJBService lEJB;
    
    /**
     * Sorts out a list of the attending students only. If no
     * students were present it returns an empty list.
     * 
     * @param attendance
     * @return 
     */
    public List<Students> sortAttending(Attendance[] attendance) {
        List<Students> attending = new ArrayList<>();
        
        for(Attendance a : attendance) {
            if(a.getAttending()){
                attending.add(a.getStudent());
            }
        }
        return attending;
    } 
    
    public Attendance[] convertToAttencanceArray(Lectures lecture) {
        Courses course = lecture.getCourse();
        
        List<Students> attending = lecture.getStudentsList();
        List<Students> students = course.getStudentsList();
        Attendance[] attendance = new Attendance[students.size()];
        
        
        for(int i = 0; i < students.size(); i++) {
            Students s = students.get(i);
            boolean attended = attending.contains(s);
            Attendance a = new Attendance(students.get(i), lecture, attended);
            attendance[i] = a;
        }
        return attendance;
    }
    
    @Override
    public Attendance[] getAttendance(Lectures lecture) {
        Lectures l;
        try {
            l = lEJB.readOne(lecture);
            return convertToAttencanceArray(l);
        } catch (Exception e) {
            System.out.println("Could not find lecture: " + e);
        }
        
        return null;
    }

    @Override
    public boolean saveAttendance(Attendance[] attendance) {
        
        List<Students> attending = sortAttending(attendance);
        
        try {
            Lectures lecture = attendance[0].getLecture();
            lecture.setStudentsList(attending);
            lEJB.upsert(lecture);
        } catch(Exception e) {
            System.out.println("The upset of attendance on lecture failed: " + e.getMessage());
            return false;
        }
        
        return true;
    }
    
}
