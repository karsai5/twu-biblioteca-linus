package com.twu.biblioteca;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.Timeout;

import static junit.framework.TestCase.assertFalse;
import static org.junit.Assert.assertEquals;

/**
 * Created by linus on 23/2/17.
 */
public class UserflowTests extends BaseTest {

    public static final String MENU_OPTION_LIST_BOOKS = "1";
    public static final String MENU_CHECKOUT_BOOK = "2";
    public static final String MENU_RETURN_BOOK = "3";
    public static final String MENU_OPTION_QUIT = "4";

    public static Book HITCHHIKERS_GUIDE;
    public static Book HANDMAIDS_TALE;
    public static Book PRINCESS_BRIDE;

    public static Movie STATION_AGENT;
    public static Movie BRAVE;

    @Rule
    public Timeout globalTimeout = Timeout.millis(200); // 10 seconds max per method tested


    @Override
    protected void initialiseDummyData() {
        HITCHHIKERS_GUIDE = new Book("Hitchhiker's Guide to the Galaxy", "Douglas Adams", "1979");
        PRINCESS_BRIDE = new Book("The Princess Bride", "William Goldman", "1973");
        HANDMAIDS_TALE = new Book("The Handmaid's Tale", "Margaret Atwood", "1989");
        HANDMAIDS_TALE.checkout(JEAN);
        STATION_AGENT = new Movie("The Station Agent", "2003", "Tom McCarthy");
        BRAVE = new Movie("Brave", "2012", "Mark Andrews");

        biblioteca.addRentable(HITCHHIKERS_GUIDE);
        biblioteca.addRentable(HANDMAIDS_TALE);
        biblioteca.addRentable(PRINCESS_BRIDE);
        biblioteca.addRentable(STATION_AGENT);
        biblioteca.addRentable(BRAVE);

        // login so that tests don't rely on the user successfully logging in
        biblioteca.login(JEAN_USERNAME, JEAN_PASS);
    }

    @Test
    public void view_movies() {
        input.addCommand(MENU_OPTION_LIST_BOOKS);
        input.addCommand(MENU_OPTION_QUIT);
        biblioteca.startInteractiveShell();
        checkForString(STATION_AGENT.getTitle());
        checkForString(BRAVE.getTitle());
    }

    @Test
    public void show_menu_then_quit() throws Exception {
        input.addCommand(MENU_OPTION_QUIT);
        biblioteca.startInteractiveShell();
        checkForString("Main Menu");
        checkForString("Print catalogue");
    }

    @Test
    public void view_books() throws Exception {
        input.addCommand(MENU_OPTION_LIST_BOOKS);
        input.addCommand(MENU_OPTION_QUIT);
        biblioteca.startInteractiveShell();
        checkForString(HITCHHIKERS_GUIDE.getTitle());
        checkForString(PRINCESS_BRIDE.getTitle());
    }

    @Test
    public void loop_the_main_menu_three_times() {
        input.addCommand(MENU_OPTION_LIST_BOOKS);
        input.addCommand(MENU_OPTION_LIST_BOOKS);
        input.addCommand(MENU_OPTION_LIST_BOOKS);
        input.addCommand(MENU_OPTION_QUIT);
        biblioteca.startInteractiveShell();

        assertEquals(stringCount("Main Menu"), 4);
    }

    @Test
    public void select_an_incorrect_menu_option() throws Exception {
        input.addCommand("bananas");
        input.addCommand(MENU_OPTION_QUIT);
        biblioteca.startInteractiveShell();
        checkForString("Select a valid option!");
    }

    @Test
    public void checkout_hitchhikers_guide() {
        input.addCommand(MENU_CHECKOUT_BOOK);
        input.addCommand(HITCHHIKERS_GUIDE.getTitle());
        input.addCommand(MENU_OPTION_QUIT);
        biblioteca.startInteractiveShell();

        output.clear();
        biblioteca.printRentables();
        checkForMissingString(HITCHHIKERS_GUIDE.getTitle());
    }

    @Test
    public void checkout_nonexistent_book() {
        input.addCommand(MENU_CHECKOUT_BOOK);
        input.addCommand("Book that doesn't exist...");
        input.addCommand(MENU_OPTION_QUIT);
        biblioteca.startInteractiveShell();

        checkForString("That item is not available.");
    }

    @Test
    public void return_the_handmaids_tale() {
        input.addCommand(MENU_RETURN_BOOK);
        input.addCommand(HANDMAIDS_TALE.getTitle());
        input.addCommand(MENU_OPTION_QUIT);
        biblioteca.startInteractiveShell();

        assertFalse(HANDMAIDS_TALE.isCheckedOut());
    }

    @Test
    public void login_and_list_books() {
        input.addCommand(JEAN_USERNAME);
        input.addCommand(JEAN_PASS);
        input.addCommand(MENU_OPTION_LIST_BOOKS);
        input.addCommand(MENU_OPTION_QUIT);

        biblioteca.logOut();
        biblioteca.startInteractiveShell();

        checkForString("Main Menu");
    }

    @Test
    public void login_and_checkout_book() {
        assertEquals(null, HITCHHIKERS_GUIDE.getOwner());
        input.addCommand(JEAN_USERNAME);
        input.addCommand(JEAN_PASS);
        input.addCommand(MENU_CHECKOUT_BOOK);
        input.addCommand(HITCHHIKERS_GUIDE.getTitle());
        input.addCommand(MENU_OPTION_QUIT);

        biblioteca.logOut();
        biblioteca.startInteractiveShell();

        assertEquals(JEAN, HITCHHIKERS_GUIDE.getOwner());
    }
}
