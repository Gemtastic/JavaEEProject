/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.gemtastic.attendancesystem.managedbeans;

import com.gemtastic.attendancesystem.services.CRUDservices.interfaces.LocalMessageEJBService;
import com.gemtastic.attendencesystem.enteties.Message;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;

/**
 * 
 * @author Gemtastic
 */
@ManagedBean(name="messages")
@RequestScoped
public class MessageMB {
    
    @EJB
    LocalMessageEJBService mEJB;
    
    private List<Message> messages;

    public MessageMB() {
    }
    
    @PostConstruct
    public void init() {
        messages = mEJB.findAll();
    }
    
    public String deleteMessage(int id) {
        System.out.println("You want to delete: " + id);
        mEJB.delete(mEJB.readOne(id));
        return "/admin/inbox.xhtml?faces-redirect=true";
    }

    public List<Message> getMessages() {
        return messages;
    }

    public void setMessages(List<Message> messages) {
        this.messages = messages;
    }
}
