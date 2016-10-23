package com.petko;

import com.petko.managers.PoolManager;

import java.sql.Connection;

public class ServiceManager {
    private ServiceManager() {}

    public static void releaseConnection(Connection connection) {
        PoolManager.getInstance().releaseConnection(connection);
    }
}
