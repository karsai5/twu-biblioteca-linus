package com.twu.biblioteca;

import java.util.regex.Pattern;

/**
 * Created by linus on 23/2/17.
 */
public class User {
    private final String username;
    private String password;
    Pattern usernameFormat = Pattern.compile("^[0-9]{3}-[0-9]{4}$"); // patterm XXX-XXXX where X is a number

    public User(String username, String password) {
        if (!usernameFormat.matcher(username).matches()) {
            throw new IllegalArgumentException("Username must be in format XXX-XXXX");
        }
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
