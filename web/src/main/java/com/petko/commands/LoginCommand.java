package com.petko.commands;

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
            if ((request.getAttribute(Constants.ERROR_MESSAGE_ATTRIBUTE)) == null && login != null) {
                setErrorMessage(request, "Неверный логин или пароль!");
            }
            redirectToLoginPage(request);
        }
    }
}
