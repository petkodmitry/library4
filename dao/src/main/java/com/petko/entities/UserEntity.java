package com.petko.entities;

public class UserEntity extends Entity{
    private String firstName;
    private String lastName;
    private String login;
    private String password;
    private boolean isAdmin;
    private boolean isBlocked;

    @Override
    public String toString() {
        return String.format("User [firstName=%s, lastName=%s, login=%s, password=%s, isAdmin=%b, isBlocked=%b]",
                firstName, lastName, login, password, isAdmin, isBlocked);
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public boolean isAdmin() {
        return isAdmin;
    }

    public void setAdmin(boolean admin) {
        isAdmin = admin;
    }

    public boolean isBlocked() {
        return isBlocked;
    }

    public void setBlocked(boolean blocked) {
        isBlocked = blocked;
    }
}
