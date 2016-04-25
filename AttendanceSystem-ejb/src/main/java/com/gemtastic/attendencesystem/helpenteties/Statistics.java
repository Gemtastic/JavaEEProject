package com.gemtastic.attendencesystem.helpenteties;

/**
 * Support entity for statistics data.
 * 
 * @author Aizic Moisen
 */
public class Statistics {
    
    private double attendancePercent;
    private int attendingCount;
    private int courseDays;
    private int lecturesCount;

    public Statistics(double attendancePercent, int attendingCount, int courseDays, int lecturesCount) {
        this.attendancePercent = attendancePercent;
        this.attendingCount = attendingCount;
        this.courseDays = courseDays;
        this.lecturesCount = lecturesCount;
    }

    public Statistics() {
    }

    public double getAttendancePercent() {
        return attendancePercent;
    }

    public void setAttendancePercent(double attendancePercent) {
        this.attendancePercent = attendancePercent;
    }

    public int getAttendingCount() {
        return attendingCount;
    }

    public void setAttendingCount(int attendingCount) {
        this.attendingCount = attendingCount;
    }

    public int getCourseDays() {
        return courseDays;
    }

    public void setCourseDays(int courseDays) {
        this.courseDays = courseDays;
    }

    public int getLecturesCount() {
        return lecturesCount;
    }

    public void setLecturesCount(int lecturesCount) {
        this.lecturesCount = lecturesCount;
    }
}
