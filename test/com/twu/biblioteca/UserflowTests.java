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

    public static final String BOOK_HITCHHIKER_S_GUIDE_TO_THE_GALAXY = "Hitchhiker's Guide to the Galaxy";
    public static final String BOOK_THE_HANDMAID_S_TALE = "The Handmaid's Tale";
    public static final String BOOK_THE_PRINCESS_BRIDE = "The Princess Bride";
    public static final String MOVIE_THE_STATION_AGENT = "The Station Agent";
    public static final String MOVIE_BRAVE = "Brave";

    @Override
    protected void initialiseDummyData() {
        biblioteca.addRentable(new Book(BOOK_HITCHHIKER_S_GUIDE_TO_THE_GALAXY, "Douglas Adams", "1979"));
        biblioteca.addRentable(new Book(BOOK_THE_PRINCESS_BRIDE, "William Goldman", "1973"));
        biblioteca.addRentable(new Movie(MOVIE_THE_STATION_AGENT, "2003", "Tom McCarthy"));
        biblioteca.addRentable(new Movie(MOVIE_BRAVE, "2012", "Mark Andrews"));
        // add item that's checked out
        Book handmaidsTale = new Book(BOOK_THE_HANDMAID_S_TALE, "Margaret Atwood", "1986");
        handmaidsTale.checkout();
        biblioteca.addRentable(handmaidsTale);
    }

    @Test
    public void userflow_viewing_movie_list() {
        systemInMock.provideLines(MENU_OPTION_LIST_BOOKS, MENU_OPTION_QUIT);
        biblioteca.start();
        checkForString(MOVIE_THE_STATION_AGENT);
        checkForString(MOVIE_BRAVE);
    }

    @Test
    public void userflow_show_menu_and_quit() throws Exception {
        systemInMock.provideLines(MENU_OPTION_QUIT);
        biblioteca.start();
        checkForString("Main Menu");
        checkForString("Print catalogue");
    }

    @Test
    public void userflow_viewing_book_list() throws Exception {
        systemInMock.provideLines(MENU_OPTION_LIST_BOOKS, MENU_OPTION_QUIT);
        biblioteca.start();
        checkForString(BOOK_HITCHHIKER_S_GUIDE_TO_THE_GALAXY);
        checkForString(BOOK_THE_PRINCESS_BRIDE);
    }

    @Test
    public void userflow_loop_menu_three_times() {
        systemInMock.provideLines(MENU_OPTION_LIST_BOOKS, MENU_OPTION_LIST_BOOKS, MENU_OPTION_LIST_BOOKS, MENU_OPTION_QUIT);
        biblioteca.start();

        assertEquals(stringCount("Main Menu"), 4);
    }

    @Test
    public void userflow_incorrect_menu_option() throws Exception {
        systemInMock.provideLines("bananas", MENU_OPTION_QUIT);
        biblioteca.start();
        checkForString("Select a valid option!");
    }

    @Test
    public void userflow_checkout_hitchhikers_guide() {
        systemInMock.provideLines(MENU_CHECKOUT_BOOK, BOOK_HITCHHIKER_S_GUIDE_TO_THE_GALAXY, MENU_OPTION_QUIT);
        biblioteca.start();

        systemOutRule.clearLog();
        biblioteca.printRentables();
        checkForMissingString(BOOK_HITCHHIKER_S_GUIDE_TO_THE_GALAXY);
    }

    @Test
    public void userflow_checkout_nonexistent_book() {
        systemInMock.provideLines(MENU_CHECKOUT_BOOK, "Book that doesn't exist...", MENU_OPTION_QUIT);
        biblioteca.start();

        checkForString("That item is not available.");
    }

    @Test
    public void userflow_return_book() {
        systemInMock.provideLines(MENU_RETURN_BOOK, BOOK_THE_HANDMAID_S_TALE, MENU_OPTION_QUIT);
        biblioteca.start();

        Book handmaidsTale = (Book) biblioteca.findRentable(BOOK_THE_HANDMAID_S_TALE);
        assertFalse(handmaidsTale.isCheckedOut());
    }
}
