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
        if (value == null) {
            return;
        }

        UIInput courseComponent = (UIInput) component.getAttributes().get("selectedCourse");
        Courses course;

        if (!courseComponent.isValid()) {
            return;
        }

        if (courseComponent.getValue() != null) {
            course = (Courses) courseComponent.getValue();
        } else if (courseComponent.getValue() == null && component.getAttributes().get("course") != null) {
            course = (Courses) component.getAttributes().get("course");
        } else {
            return;
        }

        Date enteredDate = (Date) value;

        if (!(enteredDate.compareTo(course.getStart()) >= 0 && enteredDate.compareTo(course.getStop()) <= 0)) {
            throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR, "Lecure date is not inside the course's time span.", null));
        }
    }
}
