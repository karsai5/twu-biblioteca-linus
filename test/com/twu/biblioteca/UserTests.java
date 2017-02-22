package com.twu.biblioteca;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * Created by linus on 23/2/17.
 */
public class UserTests extends BaseTest {
    public User JEAN;
    public String JEAN_PASSWORD = "password";

    @Override

    protected void initialiseDummyData() {
        JEAN = new User("222-4601", JEAN_PASSWORD);
        biblioteca.addUser(JEAN);
    }

    @Test
    public void login_as_jean() {
        biblioteca.login(JEAN.getUsername(), JEAN_PASSWORD);
        assertEquals(biblioteca.getCurrentUser(), JEAN);
    }
}
