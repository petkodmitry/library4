package com.petko.services;

import com.petko.ActiveUsers;
import com.petko.dao.UserDao;
import com.petko.entities.UserEntity;
import com.petko.managers.PoolManager;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

public class UserService implements Service<UserEntity> {
    private static UserService instance;

    private UserService() {
    }

    public static synchronized UserService getInstance() {
        if (instance == null) {
            instance = new UserService();
        }
        return instance;
    }

    public void addToActiveUsers(String login) {
        // добавляет в List<String> активных пользователей
        ActiveUsers.addUser(login);
    }

    public boolean isLoginSuccess(String login, String password) {
        Connection connection = null;
        try {
            connection = PoolManager.getInstance().getConnection();
            if (connection == null) return false;
            connection.setAutoCommit(false);
            if (UserDao.getInstance().isLoginSuccess(connection, login, password)) {
                connection.commit();
                addToActiveUsers(login);
                return true;
            } else {
                connection.rollback();
                return false;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        } finally {
            PoolManager.getInstance().releaseConnection(connection);
        }
    }

    public void add(UserEntity entity) {

    }

    public List<UserEntity> getAll() {
        return null;
    }

    public UserEntity getByLogin(String login) {
        return null;
    }

    public void update(UserEntity entity) {

    }

    public void delete(int id) {

    }
}
