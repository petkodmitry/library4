package com.petko.commands;

import com.petko.ResourceManager;
import com.petko.constants.Constants;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class ShowUsersCommand extends AbstractCommand {
    public void execute(HttpServletRequest request, HttpServletResponse response) {
        ////////////////////////////////
        String page = ResourceManager.getInstance().getProperty(Constants.PAGE_MAIN);
        setForwardPage(request, page);
        ////////////////////////////////
    }
}
