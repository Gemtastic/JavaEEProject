package com.gemtastic.attendancesystem.services.messages;

import com.gemtastic.attendancesystem.services.CRUDservices.interfaces.LocalMessageEJBService;
import com.gemtastic.attendancesystem.services.CRUDservices.interfaces.LocalUserEJBService;
import com.gemtastic.attendencesystem.enteties.Message;
import javax.annotation.PostConstruct;
import javax.ejb.ActivationConfigProperty;
import javax.ejb.EJB;
import javax.ejb.MessageDriven;
import javax.jms.MessageListener;
import javax.jms.ObjectMessage;

/**
 * Message driven bean listening to the attendance reports queue.
 * 
 * @author Aizic Moisen
 */
@MessageDriven(mappedName = "jms/myQueue", activationConfig = {
    @ActivationConfigProperty(propertyName = "destinationType", propertyValue = "javax.jms.Queue")
})
public class OnAttendanceSubmission implements MessageListener {

    @EJB
    LocalMessageEJBService mEJB;
    @EJB
    LocalUserEJBService uEJB;

    public OnAttendanceSubmission() {
    }

    /**
     * On attendance message arrival a message entity from the message will
     * be persisted into the database.
     * 
     * @param message 
     */
    @Override
    public void onMessage(javax.jms.Message message) {
        
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
            System.out.println("An error occured while creating message: " + te);
        }
    }

    /**
     * Persists a message to database.
     * @param msg 
     */
    private void createMessage(Message msg) {
        mEJB.upsert(msg);
    }
}
