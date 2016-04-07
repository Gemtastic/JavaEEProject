package com.gemtastic.attendancesystem.managedbeans;

//import com.gemtastic.attendancesystem.services.CRUDservices.StudentEJBService;
import com.gemtastic.attendancesystem.services.CRUDservices.interfaces.LocalCourseEJBService;
import com.gemtastic.attendancesystem.services.CRUDservices.interfaces.LocalStudentEJBService;
import com.gemtastic.attendencesystem.enteties.Courses;
import com.gemtastic.attendencesystem.enteties.Students;
import java.util.Date;
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
 *
 * @author Gemtastic
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
    
    private Courses course;
    public Date regdate;
    public String firstname;
    public String lastname;
    public long socialSecurityNo;
    public String email;
    public int phone;
    private UploadedFile file;
    private List<Students> students;
    
    public Students student;
    
    @PostConstruct
    public void init(){
        student = new Students();
        students = sEJB.findAll();
        System.out.println("You initialized a student bean! course id is: " + courseId);
        course = cEJB.readOne(courseId);
        System.out.println("Course: " + course);
    }

    public StudentMB() {
    }
    
    public String addToCourse() {
        List<Students> list = course.getStudentsList();
        list.add(student);
        course.setStudentsList(list);
        cEJB.upsert(course);
        return "courses/course?id=" + course.getId();
    }
    
    public List<Students> nonAttendingStudentsOnly() {
        
    }
    
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
}
