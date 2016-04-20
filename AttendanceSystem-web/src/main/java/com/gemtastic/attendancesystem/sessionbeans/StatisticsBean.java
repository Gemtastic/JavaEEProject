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
import java.time.LocalDate;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
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
@ManagedBean(name = "statistics")
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

//    @ManagedProperty(value="param.course")
    private int courseId;

    @ManagedProperty(value = "#{param.student}")
    private int studentId;

    private LineChartModel overall;

    @PostConstruct
    public void init() {
//        drawOverallAttendance();
    }

    //TODO: this is where you stopped
    public int fetchLecturesAttendedByMonth(Date firstDayOfMonth) {
        System.out.println("About to get attendance.");
        List<Lectures> attendances = lEJB.findByStudentAndDate(studentId, LocalDate.of(2016, 01, 01), LocalDate.of(2016, 05, 30));
        Collections.sort(attendances, new Comparator<Lectures>() {
            @Override
            public int compare(Lectures toTheLeft, Lectures toTheRight) {
                if (toTheLeft.getDate().getTime() > toTheRight.getDate().getTime()) {
                    return 1;
                } else if (toTheLeft.getDate().getTime() == toTheRight.getDate().getTime()) {
                    return 0;
                } else {
                    return -1;
                }
            }
        });
        System.out.println("Sorted list: " + attendances);
        return attendances.size();
    }

    /**
     * Counts the attendance of a student in a given course.
     * 
     * @param course
     * @param student
     * @return 
     */
    private int attendancePerCourse(Courses course, Students student) {
        int attendance = 0;
        // Per course
        for (int i = 0; i < course.getLecturesList().size(); i++) {
            if (course.getLecturesList().get(i).getStudentsList().contains(student)) {
                attendance++;
            }
            stats = courseStatistics(course, student);
        }
        return attendance;
    }

    private void drawOverallAttendance() {
        Students student = sEJB.readOne(studentId);
        Courses course = cEJB.readOne(courseId);
        LineChartModel model = new LineChartModel();
        LineChartSeries courseAttendance = new LineChartSeries();
        LineChartSeries overallAttendance = new LineChartSeries();
        courseAttendance.setLabel("Over-all attendance:");
        int y = 0;

        // TODO: implement get lectures for student by month
    }

    // ???
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

    public Statistics getStats() {
        return stats;
    }

    public void setStats(Statistics stats) {
        this.stats = stats;
    }

    public int getStudentId() {
        return studentId;
    }

    public void setStudentId(int studentId) {
        this.studentId = studentId;
    }

    public int getCourseId() {
        return courseId;
    }

    public void setCourseId(int courseId) {
        this.courseId = courseId;
    }
}
