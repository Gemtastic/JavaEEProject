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
import java.io.IOException;
import java.util.Date;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.RequestScoped;
import javax.faces.context.FacesContext;

/**
 *
 * @author Gemtastic
 */
@ManagedBean(name="courses")
@RequestScoped
public class CourseMB {
    
    @EJB
    LocalCourseEJBService cEJB;
    @EJB
    LocalEmployeeEJBService eEJB;
    @EJB
    LocalStudentEJBService sEJB;
    @EJB
    LocalAttendanceEJBService aEJB;
    
    
    @ManagedProperty("#{param.id}")
    private int id;
    
    @ManagedProperty("#{param.newCourse}")
    private Courses newCourse;
    
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
        if (course == null) {
            course = new Courses();
            System.out.println("You made a new course!");
        }
        System.out.println("You initialized a courses bean!");
        System.out.println("Param: " + FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("id"));
//        System.out.println("Flash: " + FacesContext.getCurrentInstance()
//                                                    .getExternalContext()
//                                                    .getFlash().get("test"));
        if (id != 0) {
            setUp();
        }
    }
    
    private void setUp() {
        try {
            course = cEJB.readOne(id);
            System.out.println("Lecture size: " + course.getLecturesList().size());
        } catch(NullPointerException e) {
            System.out.println("Parameter id is null. " + e);
        } catch(Exception e) {
            System.out.println("Could not catch the parameter id: " + e);
        }
    }
    
    public String onSubmit() {
        System.out.println("It worked!");
        System.out.println("Finished: " + course);
        Courses c = cEJB.upsert(course);
        System.out.println("upserted: " + c);
        course = c;
        return "course";
    }
    
    public String deleteCourse() {
        System.out.println("You want to delete: " + id);
        Courses placeholder = cEJB.readOne(id);
        cEJB.delete(placeholder);
        return "showCourses?faces-redirect=true";
    }
    
    public String editCourse() {
        System.out.println("Editable!");
        System.out.println("id: " + id);
        System.out.println("Course: " + course.getName());
        cEJB.upsert(course);
        return "course?id=" + id + "&faces-redirect=true";
    }
    
    // TODO useless
    public String viewCourse(Courses c) {
        System.out.println("You want to view the course: " + c);
        course = c;
        FacesContext.getCurrentInstance().getExternalContext().getFlash().put("test", c);
        return "course?faces-redirect=true";
    }
    
    public boolean attending(int id) {
        Students attend = sEJB.readOne(id);
        List<Students> attending = lecture.getStudentsList();
        boolean b = attending.contains(attend);
        System.out.println("Result attending: " + b);
        return b;
    }

    // TODO: useless
    public void addStudentToCourse() throws IOException{
        System.out.println("Redirecting to studens...");
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

    public Courses getNewCourse() {
        return newCourse;
    }

    public void setNewCourse(Courses newCourse) {
        this.newCourse = newCourse;
    }
}
