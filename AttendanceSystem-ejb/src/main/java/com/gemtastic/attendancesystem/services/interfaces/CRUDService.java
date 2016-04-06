/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.gemtastic.attendancesystem.services.interfaces;

import java.util.List;

/**
 * Interface for performing CRUD on database entity.
 * 
 * @author Gemtastic
 * @param <T>
 */
public interface CRUDService<T> {
    
    public T readOne(int id);
    
    public List<T> findAll();
    
    public void delete(T t);
    
    public T upsert(T t);
}
