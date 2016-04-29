/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.gemtastic.attendancesystem.managedbeans;

import com.gemtastic.attendancesystem.services.CRUDservices.interfaces.LocalAddressEJBService;
import com.gemtastic.attendencesystem.enteties.Address;
import javax.ejb.EJB;

/**
 *
 * @author moisen
 */
public class AddressMB {
    
    @EJB
    LocalAddressEJBService aEJB;
    
    public Address saveAddress(Address address) {
        throw new UnsupportedOperationException();
    }
    
}
