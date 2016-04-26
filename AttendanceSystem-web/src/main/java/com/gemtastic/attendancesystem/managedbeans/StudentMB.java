package com.gemtastic.attendancesystem.managedbeans;

import com.gemtastic.attendancesystem.services.CRUDservices.interfaces.LocalCourseEJBService;
import com.gemtastic.attendancesystem.services.CRUDservices.interfaces.LocalStudentEJBService;
import com.gemtastic.attendencesystem.enteties.Courses;
import com.gemtastic.attendencesystem.enteties.Students;
import java.sql.Date;
import java.time.LocalDate;
import java.time.Year;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.RequestScoped;
import javax.faces.context.FacesContext;
import org.primefaces.model.UploadedFile;

/**
 * Managed bean for the student.
 * @author Aizic Moisen
 */
@ManagedBean(name = "student")
@RequestScoped
public class StudentMB {
    
    @EJB
    LocalStudentEJBService sEJB;
    
    @EJB
    LocalCourseEJBService cEJB;
    
    @ManagedProperty(value="#{param.id}")
    private int courseId;
    
    @ManagedProperty(value="#{param.student}")
    private int studentId;
    
    private int enrollYear;
    private Courses course;
    public Date regdate;
    public String firstname;
    public String lastname;
    public long socialSecurityNo;
    public String email;
    public int phone;
    private UploadedFile file;
    private boolean disabled = true;
    private List<Students> students;
    private List<Students> unregistered;
    private List<Integer> years;
    
    public Students student;
    
    @PostConstruct
    public void init(){
        disableButtonOnNoParam();
        student = sEJB.readOne(studentId);
        students = sEJB.findAll();
        years = new ArrayList<>();
        for(int i = Year.now().getValue(); i >= 1969; i--) {
            years.add(i);
        }
        System.out.println("You initialized a student bean! course id is: " + courseId);
        course = cEJB.readOne(courseId);
        System.out.println("Course: " + course);
        if (course != null) {
            nonAttendingStudentsOnly();
        }
    }

    public StudentMB() {
    }
    
    /**
     * Sets the student list property based on the students who enrolled that year.
     */
    public void onYearSelect() {
        students = sEJB.findByEnrollmentYear(enrollYear);
        if(students != null) {
            System.out.println("Student count: " + students.size());
        } else {
            System.out.println("Students list is null");
        }
    }
    
    /**
     * Edits a student and redirects to it.
     * @return 
     */
    public String editStudent() {
        System.out.println("You want to edit student: " + studentId + ", " + student.getFirstname());
        sEJB.upsert(student);
        return "student?faces-redirect=true&student=" + studentId;
    }
    
    /**
     * Creates a student and redirects to the student list.
     * 
     * @return 
     */
    public String createStudent(){
        student = new Students();
        student.setEmail(email);
        student.setFirstname(firstname);
        student.setLastname(lastname);
        student.setPhone(phone);
        student.setRegDate(Date.valueOf(LocalDate.now()));
        student.setSocSecNo(socialSecurityNo);
        sEJB.upsert(student);
        return "students?faces-redirect=true";
    }
    
    /**
     * Disables buttons when there are no url params.
     */
    public void disableButtonOnNoParam(){
        String param = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("id");
        if(param != null) {
            disabled = false;
        }
    }
    
    /**
     * Deletes a student and redirects to the student list.
     * @return 
     */
    public String deleteStudent() {
        sEJB.delete(student);
        return "students?faces-redirect=true";
    }
    
    /**
     * Adds a student to a course and redirects to the course if successful or
     * redirects back to the add to courses page.
     * @return 
     */
    public String addToCourse() {
        if (course != null) {
            List<Students> list = course.getStudentsList();
            list.add(student);
            course.setStudentsList(list);
            cEJB.upsert(course);
            return "/courses/course?id=" + course.getId() + "&faces-redirect=true";
        } else {
            return "addToCourse?id=0&faces-redirect=true";
        }
    }
    
    /**
     * Gets a list of all the students not already attending the course.
     * @return 
     */
    public List<Students> nonAttendingStudentsOnly() {
        List<Students> attending = course.getStudentsList();
        unregistered = new ArrayList<>();
        
        for(Students s : students) {
            boolean attend = attending.contains(s);
            if (!attend) {
                unregistered.add(s);
            }
        }
        return unregistered;
    }
    
    /**
     * Removes a student from a course and redirects to the course the student
     * was removed from.
     * @return 
     */
    public String removeFromCourse() {
        System.out.println("Student id:" + studentId);
        Students toRemove = sEJB.readOne(studentId);
        List<Students> attending = course.getStudentsList();
        attending.remove(toRemove);
        course.setStudentsList(attending);
        course = cEJB.upsert(course);
        return "course.xhtml?id=" + courseId + "&faces-redirect=true";
    }
    
    // Not implemented file upload
    public UploadedFile getFile() {
        return file;
    }
 
    public void setFile(UploadedFile file) {
        this.file = file;
    }
     
    public void upload() {
        if(file != null) {
            FacesMessage message = new FacesMessage("Succesful", file.getFileName() + " is uploaded.");
            FacesContext.getCurrentInstance().addMessage(null, message);
        }
    }
    // End not implemented file upload.

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public long getSocialSecurityNo() {
        return socialSecurityNo;
    }

    public void setSocialSecurityNo(long socialSecurityNo) {
        this.socialSecurityNo = socialSecurityNo;
    }

    public Date getRegdate() {
        return regdate;
    }

    public void setRegdate(Date regdate) {
        this.regdate = regdate;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public int getPhone() {
        return phone;
    }

    public void setPhone(int phone) {
        this.phone = phone;
    }

    public Students getStudent() {
        return student;
    }

    public void setStudent(Students student) {
        this.student = student;
    }

    public List<Students> getStudents() {
        return students;
    }

    public void setStudents(List<Students> students) {
        this.students = students;
    }

    public int getCourseId() {
        return courseId;
    }

    public void setCourseId(int courseId) {
        this.courseId = courseId;
    }

    public Courses getCourse() {
        return course;
    }

    public void setCourse(Courses course) {
        this.course = course;
    }

    public List<Students> getUnregistered() {
        return unregistered;
    }

    public void setUnregistered(List<Students> unregistered) {
        this.unregistered = unregistered;
    }

    public boolean isDisabled() {
        return disabled;
    }

    public void setDisabled(boolean disabled) {
        this.disabled = disabled;
    }

    public int getStudentId() {
        return studentId;
    }

    public void setStudentId(int studentId) {
        this.studentId = studentId;
    }

    public int getEnrollYear() {
        return enrollYear;
    }

    public void setEnrollYear(int enrollYear) {
        this.enrollYear = enrollYear;
    }

    public List<Integer> getYears() {
        return years;
    }

    public void setYears(List<Integer> years) {
        this.years = years;
    }
}
