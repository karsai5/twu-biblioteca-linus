package com.twu.biblioteca;

import java.util.regex.Pattern;

/**
 * Created by linus on 23/2/17.
 */
public class User {

    private final String username;
    private String password;
    private String name;
    private String email;

    private String phone;

    Pattern usernameFormat = Pattern.compile("^[0-9]{3}-[0-9]{4}$"); // patterm XXX-XXXX where X is a number

    public User(String username, String password) {
        this(username, password, "", "", "");
    }

    public User(String username, String password, String name, String email, String phone) {
        if (!usernameFormat.matcher(username).matches()) {
            throw new IllegalArgumentException("Username must be in format XXX-XXXX");
        }
        this.username = username;
        this.password = password;
        this.name = name;
        this.email = email;
        this.phone = phone;
    }

    public boolean checkPassword(String attemptedPassword) {
        return password.equals(attemptedPassword);
    }

    public String getUsername() {
        return username;
    }

    public Object getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public String getPhone() {
        return phone;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }
}
