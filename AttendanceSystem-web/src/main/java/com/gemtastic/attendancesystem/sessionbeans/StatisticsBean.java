/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.gemtastic.attendancesystem.sessionbeans;

import com.gemtastic.attendancesystem.services.CRUDservices.interfaces.LocalCourseEJBService;
import com.gemtastic.attendancesystem.services.CRUDservices.interfaces.LocalLectureEJBService;
import com.gemtastic.attendancesystem.services.CRUDservices.interfaces.LocalStudentEJBService;
import com.gemtastic.attendancesystem.services.interfaces.LocalAttendanceEJBService;
import com.gemtastic.attendencesystem.enteties.Courses;
import com.gemtastic.attendencesystem.enteties.Lectures;
import com.gemtastic.attendencesystem.enteties.Students;
import com.gemtastic.attendencesystem.helpenteties.Statistics;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import org.primefaces.model.chart.LineChartModel;
import org.primefaces.model.chart.LineChartSeries;

/**
 *
 * @author Gemtastic
 */
@ManagedBean(name="statistics")
public class StatisticsBean {
    
    @EJB
    LocalAttendanceEJBService aEJB;
    @EJB
    LocalLectureEJBService lEJB;
    @EJB
    LocalStudentEJBService sEJB;
    @EJB
    LocalCourseEJBService cEJB;
    
    private Statistics stats;
    
    @ManagedProperty(value="param.course")
    private Courses course;
    
    @ManagedProperty(value="param.student")
    private Students student;
    
    private LineChartModel overall;
    
    @PostConstruct
    public void init() {
        drawOverallAttendance();
    }

    private void drawOverallAttendance() {
        LineChartModel model = new LineChartModel();
        LineChartSeries courseAttendance = new LineChartSeries();
        LineChartSeries overallAttendance = new LineChartSeries();
        courseAttendance.setLabel("Over-all attendance:");
        int y = 0;
        
        // Per course
        if(course != null && student != null) {
            for(int i = 0; i < course.getLecturesList().size(); i++) {
                if(course.getLecturesList().get(i).getStudentsList().contains(student)) {
                    y++;
                }
                stats = courseStatistics(course, student);
                courseAttendance.set(i, y);
            }
        }
        
        // Per all courses???
        // TODO: implement get lectures for student by month
        if(course != null && student != null) {
            int totalAttendance = 0;
            int totalOccations = 0;
            for(int i = 0; i < student.getCoursesList().size(); i++) {
                Courses c = student.getCoursesList().get(i);
                for(Lectures l : c.getLecturesList()){
                    if(l.getStudentsList().contains(student)) {
                        totalAttendance++;
                    }
                    totalOccations++;
                }
                
                overallAttendance.set(i, y);
            }
        }
    }
    
    private void getDayOfCourseSpan(Lectures lecture) {
        long courseLength = stats.getCourseDays();
    }
    
    private void setupNumbers(Courses course) {
        long start = course.getStart().getTime();
        long stop = course.getStop().getTime();
        
        int days = (int) ((int) stop - (int) start);
        
        stats.setCourseDays((days / (24 * 3600)) + 1);
    }
    
    private void countDays(Courses course, Students student) {
        Students s = sEJB.readOne(student.getId());
        Courses c = cEJB.readOne(course.getId());
        
        double percentage;
        int attendances = 0;

        for (Lectures l : c.getLecturesList()) {
            if (l.getStudentsList().contains(s)) {
                attendances++;
                System.out.println("Attended: " + attendances);
            }
        }

        if (attendances != 0) {
            percentage = (double) attendances / (double) c.getLecturesList().size() * 100;

            stats.setAttendancePercent(percentage);
            stats.setAttendingCount(attendances);
        }
    }
    
    public Statistics courseStatistics(Courses course, Students student) {
        stats = new Statistics();
        setupNumbers(course);
        countDays(course, student);
        stats.setLecturesCount(course.getLecturesList().size());
        
        return stats;
    }

    public StatisticsBean() {
    }

    public LineChartModel getOverall() {
        return overall;
    }

    public void setOverall(LineChartModel overall) {
        this.overall = overall;
    }

    public Courses getCourse() {
        return course;
    }

    public void setCourse(Courses course) {
        this.course = course;
    }
    
}
