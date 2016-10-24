package com.petko;

import com.petko.commands.Command;
import com.petko.commands.CommandType;
import com.petko.constants.Constants;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Enumeration;

public class RequestHandler {
    private RequestHandler() {}

    public static void processRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
//        request.setAttribute(Constants.ERROR_MESSAGE_ATTRIBUTE, "");
        String cmd = request.getParameter("cmd");
        Command command;
        if (cmd != null /*&& (command = CommandType.getCommand(cmd)) != null*/) {
            if ((command = CommandType.getCommand(cmd)) != null) {
                command.execute(request, response);
                RequestDispatcher dispatcher = request.getServletContext().
                        getRequestDispatcher((String) request.getAttribute(Constants.FORWARD_PAGE_ATTRIBUTE));
                dispatcher.forward(request, response);
            } else {
                request.setAttribute(Constants.ERROR_MESSAGE_ATTRIBUTE, "Команда не распознана!");
                showErrorPage(request, response);
            }
        } else {
            request.setAttribute(Constants.ERROR_MESSAGE_ATTRIBUTE, "Не задана команда!");
            showErrorPage(request, response);
        }
    }

    private static void showErrorPage(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String page = ResourceManager.getInstance().getProperty(Constants.PAGE_ERROR);
        RequestDispatcher dispatcher = request.getServletContext().getRequestDispatcher(page);
        dispatcher.forward(request, response);
    }
}
