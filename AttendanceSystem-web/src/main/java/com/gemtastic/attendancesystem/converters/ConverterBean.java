package com.gemtastic.attendancesystem.converters;

import java.text.Format;
import java.text.SimpleDateFormat;
import java.util.Date;
import javax.faces.bean.ManagedBean;

/**
 * Managed bean that converts date and time.
 * 
 * @author Aizic Moisen
 */
@ManagedBean(name="converter")
public class ConverterBean {
    
    /**
     * Converts a java.util.date to a string of the date only.
     * 
     * @param date
     * @return 
     */
    public String convertDateToString(Date date) {
        System.out.println("Date: " + date);
        Format format = new SimpleDateFormat("yyyy-MM-dd");
        return format.format(date);
    }
    
    /**
     * Converts a java.util.date to hours only.
     * 
     * @param date
     * @return 
     */
    public String convertDateToTime(Date date) {
        Format format = new SimpleDateFormat("HH:mm");
        return format.format(date);
    }
}
