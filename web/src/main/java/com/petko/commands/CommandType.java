package com.petko.commands;

public enum CommandType {
    LOGIN, SHOWUSERS, LOGOUT;

    public static CommandType getCommandType(String commandName) {
        try {
            return CommandType.valueOf(commandName.toUpperCase());
        } catch (IllegalArgumentException e) {
            return null;
        }
    }

    public static Command getCommand(String cmd) {
        CommandType type = getCommandType(cmd);
        if (type == null) return null;
        switch (type) {
            case LOGIN:
                return new LoginCommand();
            case SHOWUSERS:
                return new ShowUsersCommand();
            case LOGOUT:
                return new LogoutCommand();
            default:
                return null;
        }
    }
}
