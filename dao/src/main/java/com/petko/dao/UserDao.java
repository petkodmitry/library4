package com.petko.dao;

import com.petko.entities.UserEntity;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

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

    public Set<String> getAllLogins(Connection connection) throws SQLException{
        Set<String> answer = new HashSet<String>();
        try {
            PreparedStatement statement = null;
            ResultSet result = null;
            try {
                statement = connection.prepareStatement("SELECT login FROM USERS");
                result = statement.executeQuery();
                while (result.next()) {
                    String user = result.getString(1);
                    answer.add(user);
                }
                return answer;
            } finally {
                if (result != null) result.close();
                if (statement != null) statement.close();
            }
        } catch (SQLException e) {
            throw new SQLException("Ошибка выполнения запроса к базе о всех логинах");
        }
    }

    public UserEntity getById(int id) {
        return null;
    }

    public UserEntity getByLogin(Connection connection, String login) throws SQLException{
        try {
            UserEntity answer = new UserEntity();
            PreparedStatement statement = null;
            ResultSet result = null;
            try {
                statement = connection.prepareStatement("SELECT * FROM USERS WHERE login = ?");
                statement.setString(1, login);
                result = statement.executeQuery();
                if (result.next()) {
                    answer.setUserId(result.getInt(1));
                    answer.setFirstName(result.getString(2));
                    answer.setLastName(result.getString(3));
                    answer.setLogin(result.getString(4));
                    answer.setPassword(result.getString(5));
                    answer.setAdmin(result.getBoolean(6));
                    answer.setBlocked(result.getBoolean(7));
                }
                return answer;
            } finally {
                if (result != null) result.close();
                if (statement != null) statement.close();
            }
        } catch (SQLException e) {
            throw new SQLException("Ошибка выполнения запроса информации о пользователе");
        }
    }

    public void delete(int id) {

    }
}
