package com.petko.commands;

import com.petko.constants.Constants;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public interface Command {
    String errorMessageAttribute = Constants.ERROR_MESSAGE_ATTRIBUTE;
    void execute(HttpServletRequest request, HttpServletResponse response);
    void setErrorMessage(HttpServletRequest request, String message);
}
