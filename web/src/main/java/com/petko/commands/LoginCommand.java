package com.petko.commands;

import com.petko.ResourceManager;
import com.petko.constants.Constants;
import com.petko.services.UserService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class LoginCommand extends AbstractCommand {
    public void execute(HttpServletRequest request, HttpServletResponse response) {
        HttpSession session = request.getSession();
        String login = request.getParameter("login");
        String password = request.getParameter("password");
//        UserService.getInstance().isLoginSuccess(login, password);  // new
        if (UserService.getInstance().isLoginSuccess(login, password)) {
            session.setAttribute("user", login);
            String page = ResourceManager.getInstance().getProperty(Constants.PAGE_MAIN);
            setForwardPage(request, page);
        } else {
            setErrorMessage(request, "Неверный логин или пароль!");
        }
    }
}
