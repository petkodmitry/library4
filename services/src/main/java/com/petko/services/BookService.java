package com.petko.services;

import com.petko.DaoException;
import com.petko.ExceptionsHandler;
import com.petko.dao.BookDao;
import com.petko.entities.BookEntity;
import com.petko.managers.PoolManager;

import javax.servlet.http.HttpServletRequest;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.*;

public class BookService implements Service<BookEntity>{
    private static BookService instance;

    private BookService() {}

    public static synchronized BookService getInstance() {
        if(instance == null){
            instance = new BookService();
        }
        return instance;
    }

    public Set<BookEntity> searchBooksByTitleOrAuthor(HttpServletRequest request, String searchTextInBook) {
        Set<BookEntity> result = new HashSet<>();
        Connection connection = null;
        try {
            connection = PoolManager.getInstance().getConnection();
//            connection.setAutoCommit(false);
            result.addAll(BookDao.getInstance().getFreeBooksByTitleOrAuthor(connection, searchTextInBook));
            result.addAll(BookDao.getInstance().getBusyBooksByTitleOrAuthor(connection, searchTextInBook));
        } catch (DaoException | SQLException | ClassNotFoundException e) {
            ExceptionsHandler.processException(request, e);
            return Collections.emptySet();
        } finally {
            PoolManager.getInstance().releaseConnection(connection);
        }
        return result;
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
