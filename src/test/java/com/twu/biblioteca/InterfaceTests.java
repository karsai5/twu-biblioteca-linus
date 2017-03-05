package com.twu.biblioteca;

import org.junit.Test;

/**
 * Created by linus on 23/2/17.
 */
public class InterfaceTests extends BaseTest {

    @Test
    public void check_welcome_message_test() {
        biblioteca.printWelcome();
        checkForString("Welcome");
        checkForString("Biblioteca");
    }

    @Test
    public void check_menu_text() throws Exception {
        biblioteca.printMenu();
        checkForString("Main Menu");
        checkForString("Print catalogue");
    }

    @Override
    protected void initialiseDummyData() {
        // no data needed
    }
}
