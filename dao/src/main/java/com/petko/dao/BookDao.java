package com.petko.dao;

import com.petko.entities.BookEntity;

import java.util.List;

public class BookDao implements Dao<BookEntity> {
    private static BookDao instance;

    private BookDao() {}

    public static synchronized BookDao getInstance() {
        if(instance == null){
            instance = new BookDao();
        }
        return instance;
    }

    public void add(BookEntity entity) {

    }

    public List<BookEntity> getAll() {
        return null;
    }

    public BookEntity getById(int id) {
        return null;
    }

    public void delete(int id) {

    }
}
