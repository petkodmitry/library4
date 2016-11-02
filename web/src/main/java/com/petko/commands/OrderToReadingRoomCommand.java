package com.petko.commands;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class OrderToReadingRoomCommand extends AbstractCommand{
    private static OrderToReadingRoomCommand instance;

    private OrderToReadingRoomCommand() {
    }

    public static synchronized OrderToReadingRoomCommand getInstance() {
        if (instance == null) {
            instance = new OrderToReadingRoomCommand();
        }
        return instance;
    }

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) {

    }
}
