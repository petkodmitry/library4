package com.petko.commands;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class OrderToHomeCommand extends AbstractCommand{
    private static OrderToHomeCommand instance;

    private OrderToHomeCommand() {
    }

    public static synchronized OrderToHomeCommand getInstance() {
        if (instance == null) {
            instance = new OrderToHomeCommand();
        }
        return instance;
    }

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) {
        
    }
}
