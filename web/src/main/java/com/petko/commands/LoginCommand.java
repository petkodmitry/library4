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
        if (session.getAttribute("user") != null) {
            login = (String) session.getAttribute("user");
            redirectToMainPage(request, login);
        } else if (UserService.getInstance().isLoginSuccess(request, login, password)) {
            session.setAttribute("user", login);
            redirectToMainPage(request, login);
        } else {
            if ((request.getAttribute(Constants.ERROR_MESSAGE_ATTRIBUTE)) == null && login != null) setErrorMessage(request, "Неверный логин или пароль!");
            redirectToLoginPage(request);
        }
    }

    private void redirectToMainPage(HttpServletRequest request, String login) {
        String page;
        if (UserService.getInstance().isAdminUser(request, login)) {
            page = ResourceManager.getInstance().getProperty(Constants.PAGE_MAIN_ADMIN);
        } else {
            page = ResourceManager.getInstance().getProperty(Constants.PAGE_MAIN);
        }
        setForwardPage(request, page);
    }

    private void redirectToLoginPage(HttpServletRequest request) {
        String page = ResourceManager.getInstance().getProperty(Constants.PAGE_INDEX);
        setForwardPage(request, page);
    }
}
