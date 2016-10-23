package com.petko.services;

import com.petko.entities.OrderEntity;

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
