package com.petko.services;

import com.petko.DaoException;
import com.petko.ExceptionsHandler;
import com.petko.dao.BookDao;
import com.petko.dao.OrderDao;
import com.petko.entities.BookEntity;
import com.petko.entities.OrderEntity;
import com.petko.entities.OrderStatus;
import com.petko.managers.PoolManager;
import com.petko.vo.OrderForMyOrdersList;

import javax.servlet.http.HttpServletRequest;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class OrderService implements Service<OrderEntity>{
    private static OrderService instance;

    private OrderService() {}

    public static synchronized OrderService getInstance() {
        if(instance == null){
            instance = new OrderService();
        }
        return instance;
    }

    public void add(OrderEntity entity) {

    }

    public List<OrderForMyOrdersList> getOrdersForOrdersList(HttpServletRequest request, String login) {
        List<OrderForMyOrdersList> result = new ArrayList<>();
        Connection connection = null;
        try {
            connection = PoolManager.getInstance().getConnection();
//            connection.setAutoCommit(false);
            List<OrderEntity> orderEntityList = (OrderDao.getInstance().getAllNewOrdersOfUser(connection, login));
            for (OrderEntity entity: orderEntityList) {
                OrderForMyOrdersList orderView = new OrderForMyOrdersList(entity.getOrderId(), entity.getBookId(),
                        entity.getPlaceOfIssue(), entity.getStartDate());
                BookEntity bookEntity = BookDao.getInstance().getById(connection, entity.getBookId());
                orderView.setTitle(bookEntity.getTitle());
                orderView.setAuthor(bookEntity.getAuthor());
                result.add(orderView);
            }
        } catch (DaoException | SQLException | ClassNotFoundException e) {
            ExceptionsHandler.processException(request, e);
            return Collections.emptyList();
        } finally {
            PoolManager.getInstance().releaseConnection(connection);
        }
        return result;
    }

    public void cancelOrder(HttpServletRequest request, String login, int orderID) {
        Connection connection = null;
        try {
            connection = PoolManager.getInstance().getConnection();
            OrderEntity entity = OrderDao.getInstance().getById(connection, orderID);
            if (entity.getLogin().equals(login) && entity.getStatus().equals(OrderStatus.ORDERED)) {
                OrderDao.getInstance().changeStatusOfOrder(connection, orderID, OrderStatus.CLOSED);
            }
//            connection.setAutoCommit(false);
        } catch (DaoException | SQLException | ClassNotFoundException e) {
            ExceptionsHandler.processException(request, e);
        } finally {
            PoolManager.getInstance().releaseConnection(connection);
        }
    }

    public List<OrderEntity> getAll() {
        return null;
    }

    public OrderEntity getByLogin(String login) {
        return null;
    }

    public void update(OrderEntity entity) {

    }

    public void delete(int id) {

    }
}
