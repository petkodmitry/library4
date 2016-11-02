package com.petko.dao;

import com.petko.DaoException;
import com.petko.entities.BookEntity;
import com.petko.entities.OrderEntity;
import com.petko.entities.OrderStatus;
import com.petko.entities.PlaceOfIssue;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class OrderDao implements Dao<OrderEntity> {
    private static OrderDao instance;

    private OrderDao() {}

    public static synchronized OrderDao getInstance() {
        if(instance == null){
            instance = new OrderDao();
        }
        return instance;
    }

    public List<OrderEntity> getAllNewOrdersOfUser(Connection connection, String login) throws DaoException {
        List<OrderEntity> answer = new ArrayList<>();
        try {
            PreparedStatement statement = null;
            ResultSet result = null;
            try {
                statement = connection.prepareStatement("SELECT * FROM ORDERS WHERE login = ? AND status = ?");
                statement.setString(1, login);
                statement.setString(2, OrderStatus.ORDERED.toString());
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
            throw new DaoException("Ошибка выполнения поиска необработанных заказов пользователя");
        }
    }

    public void changeStatusOfOrder(Connection connection, int orderId, OrderStatus newStatus) throws DaoException {
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
