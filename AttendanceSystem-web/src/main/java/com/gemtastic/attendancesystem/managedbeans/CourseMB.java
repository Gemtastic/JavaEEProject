/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.gemtastic.attendancesystem.managedbeans;

import com.gemtastic.attendancesystem.services.CRUDservices.interfaces.LocalCourseEJBService;
import com.gemtastic.attendancesystem.services.CRUDservices.interfaces.LocalEmployeeEJBService;
import com.gemtastic.attendancesystem.services.CRUDservices.interfaces.LocalStudentEJBService;
import com.gemtastic.attendancesystem.services.interfaces.LocalAttendanceEJBService;
import com.gemtastic.attendencesystem.enteties.Courses;
import com.gemtastic.attendencesystem.enteties.Employees;
import com.gemtastic.attendencesystem.enteties.Lectures;
import com.gemtastic.attendencesystem.enteties.Students;
import com.gemtastic.attendencesystem.helpenteties.Attendance;
import java.io.IOException;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;

/**
 *
 * @author Gemtastic
 */
@ManagedBean(name="courses")
@SessionScoped
public class CourseMB {
    
    @EJB
    LocalCourseEJBService cEJB;
    @EJB
    LocalEmployeeEJBService eEJB;
    @EJB
    LocalStudentEJBService sEJB;
    @EJB
    LocalAttendanceEJBService aEJB;
    
    private Attendance[] att;
    
    private int id;
    private String name;
    private int points;
    private Date start;
    private Date stop;
    private Courses course;
    private Employees teacher;
    private Lectures lecture;
    private List<Employees> teachers;
    private List<Courses> all;
    
    @PostConstruct
    public void init() {
        teachers = eEJB.findAll();
        all = cEJB.findAll();
    }
    
    public void onSubmit(ActionEvent event){
        System.out.println("You submitted it!");
        teacher = new Employees();
        teacher.setId(1);
        course = new Courses();
        course.setName(name);
        course.setPoints(points);
        course.setStart(start);
        course.setStop(stop);
        course.setTeacher(teacher);
        System.out.println("Finished" + course);
        Courses c = cEJB.upsert(course);
        System.out.println("upserted: " + c);
    }
    
    public void submitAttendance(ActionEvent e) {
        System.out.println("You submitted your attendance!");
        System.out.println(Arrays.toString(att));
        aEJB.saveAttendance(att);
        // TODO: Implement a mapping between student and attendance
    }
    
    public void viewCourse(Courses c) throws IOException {
        setCourse(c);
        FacesContext.getCurrentInstance().getExternalContext()
            .redirect("course.xhtml");
    }
    
    public void viewAttendance(Lectures l) throws IOException {
        System.out.println("You're viewing the attendance! ");
        att = new Attendance[course.getStudentsList().size()];

        att = aEJB.getAttendance(l);
        setLecture(l);
        FacesContext.getCurrentInstance().getExternalContext()
            .redirect("lectures/attendance.xhtml");
    }
    
    public boolean attending(int id) {
        Students s = new Students();
        s.setId(id);
        Students attend = sEJB.readOne(s);
        List<Students> attending = lecture.getStudentsList();
        boolean b = attending.contains(attend);
        System.out.println("Result attending: " + b);
        return b;
    }

    public void addStudentToCourse() throws IOException{
        System.out.println("Redirecting to tstudens...");
        FacesContext.getCurrentInstance().getExternalContext()
            .redirect("../students/addToCourse.xhtml");
    }
    
    public CourseMB() {
    }

    
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getPoints() {
        return points;
    }

    public void setPoints(int points) {
        this.points = points;
    }

    public Date getStart() {
        return start;
    }

    public void setStart(Date start) {
        this.start = start;
    }

    public Date getStop() {
        return stop;
    }

    public void setStop(Date stop) {
        this.stop = stop;
    }

    public Employees getTeacher() {
        return teacher;
    }

    public void setTeacher(Employees teacher) {
        this.teacher = teacher;
    }

    public Courses getCourse() {
        return course;
    }

    public void setCourse(Courses course) {
        this.course = course;
    }

    public List<Employees> getTeachers() {
        return teachers;
    }

    public void setTeachers(List<Employees> teachers) {
        this.teachers = teachers;
    }

    public List<Courses> getAll() {
        return all;
    }

    public void setAll(List<Courses> all) {
        this.all = all;
    }

    public Lectures getLecture() {
        return lecture;
    }

    public void setLecture(Lectures lecture) {
        this.lecture = lecture;
    }

    public Attendance[] getAtt() {
        return att;
    }

    public void setAtt(Attendance[] att) {
        this.att = att;
    }
}
