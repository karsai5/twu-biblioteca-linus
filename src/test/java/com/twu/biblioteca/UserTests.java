package com.twu.biblioteca;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * Created by linus on 23/2/17.
 */
public class UserTests extends BaseTest {
    public Book HITCHHIKERS_GUIDE;

    @Override
    protected void initialiseDummyData() {
        HITCHHIKERS_GUIDE = new Book("Hitchhiker's Guide to the Galaxy", "Douglas Adams", "1979");
        biblioteca.addRentable(HITCHHIKERS_GUIDE);
    }

    private void loginAsJean() {
        biblioteca.login(JEAN_USERNAME, JEAN_PASS);
    }

    @Test
    public void login_as_jean() {
        loginAsJean();
        assertEquals(biblioteca.getCurrentUser(), JEAN);
    }

    @Test
    public void login_incorrectly() {
        biblioteca.login("fakeuser", "fakepassword");
        assertEquals(biblioteca.getCurrentUser(), null);
    }

    @Test
    public void show_error_message_for_incorrect_username_and_password() {
        biblioteca.login("fakeuser", "fakepassword");
        checkForString("Username and/or password incorrect.");
    }

    @Test
    public void checkout_hitchhikers_guide_as_jean() {
        loginAsJean();
        biblioteca.checkout(HITCHHIKERS_GUIDE.getTitle());
        assertEquals(JEAN, HITCHHIKERS_GUIDE.getOwner());
    }

    @Test
    public void returning_book_should_clear_owner() {
        HITCHHIKERS_GUIDE.setOwner(JEAN);
        assertEquals(JEAN, HITCHHIKERS_GUIDE.getOwner());
        biblioteca.checkin(HITCHHIKERS_GUIDE.getTitle());
        assertEquals(null, HITCHHIKERS_GUIDE.getOwner());
    }

    @Test(expected = IllegalArgumentException.class)
    public void throw_exception_when_making_user_with_incorrect_username_formatting() {
        new User("incorreclyFormattedUsername", "password");
    }

    @Test(expected = IllegalArgumentException.class)
    public void throw_exception_when_trying_to_checkout_with_null_user() {
        HITCHHIKERS_GUIDE.checkout(null);
    }

    @Test
    public void print_jeans_user_information() {
        loginAsJean();
        biblioteca.printCurrentUsersDetails();
        checkForString(JEAN_NAME);
        checkForString(JEAN_EMAIL);
        checkForString(JEAN_PHONE);
    }
}