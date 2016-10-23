package com.petko.dao;

import com.petko.entities.OrderEntity;

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
