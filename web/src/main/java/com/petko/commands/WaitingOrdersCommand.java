package com.petko.commands;

import com.petko.ResourceManager;
import com.petko.constants.Constants;
import com.petko.entities.OrderStatus;
import com.petko.services.OrderService;
import com.petko.services.UserService;
import com.petko.vo.WaitingOrdersList;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.List;

public class WaitingOrdersCommand extends AbstractCommand{
    private static WaitingOrdersCommand instance;

    private WaitingOrdersCommand() {}

    public static synchronized WaitingOrdersCommand getInstance() {
        if (instance == null) {
            instance = new WaitingOrdersCommand();
        }
        return instance;
    }

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) {
        UserService userService = UserService.getInstance();
        HttpSession session = request.getSession();
        String login = (String) session.getAttribute("user");
        if (!userService.isAdminUser(request, login)) return;

        OrderService service = OrderService.getInstance();
        String page = ResourceManager.getInstance().getProperty(Constants.PAGE_WAITING_ORDERS);
        List<WaitingOrdersList> waitingOrdersList = service.getWaitingOrdersByStatus(request, OrderStatus.ORDERED);
        session.setAttribute("waitingOrdersList", waitingOrdersList);

        setForwardPage(request, page);
    }
}
