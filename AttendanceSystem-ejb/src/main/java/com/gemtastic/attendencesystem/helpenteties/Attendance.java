/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.gemtastic.attendencesystem.helpenteties;

import com.gemtastic.attendencesystem.enteties.Lectures;
import com.gemtastic.attendencesystem.enteties.Students;

/**
 *
 * @author Gemtastic
 */
public class Attendance {
    
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
