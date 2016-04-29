/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.gemtastic.attendancesystem.services.CRUDservices;

import com.gemtastic.attendancesystem.services.CRUDservices.interfaces.LocalAddressEJBService;
import com.gemtastic.attendencesystem.enteties.Address;
import java.util.ArrayList;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author moisen
 */
@Stateless
public class AddressEJBService implements LocalAddressEJBService {
    
    @PersistenceContext
    EntityManager em;

    /**
     * Returns an address with the provided id if it exists.
     * 
     * @param id
     * @return 
     */
    @Override
    public Address readOne(int id) {
        return em.find(Address.class, id);
    }

    /**
     * Returns a list containing all addresses.
     * 
     * @return 
     */
    @Override
    public List<Address> findAll() {
        return em.createNamedQuery("Address.findAll").getResultList();
    }

    /**
     * Removes the given address.
     * @param address 
     */
    @Override
    public void delete(Address address) {
        Address merged = em.merge(address);
        em.remove(address);
    }

    /**
     * Updates or saves the given Address and returns the updated, created or 
     * existing Address.
     * 
     * @param address
     * @return 
     */
    @Override
    public Address upsert(Address address) {
        Address result = null;
        try {
            result = em.merge(address);
        } catch (Exception e) {
            System.out.println("Could not merge: " + e);
        }
        
        if(result == null) {
            return getExistingAddress(address);
        }
        return address;
    }
    
    /**
     * Takes an address and matches its fields with the existing addresses to 
     * find a match. Returns a match or null.
     * 
     * @param address
     * @return 
     */
    private Address getExistingAddress(Address address) {
        List<Address> list = null;
        try {
            list = em.createNamedQuery("SELECT a FROM Address a WHERE street = :street AND city = :city AND zip = zip", Address.class)
                    .setParameter("city", address.getCity())
                    .setParameter("street", address.getStreet())
                    .setParameter("zip", address.getZip())
                    .getResultList();
        } catch (Exception e) {
            System.out.println("Could not query for address: " + e);
            list = new ArrayList<>();
        }
        
        if(list.size() > 1) {
            Address result = null;
            for(Address a : list) {
                if (a.getCountry().equals(address.getCountry())) {
                    result = a;
                }
            }
            return result;
        } else {
            return list.get(0);
        }
    }
}
