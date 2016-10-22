package com.petko.commands;

import com.petko.LoginCheck;
import com.petko.ResourceManager;
import com.petko.constants.Constants;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class LoginCommand extends AbstractCommand {
    public void execute(HttpServletRequest request, HttpServletResponse response) {
        HttpSession session = request.getSession();
        String login = request.getParameter("login");
        String password = request.getParameter("password");
        LoginCheck loginCheck = new LoginCheck();
        if (loginCheck.check(login, password)) {
            request.setAttribute("user", login);
            String page = ResourceManager.getInstance().getProperty(Constants.PAGE_MAIN);
            request.setAttribute("forward", page);
        } else {
            setErrorMessage(request, "Неверный логин или пароль!");
        }
    }
}
