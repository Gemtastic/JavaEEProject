/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.gemtastic.attendancesystem.managedbeans;

import com.gemtastic.attendancesystem.services.CRUDservices.interfaces.LocalLectureEJBService;
import com.gemtastic.attendancesystem.services.interfaces.LocalAttendanceEJBService;
import com.gemtastic.attendencesystem.enteties.Courses;
import com.gemtastic.attendencesystem.enteties.Lectures;
import com.gemtastic.attendencesystem.helpenteties.Attendance;
import java.util.Arrays;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.RequestScoped;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;

/**
 *
 * @author Gemtastic
 */
@ManagedBean(name="attendances")
@RequestScoped
public class AttendanceMB {
    
    private Attendance[] att;
    private Lectures lecture;
    private Courses course;
    
    @ManagedProperty("#{param.id}")
    private int id;
    
    @EJB
    LocalAttendanceEJBService aEJB;
    @EJB
    LocalLectureEJBService lEJB;

    public AttendanceMB() {
    }
    
    @PostConstruct
    public void init() {
        System.out.println("You initialized an attendance bean!");
        System.out.println("Param: " + FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("id"));
        if(id != 0) {
            Lectures l = lEJB.readOne(id);
            setUp(l);
        }
    }
    
    public void setUp(Lectures lecture) {
        course = lecture.getCourse();
        this.lecture = lecture;
        if(course != null) {
            att = new Attendance[course.getStudentsList().size()];
            att = aEJB.getAttendance(lecture);
        }
    }
    
    public void submitAttendance(ActionEvent e) {
        System.out.println("You submitted your attendance!");
        System.out.println(Arrays.toString(att));
        aEJB.saveAttendance(att);
    }
    
    public String viewAttendance(Lectures l) {
        System.out.println("You're viewing the attendance!");
        System.out.println("lecture: " + l);
        FacesContext.getCurrentInstance().getExternalContext().getFlash().put("lecture", l);
        return "lectures/attendance?faces-redirect=true";
    }
    
    public Attendance[] getAtt() {
        return att;
    }

    public void setAtt(Attendance[] att) {
        this.att = att;
    }

    public Lectures getLecture() {
        return lecture;
    }

    public void setLecture(Lectures lecture) {
        this.lecture = lecture;
    }

    public Courses getCourse() {
        return course;
    }

    public void setCourse(Courses course) {
        this.course = course;
    }
}
