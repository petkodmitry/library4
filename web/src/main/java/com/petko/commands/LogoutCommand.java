package com.petko.commands;

import com.petko.ResourceManager;
import com.petko.constants.Constants;
import com.petko.services.UserService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class LogoutCommand extends AbstractCommand {
    public void execute(HttpServletRequest request, HttpServletResponse response) {
        HttpSession session = request.getSession();
        String login = (String) session.getAttribute("user");
        UserService.getInstance().logOut(request, login);
        String page = ResourceManager.getInstance().getProperty(Constants.PAGE_INDEX);
        setForwardPage(request, page);
    }
}
