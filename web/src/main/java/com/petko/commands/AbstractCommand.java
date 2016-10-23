package com.petko.commands;

import javax.servlet.http.HttpServletRequest;

public abstract class AbstractCommand implements Command{
    public void setErrorMessage(HttpServletRequest request, String message) {
        request.setAttribute(errorMessageAttribute, message);
    }

    public void setForwardPage(HttpServletRequest request, String page) {
        request.setAttribute(forwardPageAttribute, page);
    }
}
