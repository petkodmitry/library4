package com.petko.commands;

import com.petko.services.OrderService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

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
        OrderService service = OrderService.getInstance();
        HttpSession session = request.getSession();
        String login = (String) session.getAttribute("user");
        int bookId = Integer.parseInt(request.getParameter("bookId"));
        service.orderToHomeOrToRoom(request, login, bookId, true);

        MyOrdersCommand.getInstance().execute(request, response);
    }
}
