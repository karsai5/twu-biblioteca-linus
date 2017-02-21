package com.twu.biblioteca;


import org.junit.*;
import org.junit.contrib.java.lang.system.ExpectedSystemExit;
import org.junit.contrib.java.lang.system.SystemOutRule;
import org.junit.contrib.java.lang.system.TextFromStandardInputStream;

import java.io.*;

import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertEquals;
import static org.junit.contrib.java.lang.system.TextFromStandardInputStream.emptyStandardInputStream;

public class BibliotecaAppTests {

    public static final String MENU_OPTION_LIST_BOOKS = "1";
    public static final String MENU_OPTION_QUIT = "2";
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
        checkForBook("Hitchhiker's Guide to the Galaxy");
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

    private void checkForBook(String title){
        assertTrue("Couldn't find book in output: " + title, systemOutRule.getLog().contains(title));
    }

    private void checkForString(String text){
        assertTrue("Couldn't find text in output: " + text, systemOutRule.getLog().contains(text));
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
    public void check_userflow_show_menu_and_quit() throws Exception {
        systemInMock.provideLines(MENU_OPTION_QUIT);
        biblioteca.start();
        checkForMainMenuText();
    }

    @Test
    public void check_userflow_viewing_book_list() throws Exception {
        systemInMock.provideLines(MENU_OPTION_LIST_BOOKS, MENU_OPTION_QUIT);
        biblioteca.start();
        checkForMainMenuText();
        checkForBookTitleText();
    }

    @Test
    public void incorrect_menu_option() throws Exception {
        systemInMock.provideLines("bananas", MENU_OPTION_QUIT);
        biblioteca.start();
        checkForString("Select a valid option!");
    }
}
