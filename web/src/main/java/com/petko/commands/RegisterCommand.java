package com.petko.commands;

import com.petko.RequestHandler;
import com.petko.ResourceManager;
import com.petko.constants.Constants;
import com.petko.entities.UserEntity;
import com.petko.services.UserService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class RegisterCommand extends AbstractCommand {
    private static RegisterCommand instance;

    private RegisterCommand() {
    }

    public static synchronized RegisterCommand getInstance() {
        if (instance == null) {
            instance = new RegisterCommand();
        }
        return instance;
    }

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) {
        HttpSession session = request.getSession();
        // перенести в методы service
        String page = ResourceManager.getInstance().getProperty(Constants.PAGE_REGISTRATION);
        UserEntity regData;
        /**
         * creating attribute of the session: UserEntity regData
         */
        if (session.getAttribute("regData") == null) {
            regData = new UserEntity();
            session.setAttribute("regData", regData);
        }
        /**
         * reading data from session attribute regData
         */
        else {
            regData = (UserEntity) session.getAttribute("regData");
            regData.setFirstName(request.getParameter("newName"));
            regData.setLastName(request.getParameter("newLastName"));
            regData.setLogin(request.getParameter("newLogin"));
            regData.setPassword(request.getParameter("newPassword"));
            String repeatPassword = request.getParameter("repeatPassword");
            /**
             * if 'login' is entered
             */
            if (regData.getLogin() != null && !"".equals(regData.getLogin())) {
                /**
                 * check if asked login exists in database
                 */
                if (UserService.getInstance().isLoginExists(request, regData.getLogin())) {
                    request.setAttribute("unavailableMessage", "логин НЕдоступен!");
                } else {
                    request.setAttribute("unavailableMessage", "логин доступен");
                    /**
                     * if all data is entered
                     */
                    if (!"".equals(regData.getFirstName()) &&
                            !"".equals(regData.getLastName()) &&
                            !"".equals(regData.getLogin()) &&
                            !"".equals(regData.getPassword()) &&
                            !"".equals(repeatPassword)) {
                        if (UserService.getInstance().isAllPasswordRulesFollowed(regData.getPassword(), repeatPassword)) {
                            UserService.getInstance().addNewEntityToDataBase(request, regData.getFirstName(), regData.getLastName(),
                                    regData.getLogin(), regData.getPassword(), regData.isAdmin(), regData.isBlocked());
                            page = ResourceManager.getInstance().getProperty(Constants.PAGE_REGISTRATION_OK);
                        } else {
                            setErrorMessage(request, "Пароль должен содержать 8 символов");
                        }
                    }
                }
            }
        }
        setForwardPage(request, page);
    }
}
