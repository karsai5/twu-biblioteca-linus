package com.twu.biblioteca;

import org.junit.Test;

import static junit.framework.TestCase.assertFalse;
import static org.junit.Assert.assertEquals;

/**
 * Created by linus on 23/2/17.
 */
public class UserflowTests extends BaseTest {

    public static final String MENU_OPTION_LIST_BOOKS = "1";
    public static final String MENU_CHECKOUT_BOOK = "2";
    public static final String MENU_OPTION_QUIT = "4";
    public static final String MENU_RETURN_BOOK = "3";

    public static Book HITCHHIKERS_GUIDE;
    public static Book HANDMAIDS_TALE;
    public static Book PRINCESS_BRIDE;

    public static Movie STATION_AGENT;
    public static Movie BRAVE;

    public static User JEAN;

    @Override
    protected void initialiseDummyData() {
        JEAN = new User("222-4601", "password");
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
    }

    @Test
    public void view_movies() {
        systemInMock.provideLines(MENU_OPTION_LIST_BOOKS, MENU_OPTION_QUIT);
        biblioteca.start();
        checkForString(STATION_AGENT.getTitle());
        checkForString(BRAVE.getTitle());
    }

    @Test
    public void show_menu_then_quit() throws Exception {
        systemInMock.provideLines(MENU_OPTION_QUIT);
        biblioteca.start();
        checkForString("Main Menu");
        checkForString("Print catalogue");
    }

    @Test
    public void view_books() throws Exception {
        systemInMock.provideLines(MENU_OPTION_LIST_BOOKS, MENU_OPTION_QUIT);
        biblioteca.start();
        checkForString(HITCHHIKERS_GUIDE.getTitle());
        checkForString(PRINCESS_BRIDE.getTitle());
    }

    @Test
    public void loop_the_main_menu_three_times() {
        systemInMock.provideLines(MENU_OPTION_LIST_BOOKS, MENU_OPTION_LIST_BOOKS, MENU_OPTION_LIST_BOOKS, MENU_OPTION_QUIT);
        biblioteca.start();

        assertEquals(stringCount("Main Menu"), 4);
    }

    @Test
    public void select_an_incorrect_menu_option() throws Exception {
        systemInMock.provideLines("bananas", MENU_OPTION_QUIT);
        biblioteca.start();
        checkForString("Select a valid option!");
    }

    @Test
    public void checkout_hitchhikers_guide() {
        systemInMock.provideLines(MENU_CHECKOUT_BOOK, HITCHHIKERS_GUIDE.getTitle(), MENU_OPTION_QUIT);
        biblioteca.start();

        systemOutRule.clearLog();
        biblioteca.printRentables();
        checkForMissingString(HITCHHIKERS_GUIDE.getTitle());
    }

    @Test
    public void checkout_nonexistent_book() {
        systemInMock.provideLines(MENU_CHECKOUT_BOOK, "Book that doesn't exist...", MENU_OPTION_QUIT);
        biblioteca.start();

        checkForString("That item is not available.");
    }

    @Test
    public void return_the_handmaids_tale() {
        systemInMock.provideLines(MENU_RETURN_BOOK, HANDMAIDS_TALE.getTitle(), MENU_OPTION_QUIT);
        biblioteca.start();

        assertFalse(HANDMAIDS_TALE.isCheckedOut());
    }
}
