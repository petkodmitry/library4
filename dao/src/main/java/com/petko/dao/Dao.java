package com.petko.dao;

import com.petko.entities.Entity;

import java.util.List;

public interface Dao<T extends Entity> {
    /**
     * adds entity in database
     * @param entity - entity
     */
    void add(T entity);
    List<T> getAll();
    T getById(int id);
    void delete(int id);
}
