package com.twu.biblioteca;


import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.contrib.java.lang.system.SystemOutRule;
import org.junit.contrib.java.lang.system.TextFromStandardInputStream;

import java.io.IOException;

import static org.junit.Assert.*;
import static org.junit.contrib.java.lang.system.TextFromStandardInputStream.emptyStandardInputStream;

public class BibliotecaAppTests {

    public static final String MENU_OPTION_LIST_BOOKS = "1";
    public static final String MENU_CHECKOUT_BOOK = "2";
    public static final String MENU_OPTION_QUIT = "4";
    private static final String MENU_RETURN_BOOK = "3";

    public static final String HITCHHIKER_S_GUIDE_TO_THE_GALAXY = "Hitchhiker's Guide to the Galaxy";
    public static final String THE_HANDMAID_S_TALE = "The Handmaid's Tale";
    public static final String THE_PRINCESS_BRIDE = "The Princess Bride";
    public static final String THE_SPARROW = "The Sparrow";
    public static final String ENDER_S_GAME = "Ender's Game";
    public static final String THE_MOON_IS_A_HARSH_MISTRESS = "The Moon is a Harsh Mistress";
    public static final String THE_NAME_OF_THE_WIND = "The Name of the Wind";
    public static final String THE_STATION_AGENT = "The Station Agent";
    public static final String BRAVE = "Brave";
    BibliotecaApp biblioteca;

    // Used for grabbing System.out.println output so it can be asserted
    @Rule
    public final SystemOutRule systemOutRule = new SystemOutRule().enableLog();

    // Used for emulating user input
    @Rule
    public final TextFromStandardInputStream systemInMock
            = emptyStandardInputStream();

    @Before
    public void setUpStreams() throws IOException {
        biblioteca = new BibliotecaApp();
        // clear book collection and initialise test books.
        // although these are identical at time of writing, I didn't
        // want the tests to rely on data initialised in the app class.
        biblioteca.clearBooks();
        initialiseBooks();
        systemOutRule.clearLog();
    }

    private void initialiseBooks() {
        // add books
        biblioteca.addRentable(new Book(HITCHHIKER_S_GUIDE_TO_THE_GALAXY, "Douglas Adams", "1979"));
        biblioteca.addRentable(new Book(THE_PRINCESS_BRIDE, "William Goldman", "1973"));
        biblioteca.addRentable(new Book(THE_SPARROW, "Mary Doria Russell", "1996"));
        biblioteca.addRentable(new Book(ENDER_S_GAME, "Orson Scott Card", "1985"));
        biblioteca.addRentable(new Book(THE_MOON_IS_A_HARSH_MISTRESS, "Robert A. Heinlein", "1966"));
        biblioteca.addRentable(new Book(THE_NAME_OF_THE_WIND, "Patrick Rothfuss", "2007"));

        // create book that's checked out
        Book handmaidsTale = new Book(THE_HANDMAID_S_TALE, "Margaret Atwood", "1986");
        handmaidsTale.checkout();
        biblioteca.addRentable(handmaidsTale);

        // add movies
        biblioteca.addRentable(new Movie(THE_STATION_AGENT, "2003", "Tom McCarthy"));
        biblioteca.addRentable(new Movie(BRAVE, "2012", "Mark Andrews"));
    }

    private void checkForWelcomeMessageText() {
        assertTrue(systemOutRule.getLog().contains("Welcome"));
        assertTrue(systemOutRule.getLog().contains("Biblioteca"));
    }

    private void checkForBookTitleText() {
        checkForBook(HITCHHIKER_S_GUIDE_TO_THE_GALAXY);
        checkForBook(THE_PRINCESS_BRIDE);
        checkForBook(THE_SPARROW);
        checkForBook(ENDER_S_GAME);
        checkForBook(THE_MOON_IS_A_HARSH_MISTRESS);
        checkForBook(THE_NAME_OF_THE_WIND);
    }

    private void checkForAuthorAndYearText() {
        checkForString("Patrick Rothfuss");
        checkForString("2007");
        checkForString("Douglas Adams");
        checkForString("1979");
    }

    private void checkForMainMenuText() {
        checkForString("Main Menu");
        checkForString("List Books");
    }

    private int stringCount(String text) {
        String input = systemOutRule.getLog();
        int index = input.indexOf(text);
        int count = 0;
        while (index != -1) {
            count++;
            input = input.substring(index + 1);
            index = input.indexOf(text);
        }
        return count;
    }

    private void checkForBook(String title) {
        assertTrue("Couldn't find book in output: " + title, systemOutRule.getLog().contains(title));
    }

    private void checkForString(String text) {
        assertTrue("Couldn't find text in output: " + text, systemOutRule.getLog().contains(text));
    }

    private void checkForMissingString(String text) {
        assertFalse("Found text in output that shouldn't be there: " + text, systemOutRule.getLog().contains(text));
    }

    @Test
    public void check_welcome_message() {
        biblioteca.printWelcome();
        checkForWelcomeMessageText();
    }

    @Test
    public void check_books_titles_are_printed() {
        biblioteca.printCatalogue();
        checkForBookTitleText();
    }

    @Test
    public void check_books_have_author_and_year() {
        biblioteca.printCatalogue();
        checkForAuthorAndYearText();
    }

    @Test
    public void check_menu_prints() throws Exception {
        biblioteca.printMenu();
        checkForMainMenuText();
    }

    @Test
    public void checkout_hitchhikers_guide() {
        int oldOutputLength;
        int newOutputLength;

        biblioteca.printCatalogue();
        oldOutputLength = systemOutRule.getLog().split("\n").length;
        checkForString(HITCHHIKER_S_GUIDE_TO_THE_GALAXY);
        biblioteca.checkout(HITCHHIKER_S_GUIDE_TO_THE_GALAXY);

        systemOutRule.clearLog();
        biblioteca.printCatalogue();
        newOutputLength = systemOutRule.getLog().split("\n").length;
        checkForMissingString(HITCHHIKER_S_GUIDE_TO_THE_GALAXY);
        assertEquals(oldOutputLength - 1, newOutputLength);
    }

    @Test
    public void check_for_friendly_message_when_checking_out_book() {
        biblioteca.checkout(HITCHHIKER_S_GUIDE_TO_THE_GALAXY);
        checkForString("Thank you!");
        checkForString("Enjoy the book");
    }

    @Test
    public void check_for_warning_when_checking_out_nonexistent_book() {
        biblioteca.checkout("Book that doesn't exist...");
        checkForString("That book is not available.");
    }

    @Test
    public void return_book_handmaids_tale() {
        checkForMissingString(THE_HANDMAID_S_TALE);

        // return book
        systemOutRule.clearLog();
        biblioteca.checkin(THE_HANDMAID_S_TALE);
        biblioteca.printCatalogue();
        checkForString(THE_HANDMAID_S_TALE);
    }

    @Test
    public void print_number_of_books_hidden() {
        biblioteca.printCatalogue();
        checkForString("Hiding 1 book(s) because they're checked out.");
    }

    @Test
    public void check_for_message_when_returning_handmaids_tale() {
        biblioteca.checkin(THE_HANDMAID_S_TALE);
        checkForString("Thank you for returning the book.");
    }

    @Test
    public void check_for_warning_when_returning_nonexistent_book() {
        biblioteca.checkin("This book doesn't exits...");
        checkForString("That is not a valid book to return.");
    }

    @Test
    public void check_movies_are_listed_correctly() {
        biblioteca.printMovies();
        checkForMovieTitleText();
    }

    private void checkForMovieTitleText() {
        checkForString(THE_STATION_AGENT);
        checkForString(BRAVE);
    }

    @Test
    public void checkout_the_station_agent() {
        biblioteca.printMovies();
        checkForString(THE_STATION_AGENT);
        biblioteca.checkin(THE_STATION_AGENT);

        systemOutRule.clearLog();
        checkForMissingString(THE_STATION_AGENT);
    }

    @Test(timeout = 200)
    public void userflow_viewing_movie_list() {
        systemInMock.provideLines(MENU_OPTION_LIST_BOOKS, MENU_OPTION_QUIT);
        biblioteca.start();
        checkForMovieTitleText();
    }

    @Test
    public void userflow_show_menu_and_quit() throws Exception {
        systemInMock.provideLines(MENU_OPTION_QUIT);
        biblioteca.start();
        checkForMainMenuText();
    }

    @Test
    public void userflow_viewing_book_list() throws Exception {
        systemInMock.provideLines(MENU_OPTION_LIST_BOOKS, MENU_OPTION_QUIT);
        biblioteca.start();
        checkForBookTitleText();
    }

    @Test
    public void userflow_loop_menu_three_times() {
        systemInMock.provideLines(MENU_OPTION_LIST_BOOKS, MENU_OPTION_LIST_BOOKS, MENU_OPTION_LIST_BOOKS, MENU_OPTION_QUIT);
        biblioteca.start();

        assertEquals(stringCount(HITCHHIKER_S_GUIDE_TO_THE_GALAXY), 3);
    }

    @Test
    public void userflow_incorrect_menu_option() throws Exception {
        systemInMock.provideLines("bananas", MENU_OPTION_QUIT);
        biblioteca.start();
        checkForString("Select a valid option!");
    }

    @Test
    public void userflow_checkout_hitchhikers_guide() {
        systemInMock.provideLines(MENU_CHECKOUT_BOOK, HITCHHIKER_S_GUIDE_TO_THE_GALAXY, MENU_OPTION_QUIT);
        biblioteca.start();

        systemOutRule.clearLog();
        biblioteca.printCatalogue();
        checkForMissingString(HITCHHIKER_S_GUIDE_TO_THE_GALAXY);
    }

    @Test
    public void userflow_checkout_nonexistent_book() {
        systemInMock.provideLines(MENU_CHECKOUT_BOOK, "Book that doesn't exist...", MENU_OPTION_QUIT);
        biblioteca.start();

        checkForString("That book is not available.");
    }

    @Test
    public void userflow_return_book() {
        systemInMock.provideLines(MENU_RETURN_BOOK, THE_HANDMAID_S_TALE, MENU_OPTION_QUIT);
        biblioteca.start();

        Book handmaidsTale = (Book) biblioteca.findRentable(THE_HANDMAID_S_TALE);
        assertFalse(handmaidsTale.isCheckedOut());
    }

}
