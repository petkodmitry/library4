package com.petko.dao;

import com.petko.DaoException;
import com.petko.entities.OrderEntity;
import com.petko.entities.OrderStatus;
import com.petko.entities.PlaceOfIssue;
import com.petko.managers.PoolManager;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Date;
import java.util.*;

public class OrderDao implements Dao<OrderEntity> {
    private static PoolManager poolManager = PoolManager.getInstance();
    private static OrderDao instance;

    private OrderDao() {}

    public static synchronized OrderDao getInstance() {
        if(instance == null){
            instance = new OrderDao();
        }
        return instance;
    }

    public Set<OrderEntity> getAllByUser(Connection connection, String login) throws DaoException {
//        Connection connection = poolManager.getConnection();
        Set<OrderEntity> answer = new HashSet<>();
        try {
            PreparedStatement statement = null;
            ResultSet result = null;
            try {
                statement = connection.prepareStatement("SELECT * FROM ORDERS WHERE login = ?");
                statement.setString(1, login);
                result = statement.executeQuery();
                while (result.next()) {
                    OrderEntity entity;
                    entity = createNewEntity(login, result.getInt(3), OrderStatus.getOrderStatus(result.getString(4)),
                            PlaceOfIssue.getPlaceOfIssue(result.getString(5)), result.getDate(6), result.getDate(7));
                    entity.setOrderId(result.getInt(1));
                    answer.add(entity);
                }
                return answer;
            } finally {
                if (result != null) result.close();
                if (statement != null) statement.close();
            }
        } catch (SQLException e) {
            throw new DaoException("Ошибка выполнения поиска заказов по пользователю");
        }
    }

    public Set<OrderEntity> getAllByStatus(Connection connection, String status) throws DaoException {
//        Connection connection = poolManager.getConnection();
        Set<OrderEntity> answer = new HashSet<>();
        try {
            PreparedStatement statement = null;
            ResultSet result = null;
            try {
                statement = connection.prepareStatement("SELECT * FROM ORDERS WHERE status = ?");
                statement.setString(1, status);
                result = statement.executeQuery();
                while (result.next()) {
                    OrderEntity entity;
                    entity = createNewEntity(result.getString(2), result.getInt(3), OrderStatus.getOrderStatus(result.getString(4)),
                            PlaceOfIssue.getPlaceOfIssue(result.getString(5)), result.getDate(6), result.getDate(7));
                    entity.setOrderId(result.getInt(1));
                    answer.add(entity);
                }
                return answer;
            } finally {
                if (result != null) result.close();
                if (statement != null) statement.close();
            }
        } catch (SQLException e) {
            throw new DaoException("Ошибка выполнения поиска заказов по статусу");
        }
    }

    public Set<OrderEntity> getAllByBookId(Connection connection, int bookId) throws DaoException {
//        Connection connection = poolManager.getConnection();
        Set<OrderEntity> answer = new HashSet<>();
        try {
            PreparedStatement statement = null;
            ResultSet result = null;
            try {
                statement = connection.prepareStatement("SELECT * FROM ORDERS WHERE bid = ?");
                statement.setInt(1, bookId);
                result = statement.executeQuery();
                while (result.next()) {
                    OrderEntity entity;
                    entity = createNewEntity(result.getString(2), bookId, OrderStatus.getOrderStatus(result.getString(4)),
                            PlaceOfIssue.getPlaceOfIssue(result.getString(5)), result.getDate(6), result.getDate(7));
                    entity.setOrderId(result.getInt(1));
                    answer.add(entity);
                }
                return answer;
            } finally {
                if (result != null) result.close();
                if (statement != null) statement.close();
            }
        } catch (SQLException e) {
            throw new DaoException("Ошибка выполнения поиска заказов по ID книги");
        }
    }

    public void changeStatusOfOrder(Connection connection, int orderId, OrderStatus newStatus) throws DaoException {
//        Connection connection = poolManager.getConnection();
        try {
            PreparedStatement statement = null;
//            ResultSet result = null;
            try {
                statement = connection.prepareStatement("UPDATE ORDERS SET status = ? WHERE oid = ?");
                statement.setString(1, newStatus.toString());
                statement.setInt(2, orderId);
                statement.executeUpdate();
            } finally {
//                if (result != null) result.close();
                if (statement != null) statement.close();
            }
        } catch (SQLException e) {
            throw new DaoException("Ошибка изменения статуса заказа");
        }
    }

    // TODO объединить с changeStatusOfOrder() - сделать updateEntity()
    public void changeEndDateOfOrder(Connection connection, int orderId, Date endDate) throws DaoException {
//        Connection connection = poolManager.getConnection();
        try {
            PreparedStatement statement = null;
//            ResultSet result = null;
            try {
                statement = connection.prepareStatement("UPDATE ORDERS SET enddate = ? WHERE oid = ?");
                statement.setDate(1, endDate);
                statement.setInt(2, orderId);
                statement.executeUpdate();
            } finally {
//                if (result != null) result.close();
                if (statement != null) statement.close();
            }
        } catch (SQLException e) {
            throw new DaoException("Ошибка изменения даты окончания заказа");
        }
    }

    public OrderEntity createNewEntity(String login, int bookId, OrderStatus status, PlaceOfIssue place, Date startDate, Date endDate) {
        OrderEntity result = new OrderEntity();
        result.setLogin(login);
        result.setBookId(bookId);
        result.setStatus(status);
        result.setPlaceOfIssue(place);
        result.setStartDate(startDate);
        result.setEndDate(endDate);
        return result;
    }

    public OrderEntity getById(Connection connection, int id) throws DaoException {
//        Connection connection = poolManager.getConnection();
        OrderEntity answer = new OrderEntity();
        try {
            PreparedStatement statement = null;
            ResultSet result = null;
            try {
                statement = connection.prepareStatement("SELECT * FROM ORDERS WHERE oid = ?");
                statement.setInt(1, id);
                result = statement.executeQuery();
                if (result.next()) {
                    answer = createNewEntity(result.getString(2), result.getInt(3), OrderStatus.getOrderStatus(result.getString(4)),
                            PlaceOfIssue.getPlaceOfIssue(result.getString(5)), result.getDate(6), result.getDate(7));
                    answer.setOrderId(id);
                }
                return answer;
            } finally {
                if (result != null) result.close();
                if (statement != null) statement.close();
            }
        } catch (SQLException e) {
            throw new DaoException("Ошибка выполнения поиска заказа по ID");
        }
    }

    public void add(Connection connection, OrderEntity entity) throws DaoException {
//        Connection connection = poolManager.getConnection();
        try {
            PreparedStatement statement = null;
//            ResultSet result = null;
            try {
                statement = connection.prepareStatement("INSERT INTO ORDERS (login, bid, status, placeofissue, startdate, enddate) VALUES (?, ?, ?, ?, ?, ?)");
                statement.setString(1, entity.getLogin());
                statement.setInt(2, entity.getBookId());
                statement.setString(3, entity.getStatus().toString());
                statement.setString(4, entity.getPlaceOfIssue().toString());
                statement.setDate(5, entity.getStartDate());
                statement.setDate(6, entity.getEndDate());
                statement.executeUpdate();
            } finally {
//                if (result != null) result.close();
                if (statement != null) statement.close();
            }
        } catch (SQLException e) {
            throw new DaoException("Ошибка выполнения запроса на добавление заказа");
        }
    }

    /*public void update(Connection connection, OrderEntity entity) throws DaoException {
//        Connection connection = poolManager.getConnection();
        try {
            PreparedStatement statement = null;
//            ResultSet result = null;
            try {
                statement = connection.prepareStatement("UPDATE ORDERS SET (login, bid, status, placeofissue, startdate, enddate) VALUES (?, ?, ?, ?, ?, ?)");
                statement.setString(1, entity.getLogin());
                statement.setInt(2, entity.getBookId());
                statement.setString(3, entity.getStatus().toString());
                statement.setString(4, entity.getPlaceOfIssue().toString());
                statement.setDate(5, entity.getStartDate());
                statement.setDate(6, entity.getEndDate());
                statement.executeUpdate();
            } finally {
//                if (result != null) result.close();
                if (statement != null) statement.close();
            }
        } catch (SQLException e) {
            throw new DaoException("Ошибка выполнения запроса на добавление заказа");
        }
    }*/

    public void add(OrderEntity entity) {

    }

    public List<OrderEntity> getAll() {
        return null;
    }

    public OrderEntity getById(int id) {
        return null;
    }

    public void delete(int id) {

    }
}
