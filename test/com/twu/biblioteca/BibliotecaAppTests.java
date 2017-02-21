package com.twu.biblioteca;


import org.junit.*;
import org.junit.contrib.java.lang.system.ExpectedSystemExit;
import org.junit.contrib.java.lang.system.SystemOutRule;
import org.junit.contrib.java.lang.system.TextFromStandardInputStream;

import java.io.*;

import static org.junit.Assert.*;
import static org.junit.contrib.java.lang.system.TextFromStandardInputStream.emptyStandardInputStream;

public class BibliotecaAppTests {

    public static final String MENU_OPTION_LIST_BOOKS = "1";
    public static final String MENU_CHECKOUT_BOOK = "2";
    public static final String MENU_OPTION_QUIT = "3";
    public static final String HITCHHIKER_S_GUIDE_TO_THE_GALAXY = "Hitchhiker's Guide to the Galaxy";
    BibliotecaApp biblioteca;

    // Used for grabbing System.out.println output so it can be asserted
    @Rule
    public final SystemOutRule systemOutRule = new SystemOutRule().enableLog();

    // Used for emulating user input
    @Rule
    public final TextFromStandardInputStream systemInMock
            = emptyStandardInputStream();

    // Used for cecking if the system exited correctly
    @Rule
    public final ExpectedSystemExit exit = ExpectedSystemExit.none();

    @Before
    public void setUpStreams() throws IOException {
        biblioteca = new BibliotecaApp();
        systemOutRule.clearLog();
    }

    private void checkForWelcomeMessageText() {
        assertTrue(systemOutRule.getLog().contains("Welcome"));
        assertTrue(systemOutRule.getLog().contains("Biblioteca"));
    }

    private void checkForBookTitleText() {
        checkForBook("The Princess Bride");
        checkForBook(HITCHHIKER_S_GUIDE_TO_THE_GALAXY);
        checkForBook("The Princess Bride");
        checkForBook("The Sparrow");
        checkForBook("Ender's Game");
        checkForBook("The Moon is a Harsh Mistress");
        checkForBook("The Name of the Wind");
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
        while (index != -1){
            count++;
            input = input.substring(index + 1);
            index = input.indexOf(text);
        }
        return count;
    }

    private void checkForBook(String title){
        assertTrue("Couldn't find book in output: " + title, systemOutRule.getLog().contains(title));
    }

    private void checkForString(String text){
        assertTrue("Couldn't find text in output: " + text, systemOutRule.getLog().contains(text));
    }

    private void checkStringMissing(String text){
       assertFalse("Found text in output that shouldn't be there: " + text, systemOutRule.getLog().contains(text));
    }

    @Test
    public void check_welcome_message() {
        biblioteca.printWelcome();
        checkForWelcomeMessageText();
    }

    @Test
    public void check_books_titles_are_printed() {
        biblioteca.printBooks();
        checkForBookTitleText();
    }

    @Test
    public void check_books_have_author_and_year() {
        biblioteca.printBooks();
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

        biblioteca.printBooks();
        oldOutputLength = systemOutRule.getLog().split("\n").length;
        checkForString(HITCHHIKER_S_GUIDE_TO_THE_GALAXY);
        biblioteca.checkout(HITCHHIKER_S_GUIDE_TO_THE_GALAXY);

        systemOutRule.clearLog();
        biblioteca.printBooks();
        newOutputLength = systemOutRule.getLog().split("\n").length;
        checkStringMissing(HITCHHIKER_S_GUIDE_TO_THE_GALAXY);
        assertEquals(oldOutputLength - 1, newOutputLength);
    }

    @Test
    public void check_for_friendly_message_when_checking_out_book() {
        biblioteca.checkout(HITCHHIKER_S_GUIDE_TO_THE_GALAXY);
        checkForString("Thank you!");
        checkForString("Enjoy the book");
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
        checkForMainMenuText();
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
        biblioteca.printBooks();
        checkStringMissing(HITCHHIKER_S_GUIDE_TO_THE_GALAXY);
    }
}
