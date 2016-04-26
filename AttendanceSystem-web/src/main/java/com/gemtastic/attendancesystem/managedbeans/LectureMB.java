package com.gemtastic.attendancesystem.managedbeans;

import com.gemtastic.attendancesystem.services.CRUDservices.interfaces.LocalCourseEJBService;
import com.gemtastic.attendancesystem.services.CRUDservices.interfaces.LocalLectureEJBService;
import com.gemtastic.attendancesystem.services.CRUDservices.interfaces.LocalStudentEJBService;
import com.gemtastic.attendencesystem.enteties.Courses;
import com.gemtastic.attendencesystem.enteties.Employees;
import com.gemtastic.attendencesystem.enteties.Lectures;
import com.gemtastic.attendencesystem.enteties.Students;
import com.gemtastic.attendencesystem.helpenteties.Attendance;
import java.util.Date;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.RequestScoped;

/**
 * Managed bean for the lecture.
 * 
 * @author Aizic Moisen
 */
@ManagedBean(name="lecture")
@RequestScoped
public class LectureMB {
    
    @EJB
    private LocalLectureEJBService lEJB;
    
    @EJB
    private LocalCourseEJBService cEJB;
    
    private Courses param;
    
    @ManagedProperty(value="#{param.id}")
    private int paramId;
    
    @ManagedProperty(value="#{param.lecture}")
    private int lectureId;
    
    public int id;
    public Courses course;
    public Date date;
    public Date startTime;
    public Date stopTime;
    public Employees teacher;
    private Lectures lecture;
    private boolean disabled;
    
    public List<Lectures> lectures;
    public List<Lectures> attendance;
    public List<Courses> courses;
    
    private Attendance[] att;
    
    /**
     * Creates a new lecture and redirects to its course.
     * 
     * @return 
     */
    public String onCreate() {
        Lectures l = setLecture();
        lecture = lEJB.upsert(l);
        String path = "/courses/course?faces-redirect=true&id=" + course.getId();
        return path;
    }
    
    /**
     * Updates a lecture and redirects to its course.
     * @return 
     */
    public String updateLecture() {
        lEJB.upsert(lecture);
        return "/courses/course?faces-redirect=true&id=" + lecture.getCourse().getId();
    }
    
    /**
     * Creates a new lecture with information from the parameter's course.
     * @return 
     */
    public Lectures setLecture() {
        if(course == null && param != null) {
            course = param;
        }
        Lectures l = new Lectures();
        l.setCourse(course);
        l.setDate(date);
        l.setStart(startTime);
        l.setStop(stopTime);
        return l;
    }
    
    @PostConstruct
    public void init() {
        lectures = lEJB.findAll();
        courses = cEJB.findAll();
        if(paramId != 0) {
            param = cEJB.readOne(paramId);
            disabled = true;
        } else {
            disabled = false;
        }
        if(lectureId != 0) {
            lecture = lEJB.readOne(lectureId);
            date = date != null ? date : lecture.getDate();
            startTime = startTime!= null ? startTime : lecture.getStart();
            stopTime = stopTime != null ? stopTime : lecture.getStop();
        }
    }
    
    /**
     * Deletes a lecture and redirects to its course.
     * 
     * @return 
     */
    public String deleteLecture(){
        Lectures placeholder = new Lectures();
        placeholder.setId(lectureId);
        lEJB.delete(placeholder);
        return "course?id=" + paramId + "&faces-redirect=true";
    }
    
    /**
     * Returns a student list based off of the id property.
     * @return 
     */
    public List<Students> getStudentList(){
        Lectures l = lEJB.readOne(id);
        return l.getStudentsList();
    }
    
    /**
     * Returns a lecture of the given id.
     * @param id
     * @return 
     */
    public Lectures getLectureById(int id){
        Lectures l = lEJB.readOne(id);
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

    public Courses getParam() {
        return param;
    }

    public void setParam(Courses param) {
        this.param = param;
    }

    public int getParamId() {
        return paramId;
    }

    public void setParamId(int paramId) {
        this.paramId = paramId;
    }

    public boolean isDisabled() {
        return disabled;
    }

    public void setDisabled(boolean disabled) {
        this.disabled = disabled;
    }

    public int getLectureId() {
        return lectureId;
    }

    public void setLectureId(int lectureId) {
        this.lectureId = lectureId;
    }
}
