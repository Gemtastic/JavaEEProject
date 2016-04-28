/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.gemtastic.attendencesystem.enteties;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author Gemtastic
 */
@Entity
@Table(name = "courses")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Courses.findAll", query = "SELECT c FROM Courses c"),
    @NamedQuery(name = "Courses.findById", query = "SELECT c FROM Courses c WHERE c.id = :id"),
    @NamedQuery(name = "Courses.findByName", query = "SELECT c FROM Courses c WHERE c.name = :name"),
    @NamedQuery(name = "Courses.findByPoints", query = "SELECT c FROM Courses c WHERE c.points = :points"),
    @NamedQuery(name = "Courses.findByStart", query = "SELECT c FROM Courses c WHERE c.start = :start"),
    @NamedQuery(name = "Courses.findByStop", query = "SELECT c FROM Courses c WHERE c.stop = :stop"),
    @NamedQuery(name = "Courses.findByCourseCode", query = "SELECT c FROM Courses c WHERE c.courseCode = :courseCode"),
    @NamedQuery(name = "Courses.findByMaxStudent", query = "SELECT c FROM Courses c WHERE c.maxStudent = :maxStudent")})
public class Courses implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 2147483647)
    @Column(name = "name")
    private String name;
    @Basic(optional = false)
    @NotNull
    @Column(name = "points")
    private int points;
    @Basic(optional = false)
    @NotNull
    @Column(name = "start")
    @Temporal(TemporalType.DATE)
    private Date start;
    @Basic(optional = false)
    @NotNull
    @Column(name = "stop")
    @Temporal(TemporalType.DATE)
    private Date stop;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 2147483647)
    @Column(name = "course_code")
    private String courseCode;
    @Column(name = "max_student")
    private Integer maxStudent;
    @JoinTable(name = "students_courses", joinColumns = {
        @JoinColumn(name = "course", referencedColumnName = "id")}, inverseJoinColumns = {
        @JoinColumn(name = "student", referencedColumnName = "id")})
    @ManyToMany
    private List<Students> studentsList;
    @JoinColumn(name = "level", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private CourseLevel level;
    @JoinColumn(name = "teacher", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private Employees teacher;
    @JoinColumn(name = "head_of_course", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private Employees headOfCourse;
    @JoinColumn(name = "language", referencedColumnName = "id")
    @ManyToOne
    private Languages language;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "course")
    private List<Lectures> lecturesList;

    public Courses() {
    }

    public Courses(Integer id) {
        this.id = id;
    }

    public Courses(Integer id, String name, int points, Date start, Date stop, String courseCode) {
        this.id = id;
        this.name = name;
        this.points = points;
        this.start = start;
        this.stop = stop;
        this.courseCode = courseCode;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getPoints() {
        return points;
    }

    public void setPoints(int points) {
        this.points = points;
    }

    public Date getStart() {
        return start;
    }

    public void setStart(Date start) {
        this.start = start;
    }

    public Date getStop() {
        return stop;
    }

    public void setStop(Date stop) {
        this.stop = stop;
    }

    public String getCourseCode() {
        return courseCode;
    }

    public void setCourseCode(String courseCode) {
        this.courseCode = courseCode;
    }

    public Integer getMaxStudent() {
        return maxStudent;
    }

    public void setMaxStudent(Integer maxStudent) {
        this.maxStudent = maxStudent;
    }

    @XmlTransient
    public List<Students> getStudentsList() {
        return studentsList;
    }

    public void setStudentsList(List<Students> studentsList) {
        this.studentsList = studentsList;
    }

    public CourseLevel getLevel() {
        return level;
    }

    public void setLevel(CourseLevel level) {
        this.level = level;
    }

    public Employees getTeacher() {
        return teacher;
    }

    public void setTeacher(Employees teacher) {
        this.teacher = teacher;
    }

    public Employees getHeadOfCourse() {
        return headOfCourse;
    }

    public void setHeadOfCourse(Employees headOfCourse) {
        this.headOfCourse = headOfCourse;
    }

    public Languages getLanguage() {
        return language;
    }

    public void setLanguage(Languages language) {
        this.language = language;
    }

    @XmlTransient
    public List<Lectures> getLecturesList() {
        return lecturesList;
    }

    public void setLecturesList(List<Lectures> lecturesList) {
        this.lecturesList = lecturesList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Courses)) {
            return false;
        }
        Courses other = (Courses) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.gemtastic.attendencesystem.enteties.Courses[ id=" + id + " ]";
    }
    
}
