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
import com.petko.vo.WaitingOrdersList;

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

    public List<WaitingOrdersList> getWaitingOrdersByStatus(HttpServletRequest request, OrderStatus orderStatus) {
        List<WaitingOrdersList> result = new ArrayList<>();
        Connection connection = null;
        try {
            connection = PoolManager.getInstance().getConnection();
//            connection.setAutoCommit(false);
            Set<OrderEntity> listByStatus = OrderDao.getInstance().getAllByStatus(connection, orderStatus.toString());

            for (OrderEntity entity: listByStatus) {
                WaitingOrdersList orderView = new WaitingOrdersList(entity.getOrderId(), entity.getLogin(), entity.getBookId(),
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

    public void closeOrder(HttpServletRequest request, String login, int orderID) {
        Connection connection = null;
        try {
            connection = PoolManager.getInstance().getConnection();
//            connection.setAutoCommit(false);
            OrderEntity entity = OrderDao.getInstance().getById(connection, orderID);
            if ((login == null) ||
                    (entity.getLogin().equals(login) && entity.getStatus().equals(OrderStatus.ORDERED))) {
                OrderDao.getInstance().changeStatusOfOrder(connection, orderID, OrderStatus.CLOSED);
                OrderDao.getInstance().changeEndDateOfOrder(connection, orderID, new Date(Calendar.getInstance().getTime().getTime()));
            }
            // if User brought book to the Library, we mark Book as free
            if (login == null && entity.getStatus().equals(OrderStatus.ON_HAND)) {
                BookService.getInstance().setBookBusy(request, entity.getBookId(), false);
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
                // time interval from now till the end date of the order. In case not to allow a user indefinitely prolong his order
                int interval = 5;
                long gap = 30L * 24L * 60L * 60L * 1_000L;
                long delay = interval * 24L * 60L * 60L * 1_000L;
                Date endDate = entity.getEndDate();
                Date currentDate = new Date(Calendar.getInstance().getTime().getTime());
                long difference = endDate.getTime() - currentDate.getTime();
                if (difference > 0 && (difference - delay) <= interval) {
                    OrderDao.getInstance().changeEndDateOfOrder(connection, orderID, new Date(endDate.getTime() + gap));
                } else {
                    request.setAttribute(Constants.ERROR_MESSAGE_ATTRIBUTE, "Заказ не должен быть просрочен, " +
                            "и время до его окончания не должно превышать " + interval + " дней");
                }
            }
        } catch (DaoException | SQLException | ClassNotFoundException e) {
            ExceptionsHandler.processException(request, e);
        } finally {
            PoolManager.getInstance().releaseConnection(connection);
        }
    }

    public void provideBook(HttpServletRequest request, int orderID) {
        Connection connection = null;
        try {
            connection = PoolManager.getInstance().getConnection();
//            connection.setAutoCommit(false);
            OrderEntity entity = OrderDao.getInstance().getById(connection, orderID);
            if (BookService.getInstance().isBusy(request, entity.getBookId())) {
                request.setAttribute(Constants.ERROR_MESSAGE_ATTRIBUTE, "Эта книга уже выдана!");
                return;
            }
            if (entity.getStatus().equals(OrderStatus.ORDERED)) {
                long delay = 0L;
                if (PlaceOfIssue.HOME.equals(entity.getPlaceOfIssue())) {
                    delay = 30L * 24L * 60L * 60L * 1_000L;
                }
                Date currentDate = new Date(Calendar.getInstance().getTime().getTime());
                Date newEndDate = new Date(currentDate.getTime() + delay);
                OrderDao.getInstance().changeStatusOfOrder(connection, orderID, OrderStatus.ON_HAND);
                OrderDao.getInstance().changeEndDateOfOrder(connection, orderID, newEndDate);
                BookService.getInstance().setBookBusy(request, entity.getBookId(), true);
            } else {
                request.setAttribute(Constants.ERROR_MESSAGE_ATTRIBUTE, "Проверьте статус заказа!");
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
