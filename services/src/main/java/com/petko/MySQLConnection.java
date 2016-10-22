package com.petko;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class MySQLConnection {
    public static Connection getConnection() {
        try {
            Class.forName("com.mysql.jdbc.Driver");
            return DriverManager.getConnection("jdbc:mysql://localhost:3306/library4?useUnicode=yes&characterEncoding=UTF-8"
//                    + "&useJDBCCompliantTimezoneShift=true"
//                    + "&useLegacyDatetimeCode=false"
//                    + "&serverTimezone=UTC"
                    ,"root3", "1903");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
}
