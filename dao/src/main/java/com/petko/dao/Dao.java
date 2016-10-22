package com.petko.dao;

import com.petko.entities.Entity;

import java.util.List;

public interface Dao<T extends Entity> {
    void add(T entity);
    List<T> getAll();
    T getById(int id);
    void delete(int id);
}
