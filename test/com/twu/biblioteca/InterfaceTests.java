package com.twu.biblioteca;

import org.junit.Test;

/**
 * Created by linus on 23/2/17.
 */
public class InterfaceTests extends BaseTest {

    private void checkForWelcomeMessageText() {
        checkForString("Welcome");
        checkForString("Biblioteca");
    }

    public void checkForMainMenuText() {
        checkForString("Main Menu");
        checkForString("Print catalogue");
    }

    @Test
    public void check_welcome_message_test() {
        biblioteca.printWelcome();
        checkForWelcomeMessageText();
    }

    @Test
    public void check_menu_text() throws Exception {
        biblioteca.printMenu();
        checkForMainMenuText();
    }

    @Override
    protected void initialiseDummyData() {
        // no data needed
    }
}
