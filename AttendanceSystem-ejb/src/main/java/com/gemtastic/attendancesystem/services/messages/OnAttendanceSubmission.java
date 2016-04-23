/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.gemtastic.attendancesystem.services.messages;

import com.gemtastic.attendancesystem.services.CRUDservices.interfaces.LocalMessageEJBService;
import com.gemtastic.attendencesystem.enteties.Message;
import javax.ejb.ActivationConfigProperty;
import javax.ejb.EJB;
import javax.ejb.MessageDriven;
import javax.jms.JMSException;
import javax.jms.MessageListener;
import javax.jms.TextMessage;

/**
 *
 * @author Gemtastic
 */
@MessageDriven(mappedName = "jms/myQueue", activationConfig = {
    @ActivationConfigProperty(propertyName = "destinationType", propertyValue = "javax.jms.Queue")
})
public class OnAttendanceSubmission implements MessageListener {

    @EJB
    LocalMessageEJBService mEJB;
    
    public OnAttendanceSubmission() {
    }

    @Override
    public void onMessage(javax.jms.Message message) {
        TextMessage msg;
        try {
            if (message instanceof TextMessage) {
                msg = (TextMessage) message;
                System.out.println("MESSAGE BEAN: Message received: "
                        + msg.getText());
                createMessage(msg.getText());
            } else {
                System.out.println("Message of wrong type: "
                        + message.getClass().getName());
            }
        } catch (JMSException e) {
            e.printStackTrace();
        } catch (Throwable te) {
            te.printStackTrace();
        }
    }

    private void createMessage(String msg) {
        Message message = new Message();
        message.setAuthor(null);
        message.setMessage(msg);
        
        mEJB.upsert(message);
    }

}
