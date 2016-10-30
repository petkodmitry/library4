package com.petko.services;

import com.petko.ActiveUsers;
import com.petko.DaoException;
import com.petko.ExceptionsHandler;
import com.petko.dao.UserDao;
import com.petko.entities.UserEntity;
import com.petko.managers.PoolManager;

import javax.servlet.http.HttpServletRequest;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Collections;
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
        } catch (DaoException | SQLException | ClassNotFoundException e) {
            ExceptionsHandler.processException(request, e);
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
        } catch (DaoException | SQLException | ClassNotFoundException e) {
            ExceptionsHandler.processException(request, e);
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
            connection.setAutoCommit(false);
            Set<String> allLogins = UserDao.getInstance().getAllLogins(connection);
            for (String login : allLogins) {
                result.add(UserDao.getInstance().getByLogin(connection, login));
            }
            if (allLogins.size() == result.size()) connection.commit();
            else {
                result = Collections.emptySet();
                connection.rollback();
            }
        } catch (DaoException | SQLException | ClassNotFoundException e) {
            ExceptionsHandler.processException(request, e);
            return Collections.emptySet();
        } finally {
            PoolManager.getInstance().releaseConnection(connection);
        }
        return result;
    }

    public boolean isLoginExists(HttpServletRequest request, String login) {
        Connection connection = null;
        try {
            connection = PoolManager.getInstance().getConnection();
            String entityLogin = UserDao.getInstance().getByLogin(connection, login).getLogin();
            if (login.equals(entityLogin)) return true;
        } catch (DaoException | SQLException | ClassNotFoundException e) {
            ExceptionsHandler.processException(request, e);
            return false;
        } finally {
            PoolManager.getInstance().releaseConnection(connection);
        }
        return false;
    }

    public boolean isAllPasswordRulesFollowed(String password, String repeatPassword) {
        /**
         * check for equality and the length
         */
        return password.equals(repeatPassword) && password.length() >= 8;
    }

    public void addNewEntityToDataBase(HttpServletRequest request, String name, String lastName, String login, String password,
                                boolean isAdmin, boolean isBlocked) {
        Connection connection = null;
        try {
            connection = PoolManager.getInstance().getConnection();
            UserEntity entity = UserDao.getInstance().createNewEntity(name, lastName, login, password, isAdmin, isBlocked);
            UserDao.getInstance().add(connection, entity);
        } catch (DaoException | SQLException | ClassNotFoundException e) {
            ExceptionsHandler.processException(request, e);
        } finally {
            PoolManager.getInstance().releaseConnection(connection);
        }
    }

    public UserEntity getByLogin(String login) {
        return null;
    }

    public void update(UserEntity entity) {

    }

    public void delete(int id) {

    }
}
