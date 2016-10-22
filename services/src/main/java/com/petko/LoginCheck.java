package com.petko;

import com.petko.managers.PoolManager;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class LoginCheck {
    public boolean check(String login, String password) {
        Connection connection = PoolManager.getInstance().getConnection();
        if (connection == null) return false;
        // TODO перенести в dao
        try {
            PreparedStatement statement = null;
            try {
                statement = connection.prepareStatement("SELECT * FROM USERS WHERE login = ? AND psw = ?");
                statement.setString(1, login);
                statement.setString(2, password);
                ResultSet result = null;
                try {
                    result = statement.executeQuery();
                    return result.next();
                } finally {
                    if (result != null) result.close();
                }
            } finally {
                if (statement != null) statement.close();
//                connection.close();
                PoolManager.getInstance().releaseConnection(connection);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
}
