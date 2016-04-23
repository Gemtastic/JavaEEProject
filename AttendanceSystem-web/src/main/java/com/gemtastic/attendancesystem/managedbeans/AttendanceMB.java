/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.gemtastic.attendancesystem.managedbeans;

import com.gemtastic.attendancesystem.services.CRUDservices.interfaces.LocalCourseEJBService;
import com.gemtastic.attendancesystem.services.CRUDservices.interfaces.LocalLectureEJBService;
import com.gemtastic.attendancesystem.services.CRUDservices.interfaces.LocalStudentEJBService;
import com.gemtastic.attendancesystem.services.interfaces.LocalAttendanceEJBService;
import com.gemtastic.attendancesystem.sessionbeans.StatisticsBean;
import com.gemtastic.attendencesystem.enteties.Courses;
import com.gemtastic.attendencesystem.enteties.Lectures;
import com.gemtastic.attendencesystem.enteties.Students;
import com.gemtastic.attendencesystem.helpenteties.Attendance;
import com.gemtastic.attendencesystem.helpenteties.Statistics;
import java.util.Arrays;
import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.RequestScoped;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.jms.JMSConnectionFactory;
import javax.jms.JMSContext;
import javax.jms.JMSRuntimeException;
import javax.jms.Queue;

/**
 *
 * @author Gemtastic
 */
@ManagedBean(name = "attendances")
@RequestScoped
public class AttendanceMB {

    //JMS resources
    @Resource(mappedName = "jms/myQueue")
    private Queue myQueue;

    @Inject
    @JMSConnectionFactory("java:comp/DefaultJMSConnectionFactory")
    private JMSContext context;
    // end JMS resources
    
    private Attendance[] att;
    private Lectures lecture;
    private Courses course;
    
    private Statistics stats;
    private StatisticsBean statistics = new StatisticsBean();

    @ManagedProperty("#{param.id}")
    private int id;

    @EJB
    LocalAttendanceEJBService aEJB;
    @EJB
    LocalLectureEJBService lEJB;
    @EJB
    LocalStudentEJBService sEJB;
    @EJB
    LocalCourseEJBService cEJB;

    public AttendanceMB() {
    }

    @PostConstruct
    public void init() {
        System.out.println("You initialized an attendance bean! " + id);
        System.out.println("Param: " + FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("id"));
        if (id != 0) {
            Lectures l = lEJB.readOne(id);
            if(l != null){
                setUp(l);
            }
        }
    }

    public void setUp(Lectures lecture) {
        course = lecture.getCourse();
        this.lecture = lecture;
        if (course != null) {
            att = new Attendance[course.getStudentsList().size()];
            att = aEJB.getAttendance(lecture);
        }
    }

    public String submitAttendance(Attendance[] attending) {
        System.out.println("You submitted your attendance!" + id + ", " + course);
        System.out.println(Arrays.toString(attending));
        aEJB.saveAttendance(attending);
        
        // attempting JMS implementation here
        sendJMSMessageToMyQueue();
        // End JMS
        
        return "/courses/course?faces-redirect=true&id=" + course.getId();
    }
    
    

    // TODO useless
    public String viewAttendance(Lectures l) {
        System.out.println("You're viewing the attendance!");
        System.out.println("lecture: " + l);
        FacesContext.getCurrentInstance().getExternalContext().getFlash().put("lecture", l);
        return "lectures/attendance?faces-redirect=true";
    }

    public String attended(Lectures lecture, Students student) {
        String answer = student.getLecturesList().contains(lecture) ? "Yes" : "No";
        return answer;
    }

    public double attendanceStats(Courses course, Students student) {
        stats = statistics.courseStatistics(course, student);
        return stats.getAttendancePercent();
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

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    /**
     * Sends a message to the message queue.
     */
    private void sendJMSMessageToMyQueue() {
        System.out.println("About to send message!");
        try {
            String text = "Attendance was submitted for course " + course.getName() + " and lecture on the date " + lecture.getDate() + ".";
            context.createProducer().send(myQueue, text);
        } catch (JMSRuntimeException t) {
            System.out.println(t.toString());
        }
    }
}