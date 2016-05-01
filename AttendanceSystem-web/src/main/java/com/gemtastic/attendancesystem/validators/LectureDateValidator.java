package com.gemtastic.attendancesystem.validators;

import com.gemtastic.attendencesystem.enteties.Courses;
import java.util.Date;
import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.component.UIInput;
import javax.faces.context.FacesContext;
import javax.faces.validator.FacesValidator;
import javax.faces.validator.Validator;
import javax.faces.validator.ValidatorException;

/**
 *
 * @author Aizic Moisen
 */
@FacesValidator("lectureDateValidator")
public class LectureDateValidator implements Validator {

    @Override
    public void validate(FacesContext fc, UIComponent component, Object value) throws ValidatorException {
        if(value == null){
            return;
        }
        
        UIInput courseComponent = (UIInput) component.getAttributes().get("selectedCourse");
        
        if(!courseComponent.isValid()) {
            return;
        }
        
        Courses course = (Courses) courseComponent.getValue();
               
        Date enteredDate = (Date) value;
        
        if(!enteredDate.after(course.getStart()) || !enteredDate.before(course.getStop())) {
            throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR, "Lecure date is not inside the course's time span.", null));
        }
    }
}
