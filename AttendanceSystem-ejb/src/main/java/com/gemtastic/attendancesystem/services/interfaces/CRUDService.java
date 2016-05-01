package com.gemtastic.attendancesystem.services.interfaces;

import java.util.List;

/**
 * Interface for performing CRUD on database entity.
 * 
 * @author Aizic Moisen
 * @param <T>
 */
public interface CRUDService<T> {
    
    public T readOne(int id);
    
    public List<T> findAll();
    
    public void delete(T t);
    
    public T upsert(T t);
}
