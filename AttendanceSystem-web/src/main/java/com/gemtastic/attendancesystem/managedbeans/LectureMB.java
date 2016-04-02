package com.gemtastic.attendancesystem.managedbeans;

import com.gemtastic.attendancesystem.services.CRUDservices.interfaces.LocalCourseEJBService;
import com.gemtastic.attendancesystem.services.CRUDservices.interfaces.LocalLectureEJBService;
import com.gemtastic.attendancesystem.services.CRUDservices.interfaces.LocalStudentEJBService;
import com.gemtastic.attendencesystem.enteties.Courses;
import com.gemtastic.attendencesystem.enteties.Employees;
import com.gemtastic.attendencesystem.enteties.Lectures;
import com.gemtastic.attendencesystem.enteties.Students;
import java.util.Date;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;

/**
 *
 * @author Gemtastic
 */
@ManagedBean(name="lecture")
public class LectureMB {
    
    public int id;
    public Courses course;
    public Date date;
    public Date startTime;
    public Date stopTime;
    public Employees teacher;
    
    public void onCreate() {
        Lectures l = new Lectures();
        l.setCourse(course);
        l.setDate(date);
        l.setStart(startTime);
        l.setStop(stopTime);
        lEJB.upsert(l);
    }
    
    @PostConstruct
    public void init() {
        lectures = lEJB.findAll();
        courses = cEJB.findAll();
    }
    
    @EJB
    private LocalLectureEJBService lEJB;
    
    @EJB
    private LocalStudentEJBService sEJB;
    
    @EJB
    private LocalCourseEJBService cEJB;
    
    public List<Lectures> lectures;
    public List<Lectures> attendance;
    public List<Courses> courses;
    
    public List<Students> getStudentList(){
        Lectures lecture = new Lectures();
        lecture.setId(id);
        Lectures l = lEJB.readOne(lecture);
        return l.getStudentsList();
    }
    
    public Lectures getLectureById(int id){
        Lectures lecture = new Lectures();
        lecture.setId(id);
        Lectures l = lEJB.readOne(lecture);
        return l;
    }

    public LectureMB() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Courses getCourse() {
        return course;
    }

    public void setCourse(Courses course) {
        this.course = course;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public Date getStartTime() {
        return startTime;
    }

    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    public Date getStopTime() {
        return stopTime;
    }

    public void setStopTime(Date stopTime) {
        this.stopTime = stopTime;
    }

    public Employees getTeacher() {
        return teacher;
    }

    public void setTeacher(Employees teacher) {
        this.teacher = teacher;
    }

    public List<Lectures> getLectures() {
        return lectures;
    }

    public void setLectures(List<Lectures> lectures) {
        this.lectures = lectures;
    }

    public List<Lectures> getAttendance() {
        return attendance;
    }

    public void setAttendance(List<Lectures> attendance) {
        this.attendance = attendance;
    }

    public List<Courses> getCourses() {
        return courses;
    }

    public void setCourses(List<Courses> courses) {
        this.courses = courses;
    }
}
