package com.petko.commands;

public enum CommandType {
    LOGIN, REGISTER, SHOWUSERS, LOGOUT, UNKNOWN;

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
            default:
                return UnknownCommand.getInstance();
        }
    }
}
