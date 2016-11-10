package com.petko.services;

import com.petko.DaoException;
import com.petko.ExceptionsHandler;
import com.petko.constants.Constants;
import com.petko.dao.BookDao;
import com.petko.dao.OrderDao;
import com.petko.entities.BookEntity;
import com.petko.entities.OrderEntity;
import com.petko.entities.OrderStatus;
import com.petko.entities.PlaceOfIssue;
import com.petko.managers.PoolManager;
import com.petko.vo.OrderForMyOrdersList;

import javax.servlet.http.HttpServletRequest;
import java.sql.*;
import java.sql.Date;
import java.util.*;

public class OrderService implements Service<OrderEntity>{
    private static OrderService instance;

    private OrderService() {}

    public static synchronized OrderService getInstance() {
        if(instance == null){
            instance = new OrderService();
        }
        return instance;
    }

    public List<OrderForMyOrdersList> getOrdersByLoginAndStatus(HttpServletRequest request, String login, OrderStatus orderStatus) {
        List<OrderForMyOrdersList> result = new ArrayList<>();
        Connection connection = null;
        try {
            connection = PoolManager.getInstance().getConnection();
//            connection.setAutoCommit(false);
            Set<OrderEntity> orderEntityList = OrderDao.getInstance().getAllByUser(connection, login);
            Set<OrderEntity> listByStatus = OrderDao.getInstance().getAllByStatus(connection, orderStatus.toString());
            orderEntityList.retainAll(listByStatus);

            for (OrderEntity entity: orderEntityList) {
                OrderForMyOrdersList orderView = new OrderForMyOrdersList(entity.getOrderId(), entity.getBookId(),
                        entity.getPlaceOfIssue(), entity.getStartDate(), entity.getEndDate());
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
//            connection.setAutoCommit(false);
            OrderEntity entity = OrderDao.getInstance().getById(connection, orderID);
            if (entity.getLogin().equals(login) && entity.getStatus().equals(OrderStatus.ORDERED)) {
                OrderDao.getInstance().changeStatusOfOrder(connection, orderID, OrderStatus.CLOSED);
                OrderDao.getInstance().changeEndDateOfOrder(connection, orderID, new Date(Calendar.getInstance().getTime().getTime()));
            }
        } catch (DaoException | SQLException | ClassNotFoundException e) {
            ExceptionsHandler.processException(request, e);
        } finally {
            PoolManager.getInstance().releaseConnection(connection);
        }
    }

    public void orderToHomeOrToRoom(HttpServletRequest request, String login, int bookID, boolean isToHome) {
        Connection connection = null;
        try {
            connection = PoolManager.getInstance().getConnection();
//            connection.setAutoCommit(false);
            Set<OrderEntity> orderEntityList = OrderDao.getInstance().getAllByUser(connection, login);
            Set<OrderEntity> orderEntityList2 = new HashSet<>(orderEntityList);

            Set<OrderEntity> listByStatus = OrderDao.getInstance().getAllByStatus(connection, OrderStatus.ORDERED.toString());
            orderEntityList.retainAll(listByStatus);

            listByStatus = OrderDao.getInstance().getAllByStatus(connection, OrderStatus.ON_HAND.toString());
            orderEntityList2.retainAll(listByStatus);
            orderEntityList.addAll(orderEntityList2);

            Set<OrderEntity> listByBookId = OrderDao.getInstance().getAllByBookId(connection, bookID);
            orderEntityList.retainAll(listByBookId);
            if (orderEntityList.isEmpty()) {
                long delay = 0L;
                PlaceOfIssue place = PlaceOfIssue.READING_ROOM;
                if (isToHome) {
                    delay = 30L * 24L * 60L * 60L * 1_000L;
                    place = PlaceOfIssue.HOME;
                }
                Date startDate = new Date(Calendar.getInstance().getTime().getTime());
                Date endDate = new Date(startDate.getTime() + delay);
                OrderEntity newEntity = OrderDao.getInstance().createNewEntity(login, bookID, OrderStatus.ORDERED, place,
                        startDate, endDate);
                OrderDao.getInstance().add(connection, newEntity);
            } else {
                request.setAttribute(Constants.ERROR_MESSAGE_ATTRIBUTE, "Заказ на эту книгу имеется и активен");
            }
        } catch (DaoException | SQLException | ClassNotFoundException e) {
            ExceptionsHandler.processException(request, e);
        } finally {
            PoolManager.getInstance().releaseConnection(connection);
        }
    }

    public void prolongOrder(HttpServletRequest request, String login, int orderID) {
        Connection connection = null;
        try {
            connection = PoolManager.getInstance().getConnection();
//            connection.setAutoCommit(false);
            OrderEntity entity = OrderDao.getInstance().getById(connection, orderID);
            if (entity.getLogin().equals(login) && entity.getStatus().equals(OrderStatus.ON_HAND)) {
                long gap = 30L * 24L * 60L * 60L * 1_000L;
                long delay = 5L * 24L * 60L * 60L * 1_000L;
                Date endDate = entity.getEndDate();
                Date currentDate = new Date(Calendar.getInstance().getTime().getTime());
                long difference = endDate.getTime() - currentDate.getTime();
                if (difference > 0 && (difference - delay) < 6) {
                    OrderDao.getInstance().changeEndDateOfOrder(connection, orderID, new Date(endDate.getTime() + gap));
                } else {
                    request.setAttribute(Constants.ERROR_MESSAGE_ATTRIBUTE, "Заказ не должен быть просрочен, " +
                            "и время до его окончания не должно превышать 5 дней");
                }
            }
        } catch (DaoException | SQLException | ClassNotFoundException e) {
            ExceptionsHandler.processException(request, e);
        } finally {
            PoolManager.getInstance().releaseConnection(connection);
        }
    }

    public void add(OrderEntity entity) {

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
