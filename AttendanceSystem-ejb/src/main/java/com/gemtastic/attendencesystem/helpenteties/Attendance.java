package com.gemtastic.attendencesystem.helpenteties;

import com.gemtastic.attendencesystem.enteties.Lectures;
import com.gemtastic.attendencesystem.enteties.Students;
import java.io.Serializable;

/**
 * Attendance support entity.
 * 
 * @author Aizic Moisen
 */
public class Attendance implements Serializable {
    
    private static final long serialVersionUID = 1L;
    
    private Students student;
    private Lectures lecture;
    private Boolean attending;

    public Attendance(Students student, Lectures lecture, Boolean attending) {
        this.student = student;
        this.attending = attending;
        this.lecture = lecture;
    }

    public Students getStudent() {
        return student;
    }

    public void setStudent(Students student) {
        this.student = student;
    }

    public Boolean getAttending() {
        return attending;
    }

    public void setAttending(Boolean attending) {
        this.attending = attending;
    }

    public Lectures getLecture() {
        return lecture;
    }

    public void setLecture(Lectures lecture) {
        this.lecture = lecture;
    }
    
    @Override
    public String toString() {
        return "Student: " + student.getId() + ", Lecture: " + lecture.getId() + ", Attending: " + attending;
    }
}
