package com.petko.commands;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class MyOrdersCommand extends AbstractCommand{
    private static MyOrdersCommand instance;

    private MyOrdersCommand() {
    }

    public static synchronized MyOrdersCommand getInstance() {
        if (instance == null) {
            instance = new MyOrdersCommand();
        }
        return instance;
    }

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) {

    }
}
