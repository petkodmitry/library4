package com.petko.commands;

import com.petko.ResourceManager;
import com.petko.constants.Constants;
import com.petko.entities.UserEntity;
import com.petko.services.UserService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.Set;

public class ShowUsersCommand extends AbstractCommand {
    public void execute(HttpServletRequest request, HttpServletResponse response) {
        HttpSession session = request.getSession();
        String login = (String) session.getAttribute("user");
        // если админ, то выполняем команду
        if (UserService.getInstance().isAdminUser(request, login)) {
            Set<UserEntity> userSet = UserService.getInstance().getAll(request);
            request.setAttribute(/*ResourceManager.getInstance().getProperty(*/Constants.USER_SET/*)*/, userSet);
            String page = ResourceManager.getInstance().getProperty(Constants.PAGE_SHOW_USERS);
            setForwardPage(request, page);
        // если не админ, сообщаем о невозможности выполнения команды
        } else if ((request.getAttribute(Constants.ERROR_MESSAGE_ATTRIBUTE)) == null) {
            setErrorMessage(request, "У Вас нет прав для выполнения данной команды");
            redirectToMainPage(request, login);
        }
    }
}
