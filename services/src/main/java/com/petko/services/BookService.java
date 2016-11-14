package com.petko.services;

import com.petko.DaoException;
import com.petko.ExceptionsHandler;
import com.petko.dao.BookDao;
import com.petko.entities.BookEntity;
import com.petko.managers.PoolManager;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
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

    public List<BookEntity> getAllBooksByTitleOrAuthor(HttpServletRequest request, String searchTextInBook) {
        List<BookEntity> result = new ArrayList<>();
        Connection connection = null;
        try {
            connection = PoolManager.getInstance().getConnection();
//            connection.setAutoCommit(false);
            result = BookDao.getInstance().getBooksByTitleOrAuthor(connection, searchTextInBook);
        } catch (DaoException | SQLException | ClassNotFoundException e) {
            ExceptionsHandler.processException(request, e);
            return Collections.emptyList();
        } finally {
            PoolManager.getInstance().releaseConnection(connection);
        }
        return result;
    }

    public void setBookBusy(HttpServletRequest request, Integer bookId, Boolean isBusy) {
        Connection connection = null;
        try {
            connection = PoolManager.getInstance().getConnection();
//            connection.setAutoCommit(false);
            BookEntity entity = BookDao.getInstance().getById(connection, bookId);
            entity.setBusy(isBusy);
            BookDao.getInstance().update(connection, entity);
        } catch (DaoException | SQLException | ClassNotFoundException e) {
            ExceptionsHandler.processException(request, e);
        } finally {
            PoolManager.getInstance().releaseConnection(connection);
        }
    }

    public boolean isBusy(HttpServletRequest request, Integer bookId) {
        Connection connection = null;
        try {
            connection = PoolManager.getInstance().getConnection();
//            connection.setAutoCommit(false);
            BookEntity entity = BookDao.getInstance().getById(connection, bookId);
            return entity.isBusy();
        } catch (DaoException | SQLException | ClassNotFoundException e) {
            ExceptionsHandler.processException(request, e);
        } finally {
            PoolManager.getInstance().releaseConnection(connection);
        }
        return true;
    }

    public void deleteBook(HttpServletRequest request, Integer bookId) {
        Connection connection = null;
        try {
            connection = PoolManager.getInstance().getConnection();
//            connection.setAutoCommit(false);
            BookDao.getInstance().delete(connection, bookId);
//            entity.setBusy(isBusy);
//            BookDao.getInstance().update(connection, entity);
        } catch (DaoException | SQLException | ClassNotFoundException e) {
            ExceptionsHandler.processException(request, e);
        } finally {
            PoolManager.getInstance().releaseConnection(connection);
        }
    }

    public void add(HttpServletRequest request, BookEntity bookEntity) {
        Connection connection = null;
        try {
            connection = PoolManager.getInstance().getConnection();
//            connection.setAutoCommit(false);
            BookDao.getInstance().add(connection, bookEntity);
        } catch (DaoException | SQLException | ClassNotFoundException e) {
            ExceptionsHandler.processException(request, e);
        } finally {
            PoolManager.getInstance().releaseConnection(connection);
        }
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
