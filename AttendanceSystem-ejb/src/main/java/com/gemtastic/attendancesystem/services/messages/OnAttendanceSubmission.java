/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.gemtastic.attendancesystem.services.messages;

import com.gemtastic.attendancesystem.services.CRUDservices.interfaces.LocalMessageEJBService;
import com.gemtastic.attendancesystem.services.CRUDservices.interfaces.LocalUserEJBService;
import com.gemtastic.attendencesystem.enteties.Employees;
import com.gemtastic.attendencesystem.enteties.Message;
import com.gemtastic.attendencesystem.enteties.Users;
import com.gemtastic.attendencesystem.helpenteties.Attendance;
import javax.annotation.PostConstruct;
import javax.ejb.ActivationConfigProperty;
import javax.ejb.EJB;
import javax.ejb.MessageDriven;
import javax.faces.bean.ManagedProperty;
import javax.jms.MessageListener;
import javax.jms.ObjectMessage;
import javax.servlet.http.HttpSession;

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
    @EJB
    LocalUserEJBService uEJB;


    @PostConstruct
    public void init() {
//        session = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(false);
    }

    public OnAttendanceSubmission() {
    }

    @Override
    public void onMessage(javax.jms.Message message) {
//        TextMessage msg;
//        try {
//            if (message instanceof TextMessage) {
//                msg = (TextMessage) message;
//                System.out.println("MESSAGE BEAN: Message received: "
//                        + msg.getText());
//                createMessage(msg.getText());
//            } else {
//                System.out.println("Message of wrong type: "
//                        + message.getClass().getName());
//            }
//        } catch (JMSException e) {
//            e.printStackTrace();
//        } catch (Throwable te) {
//            te.printStackTrace();
//        }
        try {
            if (message instanceof ObjectMessage) {
                ObjectMessage objMsg = (ObjectMessage) message;
                Message msg = (Message) objMsg.getObject();
                System.out.println("MESSAGE BEAN: Received message! " + msg.getMessage());
                createMessage(msg);
            } else {
                System.out.println("Message of wrong type: "
                        + message.getClass().getName());
            }
        } catch (Throwable te) {
            te.printStackTrace();
        }

    }

    private void createMessage(Message msg) {
        mEJB.upsert(msg);
    }
}
