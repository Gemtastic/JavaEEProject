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
    
    /**
     * Takes a lecture and returns an Attendance array.
     * 
     * @param lecture
     * @return 
     */
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
    
    /**
     * Takes a lecture and returns an array of Attendance
     * or null.
     * 
     * @param lecture
     * @return 
     */
    @Override
    public Attendance[] getAttendance(Lectures lecture) {
        Lectures l;
        try {
            l = lEJB.readOne(lecture.getId());
            return convertToAttencanceArray(l);
        } catch (Exception e) {
            System.out.println("Could not find lecture: " + e);
        }
        
        return null;
    }

    /**
     * Saves the given attendance in the database.
     * 
     * @param attendance
     * @return 
     */
    @Override
    public boolean saveAttendance(Attendance[] attendance) {
        
        List<Students> attending = sortAttending(attendance);
        
        try {
            Lectures lecture = attendance[0].getLecture();
            lecture.setStudentsList(attending);
            lEJB.upsert(lecture);
        } catch(Exception e) {
            System.out.println("The upsert of attendance on lecture failed: " + e);
            return false;
        }
        
        return true;
    }
    
}
