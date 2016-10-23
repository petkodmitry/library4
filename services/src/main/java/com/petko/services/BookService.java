package com.petko.services;

import com.petko.entities.BookEntity;

import java.util.List;

public class BookService implements Service<BookEntity>{
    private static BookService instance;

    private BookService() {}

    public static synchronized BookService getInstance() {
        if(instance == null){
            instance = new BookService();
        }
        return instance;
    }

    public void add(BookEntity entity) {

    }

    public List<BookEntity> getAll() {
        return null;
    }

    public BookEntity getByLogin(String login) {
        return null;
    }

    public void update(BookEntity entity) {

    }

    public void delete(int id) {

    }
}
