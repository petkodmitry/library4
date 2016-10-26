package com.petko.services;

import com.petko.ActiveUsers;
import com.petko.constants.Constants;
import com.petko.dao.UserDao;
import com.petko.entities.UserEntity;
import com.petko.managers.PoolManager;

import javax.servlet.http.HttpServletRequest;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

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

    private void addToActiveUsers(String login) {
        // добавляет в List<String> активных пользователей
        ActiveUsers.addUser(login);
    }

    private void removeFromActiveUsers(String login) {
        // удаляет из List<String> активных пользователей
        ActiveUsers.removeUser(login);
    }

    public void logOut(HttpServletRequest request, String login) {
        if (login != null) removeFromActiveUsers(login);
        request.getSession().invalidate();

    }

    public boolean isLoginSuccess(HttpServletRequest request, String login, String password) {
        if (login == null || password == null) return false;
        Connection connection = null;
        try {
            connection = PoolManager.getInstance().getConnection();
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
            String eMessage;
            if ((eMessage = e.getMessage()) == null) eMessage = "Ошибка отправки запроса";
            request.setAttribute(Constants.ERROR_MESSAGE_ATTRIBUTE, eMessage);
            return false;
        } catch (ClassNotFoundException e) {
            String eMessage;
            if ((eMessage = e.getMessage()) == null) eMessage = "Ошибка загрузки неизвестного класса";
            request.setAttribute(Constants.ERROR_MESSAGE_ATTRIBUTE, eMessage);
            return false;
        } finally {
            PoolManager.getInstance().releaseConnection(connection);
        }
    }

    public boolean isAdminUser(HttpServletRequest request, String login) {
        Connection connection = null;
        try {
            connection = PoolManager.getInstance().getConnection();
            return UserDao.getInstance().getUserStatus(connection, login) == 1;
        } catch (SQLException e) {
            String eMessage;
            if ((eMessage = e.getMessage()) != null) eMessage = "Ошибка отправки запроса";
            request.setAttribute(Constants.ERROR_MESSAGE_ATTRIBUTE, eMessage);
            return false;
        } catch (ClassNotFoundException e) {
            String eMessage;
            if ((eMessage = e.getMessage()) != null) eMessage = "Ошибка загрузки неизвестного класса";
            request.setAttribute(Constants.ERROR_MESSAGE_ATTRIBUTE, eMessage);
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

    public Set<UserEntity> getAll(HttpServletRequest request) {
        Connection connection = null;
        Set<UserEntity> result = new HashSet<UserEntity>();
        try {
            connection = PoolManager.getInstance().getConnection();
            for (String login : UserDao.getInstance().getAllLogins(connection)) {
                result.add(UserDao.getInstance().getByLogin(connection, login));
            }
        } catch (SQLException e) {
            String eMessage;
            if ((eMessage = e.getMessage()) != null) eMessage = "Ошибка отправки запроса";
            request.setAttribute(Constants.ERROR_MESSAGE_ATTRIBUTE, eMessage);
            return null;
        } catch (ClassNotFoundException e) {
            String eMessage;
            if ((eMessage = e.getMessage()) != null) eMessage = "Ошибка загрузки неизвестного класса";
            request.setAttribute(Constants.ERROR_MESSAGE_ATTRIBUTE, eMessage);
            return null;
        } finally {
            PoolManager.getInstance().releaseConnection(connection);
        }
        return result;
    }

    public UserEntity getByLogin(String login) {
        return null;
    }

    public void update(UserEntity entity) {

    }

    public void delete(int id) {

    }
}
