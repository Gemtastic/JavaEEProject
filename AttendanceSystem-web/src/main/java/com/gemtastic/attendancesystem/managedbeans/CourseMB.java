package com.gemtastic.attendancesystem.managedbeans;

import com.gemtastic.attendancesystem.services.CRUDservices.interfaces.LocalCourseEJBService;
import com.gemtastic.attendancesystem.services.CRUDservices.interfaces.LocalEmployeeEJBService;
import com.gemtastic.attendancesystem.services.CRUDservices.interfaces.LocalStudentEJBService;
import com.gemtastic.attendancesystem.services.interfaces.LocalAttendanceEJBService;
import com.gemtastic.attendencesystem.enteties.CourseLevel;
import com.gemtastic.attendencesystem.enteties.Courses;
import com.gemtastic.attendencesystem.enteties.Employees;
import com.gemtastic.attendencesystem.enteties.Languages;
import com.gemtastic.attendencesystem.enteties.Lectures;
import com.gemtastic.attendencesystem.enteties.Students;
import java.util.Date;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.RequestScoped;

/**
 * Managed bean for the courses.
 *
 * @author Aizic Moisen
 */
@ManagedBean(name = "courses")
@RequestScoped
public class CourseMB {

    @EJB
    LocalCourseEJBService cEJB;
    @EJB
    LocalEmployeeEJBService eEJB;
    @EJB
    LocalStudentEJBService sEJB;
    @EJB
    LocalAttendanceEJBService aEJB;

    @ManagedProperty("#{param.id}")
    private int id;

    @ManagedProperty("#{param.newCourse}")
    private Courses newCourse;

    private String name;
    private String language;
    private int points;
    private Date start;
    private Date stop;
    private Courses course;
    private Employees teacher;
    private Lectures lecture;
    private List<Employees> teachers;
    private List<Courses> all;
    private List<CourseLevel> levels;
    private List<Employees> headmasters;

    @PostConstruct
    public void init() {
        teachers = eEJB.findAll();
        headmasters = eEJB.findAllByPosition("headmaster");
        all = cEJB.findAll();
        levels = cEJB.getLevels();
        if (course == null) {
            course = new Courses();
        }
        if (id != 0) {
            setUp();
        }
    }

    /**
     * Sets up the bean with the accurate course given the course parameter.
     */
    private void setUp() {
        try {
            course = cEJB.readOne(id);
            System.out.println("Lecture size: " + course.getLecturesList().size());
        } catch (NullPointerException e) {
            System.out.println("Parameter id is null. " + e);
        } catch (Exception e) {
            System.out.println("Could not catch the parameter id: " + e);
        }
    }

    /**
     * Submits the created course and returns it.
     *
     * @return
     */
    public String onSubmit() {
        if (language != null) {
            Languages l = cEJB.findLanguageByName(language);

            if (l != null) {
                l = new Languages();
                l.setLanguage(language);
                course.setLanguage(l);
            }else {
                course.setLanguage(l);
            }
        }

        Courses c = cEJB.upsert(course);
        course = c;
        return "course";
    }

    /**
     * Deletes the course of the given course param and redirects to courses
     * list.
     *
     * @return
     */
    public String deleteCourse() {
        Courses placeholder = cEJB.readOne(id);
        cEJB.delete(placeholder);
        return "showCourses?faces-redirect=true";
    }

    /**
     * Edits the course and redirects to it.
     *
     * @return
     */
    public String editCourse() {
        cEJB.upsert(course);
        return "course?id=" + id + "&faces-redirect=true";
    }

    // TODO useless
//    public String viewCourse(Courses c) {
//        System.out.println("You want to view the course: " + c);
//        course = c;
//        FacesContext.getCurrentInstance().getExternalContext().getFlash().put("test", c);
//        return "course?faces-redirect=true";
//    }
    /**
     * Checks if a given student is attending the the set lecture..
     *
     * @param id
     * @return
     */
    public boolean attending(int id) {
        Students attend = sEJB.readOne(id);
        List<Students> attending = lecture.getStudentsList();
        boolean b = attending.contains(attend);
        return b;
    }

    // TODO: useless
//    public void addStudentToCourse() throws IOException{
//        System.out.println("Redirecting to studens...");
//        FacesContext.getCurrentInstance().getExternalContext()
//            .redirect("../students/addToCourse.xhtml");
//    }
    public CourseMB() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
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

    public Employees getTeacher() {
        return teacher;
    }

    public void setTeacher(Employees teacher) {
        this.teacher = teacher;
    }

    public Courses getCourse() {
        return course;
    }

    public void setCourse(Courses course) {
        this.course = course;
    }

    public List<Employees> getTeachers() {
        return teachers;
    }

    public void setTeachers(List<Employees> teachers) {
        this.teachers = teachers;
    }

    public List<Courses> getAll() {
        return all;
    }

    public void setAll(List<Courses> all) {
        this.all = all;
    }

    public Lectures getLecture() {
        return lecture;
    }

    public void setLecture(Lectures lecture) {
        this.lecture = lecture;
    }

    public Courses getNewCourse() {
        return newCourse;
    }

    public void setNewCourse(Courses newCourse) {
        this.newCourse = newCourse;
    }

    public List<CourseLevel> getLevels() {
        return levels;
    }

    public void setLevels(List<CourseLevel> levels) {
        this.levels = levels;
    }

    public List<Employees> getHeadmasters() {
        return headmasters;
    }

    public void setHeadmasters(List<Employees> headMasters) {
        this.headmasters = headMasters;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }
}
