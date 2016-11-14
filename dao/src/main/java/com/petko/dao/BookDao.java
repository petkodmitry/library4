package com.petko.dao;

import com.petko.DaoException;
import com.petko.entities.BookEntity;
import com.petko.entities.UserEntity;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

public class BookDao implements Dao<BookEntity> {
    private static BookDao instance;

    private BookDao() {}

    public static synchronized BookDao getInstance() {
        if(instance == null){
            instance = new BookDao();
        }
        return instance;
    }

    public Set<BookEntity> getBusyBooksByTitleOrAuthor(Connection connection, String searchTextInBook) throws DaoException {
        searchTextInBook = "%" + searchTextInBook + "%";
        Set<BookEntity> answer = new HashSet<>();
        try {
            PreparedStatement statement = null;
            ResultSet result = null;
            try {
                statement = connection.prepareStatement("SELECT * FROM BOOKS WHERE (title LIKE ? OR author LIKE ?) AND isbusy = TRUE");
                statement.setString(1, searchTextInBook);
                statement.setString(2, searchTextInBook);
                result = statement.executeQuery();
                while (result.next()) {
                    BookEntity bookEntity = createNewEntity(result.getString(2), result.getString(3), result.getBoolean(4));
                    bookEntity.setBookId(result.getInt(1));
                    answer.add(bookEntity);
                }
                return answer;
            } finally {
                if (result != null) result.close();
                if (statement != null) statement.close();
            }
        } catch (SQLException e) {
            throw new DaoException("Ошибка выполнения поиска в базе занятых книг");
        }
    }

    public Set<BookEntity> getFreeBooksByTitleOrAuthor(Connection connection, String searchTextInBook) throws DaoException {
        searchTextInBook = "%" + searchTextInBook + "%";
        Set<BookEntity> answer = new HashSet<>();
        try {
            PreparedStatement statement = null;
            ResultSet result = null;
            try {
                statement = connection.prepareStatement("SELECT * FROM BOOKS WHERE (title LIKE ? OR author LIKE ?) AND isbusy = FALSE");
                statement.setString(1, searchTextInBook);
                statement.setString(2, searchTextInBook);
                result = statement.executeQuery();
                while (result.next()) {
                    BookEntity bookEntity = createNewEntity(result.getString(2), result.getString(3), result.getBoolean(4));
                    bookEntity.setBookId(result.getInt(1));
                    answer.add(bookEntity);
                }
                return answer;
            } finally {
                if (result != null) result.close();
                if (statement != null) statement.close();
            }
        } catch (SQLException e) {
            throw new DaoException("Ошибка выполнения поиска в базе свободных книг");
        }
    }

    public List<BookEntity> getBooksByTitleOrAuthor(Connection connection, String searchTextInBook) throws DaoException {
        searchTextInBook = "%" + searchTextInBook + "%";
        List<BookEntity> answer = new ArrayList<>();
        try {
            PreparedStatement statement = null;
            ResultSet result = null;
            try {
                statement = connection.prepareStatement("SELECT * FROM BOOKS WHERE (title LIKE ? OR author LIKE ?)");
                statement.setString(1, searchTextInBook);
                statement.setString(2, searchTextInBook);
                result = statement.executeQuery();
                while (result.next()) {
                    BookEntity bookEntity = createNewEntity(result.getString(2), result.getString(3), result.getBoolean(4));
                    bookEntity.setBookId(result.getInt(1));
                    answer.add(bookEntity);
                }
                return answer;
            } finally {
                if (result != null) result.close();
                if (statement != null) statement.close();
            }
        } catch (SQLException e) {
            throw new DaoException("Ошибка выполнения поиска книг в базе");
        }
    }

    public BookEntity getById(Connection connection, int id) throws DaoException {
        BookEntity answer = new BookEntity();
        try {
            PreparedStatement statement = null;
            ResultSet result = null;
            try {
                statement = connection.prepareStatement("SELECT * FROM BOOKS WHERE bid = ?");
                statement.setInt(1, id);
                result = statement.executeQuery();
                if (result.next()) {
                    answer = createNewEntity(result.getString(2), result.getString(3), result.getBoolean(4));
                    answer.setBookId(id);
                }
                return answer;
            } finally {
                if (result != null) result.close();
                if (statement != null) statement.close();
            }
        } catch (SQLException e) {
            throw new DaoException("Ошибка выполнения поиска книги по ID");
        }
    }

    public void update(Connection connection, BookEntity entity) throws DaoException {
        try {
            PreparedStatement statement = null;
            try {
                statement = connection.prepareStatement("UPDATE BOOKS SET title = ?, author = ?, isbusy = ? WHERE bid = ?");
                statement.setString(1, entity.getTitle());
                statement.setString(2, entity.getAuthor());
                statement.setBoolean(3, entity.isBusy());
                statement.setInt(4, entity.getBookId());
                statement.executeUpdate();
            } finally {
                if (statement != null) statement.close();
            }
        } catch (SQLException e) {
            throw new DaoException("Ошибка обновления полей книги с ID " + entity.getBookId());
        }
    }

    public void delete(Connection connection, int id) throws DaoException {
        try {
            PreparedStatement statement = null;
            try {
                statement = connection.prepareStatement("DELETE FROM BOOKS WHERE bid = ?");
                statement.setInt(1, id);
                statement.executeUpdate();
            } finally {
                if (statement != null) statement.close();
            }
        } catch (SQLException e) {
            throw new DaoException("Ошибка удаления книги с ID " + id);
        }
    }

    public void add(Connection connection, BookEntity entity) throws DaoException {
        try {
            PreparedStatement statement = null;
            try {
                statement = connection.prepareStatement("INSERT INTO BOOKS (title, author, isbusy) VALUES (?, ?, ?)");
                statement.setString(1, entity.getTitle());
                statement.setString(2, entity.getAuthor());
                statement.setBoolean(3, false);
                entity.setBookId(statement.executeUpdate());
            } finally {
                if (statement != null) statement.close();
            }
        } catch (SQLException e) {
            throw new DaoException("Ошибка добавления новой книги в базу");
        }
    }

    public BookEntity createNewEntity(String title, String author, boolean isBusy) {
        BookEntity result = new BookEntity();
        result.setTitle(title);
        result.setAuthor(author);
        result.setBusy(isBusy);
        return result;
    }

    public void add(BookEntity entity) {}

    public List<BookEntity> getAll() {
        return null;
    }

    @Override
    public BookEntity getById(int id) {
        return null;
    }

    public void delete(int id) {}

}
