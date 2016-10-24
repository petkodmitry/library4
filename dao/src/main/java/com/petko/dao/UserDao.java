package com.petko.dao;

import com.petko.entities.UserEntity;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class UserDao implements Dao<UserEntity> {
    private static UserDao instance;

    private UserDao() {}

    public static synchronized UserDao getInstance() {
        if (instance == null) {
            instance = new UserDao();
        }
        return instance;
    }

    public boolean isLoginSuccess(Connection connection, String login, String password) throws SQLException{
        try {
            PreparedStatement statement = null;
            ResultSet result = null;
//            Connection connection = PoolManager.getInstance().getConnection();
            try {
                statement = connection.prepareStatement("SELECT * FROM USERS WHERE login = ? AND psw = ?");
                statement.setString(1, login);
                statement.setString(2, password);
                result = statement.executeQuery();
                return result.next();
            } finally {
                if (result != null) result.close();
                if (statement != null) statement.close();
//                PoolManager.getInstance().releaseConnection(connection);
            }
        } catch (SQLException e) {
            throw new SQLException("Ошибка выполнения запроса логина/пароля к базе");
        }
//        return false;
    }

    public int getUserStatus(Connection connection, String login) throws SQLException{
        try {
            PreparedStatement statement = null;
            ResultSet result = null;
            try {
                statement = connection.prepareStatement("SELECT isadmin FROM USERS WHERE login = ?");
                statement.setString(1, login);
                result = statement.executeQuery();
                if (result.next()) {
                    return result.getInt("isadmin");
                }
                return 0;
            } finally {
                if (result != null) result.close();
                if (statement != null) statement.close();
            }
        } catch (SQLException e) {
            throw new SQLException("Ошибка выполнения запроса к базе об уровне пользователя");
        }
    }

    public void add(UserEntity entity) {
    }

    public List<UserEntity> getAll() {
        return null;
    }

    public UserEntity getById(int id) {
        return null;
    }

    public UserEntity getByLogin(String login) {

        return null;
    }

    public void delete(int id) {

    }
}
