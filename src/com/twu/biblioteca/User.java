package com.twu.biblioteca;

/**
 * Created by linus on 23/2/17.
 */
public class User {
    private final String username;
    private String password;

    public User(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public boolean checkPassword(String attemptedPassword) {
        return password.equals(attemptedPassword);
    }

    public String getUsername() {
        return username;
    }
}
