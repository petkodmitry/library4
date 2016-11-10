package com.petko.commands;

public enum CommandType {
    LOGIN, REGISTER, SHOWUSERS, LOGOUT, UNKNOWN, SEARCHBOOK, ORDERTOREADINGROOM, ORDERTOHOME, MYORDERS,
    CANCELUSERORDER, MYBOOKS, PROLONGORDER;

    public static CommandType getCommandType(String commandName) {
        try {
            return CommandType.valueOf(commandName.toUpperCase());
        } catch (IllegalArgumentException e) {
            return UNKNOWN;
        }
    }

    public static Command getCommand(String cmd) {
        CommandType type = getCommandType(cmd);
        switch (type) {
            case LOGIN:
                return LoginCommand.getInstance();
            case REGISTER:
                return RegisterCommand.getInstance();
            case SHOWUSERS:
                return ShowUsersCommand.getInstance();
            case LOGOUT:
                return LogoutCommand.getInstance();
            case SEARCHBOOK:
                return SearchBookForUserCommand.getInstance();
            case ORDERTOREADINGROOM:
                return OrderToReadingRoomCommand.getInstance();
            case ORDERTOHOME:
                return OrderToHomeCommand.getInstance();
            case MYORDERS:
                return MyOrdersCommand.getInstance();
            case CANCELUSERORDER:
                return CancelUserOrderCommand.getInstance();
            case MYBOOKS:
                return MyBooksCommand.getInstance();
            case PROLONGORDER:
                return ProlongOrderCommand.getInstance();
            default:
                return UnknownCommand.getInstance();
        }
    }
}
