/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.gemtastic.attendancesystem.managedbeans;

import com.gemtastic.attendancesystem.services.CRUDservices.interfaces.LocalCourseEJBService;
import com.gemtastic.attendancesystem.services.CRUDservices.interfaces.LocalLectureEJBService;
import com.gemtastic.attendancesystem.services.CRUDservices.interfaces.LocalStudentEJBService;
import com.gemtastic.attendancesystem.services.CRUDservices.interfaces.LocalUserEJBService;
import com.gemtastic.attendancesystem.services.interfaces.LocalAttendanceEJBService;
import com.gemtastic.attendancesystem.services.interfaces.LoginServices;
import com.gemtastic.attendancesystem.sessionbeans.SessionBean;
import com.gemtastic.attendancesystem.sessionbeans.StatisticsBean;
import com.gemtastic.attendencesystem.enteties.Courses;
import com.gemtastic.attendencesystem.enteties.Lectures;
import com.gemtastic.attendencesystem.enteties.Message;
import com.gemtastic.attendencesystem.enteties.Students;
import com.gemtastic.attendencesystem.enteties.Users;
import com.gemtastic.attendencesystem.helpenteties.Attendance;
import com.gemtastic.attendencesystem.helpenteties.Statistics;
import java.util.Arrays;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.RequestScoped;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.jms.JMSConnectionFactory;
import javax.jms.JMSContext;
import javax.jms.JMSException;
import javax.jms.JMSRuntimeException;
import javax.jms.ObjectMessage;
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
    
    @ManagedProperty(value="#{sessionBean}")
    public SessionBean sessionBean;
    
    
    private Attendance[] att;
    private Lectures lecture;
    private Courses course;
    
    private Statistics stats;
    private StatisticsBean statistics = new StatisticsBean();

    @ManagedProperty("#{param.id}")
    private int id;

    @EJB
    private LoginServices loginService;
    @EJB
    LocalAttendanceEJBService aEJB;
    @EJB
    LocalLectureEJBService lEJB;
    @EJB
    LocalStudentEJBService sEJB;
    @EJB
    LocalCourseEJBService cEJB;
    @EJB
    LocalUserEJBService uEJB;

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

    public SessionBean getSessionBean() {
        return sessionBean;
    }

    public void setSessionBean(SessionBean sessionBean) {
        this.sessionBean = sessionBean;
    }

    /**
     * Sends a message to the message queue.
     */
    private void sendJMSMessageToMyQueue() {
        System.out.println("About to send message!");
        Message msg = new Message();
        try {
            String text = "Attendance was submitted for course " + course.getName() + " and lecture of the date " + lecture.getDate() + ".";
            Users user = uEJB.findByUser(sessionBean.getUsername());
            msg.setAuthor(user.getEmployee());
            msg.setMessage(text);
            ObjectMessage message = context.createObjectMessage();
            message.setObject(msg);
            
            context.createProducer().send(myQueue, message);
        } catch (JMSRuntimeException t) {
            System.out.println(t.toString());
        } catch (JMSException ex) {
            Logger.getLogger(AttendanceMB.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}