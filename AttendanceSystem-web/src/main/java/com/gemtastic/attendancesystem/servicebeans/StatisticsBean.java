package com.gemtastic.attendancesystem.servicebeans;

import com.gemtastic.attendancesystem.converters.ConverterBean;
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
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import org.primefaces.model.chart.AxisType;
import org.primefaces.model.chart.DateAxis;
import org.primefaces.model.chart.LineChartModel;
import org.primefaces.model.chart.LineChartSeries;

/**
 * Statistics bean for managing the statistics.
 *
 * @author Aizic Moisen
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

    ConverterBean converter = new ConverterBean();

    @ManagedProperty(value = "#{param.student}")
    private int studentId;

    private LineChartModel overall;

    // A comparator for lectures
    private final Comparator lectureComparator = new Comparator<Lectures>() {
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
    };

    @PostConstruct
    public void init() {
        overall = new LineChartModel();
        drawOverallAttendance();
    }
    
    /**
     * Checks if the given student was attending the given lecture.
     * 
     * @param lecture
     * @param student
     * @return 
     */
    public String attended(Lectures lecture, Students student) {
        Students result = sEJB.readOne(student.getId());
        String answer = result.getLecturesList().contains(lecture) ? "Yes" : "No";
        return answer;
    }

    /**
     * Returns a list of the lectures that the student the studentId param
     * represents has attended in the month of the given date.
     *
     * @param firstDayOfMonth
     * @return
     */
    public int fetchLecturesAttendedByMonth(Date firstDayOfMonth) {
        System.out.println("About to get attendance.");
        // Converts the given date to Localdates of the first and last dates of the month.
        LocalDate firstDay = LocalDate.ofEpochDay(firstDayOfMonth.getTime()).withDayOfMonth(1);
        LocalDate lastDay = LocalDate.of(firstDay.getYear(), firstDay.getMonthValue(), firstDay.lengthOfMonth());
        System.out.println("First day of month: " + firstDay + ", Last day of month: " + lastDay);
        List<Lectures> attendances = lEJB.findByStudentAndDate(studentId, firstDay, lastDay);
        Collections.sort(attendances, lectureComparator);
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
        for (Lectures l : course.getLecturesList()) {
            if (l.getStudentsList().contains(student)) {
                attendance++;
            }
        }
        return attendance;
    }

    /**
     * Compares a current map of "first" and "last" dates with the given
     * course's dates and returns the dates that were the least and most recent.
     *
     * @param current
     * @param course
     * @return
     */
    private Map<String, Date> isMoreRecent(Map<String, Date> current, Courses course) {
        Map<String, Date> dates = current;

        Date first = null;
        Date last = null;

        if (dates.isEmpty()) {
            System.out.println("Dates is empty!");
            first = course.getStart();
            last = course.getStop();
        } else {
            if (course.getStart().compareTo(dates.get("first")) < 0) {
                first = course.getStart();
            } else {
                first = dates.get("first");
            }
            if (course.getStop().compareTo(dates.get("last")) > 0) {
                last = course.getStop();
            } else {
                last = dates.get("last");
            }
        }
        dates.put("first", first);
        dates.put("last", last);
        System.out.println("First date: " + first + ", Last date: " + last);
        return dates;
    }

    /**
     * Gets a HashMap with the first and last dates of the given courses' start
     * and stop dates.
     *
     * @param courses
     * @return
     */
    private Map<String, Date> getFirstAndLastDatesOfCourses(List<Courses> courses) {

        Map<String, Date> dates = new HashMap<>();

        for (Courses c : courses) {
            dates = isMoreRecent(dates, c);
        }

        return dates;
    }

    /**
     * Sets up a LineChartSeries with the date and attendance.
     *
     * @param student
     * @return
     */
    private LineChartSeries setUpLineChartSeries(Students student) {
        LineChartSeries chart = new LineChartSeries();

        int attendance = 0;
        for (Courses c : student.getCoursesList()) {

            List<Lectures> lectures = c.getLecturesList();
            Collections.sort(lectures, lectureComparator);

            for (Lectures l : lectures) {
                if (l.getStudentsList().contains(student)) {
                    attendance++;
                    System.out.println("Date: " + converter.convertDateToString(l.getDate()) + ", Attendance: " + attendance);
                    chart.set(converter.convertDateToString(l.getDate()), attendance);
                }
            }
        }
        return chart;
    }

    /**
     * Counts the total amount of lectures for the given list of courses.
     *
     * @param courses
     * @return
     */
    private int totalLectureCount(List<Courses> courses) {
        int total = 0;

        for (Courses c : courses) {
            total += c.getLecturesList().size();
        }

        return total;
    }

    /**
     * Sets up a LineChartModel to display the student's attendance over time.
     */
    private void drawOverallAttendance() {
        Students student = sEJB.readOne(studentId);
        if (!student.getCoursesList().isEmpty()) {
            LineChartSeries overallAttendance = setUpLineChartSeries(student);

            overallAttendance.setLabel("Over-all attendance:");

            overall.addSeries(overallAttendance);

            overall.setTitle("Attendance graph");
            overall.setZoom(false);
            overall.getAxis(AxisType.Y).setLabel("Attended lectures");
            overall.getAxis(AxisType.Y).setMax(totalLectureCount(student.getCoursesList()) + 2);

            DateAxis axis = new DateAxis("Dates");
            axis.setTickAngle(-50);

            // Set the total course range of dates
            Map<String, Date> dates = getFirstAndLastDatesOfCourses(student.getCoursesList());
            Date first = dates.get("first");
            Date last = dates.get("last");

            axis.setMax(converter.convertDateToString(last));
            axis.setMin(converter.convertDateToString(first));
            axis.setTickFormat("%b %#d, %y");

            overall.getAxes().put(AxisType.X, axis);
        }
    }

    /**
     * Calculates how many days a course spans.
     *
     * @param course
     * @return
     */
    private int calculateCourseDays(Courses course) {
        long start = course.getStart().getTime();
        long stop = course.getStop().getTime();

        int days = (int) ((int) stop - (int) start);

        return (days / (24 * 3600)) + 1;
    }

    /**
     * Calculates the attendance percentage.
     *
     * @param course
     * @param student
     * @return
     */
    private double attendancePercentage(int attendanceCount, int lectureCount) {

        double percentage = 0.0;

        if (attendanceCount != 0 && lectureCount != 0) {
            percentage = (double) attendanceCount / (double) lectureCount * 100;
        }
        return percentage;
    }

    /**
     * Get a statistics object of the given student and course.
     *
     * @param course
     * @param student
     * @return
     */
    public Statistics courseStatistics(Courses course, Students student) {
        Statistics newStats = new Statistics();
        newStats.setCourseDays(calculateCourseDays(course));
        newStats.setAttendingCount(attendancePerCourse(course, student));
        newStats.setAttendancePercent(attendancePercentage(newStats.getAttendingCount(), course.getLecturesList().size()));
        newStats.setLecturesCount(course.getLecturesList().size());

        return newStats;
    }

    public StatisticsBean() {
    }

    public LineChartModel getOverall() {
        return overall;
    }

    public void setOverall(LineChartModel overall) {
        this.overall = overall;
    }

    public int getStudentId() {
        return studentId;
    }

    public void setStudentId(int studentId) {
        this.studentId = studentId;
    }
}
